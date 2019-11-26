package com.knoldus.cafeteriamgmt.data.model
import slick.jdbc.MySQLProfile.api._

case class PaymentDetails(paymentId:String,
                          totalAmount:Double,
                          status:Boolean)

trait PaymentDetailsTable {
  class PaymentDetailsTable(tag:Tag) extends Table[PaymentDetails](tag, "PAYMENT_DETAILS"){
    def paymentId = column[String]("paymentId")
    def totalAmount = column[Double]("totalAmount")
    def status = column[Boolean]("status")
    def * = (paymentId, totalAmount, status).<>(PaymentDetails.tupled, PaymentDetails.unapply)
  }

  lazy val paymentDetailsTable = TableQuery[PaymentDetailsTable]
}
