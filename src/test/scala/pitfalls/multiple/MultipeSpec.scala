package pitfalls.multiple

import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

class MultipeSpec extends FlatSpec with MockitoSugar {

  val service = mock[Service]
  val multiple = new Multiple(service)
  val firstValue = "xxx"
  val secondValue = "yyy"
  val someText = "lorem"

  when(service.unless(false)(someText)).thenReturn(firstValue)
  when(service.unless(true)(someText)).thenReturn(secondValue)

  "Multiple" should "return value from service.unless if x < 5" in {
    val result = multiple.doIt(1, someText)

    verify(service).unless(false)(someText)
    assert(result == firstValue)
  }

  "Multiple" should "return value from service.unless if x > 5" in {
    val result = multiple.doIt(6, someText)

    verify(service).unless(true)(someText)
    assert(result == secondValue)
  }
}
