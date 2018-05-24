package com.example.practice
import cats.effect.IO
import org.http4s.client.blaze._
import org.http4s.circe._
import io.circe._, io.circe.syntax._, io.circe.parser._
import io.circe.optics.JsonPath._

import org.http4s._
import org.http4s.implicits._

object Client extends App {
  import io.circe.Json
  case class Greeting(message: String)

//  val httpClient = Http1Client[IO]().unsafeRunSync
//  val getAPICall = httpClient.expect[Json](
//    "http://api.openweathermap.org/data/2.5/weather?lat=32.817386&lon=-79.953325&APPID=3d4e65594771389b32656ae1be96b544"
//  )
//  println(getAPICall)


  val getAPICall: Response[IO] ={
    val getCall = Request[IO](Method.GET, Uri.uri("getWeather"))
    new HelloWorldService[IO].service.orNotFound(getCall).unsafeRunSync()
  }

  val weatherResult: String = {
    getAPICall
      .as[String]
      .map(parse(_)
      .getOrElse(Json.Null)
      .findAllByKey("id")
      .headOption
      .map( _.toString().toInt)

      match{
        case Some(962) | Some(902) => "Hurricane, take shelter!"
        case Some(960) | Some(961) => "Violent storm warning"
        case Some(x) if x == 900 | x == 905 | x == 901 => "Extreme wind, possible tornadoes"
        case Some(800) => "Beautiful weather, go see for yourself!"
        case Some(x) if x > 800 => "Clouds but no rain overhead!"
        case Some(x) if x > 700 => "Debris in the sky such as smoke!"
        case Some(x) if x >= 600 => "Snowing!"
        case Some(x) if x >= 500 => "Raining!"
        case Some(x) if x >= 300 => "Drizzling!"
        case Some(x) if x >= 200 => "Stormy!"
        case _ => "Currently no status available!"
      })
  }.unsafeRunSync()


  val tag: String = "Current weather @ BoomTown Charleston: "

  println(tag+weatherResult)

}