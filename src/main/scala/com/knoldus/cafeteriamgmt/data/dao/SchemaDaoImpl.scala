package com.knoldus.cafeteriamgmt.data.dao

import java.sql.Timestamp
import com.knoldus.cafeteriamgmt.config.DB
import com.knoldus.cafeteriamgmt.data.model.{Menu, MenuComponents, UserOrder, UserOrderTable}
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class SchemaDaoImpl(implicit val ex:ExecutionContext) extends SchemaDao with UserOrderTable with MenuComponents{
  this:DB =>

  def getMenuData: Future[Seq[Menu]] = db.run {
    menuTable.result
  }
  def insertIntoMenu(menu: Menu): Future[Int] = {
    db.run(DBIO.seq(
      menuTable.schema.createIfNotExists))
    db.run(menuTable += menu)
  }
  def deleteItemFromMenu(itemId: Int): Future[Int] = db.run {
    menuTable.filter(_.id === itemId).delete
  }
  def updateMenuItem(item: Menu): Future[Int] = db.run {
    menuTable.filter(_.id === item.id).map(_.stock).update(item.stock) andThen
      menuTable.filter(_.id === item.id).map(_.name).update(item.name) andThen
      menuTable.filter(_.id === item.id).map(_.price).update(item.price)
  }
}
