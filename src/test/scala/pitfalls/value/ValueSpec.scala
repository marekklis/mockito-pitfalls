package pitfalls.value

import org.mockito.BDDMockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import pitfalls.value.Value.{LongValue, StringValue}

class ValueSpec extends FunSpec with MockitoSugar {

  val valueService = mock[ValueService]

  describe("While dealing with value object") {
    describe("Mockito doesn't work with matcher") {
      it("for String") {
        val firstValue = StringValue("first")
        val eqFirst: StringValue = org.mockito.Matchers.eq(StringValue("first"))
        val anySecond: StringValue = org.mockito.Matchers.any[StringValue]

        given(valueService.doItString(
          eqFirst,
          anySecond))
          .willReturn("lorem ipsum")

        val result = valueService.doItString(StringValue("first"), StringValue("second"))

        assert(result == "lorem ipsum")

      }
    }

    describe("Mockito works correctly if matcher is 'inside' value object") {

      it("works for String") {

        val eqFirst: StringValue = StringValue(org.mockito.Matchers.eq("first"))
        val anySecond: StringValue = StringValue(org.mockito.Matchers.any[String])

        given(valueService.doItString(
          eqFirst,
          anySecond))
          .willReturn("lorem ipsum")

        val result = valueService.doItString(StringValue("first"), StringValue("second"))

        assert(result == "lorem ipsum")
      }

      it("it works for Long") {

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
  }

}
