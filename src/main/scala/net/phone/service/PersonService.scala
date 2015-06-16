package net.phone.service

import scalaz._
import net.phone.model.Person
import net.phone.model.Person._

trait PersonService {
	def create(name: String, age: Int, gender: Person.Gender): \/[NonEmptyList[InvalidPersonError], Person]
	def update(person: Person, name: String, age: Int, gender: Person.Gender): \/[NonEmptyList[InvalidPersonError], Person]
}