package net.phone.repository

import net.phone.model.Person
import net.phone.model.Person._
import net.phone.common.Common.{PersonError, DatabaseError}
import scalaz._
import Scalaz._
import scalaz.effect.IO
import doobie.imports._

object PostgresRepository extends PersonRepository {

	val xa = DriverManagerTransactor[IO](
	  "org.postgresql.Driver", "jdbc:postgresql:person", "postgres", "postgres"
	)

	override def create(person: Person): IO[NonEmptyList[PersonError] \/ Person] = {
		val createPerson: IO[\/[PersonError, NonEmptyList[PersonError] \/ Person]] = 
			safeInsert(person.name, person.age, person.gender).transact(xa) 
		createPerson >>= { x => f[PersonError, Person](x).point[IO] }
	}		


	override def get(id: Long): IO[NonEmptyList[PersonError] \/ Option[Person]] = {
		val personLookup: IO[\/[PersonError, NonEmptyList[PersonError] \/ Option[Person]]] = 
			safeGet(id).transact(xa)
		personLookup.map(f[PersonError, Option[Person]](_))
	}	

	private def unsafeGet(id: Long): ConnectionIO[NonEmptyList[PersonError] \/ Option[Person]] = { 
		val result: ConnectionIO[List[(Long, String, Int, String)]] = 
			sql"select id, name, age, gender from person where id = $id".query[(Long, String, Int, String)].process.take(1).list
		result.map{ _ match { 
				case Nil    => \/-(None)
				case p :: _ => Person.read(p._1, p._2, p._3, p._4) >>= {a => \/-(Some(a)) }
			}	
		}
	}

	private def safeGet(id: Long): ConnectionIO[\/[PersonError, NonEmptyList[PersonError] \/ Option[Person]]] = 
		unsafeGet(id).attemptSomeSqlState {
			case _ => DatabaseError
		}

	// Helper function to return a `NonEmptyList[PersonError] \/ Person` due to the
	// `\/[DatabaseError, NonEmptyList[PersonError] \/ Person]`, a result of possible database errors when querying.
	private def f[A, B](e: \/[A, NonEmptyList[A] \/ B]): NonEmptyList[A] \/ B = e match {
		case -\/(l) => -\/(NonEmptyList(l))
		case \/-(r) => r
	}

	private def safeInsert(name: String, age: Int, gender: Gender): ConnectionIO[\/[PersonError, NonEmptyList[PersonError] \/ Person]] =
		insert1(name, age, gender).attemptSomeSqlState {
			case _ => DatabaseError
		}

	// credit: http://stackoverflow.com/questions/30946659/jdbc-insert-with-postgres-enum
	private def insert1(name: String, age: Int, gender: Gender): ConnectionIO[NonEmptyList[PersonError] \/ Person] = { 
		val sgender: String = implicitly[Show[Gender]].shows(gender)
		for {
			_	<- sql"insert into person (name, age, gender) values ($name, $age, CAST($sgender AS sex))".update.run
			res <- sql"select id, name, age, gender from person where name = $name".query[(Long, String, Int, String)].unique
		} yield Person.read(res._1, res._2, res._3, res._4)    
	}
}

