package pitfalls.defparam

import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import org.mockito.BDDMockito._

trait Rockable {

  def rock1(someInt: Int, someString: String): Int

  def rock2(someInt: Int, someString: String, someWithDefault: Long = 6L)

}

class Band(rockable: Rockable) {

  def letsRock1(someInt: Int, someString: String): Int =
    rockable.rock1(someInt, someString)

  def letsRock2(someInt: Int, someString: String) =
    rockable.rock2(someInt, someString)

  def letsRock2WithThird(someInt: Int, someString: String) =
    rockable.rock2(someInt, someString, 6)

}

class DefParamSpec extends FunSpec with MockitoSugar with Matchers {

  val someInt = 1
  val otherInt = 2
  val someString = "let's rock"
  val rockable = mock[Rockable]
  val band = new Band(rockable)

  describe("While dealing with default parameters") {
    describe("Mockito works correctly when no default parameters provided") {
      it("verify a mock call works [ok]") {
        band.letsRock1(someInt, someString)
        verify(rockable).rock1(someInt, someString)
      }

      it("`given` a mock call works [ok]") {
        val toReturn = 10
        given(rockable.rock1(someInt, someString)).willReturn(toReturn)
        band.letsRock1(someInt, someString) should equal(toReturn)
      }
    }

    describe("Mockito acts weird if default parameter added but we are not verifig it") {
      it("verify a mock call works but it should not [ok-but-should-fail]") {
        band.letsRock2(someInt, someString)
        verify(rockable).rock2(someInt, someString)
      }
    }

    describe("Mockito fails if default parameter added and we're asserting on that value") {
      it("verify a mock call fails [fail-but-should-be-ok]") {
        band.letsRock2WithThird(someInt, someString)
        verify(rockable).rock2(otherInt, someString, 6)
      }

    }

  }

}
