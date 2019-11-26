package com.knoldus.cafeteriamgmt.services

import com.knoldus.cafeteriamgmt.config.DB
import com.knoldus.cafeteriamgmt.data.dao.{SchemaDao, SchemaDaoImpl}
import com.knoldus.cafeteriamgmt.data.model.Menu
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
trait StartupServiceImpl {
  val schemaDao: SchemaDao
  def getMenu: Future[Seq[Menu]] = schemaDao.getMenuData
  def addNewItem(menu: Menu): Future[Int] = schemaDao.insertIntoMenu(menu)
  def removeItemFromMenu(itemId: Int): Future[Int] = schemaDao.deleteItemFromMenu(itemId)
  def editMenuItem(item: Menu): Future[Int] = schemaDao.updateMenuItem(item)
}
object StartupServiceImpl extends StartupServiceImpl {
  val schemaDao = new SchemaDaoImpl with DB
}

