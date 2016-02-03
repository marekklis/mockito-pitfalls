package pitfalls

import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

class BandSpec extends FlatSpec with MockitoSugar {

  val someInt = 1
  val otherInt = 2
  val someString = "let's rock"
  val rockable = mock[Rockable]
  val band = new Band(rockable)

  "Band" should "call rock1" in {
    band.letsRock1(someInt, someString)

    verify(rockable).rock1(someInt, someString)
  }

  it should "call rock2 with default value for third parameter" in {
    band.letsRock2(someInt, someString)

    // it should fail - but it's OK
    verify(rockable).rock2(someInt, someString)
  }

  it should "call rock2 with tree parameters" in {
    band.letsRock2WithThird(someInt, someString)

    // it should and fail
    verify(rockable).rock2(otherInt, someString, 2)
  }

}
