import zio.{ULayer, ZIO, ZLayer}
import zio.test.Assertion._
import zio.test._

trait SomeEnv

object SomeEnv {
  lazy val live: ULayer[SomeEnv] = ZLayer.succeed(new SomeEnv {})
}

object TmpSpec extends ZIOSpecDefault {
  def spec =
    suite("TmpSpec")(
      test("failed") {
        assertM(ZIO.runtime[SomeEnv].as(false))(isTrue)
      },
    ).provideShared(SomeEnv.live)
}
