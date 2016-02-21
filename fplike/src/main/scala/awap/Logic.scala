package awap

import Repository._

class Logic(userProvider: (String) => Option[User],
            insertEmployee: (String, String) => EmployeeId,
            removeEmployeeById: (EmployeeId, Boolean) => Int,
            listEmployeesPaged: (Int) => List[Employee]) {

  def addEmployee(firstName: String, lastName: String)(implicit token: String): Either[String, Long] = userProvider(token) match {
    case Some(user) if user.roles.contains(Editor) =>
      Right(insertEmployee(firstName, lastName).id)
    case Some(user) =>
      Left("Editor role is needed")
    case _ =>
      Left("user not logged in")
  }

  def removeEmployee(employeeId: Long)(implicit token: String): Either[String, Int] = userProvider(token) match {
    case Some(user) if user.roles.contains(Editor) =>
      Right(removeEmployeeById(EmployeeId(employeeId), true))
    case Some(user) =>
      Left("Editor role is needed")
    case _ =>
      Left("user not logged in")
  }

  def listEmployees(pageNo: Int)(implicit token: String): Either[String, List[Employee]] = userProvider(token) match {
    case Some(user) if user.roles.exists(Set[Role](Editor, Reader).contains) =>
      Right(listEmployeesPaged(pageNo))
    case Some(user) =>
      Left("Editor or Reader role is needed")
    case _ =>
      Left("user not logged in")
  }
}
