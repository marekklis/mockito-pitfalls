package awap

import Repository._

class Logic(userProvider: UserProvider, repository: Repository) {

  def addEmployee(firstName: String, lastName: String)(implicit token: String): Either[String, Long] = userProvider.user(token) match {
    case Some(user) if user.roles.contains(Editor) =>
      Right(repository.addEmployee(firstName, lastName).id)
    case Some(user) =>
      Left("Editor role is needed")
    case _ =>
      Left("user not logged in")
  }

  def removeEmployee(employeeId: Long)(implicit token: String): Either[String, Int] = userProvider.user(token) match {
    case Some(user) if user.roles.contains(Editor) =>
      Right(repository.removeEmployeeById(EmployeeId(employeeId), true))
    case Some(user) =>
      Left("Editor role is needed")
    case _ =>
      Left("user not logged in")
  }

  def listEmployees(pageNo: Int)(implicit token: String): Either[String, List[Employee]] = userProvider.user(token) match {
    case Some(user) if user.roles.exists(Set[Role](Editor, Reader).contains) =>
      Right(repository.listEmployeesPaged(pageNo))
    case Some(user) =>
      Left("Editor or Reader role is needed")
    case _ =>
      Left("user not logged in")
  }
}
