package pitfalls.byname

import org.scalatest.FunSpec

class FuncByNameSpec extends FunSpec {

  def intFunction() = 137

  describe("call-by-name parameter in functional way") {

    it("just works") {
      var wasCalled = false
      def mocked(value: => Int): Unit = {
        wasCalled = true
      }
      val byName = new FuncByName(mocked)

      byName.doIt(intFunction)

      assert(wasCalled == true)
    }
  }

}

