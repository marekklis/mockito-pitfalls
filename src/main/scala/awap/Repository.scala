package awap

import scala.collection.mutable

object Repository {

  case class EmployeeId(id: Long)
  case class Employee(id: EmployeeId, firstName: String, lastName: String)

  private val employees = mutable.ListBuffer[Employee]()

  employees += Employee(EmployeeId(autoincrementId()), "Gertie","Albertson")
  employees += Employee(EmployeeId(autoincrementId()), "Alycia","Audley")
  employees += Employee(EmployeeId(autoincrementId()), "Gaye","Anthonyson")
  employees += Employee(EmployeeId(autoincrementId()), "Simone","Daniell")
  employees += Employee(EmployeeId(autoincrementId()), "Judy","Lynton")
  employees += Employee(EmployeeId(autoincrementId()), "Sefton","Adams")
  employees += Employee(EmployeeId(autoincrementId()), "Danny","Peacock")
  employees += Employee(EmployeeId(autoincrementId()), "Dottie","Varnham")
  employees += Employee(EmployeeId(autoincrementId()), "July","Baines")
  employees += Employee(EmployeeId(autoincrementId()), "Marta","Michael")
  employees += Employee(EmployeeId(autoincrementId()), "Harris","Thurstan")
  employees += Employee(EmployeeId(autoincrementId()), "Tansy","Filipek")
  employees += Employee(EmployeeId(autoincrementId()), "Anemone","Marlow")
  employees += Employee(EmployeeId(autoincrementId()), "Alexander","Stone")
  employees += Employee(EmployeeId(autoincrementId()), "Eulalia","Gilliam")
  employees += Employee(EmployeeId(autoincrementId()), "Tammi","Jephson")
  employees += Employee(EmployeeId(autoincrementId()), "Louise","Hyland")
  employees += Employee(EmployeeId(autoincrementId()), "Tatyanna","Fletcher")

  def autoincrementId(): Long = employees.size + 1L

  def apply() = new Repository
}

class Repository {

  import Repository._

  def addEmployee(firstName: String, lastName: String): Long = {
    val id: Long = autoincrementId()
    employees += Employee(EmployeeId(id), firstName, lastName)
    id
  }

  def removeEmployeeById(id: EmployeeId) = {
    employees.find(_.id == id).map(employees -= _)
  }

  def listEmployeesPaged(pageNo: Int, pageSize: Int = 5) = {
    employees.drop((pageNo - 1) * pageSize).take(pageSize)
  }

}
