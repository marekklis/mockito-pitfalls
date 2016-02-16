package pitfalls.byname

object App {

  def main(args: Array[String]) {
    def someInt() = {
      println("in someInt")
      10
    }

    val byName = new ByName(new Service {})
    byName.doIt(someInt)

    val funcByName = new FuncByName(new Service {}.doItByName)
    funcByName.doIt(someInt)
  }

}
