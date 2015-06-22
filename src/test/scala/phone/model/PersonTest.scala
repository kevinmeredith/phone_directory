package net.phone.model

import org.scalatest._
import org.scalatest.prop.Checkers._

class PersonTest extends FlatSpec {

	"Person#create with an age between 0 and Int.MaxValue" should "be valid" in {
		check(PersonSpecification.validPersonAges)
	}

	"Person#create with an age between Int.MinValue and -1" should "be invalid" in {
		check(PersonSpecification.invalidPersonAges)
	}

	"Person#create with a non-empty name" should "succeed" in {
		check(PersonSpecification.validName)
	}

	"Person#create with an empty name" should "be invalid" in {
		check(Person.create(None, "", 55, Person.Male).isLeft == true)
	}

	"Person#read with a valid gender" should "be valid" in {
		check(PersonSpecification.validGender)
	}

	"Person#read with an invalid gender" should "be invalid" in {
		check(PersonSpecification.invalidGender)
	}	

}