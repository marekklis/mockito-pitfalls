package pitfalls.multiple

class Multiple(service: Service) {

  def doIt(x: Int, text: String): String = service.unless(x > 5)(text)
}

trait Service {

  def unless(exp: Boolean)(text: String): String = if(exp) text else "lorem ipsum"
}
