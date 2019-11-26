package com.knoldus.cafeteriamgmt.data.model

import java.sql.Timestamp

case class UserOrder(userId:String,
                     orderId:String,
                     paymentId:String)
