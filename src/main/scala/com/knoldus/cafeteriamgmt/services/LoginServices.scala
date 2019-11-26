package com.knoldus.cafeteriamgmt.services

import com.knoldus.cafeteriamgmt.data.dao.{LoginDao, UserDao}
import com.knoldus.cafeteriamgmt.data.model.{LoginDetail, RegisterDetail, UserDetail, UserDetails}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait LoginServices extends LoginDao with UserDao with UserDetails {

  def registerUser(registration: RegisterDetail): Future[Int] = {
    addLoginDetail(LoginDetail(registration.uid, registration.username, registration.password))
    addUserDetail(UserDetail(registration.uid, registration.name, registration.email))
  }

  def viewDetails: Future[Seq[UserDetail]] = {
    getLoginDetail
    getUserDetail
  }


  def login(value: LoginDetail): Boolean = {
    if (value.username == getUserDetail.map(_.map(x => x.email)) && value.password == getLoginDetail.map(_.map(_.password))) true
    else false
  }

}
