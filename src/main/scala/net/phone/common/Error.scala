package net.phone.common

object Common {
	
	sealed trait PersonError
	case object DatabaseError extends PersonError // TODO: add more information

	sealed trait InvalidPersonError extends PersonError
	case object InvalidName extends InvalidPersonError
	case object InvalidAge extends InvalidPersonError
	case object InvalidGender extends PersonError
}