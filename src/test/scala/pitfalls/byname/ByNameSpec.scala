package pitfalls.byname

import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar

class ByNameSpec extends FunSpec with MockitoSugar {

  val service = mock[Service]
  val intValue = 137

  def intFunction() = intValue

  describe("call-by-name parameter") {

    it("does not work with verify") {
      val byName = new ByName(service)

      byName.doIt(intFunction)

      verify(service).doItByName(intFunction)
    }
  }

}

