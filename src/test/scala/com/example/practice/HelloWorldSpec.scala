package com.example.practice

import cats.effect.IO
import io.circe.Json
import org.http4s._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult

class HelloWorldSpec extends org.specs2.mutable.Specification {

  "Running Tests" >> {
    "return 200" >> {
      uriReturns200()
    }
    "return hello world test" >> {
      uriReturnsHelloWorld()
    }
    "API call test" >> {
      uriReturnsAPICall()
    }
  }

  private[this] val retHelloWorld: Response[IO] = {
    val getHW = Request[IO](Method.GET, Uri.uri("hello/world"))
    new HelloWorldService[IO].service.orNotFound(getHW).unsafeRunSync()
  }
  private[this] val retAPICall: Response[IO] ={
    val getCall = Request[IO](Method.GET, Uri.uri("call"))
    new HelloWorldService[IO].service.orNotFound(getCall).unsafeRunSync()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retHelloWorld.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsHelloWorld(): MatchResult[String] =
    retHelloWorld.as[String].unsafeRunSync() must beEqualTo("{\"message\":\"Hello, world\"}")

  private[this] def uriReturnsAPICall(): MatchResult[String] =
    retAPICall.as[String].unsafeRunSync() must be_!=/("Not Found")
}
