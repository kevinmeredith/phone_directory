package net.phone.repository

import net.phone.model.Person
import net.phone.common.Common.PersonError
import scalaz._

object PostgresRepository extends PersonRepository {
	override def create(person: Person): \/[NonEmptyList[PersonError], Person] = {
		p <- createIO(person) match {
			
		}
	}


	private def createIO(person: Person): IO[]
}