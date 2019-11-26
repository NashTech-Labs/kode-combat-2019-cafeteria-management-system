package com.knoldus.cafeteriamgmt.data.dao

import com.knoldus.cafeteriamgmt.config.DB
import com.knoldus.cafeteriamgmt.data.model.{LoginDetail, LoginDetails, UserDetail}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

trait LoginDao extends LoginDetails with DB {


  def getLoginDetail: Future[Seq[LoginDetail]] = db.run {
    loginDetail.result
  }

  def addLoginDetail(loginCriteria: LoginDetail): Future[Int] = db.run {
    loginDetail += loginCriteria
  }

}
