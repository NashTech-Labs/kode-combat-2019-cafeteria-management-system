package com.knoldus.cafeteriamgmt.data.model

import java.sql.Timestamp

import slick.jdbc.MySQLProfile.api._

trait UserOrderTable {

  class UserOrderTable(tag:Tag) extends Table[UserOrder](tag, "USER_ORDER"){

    def userId = column[String]("userId")
    def orderId = column[String]("orderId")
    def paymentId = column[String]("paymentId")
    def * = (userId,orderId,paymentId).<>(UserOrder.tupled,UserOrder.unapply)
  }

  val userOrderTable = TableQuery[UserOrderTable]

}
