package pitfalls.value

import org.mockito.BDDMockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import pitfalls.value.Value.{LongValue, StringValue}

class ValueStage3Spec extends FunSpec with MockitoSugar {

  val valueService = mock[ValueService]

  describe("While dealing with value object") {

    describe("Mockito works correctly if matcher is 'inside' value object") {

      it("works for String") {

        given(valueService.doItString(
          StringValue(org.mockito.Matchers.eq("first")),
          StringValue(org.mockito.Matchers.any[String]))
        ).willReturn("lorem ipsum")

        val result = valueService.doItString(StringValue("first"), StringValue("second"))

        assert(result == "lorem ipsum")
      }

      it("it works for Long") {

        given(valueService.doItLong(
          LongValue(org.mockito.Matchers.eq(1L)),
          LongValue(org.mockito.Matchers.any[Long]))
        ).willReturn(7)

        val result = valueService.doItLong(LongValue(1), LongValue(3))

        assert(result == 7)
      }
    }
  }

}
