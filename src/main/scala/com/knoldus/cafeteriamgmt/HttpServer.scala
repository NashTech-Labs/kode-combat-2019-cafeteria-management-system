package com.knoldus.cafeteriamgmt

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import com.knoldus.cafeteriamgmt.config.{CafeteriaMgmtConfig, DB}
import com.knoldus.cafeteriamgmt.controller.Routes
import com.knoldus.cafeteriamgmt.services.LoginServices
import scala.concurrent.ExecutionContext
import scala.io.StdIn

class HttpServer(implicit val system: ActorSystem, implicit val materializer: Materializer) extends CafeteriaMgmtConfig
  with Routes {

  override val repo: LoginServices = new LoginServices with DB

  def startServer(address: String, port: Int): Unit = {

    implicit val executor: ExecutionContext = system.dispatcher
    val bindingFuture = Http().bindAndHandle(routes, httpHost, httpPort)
    println(s"Server online at http://localhost:8081/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}

object HttpServer {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    val server = new HttpServer()
    server.startServer("localhost", 8081)

  }
}
