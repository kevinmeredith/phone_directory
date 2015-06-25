package net.phone.service

import scalaz._
import Scalaz._
import net.phone.model.Person
import net.phone.common.Common.PersonError
import net.phone.repository.PersonRepository
import scalaz.effect.IO

trait PersonService {
	// TODO: consider `http://stackoverflow.com/questions/30928454/for-comprehension-example-with-and-io`
	def create(repository: PersonRepository, person: Person): IO[NonEmptyList[PersonError] \/ Person] = { 
		Person.create(person.id, person.name, person.age, person.gender).pure[IO] >>=
			(_ match {
				case -\/(errors) => errors.left.pure[IO]
				case \/-(p)      => repository.create(p)
			})
	}
}