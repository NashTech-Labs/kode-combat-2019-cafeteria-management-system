package com.knoldus.cafeteriamgmt.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.cafeteriamgmt.data.model.{FoodItemQuantity, Orders, UserCartItem, UserOrder}
import com.knoldus.cafeteriamgmt.data.model.{FoodItemQuantity, LoginDetail, Menu, Orders, RegisterDetail, UserDetail, UserOrder}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val UserOrderFormat = jsonFormat2(FoodItemQuantity)
  implicit val itemFormat: RootJsonFormat[Menu] = jsonFormat4(Menu)
  implicit val ordersFormat = jsonFormat1(Orders)
  implicit val UserCartFormat = jsonFormat3(UserCartItem)
  implicit val loginSupport: RootJsonFormat[LoginDetail] = jsonFormat3(LoginDetail)
  implicit val userSupport: RootJsonFormat[UserDetail] = jsonFormat3(UserDetail)
  implicit val registrationSupport: RootJsonFormat[RegisterDetail] = jsonFormat5(RegisterDetail)

}
