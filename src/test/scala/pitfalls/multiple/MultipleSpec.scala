package pitfalls.multiple

import org.mockito.BDDMockito._
import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar

class MultipleSpec extends FunSpec with MockitoSugar {

  val service = mock[Service]
  val multiple = new Multiple(service)
  val firstValue = "xxx"
  val secondValue = "yyy"
  val someText = "lorem"

  when(service.unless(false)(someText)).thenReturn(firstValue)
  when(service.unless(true)(someText)).thenReturn(secondValue)

  describe("Multiple") {

    describe("works for Mockito.verify") {

      it("return value from service.unless if x < 5") {
        val result = multiple.doIt(1, someText)

        verify(service).unless(false)(someText)
        assert(result == firstValue)
      }

      it("return value from service.unless if x > 5") {
        val result = multiple.doIt(6, someText)

        verify(service).unless(true)(someText)
        assert(result == secondValue)
      }
    }

    describe("Matchers also works") {

      it("when we call in 'normal way'") {
        given(service.unless
          (org.mockito.Matchers.eq(true))
          (org.mockito.Matchers.any[String])
        ).willReturn("lorem ipsum")

        val result = service.unless(true)("any string")

        assert(result == "lorem ipsum")
      }

      it("with carrying") {
        given(service.unless
          (org.mockito.Matchers.eq(true))
          (org.mockito.Matchers.any[String])
        ).willReturn("lorem ipsum")

        val carry = service.unless(true) _

        val result = carry("any string")

        assert(result == "lorem ipsum")
      }
    }
  }
}
