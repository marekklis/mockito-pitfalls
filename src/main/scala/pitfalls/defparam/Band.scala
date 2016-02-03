package pitfalls.defparam

class Band(rockable: Rockable) {

  def letsRock1(someInt: Int, someString: String) =
    rockable.rock1(someInt, someString)

  def letsRock2(someInt: Int, someString: String) =
    rockable.rock2(someInt, someString)

  def letsRock2WithThird(someInt: Int, someString: String) =
    rockable.rock2(someInt, someString, 2)

}
