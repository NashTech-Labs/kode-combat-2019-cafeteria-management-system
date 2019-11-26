package com.knoldus.cafeteriamgmt.data.model

import slick.jdbc.MySQLProfile.api._

case class FoodItem(foodId:String,
                    foodName:String,
                    quantity:Int,
                    price:Double)

trait FoodItemTable{
  class FoodItemTable(tag:Tag) extends Table[FoodItem](tag, "FOOD_ITEMS"){
    def foodId = column[String]("foodId")
    def foodName=column[String]("foodName")
    def quantity = column[Int]("quantity")
    def price = column[Double]("price")
    def * = (foodId,foodName,quantity,price).<>(FoodItem.tupled,FoodItem.unapply)
  }
  lazy val foodItemTable = TableQuery[FoodItemTable]

}
