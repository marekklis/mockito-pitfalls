package awap

object awap extends App {

  println("AWesome APplication started")

  println("--------- page 1 -------------")
  Repository().listEmployeesPaged(1).foreach(println)
  println("--------- page 2 -------------")
  Repository().listEmployeesPaged(2).foreach(println)
  println("--------- page 3 -------------")
  Repository().listEmployeesPaged(3).foreach(println)
  println("--------- page 4 -------------")
  Repository().listEmployeesPaged(4).foreach(println)

}
