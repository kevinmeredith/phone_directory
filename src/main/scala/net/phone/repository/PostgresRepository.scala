package net.phone.repository

import net.phone.model.Person
import net.phone.model.Person._
import net.phone.common.Common.PersonError
import scalaz._
import scalaz.effect.IO
import doobie.imports._

object PostgresRepository extends PersonRepository {

	val xa = DriverManagerTransactor[IO](
	  "org.postgresql.Driver", "jdbc:postgresql:person", "postgres", "postgres"
	)

	override def create(person: Person): IO[NonEmptyList[PersonError] \/ Person] =  IO {
		// try to create
		// return ... \/ ...
		???
	}

	def insert1(name: String, age: Int, gender: Gender) =
  		sql"insert into person (name, age, gender) values ($name, $age, ${implicitly[Show[Gender]].shows(gender)})".update.run.transact(xa)
}