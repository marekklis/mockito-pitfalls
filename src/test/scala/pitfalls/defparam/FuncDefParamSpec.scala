package pitfalls.defparam

import org.scalatest._

trait FuncRockable {

  def rock1(someInt: Int, someString: String): Int

  def rock2(someInt: Int, someString: String, someWithDefault: Long = 6L)

}

class FuncBand(rock1: (Int, String) => Int, rock2: (Int, String, Long) => Int) {

  def letsRock1(someInt: Int, someString: String): Int =
    rock1(someInt, someString)

  def letsRock2(someInt: Int, someString: String) =
    rock2(someInt, someString, 0)

  def letsRock2WithThird(someInt: Int, someString: String) =
    rock2(someInt, someString, 6)

}

class FuncDefParamSpec extends FunSpec {

  val someInt = 1
  val otherInt = 2
  val someString = "let's rock"

  describe("While dealing with default parameters") {

    it("we want to know if it was called with given values [ok]") {
      var calledInt: Int = 0
      var calledString: String = ""
      val band = new FuncBand(
        rock1 = (a: Int, b: String) => {
          calledInt = a
          calledString = b
          1
        },
        rock2 = (a: Int, b: String, c: Long) => {
          calledInt = a
          calledString = b
          1
        }
      )

      band.letsRock1(someInt, someString)

      assert(calledInt == someInt)
      assert(calledString == someString)
    }

    it("we want to know if it works [ok]") {
      val toReturn = 10
      val band = new FuncBand(
        rock1 = (a: Int, b: String) => {
          toReturn
        },
        rock2 = (a: Int, b: String, c: Long) => {
          0
        }
      )

      assert(band.letsRock1(someInt, someString) == toReturn)
    }
  }

}
