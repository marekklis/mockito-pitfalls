package awap

import Repository._
import org.scalatest.FunSpec

class LogicSpec extends FunSpec {

  implicit val token = "token"
  val id = 12L
  val employee = Employee(EmployeeId(id), "fn", "ln")
  val pageNo = 1

  def userProvider(result: Option[User])(token: String) = result

  def createLogic(userProvider: (String) => Option[User],
            insertEmployee: (String, String) => EmployeeId = (fn: String, ln: String) => EmployeeId(id),
            removeEmployeeById: (EmployeeId, Boolean) => Int = (id: EmployeeId, permanently: Boolean) => 0,
            listEmployeesPaged: (Int) => List[Employee] = (pageNo: Int) => List.empty[Employee]): Logic =
    new Logic(
      userProvider = userProvider,
      insertEmployee = insertEmployee,
      removeEmployeeById = removeEmployeeById,
      listEmployeesPaged = listEmployeesPaged)

  describe("logic") {

    describe("adding employee") {

      it("can add if user is logged in and has role Editor") {
        val logic: Logic = createLogic(
          userProvider = userProvider(Option(User("user", Set(Editor))))
        )

        val result = logic.addEmployee("fn", "ln")

        assert(result == Right(id))
      }

      it("can not add if user is logged in and has role Reader") {
        val logic = createLogic(
          userProvider = userProvider(Option(User("user", Set(Reader))))
        )

        val result = logic.addEmployee("fn", "ln")

        assert(result == Left("Editor role is needed"))
      }

      it("can not add if user is not logged") {
        val logic = createLogic(
          userProvider = userProvider(None)
        )

        val result = logic.addEmployee("fn", "ln")

        assert(result == Left("user not logged in"))
      }
    }

    describe("removing employee") {

      it("can remove if user is logged in and has role Editor") {
        val logic = createLogic(
          userProvider = userProvider(Option(User("user", Set(Editor)))),
          removeEmployeeById = (id: EmployeeId, p: Boolean) => 1
        )

        val result = logic.removeEmployee(id)

        assert(result == Right(1))
      }

      it("can not remove if user is logged in and has role Reader") {
        val logic = createLogic(
          userProvider = userProvider(Option(User("user", Set(Reader))))
        )

        val result = logic.removeEmployee(id)

        assert(result == Left("Editor role is needed"))
      }

      it("can not remove if user is not logged in") {
        val logic = createLogic(
          userProvider = userProvider(None)
        )

        val result = logic.removeEmployee(id)

        assert(result == Left("user not logged in"))
      }
    }

    describe("listing employees") {

      it("should call repository with the same pageNo if user logged in and has role Editor - it's awful but works") {
        var expectedPageNo = -1
        val logic = createLogic(
          userProvider = userProvider(Option(User("user", Set(Editor)))),
          listEmployeesPaged = (p: Int) => {
            expectedPageNo = p
            List.empty[Employee]
          }
        )

        logic.listEmployees(pageNo)

        assert(pageNo == expectedPageNo)
      }

      it("should list if user logged in and has role Editor") {
        val logic = createLogic(
          userProvider = userProvider(Option(User("user", Set(Editor)))),
          listEmployeesPaged = (p: Int) => List(employee)
        )

        val result = logic.listEmployees(pageNo)

        assert(result == Right(List(employee)))
      }

      it("should list if user logged in and has role Reader") {
        val logic = createLogic(
          userProvider = userProvider(Option(User("user", Set(Reader)))),
          listEmployeesPaged = (p: Int) => List(employee)
        )

        val result = logic.listEmployees(pageNo)

        assert(result == Right(List(employee)))
      }

      it("should not list if user is not logged in") {
        val logic = createLogic(
          userProvider = userProvider(None)
        )

        val result = logic.listEmployees(pageNo)

        assert(result == Left("user not logged in"))
      }
    }
  }

}

