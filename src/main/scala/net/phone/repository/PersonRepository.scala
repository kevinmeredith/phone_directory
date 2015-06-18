package net.phone.repository

import net.phone.model.Person
import net.phone.common.Common.PersonError
import scalaz._

trait PersonRepository {
	def create(person: Person): \/[NonEmptyList[PersonError], Person]
}