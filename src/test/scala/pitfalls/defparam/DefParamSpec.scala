package pitfalls.defparam

import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar

class DefParamSpec extends FunSpec with MockitoSugar {

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
    }

    describe("Mockito acts weird if default parameter added but we are not verifig it") {
      it("verify a mock call works but it should not [ok-but-should-fail]") {
        band.letsRock2(someInt, someString)
        verify(rockable).rock2(someInt, someString)
      }
    }

    describe("Mockito fails if default parametr added and we're asserting on that value") {
      it("verify a mock call fails [fail-but-should-be-ok]") {
        band.letsRock2WithThird(someInt, someString)
        verify(rockable).rock2(otherInt, someString, 6)
      }

    }

  }

}
