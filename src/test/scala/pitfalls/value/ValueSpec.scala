package pitfalls.value

import org.mockito.BDDMockito._
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar
import pitfalls.value.Value.{LongValue, StringValue}

class ValueSpec extends FlatSpec with MockitoSugar {

  val valueService = mock[ValueService]

  "Value.doItString" should "be called with eq('first') and anyString" in {

    val eqFirst: StringValue = StringValue(org.mockito.Matchers.eq("first"))
    val anySecond: StringValue = StringValue(org.mockito.Matchers.any[String])

    given(valueService.doItString(
      eqFirst,
      anySecond))
      .willReturn("lorem ipsum")

    val result = valueService.doItString(StringValue("first"), StringValue("second"))

    assert(result == "lorem ipsum")
  }

  "Value.doItLong" should "be called with eq(1L) and anyLong" in {

    val eqFirst: LongValue = LongValue(org.mockito.Matchers.eq(1L))
    val anySecond: LongValue = LongValue(org.mockito.Matchers.any[Long])

    given(valueService.doItLong(
      eqFirst,
      anySecond))
      .willReturn(7)

    val result = valueService.doItLong(LongValue(1), LongValue(3))

    assert(result == 7)
  }

}
