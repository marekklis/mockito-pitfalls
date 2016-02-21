package awap

import Repository.EmployeeId
import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar

class LogicSpec extends FunSpec with MockitoSugar {

  val token = "token"
  val id = 1L
  val userProvider = mock[UserProvider]
  val repository = mock[Repository]
  val logic = new Logic(userProvider, repository)

  describe("logic") {

    describe("adding employee") {

      ignore("can add if user is logged in and has role Editor - returning Value object doesn't work") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Editor))))
        when(repository.addEmployee("fn", "ln")).thenReturn(EmployeeId(id))

        val result = logic.addEmployee(token, "fn", "ln")

        verify(repository).addEmployee("fn", "ln")
        assert(result.right.get == id)
      }

      it("can not add if user is logged in and has role Reader") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Reader))))

        val result = logic.addEmployee(token, "fn", "ln")

        assert(result.left.get == "Editor role is needed")
      }

      it("can not add if user is not logged") {
        when(userProvider.user(token)).thenReturn(None)

        val result = logic.addEmployee(token, "fn", "ln")

        assert(result.left.get == "user not logged in")
      }
    }

    describe("removing employee") {

      it("can remove if user is logged in and jas role Editor") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Editor))))
        when(repository.removeEmployeeById(EmployeeId(id))).thenReturn(1)

        val result = logic.removeEmployee(token, id)

        assert(result.right.get == 1)
      }
    }
  }

}

