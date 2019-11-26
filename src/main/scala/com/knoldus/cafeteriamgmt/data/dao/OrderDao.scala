package com.knoldus.cafeteriamgmt.data.dao

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy
import com.knoldus.cafeteriamgmt.config.DB
import com.knoldus.cafeteriamgmt.data.model.{FoodItemQuantity, FoodItemTable, Orders, PaymentDetails, PaymentDetailsTable, UserCartItem, UserCartTable, UserOrder, UserOrderTable}

import scala.util.{Random, Success}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

class OrderDao(implicit val ec:ExecutionContext) extends UserCartTable with FoodItemTable with PaymentDetailsTable with UserOrderTable {
  this: DB =>
  //  //foodTable -> TableQuery for foodTable
  //  def checkQuantity(order:FoodItemQuantity)=db.run{
  //    foodTable.filter(foodItem => foodItem.foodId === order.foodId).map(_.quantity).result
  //  }

  def addOrderToCart(orders: Orders) = {
    val orderId = Random.nextInt(1000).toString
    orders.order.foreach(foodItem =>
      saveToDB(UserCartItem(orderId, foodItem.foodId, foodItem.quantity)))
    orderId
  }

  def saveToDB(item: UserCartItem) = db.run {
    userCartTable += item
  }

  def fetchOrderItems(orderId: String) = db.run {
    userCartTable.filter(orderItem => orderItem.orderId === orderId).result
  }

  def generateTransitPaymentTable(orderId: String) = {
    val matchingOrders = userCartTable.filter(_.orderId === orderId)
    val joinedTable = for {
      (foodItem, userCart) <- foodItemTable join matchingOrders on (_.foodId === _.foodId)
    } yield (userCart.orderId, userCart.quantity, foodItem.price)
    db.run(joinedTable.result)
  }

  def getTotalAmount(orderId: String) = {
    val consolidatedPrices = generateTransitPaymentTable(orderId).map(listOrders => listOrders.map(
      obj => (obj._1, obj._2 * obj._3)))
    consolidatedPrices.map(listPayment => listPayment.reduce((x, y) => (x._1, x._2 + y._2))
    )
  }

  def updatePaymentTable(paymentByOrder: (String, Double), userId:String) = {
    val paymentId = Random.nextInt(10).toString
    val actions = for {
      ax1 <- (paymentDetailsTable += PaymentDetails(paymentId, paymentByOrder._2, true))
      ax2 <- (userOrderTable += UserOrder(userId, paymentByOrder._1, paymentId))
    } yield {
      (ax1, ax2)
    }
    db.run(
      actions
    )
  }

//  def updateCartByOrderId(orderId: String, updatedOrder: Orders) = {
//    val foodItemsInOrderId = userCartTable.filter(row => row.orderId === orderId)
//    updatedOrder.order.map { foodItem =>
//      println(foodItemsInOrderId.filter(_.foodId === foodItem.foodId).length)
//      if (foodItemsInOrderId.filter(_.foodId === foodItem.foodId).length == 0) {
//        println("New added item")
//        db.run(userCartTable += UserCartItem(orderId, foodItem.foodId, foodItem.quantity))
//      }
//      else{
//        println(foodItemsInOrderId.filter(_.foodId === foodItem.foodId).length)
//        if(foodItem.quantity == 0) {
//          db.run(userCartTable.filter(_.foodId === foodItem.foodId).delete)
//        }
//        else {
//          db.run(userCartTable.filter(item => (item.orderId===orderId && item.foodId === foodItem.foodId)).map(_.quantity).update(foodItem.quantity))
//        }
//      }
//    }
//    }

//  def checkOrderId(orderId:String)={
//    userCartTable.filter(orderItem => orderItem.orderId === orderId).length
//  }
//

  }
