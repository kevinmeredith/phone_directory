package net.phone.model

import net.phone.model.Person
import net.phone.common.Common._
import org.scalacheck.{Properties, Gen, Prop}
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import scalaz._

// TODO: Check on the exact \/ value, rather than isLeft or isRight
object PersonSpecification extends Properties("Person") {

  val zeroOrGreater: Gen[Int]    = Gen.choose(0,Int.MaxValue)
  val negative: Gen[Int]         = Gen.choose(Int.MinValue, -1)
  val nonEmpty: Gen[String]      = arbitrary[String].filter(_.nonEmpty)
  val maleFemale: Gen[String] 	 = Gen.oneOf("male", "MaLE", "maLE", "female", "FEMALE", "fEmALe")
  val notMaleFemale: Gen[String] = arbitrary[String].filter(s => s.toUpperCase != "MALE" && s.toUpperCase != "FEMALE")

  private def createPersonByAge(age: Int): NonEmptyList[PersonError] \/ Person = 
  	Person.create(None, "Bubba Gump", age, Person.Male)

  private def createPersonByName(name: String): NonEmptyList[PersonError] \/ Person = 
  	Person.create(None, name, 55, Person.Male)  	

  private def readPersonByGender(gender: String): NonEmptyList[PersonError] \/ Person = 
  	Person.read(1L, "Foo Bar", 55, gender)  	  	

  val validPersonAges: Prop = forAll(zeroOrGreater) { 
  	x => createPersonByAge(x).isRight == true
  }

  val invalidPersonAges: Prop = forAll(negative) { 
  	x => createPersonByAge(x).isLeft == true
  }

  val validName: Prop = forAll(nonEmpty) { 
  	x => createPersonByName(x).isRight == true
  } 

  val validGender: Prop = forAll(maleFemale) { 
  	x => readPersonByGender(x).isRight == true
  } 

  val invalidGender: Prop = forAll(notMaleFemale) {
	x => readPersonByGender(x).isLeft == true
  }

} 
