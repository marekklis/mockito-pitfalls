package pitfalls.defparam

trait Rockable {

  def rock1(someInt: Int, someString: String)

  def rock2(someInt: Int, someString: String, someWithDefault: Long = 6L)

}

class Band(rockable: Rockable) {

  def letsRock1(someInt: Int, someString: String) =
    rockable.rock1(someInt, someString)

  def letsRock2(someInt: Int, someString: String) =
    rockable.rock2(someInt, someString)

  def letsRock2WithThird(someInt: Int, someString: String) =
    rockable.rock2(someInt, someString, 2)

}
