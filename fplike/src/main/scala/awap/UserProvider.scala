package awap

sealed trait Role
case object Editor extends Role
case object Reader extends Role

case class User(name: String, roles: Set[Role])

object UserProvider {

  private val usersAndTokens = Map(
    "secret-editor" -> User("John Editor", Set(Editor)),
    "secret-reader" -> User("John Reader", Set(Reader)))
}

class UserProvider {

  import UserProvider._

  def user(token: String): Option[User] = {
    usersAndTokens.get(token)
  }
}
