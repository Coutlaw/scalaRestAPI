package com.example.practice

package com.example.practice
import cats.effect.IO
import io.circe.Json
import org.http4s._
import org.http4s.implicits._


trait APIcall {
  class HelloWorldSpec {

    case class Greeting(message: String)

    val retAPICall: Response[IO] ={
      val getCall = Request[IO](Method.GET, Uri.uri("call"))
      new HelloWorldService[IO].service.orNotFound(getCall).unsafeRunSync()
    }

    //    val retHelloWorld: Response[IO] = {
    //      val getHW = Request[IO](Method.POST, Uri.uri("/hello/world"))
    //        .withBody(Greeting("Hello, world").asJson)
    //      getHW.flatMap(f => HelloWorldServer.service.orNotFound(f)).unsafeRunSync()
    //    }

    def showCallResults() = retAPICall.bodyAsText.compile.toVector.unsafeRunSync()
      .foreach(println)
  }
}
