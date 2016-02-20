package awap

class Logic(userProvider: UserProvider, repository: Repository) {

  def addEmployee(token: String, firstName: String, lastName: String): Either[String, Long] = userProvider.user(token) match {
    case Some(user) if user.roles.contains(Editor) =>
      Right(repository.addEmployee(firstName, lastName))
    case Some(user) =>
      Left("Editor role is needed")
    case _ =>
      Left("user not found")
  }
}
