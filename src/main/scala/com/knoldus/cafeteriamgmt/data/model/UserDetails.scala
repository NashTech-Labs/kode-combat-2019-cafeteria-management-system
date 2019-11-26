package com.knoldus.cafeteriamgmt.data.model

import com.knoldus.cafeteriamgmt.config.DB
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

trait UserDetails extends DB {

  val userDetail = TableQuery[UserDetailTable]

  class UserDetailTable(tag: Tag) extends Table[UserDetail](tag, "UserDetail") {
    def * : ProvenShape[UserDetail] = (uid, name, email).<>(UserDetail.tupled, UserDetail.unapply)

    def uid: Rep[String] = column[String]("uid", O.PrimaryKey)

    def name: Rep[String] = column[String]("username")

    def email: Rep[String] = column[String]("password")
  }

}
