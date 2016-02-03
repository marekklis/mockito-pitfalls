package pitfalls

trait Rockable {

  def rock1(someInt: Int, someString: String)

  def rock2(someInt: Int, someString: String, someWithDefault: Long = 6L)

}
