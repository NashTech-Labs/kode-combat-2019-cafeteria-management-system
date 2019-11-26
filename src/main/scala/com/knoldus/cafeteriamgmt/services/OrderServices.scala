package com.knoldus.cafeteriamgmt.services

import com.knoldus.cafeteriamgmt.config.DB
import com.knoldus.cafeteriamgmt.data.dao.OrderDao
import com.knoldus.cafeteriamgmt.data.model.Orders

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class OrderServices {

  val orderDao = new OrderDao with DB

  //  def requestOrder(order:Orders): Unit ={
  //    val listOfOrders = order.order
  //    val possibleOrders = listOfOrders.map(order =>
  //    orderDao.isOrderPossible(order))
  //  }
  def addOrdersToCart(orders: Orders) = orderDao.addOrderToCart(orders)

  def getOrderItems(orderId: String) = orderDao.fetchOrderItems(orderId)

  def getPayment(orderId: String, userId:String) = {
    val totalAmount = orderDao.getTotalAmount(orderId)
    totalAmount.map(paymentByOrder => orderDao.updatePaymentTable(paymentByOrder, userId))
    totalAmount
  }

//  def updateCartByOrderId(orderId: String, updatedOrder: Orders) = {
//    val listOfFtures = orderDao.updateCartByOrderId(orderId, updatedOrder)
//    val listOfFutureTry = listOfFtures.map(futureToFutureTry(_))
//    val futureListOfTry = Future.sequence(listOfFutureTry)
//    futureListOfTry.map(_.filter(_.isSuccess))
//  }

  def futureToFutureTry[T](f: Future[T]): Future[Try[T]] = f.map(Success(_)).recover({case x => Failure(x)})
}
