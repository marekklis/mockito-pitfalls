package awap

object awap extends App {

  println("fplike AWesome APplication started\n")

  val repository = Repository()

  val logic = new Logic(
    userProvider = new UserProvider().user,
    insertEmployee = repository.addEmployee,
    removeEmployeeById = repository.removeEmployeeById,
    listEmployeesPaged = repository.listEmployeesPaged(_)
  )

  {
    implicit val token = "secret-editor"
    println(s"New employee added by Editor: ${logic.addEmployee("Gertie", "Albertson")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Alycia", "Audley")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Gaye", "Anthonyson")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Simone", "Daniell")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Judy", "Lynton")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Sefton", "Adams")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Judy", "Lynton")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Danny", "Peacock")}")
    println(s"New employee added by Editor: ${logic.addEmployee("Dottie", "Varnham")}")
  }

  println("\n--------- page 1 by Editor -------------")
  logic.listEmployees(1)("secret-editor").right.map(_.foreach(println))
  println("\n--------- page 2 by Reader -------------")
  logic.listEmployees(2)("secret-reader").right.map(_.foreach(println))

  println(s"\nNew employee added by Reader: ${logic.addEmployee( "July","Baines")("secret-reader")}")
  println(s"New employee added by unknown: ${logic.addEmployee("Marta","Michael")("unknown-token")}")

  println(s"\nEmployee removed by Editor: ${logic.removeEmployee(7)("secret-editor")}")
  println(s"Employee removed by Reader: ${logic.removeEmployee(6)("secret-reader")}")
  println(s"Employee removed by unknown: ${logic.removeEmployee(5)("unknown-token")}")

  println("\n--------- page 2 by Reader -------------")
  logic.listEmployees(2)("secret-reader").right.map(_.foreach(println))

  println("\nfplike AWesome APplication finished")

}
