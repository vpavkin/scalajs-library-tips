package model

import utils.Typed

import scala.annotation.meta.field
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSExport

sealed trait Repo extends Typed {
  @JSExport
  def name: String

  @JSExport
  def description: String

  @JSExport
  def stargazersCount: Int

  // leaving unfriendly value unexported
  def homepage: Option[String]

  @JSExport("homepage")
  def homepageJS: js.UndefOr[String] = homepage.orUndefined
}

case class Fork(name: String,
                description: String,
                stargazersCount: Int,
                homepage: Option[String]) extends Repo

case class Origin(name: String,
                  description: String,
                  stargazersCount: Int,
                  homepage: Option[String],
                  @(JSExport@field) forksCount: Int) extends Repo

case class User(@(JSExport@field) name: String,
                @(JSExport@field) avatarUrl: String,
                repos: List[Repo]) {

  @JSExport("repos")
  def reposJS: js.Array[Repo] = repos.toJSArray
}
