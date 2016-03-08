import model.{Fork, Repo, User}
import utils.TypeNameConstant

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.JSExport
import cats.data.{XorT, Xor}
import cats.std.future._
import utils.promise._

@JSExport
object Github {

  @JSExport
  def createFork(name: String,
                 description: String,
                 stargazers_count: Int,
                 homepage: js.UndefOr[String]): Fork =
    model.Fork(name, description, stargazers_count, homepage.toOption)

  @JSExport
  def createUser(name: String,
                 avatarUrl: String,
                 repos: js.Array[Repo]): User =
    model.User(name, avatarUrl, repos.toList)

  def loadUser(login: String): Future[String Xor User] = {
    for {
      userDTO <- XorT(API.user(login))
      repoDTO <- XorT(API.repos(login))
    } yield userFromDTO(userDTO, repoDTO)
  }.value

  @JSExport("loadUser")
  def loadUserJS(login: String): Promise[User] = loadUser(login).toPromise

  @JSExport
  val Fork = new TypeNameConstant[model.Fork]
  @JSExport
  val Origin = new TypeNameConstant[model.Origin]

  private def userFromDTO(dto: API.UserDTO,
                          repos: List[API.RepoDTO]): User =
    model.User(dto.name, dto.avatar_url, repos.map(repoFromDTO))

  private def repoFromDTO(dto: API.RepoDTO): Repo = dto.fork match {
    case true => model.Fork(dto.name, dto.description, dto.stargazers_count, dto.homepage)
    case false => model.Origin(dto.name, dto.description, dto.stargazers_count, dto.homepage, dto.forks)
  }
}
