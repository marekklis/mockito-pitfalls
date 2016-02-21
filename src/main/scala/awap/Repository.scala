package awap

import scala.collection.mutable

object Repository {

  case class EmployeeId(id: Long)
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

  def removeEmployeeById(id: EmployeeId) = {
    employees.find(_.id == id).map(employees -= _)
  }

  def listEmployeesPaged(pageNo: Int, pageSize: Int = 5): List[Employee] = {
    employees.drop((pageNo - 1) * pageSize).take(pageSize).toList
  }

}
