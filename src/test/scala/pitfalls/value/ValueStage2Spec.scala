package pitfalls.value

import org.mockito.BDDMockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import pitfalls.value.Value.StringValue

class ValueStage2Spec extends FunSpec with MockitoSugar {

  val valueService = mock[ValueService]

  describe("While dealing with value object") {

    describe("Mockito should work with matcher - after some magic added") {

      it("for String") {

        val first = StringValue("first")

        given(valueService.doItString(
          org.mockito.Matchers.eq(first.text).asInstanceOf[StringValue],
          org.mockito.Matchers.any[String].asInstanceOf[StringValue]))
          .willReturn("lorem ipsum")

        val result = valueService.doItString(first, StringValue("second"))

        assert(result == "lorem ipsum")
      }
    }
  }

}
