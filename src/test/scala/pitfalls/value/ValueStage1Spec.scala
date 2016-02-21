package pitfalls.value

import org.mockito.BDDMockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import pitfalls.value.Value.StringValue

class ValueStage1Spec extends FunSpec with MockitoSugar {

  val valueService = mock[ValueService]

  describe("While dealing with value object") {

    describe("Mockito doesn't work with matcher") {

      it("for String") {

        val first = StringValue("first")

        given(valueService.doItString(
          org.mockito.Matchers.eq(first),
          org.mockito.Matchers.any[StringValue]))
          .willReturn("lorem ipsum")

        val result = valueService.doItString(first, StringValue("second"))

        assert(result == "lorem ipsum")
      }
    }

    describe("Mockito doesn't work with function returning value object") {
      it("for String") {
        val first = StringValue("first")
        given(valueService.returnStringValue("first")).willReturn(first)

        val result = valueService.returnStringValue("first")

        assert(result == first)
      }
    }
  }

}
