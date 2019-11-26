package com.knoldus.cafeteriamgmt.data.model
import slick.jdbc.MySQLProfile.api._

case class UserCartItem(orderId:String,
                        foodId:String,
                        quantity:Int)

trait UserCartTable{
  class UserCartTable(tag:Tag) extends Table[UserCartItem](tag, "USER_CART"){
    def orderId = column[String]("orderId")
    def foodId = column[String]("foodId")
    def quantity = column[Int]("quantity")
    def * = (orderId,foodId,quantity).<>(UserCartItem.tupled, UserCartItem.unapply)
  }

  lazy val userCartTable = TableQuery[UserCartTable]
}
