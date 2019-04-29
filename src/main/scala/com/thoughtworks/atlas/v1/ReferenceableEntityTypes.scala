package com.thoughtworks.atlas.v1

import java.lang.{Boolean => JBoolean}
import java.util

import com.thoughtworks.atlas.models._
import org.apache.atlas.v1.model.instance.Referenceable
import simulacrum.typeclass

import scala.collection.JavaConverters._
import scala.language.implicitConversions

@typeclass trait ReferenceableEntityTypes[A <: AtlasAsset] {
  self =>
  def toReference(a: A): Referenceable
}

object ReferenceableEntityTypes {

  import ReferenceableEntityTypes.ops._

  private val NameAttribute = "name"
  private val QualifiedName = "qualifiedName"
  implicit val demoDataSetAtlas: ReferenceableEntityTypes[DemoDataSet] = new ReferenceableEntityTypes[DemoDataSet] {
    override def toReference(demo: DemoDataSet): Referenceable = {
      val entity = new Referenceable(demo.typeName, new util.HashMap[String,AnyRef]())
      entity.set(NameAttribute, demo.name)
      entity.set(QualifiedName, demo.qualifiedName)
      entity.set("description", demo.description)
      entity.set("dataShape", demo.dataShape)
      entity.set("sourceSystem", demo.sourceSystem)
      entity.set("createTime", demo.createTime)
      entity.set("filePattern", demo.filePattern)
      entity.set("fileFormat", demo.fileFormat.toString)
      entity.set("location", demo.location)
      entity.set("fields", demo.fields.map(_.toReference).asJava)
      entity.set("retention", demo.retention)
      entity.set("validity", demo.validity)
      entity.set("purity", demo.purity.getOrElse(0))
      entity.set("outputFormat", demo.outputFormat.toString)
      entity.set("owner", demo.owner)
      entity.set("subscribers", demo.subscribers.map(_.toReference).asJava)
      entity
    }
  }
  implicit val demoUserAtlas: ReferenceableEntityTypes[DemoUser] = new ReferenceableEntityTypes[DemoUser] {
    override def toReference(demo: DemoUser): Referenceable = {
      val entity = new Referenceable(demo.typeName, new util.HashMap[String,AnyRef]())
      entity.set(QualifiedName, demo.qualifiedName)
      entity.set(NameAttribute, demo.name)
      entity.set("sourceSystem", demo.sourceSystem)
      entity.set("description", demo.description)
      entity.set("email", demo.email)
      entity.set("owner", demo.owner)
      entity
    }
  }

  implicit val demoFieldAtlas: ReferenceableEntityTypes[DemoField] = new ReferenceableEntityTypes[DemoField] {
    override def toReference(demo: DemoField): Referenceable = {
      val entity = new Referenceable(demo.typeName, new util.HashMap[String,AnyRef]())
      entity.set(QualifiedName, demo.qualifiedName)
      entity.set(NameAttribute, demo.name)
      entity.set("dataType", demo.dataType)
      entity.set("description", demo.description)
      entity.set("owner", demo.owner)
      entity.set("primaryKey", new JBoolean(demo.primaryKey))
      entity
    }
  }

  implicit val demoProcessAtlas: ReferenceableEntityTypes[DemoProcess] = new ReferenceableEntityTypes[DemoProcess] {
    override def toReference(demo: DemoProcess): Referenceable = {
      val entity = new Referenceable(demo.typeName, new util.HashMap[String,AnyRef]())
      entity.set(QualifiedName, demo.qualifiedName)
      entity.set(NameAttribute, demo.name)
      entity.set("operations", demo.operations.toArray)
      entity.set("inputs", demo.inputs.map(_.toReference).asJava)
      entity.set("outputs", demo.outputs.map(_.toReference).asJava)
      entity.set("startTime", demo.startTime)
      entity.set("endTime", demo.endTime)
      entity.set("user", demo.user)
      entity
    }
  }
}

