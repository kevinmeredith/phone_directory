package net.phone.service

import scalaz._
import Scalaz._
import net.phone.model.Person
import net.phone.common.Common.PersonError
import net.phone.repository.PersonRepository

trait PersonService {
	def create(repository: PersonRepository, person: Person): \/[NonEmptyList[PersonError], Person] =
		Person.create(person.id, person.name, person.age, person.gender) >>= (p => repository.create(p))
}