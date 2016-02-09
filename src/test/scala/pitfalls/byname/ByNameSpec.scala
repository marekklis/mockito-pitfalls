package pitfalls.byname

import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

class ByNameSpec extends FlatSpec with MockitoSugar {

  val service = mock[Service]
  val intValue = 137
  def intFunction() = intValue

  "doIt" should "call service.doItByName" in {
    val byName = new ByName(service)

    byName.doIt(intFunction)

    verify(service).doItByName(intFunction)
  }

}

