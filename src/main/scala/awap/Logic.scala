package awap

import Repository._

class Logic(userProvider: UserProvider, repository: Repository) {

  def addEmployee(token: String, firstName: String, lastName: String): Either[String, Long] = userProvider.user(token) match {
    case Some(user) if user.roles.contains(Editor) =>
      Right(repository.addEmployee(firstName, lastName).id)
    case Some(user) =>
      Left("Editor role is needed")
    case _ =>
      Left("user not logged in")
  }

  def removeEmployee(token: String, employeeId: Long): Either[String, Int] = userProvider.user(token) match {
    case Some(user) if user.roles.contains(Editor) =>
      Right(repository.removeEmployeeById(EmployeeId(employeeId)))
    case Some(user) =>
      Left("Editor role is needed")
    case _ =>
      Left("user not logged in")
  }

  def listEmployees(token: String, pageNo: Int): Either[String, List[Employee]] = userProvider.user(token) match {
    case Some(user) if user.roles.exists(Set[Role](Editor, Reader).contains) =>
      Right(repository.listEmployeesPaged(pageNo))
    case Some(user) =>
      Left("Editor or Reader role is needed")
    case _ =>
      Left("user not logged in")
  }
}
