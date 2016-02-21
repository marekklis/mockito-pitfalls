package awap

import Repository._
import org.scalatest.FunSpec

class LogicSpec extends FunSpec {

  implicit val token = "token"
  val id = 12L
  val employee = Employee(EmployeeId(id), "fn", "ln")
  val pageNo = 1

  def userProvider(result: Option[User])(token: String) = result

  def insertEmployee(fn: String, ln: String) = EmployeeId(id)

  def removeEmployeeById(id: EmployeeId, permanently: Boolean) = 0

  def listEmployeesPaged(pageNo: Int) = List.empty[Employee]

  describe("logic") {

    describe("adding employee") {

      it("can add if user is logged in and has role Editor") {
        val logic = new Logic(
          userProvider(Option(User("user", Set(Editor)))),
          insertEmployee = (fn: String, ln: String) => EmployeeId(id),
          removeEmployeeById,
          listEmployeesPaged
        )

        val result = logic.addEmployee("fn", "ln")

        assert(result.right.get == id)
      }

      it("can not add if user is logged in and has role Reader") {
        val logic = new Logic(
          userProvider(Option(User("user", Set(Reader)))),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged
        )

        val result = logic.addEmployee("fn", "ln")

        assert(result.left.get == "Editor role is needed")
      }

      it("can not add if user is not logged") {
        val logic = new Logic(
          userProvider(None),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged
        )

        val result = logic.addEmployee("fn", "ln")

        assert(result.left.get == "user not logged in")
      }
    }

    describe("removing employee") {

      it("can remove if user is logged in and has role Editor") {
        val logic = new Logic(
          userProvider(Option(User("user", Set(Editor)))),
          insertEmployee,
          removeEmployeeById = (id: EmployeeId, p: Boolean) => 1,
          listEmployeesPaged
        )

        val result = logic.removeEmployee(id)

        assert(result.right.get == 1)
      }

      it("can not remove if user is logged in and has role Reader") {
        val logic = new Logic(
          userProvider(Option(User("user", Set(Reader)))),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged
        )

        val result = logic.removeEmployee(id)

        assert(result.left.get == "Editor role is needed")
      }

      it("can not remove if user is not logged in") {
        val logic = new Logic(
          userProvider(None),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged
        )

        val result = logic.removeEmployee(id)

        assert(result.left.get == "user not logged in")
      }
    }

    describe("listing employees") {

      it("should call repository with the same pageNo if user logged in and has role Editor - it's awful but works") {
        var expectedPageNo = -1
        val logic = new Logic(
          userProvider(Option(User("user", Set(Editor)))),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged = (p: Int) => {
            expectedPageNo = p
            List.empty[Employee]
          }
        )

        logic.listEmployees(pageNo)

        assert(pageNo == expectedPageNo)
      }

      it("should list if user logged in and has role Editor") {
        val logic = new Logic(
          userProvider(Option(User("user", Set(Editor)))),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged = (p: Int) => List(employee)
        )

        val result = logic.listEmployees(pageNo)

        assert(result.right.get == List(employee))
      }

      it("should list if user logged in and has role Reader") {
        val logic = new Logic(
          userProvider(Option(User("user", Set(Reader)))),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged = (p: Int) => List(employee)
        )

        val result = logic.listEmployees(pageNo)

        assert(result.right.get == List(employee))
      }

      it("should not list if user is not logged in") {
        val logic = new Logic(
          userProvider(None),
          insertEmployee,
          removeEmployeeById,
          listEmployeesPaged
        )

        val result = logic.listEmployees(pageNo)

        assert(result.left.get == "user not logged in")
      }
    }
  }

}

