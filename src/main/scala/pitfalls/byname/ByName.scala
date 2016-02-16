package pitfalls.byname

class ByName(service: Service) {

  def doIt(byNameInt: => Int) = {
    println("in ByName.doIt")
    service.doItByName(byNameInt)
  }

}

trait Service {

  def doItByName(value: => Int) = {
    println("external service")
    println(s"service.doItByName($value)")
  }
}

