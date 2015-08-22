import org.scalajs.dom.ext.Ajax

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.util.Try
import scalaz._
import Scalaz._

object API {

  case class UserDTO(name: String, avatar_url: String)
  case class RepoDTO(name: String,
                     description: String,
                     stargazers_count: Int,
                     homepage: Option[String],
                     forks: Int,
                     fork: Boolean)

  def user(login: String)
          (implicit ec: ExecutionContext): Future[String \/ UserDTO] =
    load(login, s"$BASE_URL/users/$login", jsonToUserDTO)

  def repos(login: String)
           (implicit ec: ExecutionContext): Future[String \/ List[RepoDTO]] =
    load(login, s"$BASE_URL/users/$login/repos", arrayToRepos)

  private def load[T](login: String,
                      url: String,
                      parser: js.Any => Option[T])
                     (implicit ec: ExecutionContext): Future[String \/ T] =
    if (login.isEmpty)
      Future.successful("Error: login can't be empty".left)
    else
      Ajax.get(url).map(xhr =>
        if (xhr.status == 200) {
          parser(js.JSON.parse(xhr.responseText))
            .map(_.right)
            .getOrElse("Request failed: can't deserialize result".left)
        } else {
          s"Request failed with response code ${xhr.status}".left
        }
      )

  private val BASE_URL: String = "https://api.github.com"

  // JSON deserialization is implemented manually for simplification
  // In real project it's wiser to use a library,
  // or implement type class derivations with shapeless

  private def jsonToRepoDTO(json: js.Any): Option[RepoDTO] = Try {
    val o: Map[String, js.Any] = json.asInstanceOf[js.Dictionary[js.Any]].toMap
    Some(RepoDTO(
      Option(o("name")).map(_.toString).getOrElse(""),
      Option(o("description")).map(_.toString).getOrElse(""),
      o("stargazers_count").asInstanceOf[Int],
      Option(o("homepage")).map(_.toString),
      o("forks_count").asInstanceOf[Int],
      o("fork").asInstanceOf[Boolean]
    ))
  }.getOrElse(None)


  private def jsonToUserDTO(json: js.Any): Option[UserDTO] = Try {
    val o: Map[String, js.Any] = json.asInstanceOf[js.Dictionary[js.Any]].toMap
    Some(UserDTO(
      Option(o("name")).map(_.toString).getOrElse(""),
      Option(o("avatar_url")).map(_.toString).getOrElse("")
    ))
  }.getOrElse(None)

  private def arrayToRepos(json: js.Any): Option[List[RepoDTO]] = Try {
    val list = json.asInstanceOf[js.Array[js.Any]].toList
    list.map(jsonToRepoDTO).sequenceU
  }.getOrElse(None)
}
