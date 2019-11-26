package com.knoldus.cafeteriamgmt.data.model

import com.knoldus.cafeteriamgmt.config.DB
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

trait LoginDetails {
  this: DB =>

  val loginDetail = TableQuery[LoginDetailTable]

  class LoginDetailTable(tag: Tag) extends Table[LoginDetail](tag, "LoginDetail") {
    def * : ProvenShape[LoginDetail] = (uid, username, password).<>(LoginDetail.tupled, LoginDetail.unapply)

    def uid: Rep[String] = column[String]("uid", O.PrimaryKey)

    def username: Rep[String] = column[String]("username")

    def password: Rep[String] = column[String]("password")
  }
}
