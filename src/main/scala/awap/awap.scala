package awap

object awap extends App {

  println("AWesome APplication started\n")

  val logic = new Logic(
    userProvider = new UserProvider,
    repository = Repository())

  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Gertie", "Albertson")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Alycia","Audley")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Gaye","Anthonyson")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Simone","Daniell")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Judy","Lynton")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Sefton","Adams")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Judy","Lynton")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Danny","Peacock")}")
  println(s"New employee added by Editor: ${logic.addEmployee("secret-editor", "Dottie","Varnham")}")

  println("\n--------- page 1 by Editor -------------")
  logic.listEmployees("secret-editor", 1).right.map(_.foreach(println))
  println("\n--------- page 2 by Reader -------------")
  logic.listEmployees("secret-reader", 2).right.map(_.foreach(println))

  println(s"\nNew employee added by Reader: ${logic.addEmployee("secret-reader", "July","Baines")}")
  println(s"New employee added by unknown: ${logic.addEmployee("unknown-token", "Marta","Michael")}")

  println(s"\nEmployee removed by Editor: ${logic.removeEmployee("secret-editor", 7)}")
  println(s"Employee removed by Reader: ${logic.removeEmployee("secret-reader", 6)}")
  println(s"Employee removed by unknown: ${logic.removeEmployee("unknown-token", 5)}")

  println("\n--------- page 2 by Reader -------------")
  logic.listEmployees("secret-reader", 2).right.map(_.foreach(println))

  println("\nAWesome APplication finished")

}
