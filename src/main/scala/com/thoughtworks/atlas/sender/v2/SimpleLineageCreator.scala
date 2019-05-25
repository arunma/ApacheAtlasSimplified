package com.thoughtworks.atlas.sender.v2

import com.thoughtworks.atlas.sender.fixtures
import org.apache.atlas.AtlasClientV2
import org.apache.atlas.hook.AtlasHook
import org.apache.atlas.model.instance.{AtlasEntity, EntityMutationResponse}
import org.apache.atlas.model.notification.HookNotification
import org.apache.atlas.utils.AtlasJson
import org.apache.hadoop.security.UserGroupInformation

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent._

object SimpleLineageCreator extends App {

  implicit val ec = ExecutionContext.global
  import AtlasEntityTypes.ops._
  import AtlasEntityTypes._

  val fields = fixtures.fields.map(_.toAtlas)
  val users = fixtures.users.map(_.toAtlas)

  val sourceAtlas = fixtures.songsSourceDataset.toAtlas
  val dest1Atlas = fixtures.songsByCityDataset.toAtlas
  val dest2Atlas = fixtures.songsByCountryDataset.toAtlas
  val processAtlas = fixtures.songsPipeline.toAtlas

  val persistableEntities = fields ++ users ++ List(sourceAtlas, dest1Atlas, dest2Atlas, processAtlas)

  //AtlasWriter.store(List(persistableEntities)) // For using AtlasHook
  private val response= AtlasRestWriter.store(persistableEntities)
  response.getUpdatedEntities.asScala.foreach(println)

  Thread.sleep(2000)
}

class AtlasKafkaWriter extends AtlasHook {

  def store [A<:AtlasEntity](entities: List[A])(implicit ec: ExecutionContext): Future[Unit] = Future {
    import scala.collection.JavaConverters._
    blocking {
      val atlasUser = AtlasHook.getUser()
      val user = if(atlasUser == null || atlasUser.isEmpty) "Anonymous" else atlasUser

      val entityExt = new AtlasEntity.AtlasEntitiesWithExtInfo()
      entities.foreach(entityExt.addEntity)

      val request: HookNotification = new HookNotification.EntityCreateRequestV2(user, entityExt)
      val ugi = UserGroupInformation.getCurrentUser

      println (s"Request ${AtlasJson.toJson(request)}")
      this.notifyEntities(List(request).asJava, ugi)

    }
  }
}

object AtlasRestWriter {

  val atlasClientV2 = new AtlasClientV2(Array("http://ec2-52-221-185-64.ap-southeast-1.compute.amazonaws.com:21000"), Array("admin", sys.env("ATLAS_PASSWORD")))

  def store(entities: List[AtlasEntity]): EntityMutationResponse = {

    val atlasUser = AtlasHook.getUser()
    val user = if (atlasUser == null || atlasUser.isEmpty) "Anonymous" else atlasUser

    val entityExt = new AtlasEntity.AtlasEntitiesWithExtInfo()
    entities.foreach(entityExt.addEntity)
    println(s"Request ${AtlasJson.toJson(entityExt)}")

    atlasClientV2.createEntities(entityExt)
  }

}
