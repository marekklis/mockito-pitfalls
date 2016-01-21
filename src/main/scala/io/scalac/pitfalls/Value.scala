package io.scalac.pitfalls

import io.scalac.pitfalls.Value.{LongValue, StringValue}

object Value {

  case class StringValue(val text: String) extends AnyVal

  case class LongValue(val value: Long) extends AnyVal

}

trait ValueService {

  def doItString(first: StringValue, second: StringValue): String

  def doItLong(first: LongValue, second: LongValue): Long
}