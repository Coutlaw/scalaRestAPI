package com.example.practice

import cats.effect.Effect
import io.circe.Json
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl


// rebuild the project
//sbt -sbt-version 1.1.1 new http4s/http4s.g8

class HelloWorldService[F[_]: Effect] extends Http4sDsl[F] {
  val service: HttpService[F] = {
    HttpService[F] {

      case GET -> Root / "hello" / name =>
        Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))

      case GET -> Root / "whatever" =>
        Ok(Json.obj("message" -> Json.fromString("Hello ")))

      case GET -> Root / "getWeather" =>
        Ok(scala.io.Source.fromURL(
          "http://api.openweathermap.org/data/2.5/weather?lat=32.817386&lon=-79.953325&APPID=3d4e65594771389b32656ae1be96b544"
        ).mkString)
    }
  }
}
