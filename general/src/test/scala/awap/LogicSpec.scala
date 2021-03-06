package awap

import Repository._
import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar

class LogicSpec extends FunSpec with MockitoSugar {

  implicit val token = "token"
  val id = 12L
  val employee = Employee(EmployeeId(id), "fn", "ln")
  val pageNo = 1

  val userProvider = mock[UserProvider]
  val repository = mock[Repository]
  val logic = new Logic(userProvider, repository)

  describe("logic") {

    describe("adding employee") {

      ignore("can add if user is logged in and has role Editor - returning Value object doesn't work") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Editor))))
        when(repository.addEmployee("fn", "ln")).thenReturn(EmployeeId(id))

        val result = logic.addEmployee("fn", "ln")

        verify(repository).addEmployee("fn", "ln")
        assert(result == Right(id))
      }

      it("can not add if user is logged in and has role Reader") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Reader))))

        val result = logic.addEmployee("fn", "ln")

        assert(result == Left("Editor role is needed"))
      }

      it("can not add if user is not logged") {
        when(userProvider.user(token)).thenReturn(None)

        val result = logic.addEmployee("fn", "ln")

        assert(result == Left("user not logged in"))
      }
    }

    describe("removing employee") {

      it("can remove if user is logged in and has role Editor") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Editor))))
        when(repository.removeEmployeeById(
          EmployeeId(org.mockito.Matchers.eq(id)),
          org.mockito.Matchers.any[Boolean])
        ).thenReturn(1)

        val result = logic.removeEmployee(id)

        assert(result == Right(1))
      }

      it("can not remove if user is logged in and has role Reader") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Reader))))

        val result = logic.removeEmployee(id)

        assert(result == Left("Editor role is needed"))
      }

      it("can not remove if user is not logged in") {
        when(userProvider.user(token)).thenReturn(None)

        val result = logic.removeEmployee(id)

        assert(result == Left("user not logged in"))
      }
    }

    describe("listing employees") {

      it("should call repository with the same pageNo if user logged in and has role Editor - SHOULD FAIL BUT WORKS") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Editor))))

        val result = logic.listEmployees(pageNo)

        verify(repository).listEmployeesPaged(pageNo + 2) // pageNo is different
      }

      it("should list if user logged in and has role Editor") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Editor))))
        when(repository.listEmployeesPaged(pageNo)).thenReturn(List(employee))

        val result = logic.listEmployees(pageNo)

        assert(result == Right(List(employee)))
      }

      it("should list if user logged in and has role Reader") {
        when(userProvider.user(token)).thenReturn(Option(User("user", Set(Reader))))
        when(repository.listEmployeesPaged(pageNo)).thenReturn(List(employee))

        val result = logic.listEmployees(pageNo)

        assert(result == Right(List(employee)))
      }

      it("should not list if user is not logged in") {
        when(userProvider.user(token)).thenReturn(None)

        val result = logic.listEmployees(pageNo)

        assert(result == Left("user not logged in"))
      }
    }
  }

}

