package com.knoldus.cafeteriamgmt.data.dao

import com.knoldus.cafeteriamgmt.data.model.Menu
import scala.concurrent.Future

trait SchemaDao {
  def getMenuData: Future[Seq[Menu]]
  def insertIntoMenu(menu: Menu): Future[Int]
  def deleteItemFromMenu(itemId: Int): Future[Int]
  def updateMenuItem(item: Menu): Future[Int]
}
