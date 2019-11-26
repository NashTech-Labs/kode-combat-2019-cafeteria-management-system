package com.knoldus.cafeteriamgmt.controller

import akka.http.scaladsl.server.Directives.{path, post, _}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.knoldus.cafeteriamgmt.data.model.{LoginDetail, Menu, Orders, RegisterDetail}
import com.knoldus.cafeteriamgmt.services.{LoginServices, OrderServices, StartupServiceImpl}
import com.knoldus.cafeteriamgmt.data.model.{LoginDetail, Menu, Orders, RegisterDetail}

trait Routes extends JsonSupport {

  val repo: LoginServices
  val orderService: OrderServices = new OrderServices
  val routes: Route =
    path("requestOrder") {
      post {
        entity(as[Orders]) { orders =>
          val orderId = orderService.addOrdersToCart(orders)
          complete("Your order Id is " + orderId)
        }
      }
    } ~
      get {
        pathPrefix("viewOrder" / Segment) { orderId =>
          onComplete(orderService.getOrderItems(orderId)) {
            case util.Success(listOfOrders) => complete(listOfOrders)
          }
        }
      } ~
      get {
        pathPrefix("confirmOrder" / Segment) { orderId => {
          path(Segment) { userId =>
            onComplete(orderService.getPayment(orderId, userId)) {
              case util.Success(listOfPayments) => complete(listOfPayments)
            }
          }
        }
          //  post{
          //    pathPrefix("updateCartItems" / Segment){ orderId =>
          //      entity(as[Orders]){ updatedOrder =>
          //        onComplete(orderService.updateCartByOrderId(orderId,updatedOrder)){
          //          case util.Success(x) => complete("User Cart Updated")
          //         // case util.Success(0) => complete("User Cart Not found")
          //          case util.Failure(ex) => complete(ex.getMessage)
          //        }
          //      }
          //    }
          //  }
        }
      }
  get {
    path("userDetail") {
      complete(repo.viewDetails)
    }
  } ~
    post {
      path("RegisterUser") {
        entity(as[RegisterDetail]) { entry =>
          val stored = repo.registerUser(entry)
          val ud = "http://localhost:8081/userDetail"
          onComplete(stored)(done => complete(s"Item Inserted \n $ud"))
        }
      }
    } ~
    post {
      path("login") {
        entity(as[LoginDetail]) { entry =>
          val value = repo.login(entry)
          if (value) {
            complete("Login successFully")
          }
          else {
            complete("unsuccessful")

          }
        }
      }
    } ~
    path("postOrder") {
      post {
        entity(as[Orders]) { orders =>
          println(orders)
          complete("ok")
        }
      }
    } ~
    post {
      path("addItemIntoMenu") {
        entity(as[Menu]) { item =>
          onComplete(StartupServiceImpl.addNewItem(item)) {
            case util.Success(value) if value == 1 =>
              val menulink = "http://localhost:8081/menu"
              complete(s"Added successfully\nHere is your menu link:\n $menulink")
            case util.Success(res) =>
              val linkToAddItem = s"http://localhost:8081/addItemIntoMenu"
              complete(s"Item is not added successfully\nPlease try again\nHere is the link to add item in cart : $linkToAddItem")
            case util.Failure(ex) => complete(ex)
          }
        }
      }
    } ~
    get {
      path("menu") {
        complete(StartupServiceImpl.getMenu)
      }
    } ~
    get {
      path("removeItemFromMenu" / IntNumber) { itemId =>
        onComplete(StartupServiceImpl.removeItemFromMenu(itemId)) {
          case util.Success(value) if value == 1 =>
            val menulink = s"http://localhost:8081/menu"
            complete(s"Item removed successfully\nHere is your menu link:\n $menulink")
          case util.Success(res) =>
            val removeItemFromMenuLink = s"http://localhost:8081/removeItemFromMenu"
            complete(s"Item is not removed successfully\nPlease try again\nHere is the link to remove item from menu : $removeItemFromMenuLink")
          case util.Failure(ex) => complete(ex)
        }
      }
    } ~
    path("update-menu") {
      post {
        entity(as[Menu]) { item =>
          onComplete(StartupServiceImpl.editMenuItem(item)) {
            case util.Success(value) if value == 1 =>
              val menulink = "http://localhost:8081/menu"
              complete(s"Updated successfully\nHere is your menu link:\n $menulink")
            case util.Success(res) =>
              val linkToUpdateItem = s"http://localhost:8081/update-menu"
              complete(s"Item is not Updated successfully\nPlease try again\nHere is the link to update item in menu : $linkToUpdateItem")
            case util.Failure(ex) => complete(ex)
          }
        }
      }
    }
}
