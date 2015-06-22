package net.phone.model

import scalaz._
import Scalaz._
import net.phone.common.Common._

object Person {
    def create(id: Option[Long], name: String, age: Int, gender: Gender): NonEmptyList[PersonError] \/ Person =
		(validateName(name) |@| validateAge(age)) {(n, a) => Person(id, n, a, gender)}.disjunction

	private def validateName(name: String): Validation[NonEmptyList[PersonError], String] = 
		if(name.nonEmpty) name.successNel else InvalidName.failureNel

	private def validateAge(age: Int): Validation[NonEmptyList[PersonError], Int] = 
		if(age >= 0) age.successNel else InvalidAge.failureNel

	private def validateGender(gender: String): Validation[NonEmptyList[PersonError], Gender] = gender.toUpperCase match {
		case "MALE"   => Male.successNel
		case "FEMALE" => Female.successNel
		case _ 		  => InvalidGender.failureNel
	}

	sealed trait Gender 
	case object Male extends Gender 
	case object Female extends Gender 

	// credit: http://stackoverflow.com/a/30946172/409976
	implicit val GenderShows: Show[Gender] = Show.shows {
	  case Male   => "male"
	  case Female => "female"
	}

	def read(id: Long, name: String, age: Int, gender: String): NonEmptyList[PersonError] \/ Person =
		(validateName(name) |@| validateAge(age) |@| validateGender(gender)) {(n, a, g) => Person(Some(id), n, a, g)}.disjunction	
}

case class Person private(id: Option[Long], name: String, age: Int, gender: Person.Gender)