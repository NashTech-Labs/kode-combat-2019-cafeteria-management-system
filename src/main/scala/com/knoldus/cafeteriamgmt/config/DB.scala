package com.knoldus.cafeteriamgmt.config

import slick.jdbc.JdbcProfile

trait DB {
  val driver: JdbcProfile  = slick.jdbc.MySQLProfile
  import driver.api._
  val db: Database = Database.forConfig("mysql")

  def close(): Unit = {
    db.close()
  }
}

