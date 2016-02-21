package pitfalls.value

import pitfalls.value.Value.{LongValue, StringValue}

object Value {

  case class StringValue(val text: String) extends AnyVal

  case class LongValue(val value: Long) extends AnyVal

}

trait ValueService {

  def doItString(first: StringValue, second: StringValue): String

  def doItLong(first: LongValue, second: LongValue): Long

  def returnStringValue(text: String): StringValue = StringValue(text)
}