package com.thoughtworks.atlas.sender.v1

import java.lang.{Boolean => JBoolean}
import java.util

import com.thoughtworks.atlas.sender.models._
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
  implicit val demoDataSetAtlas: ReferenceableEntityTypes[PlayListDataset] = new ReferenceableEntityTypes[PlayListDataset] {
    override def toReference(dataset: PlayListDataset): Referenceable = {
      val entity = new Referenceable(dataset.typeName, new util.HashMap[String,AnyRef]())
      entity.set(NameAttribute, dataset.name)
      entity.set(QualifiedName, dataset.qualifiedName)
      entity.set("description", dataset.description)
      entity.set("dataShape", dataset.dataShape)
      entity.set("sourceSystem", dataset.sourceSystem)
      entity.set("createTime", dataset.createTime)
      entity.set("filePattern", dataset.filePattern)
      entity.set("fileFormat", dataset.fileFormat.toString)
      entity.set("location", dataset.location)
      entity.set("fields", dataset.fields.map(_.toReference).asJava)
      entity.set("retention", dataset.retention)
      entity.set("validity", dataset.validity)
      entity.set("purity", dataset.purity.getOrElse(0))
      entity.set("outputFormat", dataset.outputFormat.toString)
      entity.set("owner", dataset.owner)
      entity.set("subscribers", dataset.subscribers.map(_.toReference).asJava)
      entity
    }
  }
  implicit val demoUserAtlas: ReferenceableEntityTypes[PlayListUser] = new ReferenceableEntityTypes[PlayListUser] {
    override def toReference(user: PlayListUser): Referenceable = {
      val entity = new Referenceable(user.typeName, new util.HashMap[String,AnyRef]())
      entity.set(QualifiedName, user.qualifiedName)
      entity.set(NameAttribute, user.name)
      entity.set("sourceSystem", user.sourceSystem)
      entity.set("description", user.description)
      entity.set("email", user.email)
      entity.set("owner", user.owner)
      entity
    }
  }

  implicit val demoFieldAtlas: ReferenceableEntityTypes[PlayListField] = new ReferenceableEntityTypes[PlayListField] {
    override def toReference(field: PlayListField): Referenceable = {
      val entity = new Referenceable(field.typeName, new util.HashMap[String,AnyRef]())
      entity.set(QualifiedName, field.qualifiedName)
      entity.set(NameAttribute, field.name)
      entity.set("dataType", field.dataType)
      entity.set("description", field.description)
      entity.set("owner", field.owner)
      entity.set("primaryKey", new JBoolean(field.primaryKey))
      entity
    }
  }

  implicit val demoProcessAtlas: ReferenceableEntityTypes[PlayListPipeline] = new ReferenceableEntityTypes[PlayListPipeline] {
    override def toReference(demo: PlayListPipeline): Referenceable = {
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

