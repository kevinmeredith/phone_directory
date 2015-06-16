package net.phone.model

import scalaz._
import Scalaz._

object Person {
	
    def makePerson(name: String, age: Int, gender: Gender): \/[NonEmptyList[InvalidPersonError], Person] =
		(validateName(name) |@| validateAge(age)) {(n, a) => Person(n, a, gender)}.disjunction

	private def validateName(name: String): Validation[NonEmptyList[InvalidPersonError], String] = 
		if(name.nonEmpty) name.successNel else InvalidName.failureNel

	private def validateAge(age: Int): Validation[NonEmptyList[InvalidPersonError], Int] = 
		if(age >= 0) age.successNel else InvalidAge.failureNel

	sealed trait Gender
	case object Male extends Gender
	case object Female extends Gender

	sealed trait InvalidPersonError
	case object InvalidName extends InvalidPersonError
	case object InvalidAge extends InvalidPersonError
}

case class Person(name: String, age: Int, gender: Person.Gender)
