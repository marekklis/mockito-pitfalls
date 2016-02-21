package pitfalls.value

import org.mockito.BDDMockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import pitfalls.value.Value.{LongValue, StringValue}

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

    it("for Long - magic not works") {

      val first = LongValue(317L)
      val expected = 734

      given(valueService.doItLong(
        org.mockito.Matchers.eq(first.value).asInstanceOf[LongValue],
        org.mockito.Matchers.any[Long].asInstanceOf[LongValue]))
        .willReturn(expected)

      val result = valueService.doItLong(first, LongValue(452))

      assert(result == expected)
    }

    describe("Mockito should work with returning value - after some magic added") {
      it("for String - MAGIC DOESN'T WORK") {
        val text = "lorem ipsum"
        given(valueService.returnStringValue(text)).willReturn(text.asInstanceOf[StringValue])

        val result = valueService.returnStringValue(text)

        assert(result == StringValue(text))
      }
    }
  }

}
