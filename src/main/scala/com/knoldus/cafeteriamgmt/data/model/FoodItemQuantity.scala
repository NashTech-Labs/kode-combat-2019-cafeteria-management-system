package com.knoldus.cafeteriamgmt.data.model

case class FoodItemQuantity(foodId:String, quantity:Int)

case class Orders(order:List[FoodItemQuantity])
