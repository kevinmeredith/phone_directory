package net.phone.repository

import net.phone.model.Person
import net.phone.common.Common.PersonError
import scalaz._
import scalaz.effect.IO

object PostgresRepository extends PersonRepository {
	override def create(person: Person): IO[\/[NonEmptyList[PersonError], Person]] =  IO {
		???
	}
}