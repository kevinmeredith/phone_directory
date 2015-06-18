package net.phone.common

object Common {
	
	sealed trait PersonError
	case class DatabaseError(e: Exception) extends PersonError

	sealed trait InvalidPersonError extends PersonError
	case object InvalidName extends InvalidPersonError
	case object InvalidAge extends InvalidPersonError
}