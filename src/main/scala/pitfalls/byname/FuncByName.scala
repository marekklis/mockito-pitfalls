package pitfalls.byname

class FuncByName(doItByName: (=> Int) => Unit) {

  def doIt(byNameInt: => Int) = {
    println("in FuncByName.doIt")
    doItByName(byNameInt)
  }

}
