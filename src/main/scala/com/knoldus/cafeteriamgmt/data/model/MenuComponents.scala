package com.knoldus.cafeteriamgmt.data.model

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape


trait MenuComponents {

  val menuTable = TableQuery[MenuTable]

  class MenuTable(tag: Tag) extends Table[Menu](tag, "MENUTABLE") {

    def * : ProvenShape[Menu] = (name, id, price, stock).<>(Menu.tupled, Menu.unapply)

    def id: Rep[Int] = column[Int]("id")

    def name: Rep[String] = column[String]("name")

    def price: Rep[Int] = column[Int]("price")

    def stock: Rep[Int] = column[Int]("stock")

  }

}
