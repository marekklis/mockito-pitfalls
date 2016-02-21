package awap

import scala.collection.mutable

object Repository {

  case class EmployeeId(val id: Long) extends AnyVal
  case class Employee(id: EmployeeId, firstName: String, lastName: String)

  private val employees = mutable.ListBuffer[Employee]()

  private var id: Long = 0

  def autoincrementId(): EmployeeId = {
    id = id + 1
    EmployeeId(id)
  }

  def apply() = new Repository
}

class Repository {

  import Repository._

  def addEmployee(firstName: String, lastName: String): EmployeeId = {
    val id = autoincrementId()
    employees += Employee(id, firstName, lastName)
    id
  }

  def removeEmployeeById(id: EmployeeId): Int = {
    employees.find(_.id == id).map{ u =>
      employees -= u
      1
    }.getOrElse(0)
  }

  def listEmployeesPaged(pageNo: Int, pageSize: Int = 5): List[Employee] = {
    employees.drop((pageNo - 1) * pageSize).take(pageSize).toList
  }

}
