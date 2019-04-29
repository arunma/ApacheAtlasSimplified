package com.thoughtworks.atlas.v1

import com.thoughtworks.atlas.fixtures
import org.apache.atlas.AtlasClient
import org.apache.atlas.`type`.AtlasType
import org.apache.atlas.hook.AtlasHook
import org.apache.atlas.model.notification.HookNotification
import org.apache.atlas.utils.AtlasJson
import org.apache.atlas.v1.model.instance.Referenceable
import org.apache.atlas.v1.model.notification.HookNotificationV1.EntityCreateRequest
import org.apache.hadoop.security.UserGroupInformation

import scala.concurrent.{ExecutionContext, Future, _}

object SimpleLineageCreator extends App {

  import ReferenceableEntityTypes._
  import ReferenceableEntityTypes.ops._
  implicit val ec = ExecutionContext.global

  val fields = fixtures.fields.map(_.toReference)
  val users = fixtures.users.map(_.toReference)

  val sourceAtlas = fixtures.sourceDataSet.toReference
  val dest1Atlas = fixtures.destDataSet1.toReference
  val dest2Atlas = fixtures.destDataSet2.toReference
  val processAtlas = fixtures.demoProcess.toReference

  val persistableEntities = fields ++ users ++ List(sourceAtlas, dest1Atlas, dest2Atlas, processAtlas)

  // For using AtlasHook
  /*val atlasWriter = new AtlasKafkaWriter
  atlasWriter.store(persistableEntities)*/
  AtlasRestWriter.store(persistableEntities)
  Thread.sleep(2000)

}

class AtlasKafkaWriter extends AtlasHook {

  def store (entities: List[Referenceable])(implicit ec: ExecutionContext): Future[Unit] = Future {
    import scala.collection.JavaConverters._
    blocking {
      val atlasUser = AtlasHook.getUser()
      val user = if(atlasUser == null || atlasUser.isEmpty) "Anonymous" else atlasUser

      val request: HookNotification = new EntityCreateRequest(user, entities.asJava)
      val ugi = UserGroupInformation.getCurrentUser

      println (s"Request ${AtlasJson.toJson(request)}")
      this.notifyEntities(List(request).asJava, ugi)

    }
  }
}

object AtlasRestWriter {

  val atlasClient = new AtlasClient(Array("http://localhost:21000"), Array("admin", "admin"))

  def store(entities: List[Referenceable]) = {
    //atlasClient.createEntity(entities.asJavaCollection)
    entities.foreach{ entity =>
      val json = AtlasType.toV1Json(entity)
      println(s"$json")
      atlasClient.createEntity(json)
    }
  }
}
