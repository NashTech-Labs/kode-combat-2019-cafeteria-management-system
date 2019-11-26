package com.knoldus.cafeteriamgmt.data.dao

import java.sql.Timestamp

import com.knoldus.cafeteriamgmt.config.DB
import com.knoldus.cafeteriamgmt.data.model.{UserDetail, UserDetails, UserOrder}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

trait UserDao extends  UserDetails {
  this: DB =>

  def getUserDetail: Future[Seq[UserDetail]] = db.run {
    userDetail.result
  }

  def addUserDetail(userCriteria: UserDetail): Future[Int] = db.run {
    userDetail += userCriteria
  }

}
