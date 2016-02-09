package pitfalls.byname

class ByName(service: Service) {

  def doIt(byNameInt: => Int) = {
    println("in byNameInt")
    service.doItByName(byNameInt)
  }

}

trait Service {

  def doItByName(value: => Int) = {
    println(s"service.doItByName($value)")
  }
}

object App {

  def main(args: Array[String]) {
    def someInt() = {
      println("in someInt")
      10
    }
    val byName = new ByName(new Service {})
    byName.doIt(someInt)
  }
}