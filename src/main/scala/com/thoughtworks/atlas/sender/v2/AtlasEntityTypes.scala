package com.thoughtworks.atlas.sender.v2

import java.lang.{Boolean => JBoolean}

import com.thoughtworks.atlas.sender.models._
import org.apache.atlas.model.instance.AtlasEntity
import org.apache.atlas.v1.model.instance.Id
import simulacrum.typeclass

import scala.collection.JavaConverters._
import scala.language.implicitConversions

@typeclass trait AtlasEntityTypes[A <: AtlasAsset] {
  self =>
  def toAtlas(a: A): AtlasEntity
}

object AtlasEntityTypes {

  import AtlasEntityTypes.ops._

  private val NameAttribute = "name"
  private val QualifiedName = "qualifiedName"
  implicit val demoDataSetAtlas: AtlasEntityTypes[PlayListDataset] = new AtlasEntityTypes[PlayListDataset] {
    override def toAtlas(demo: PlayListDataset): AtlasEntity = {
      val entity = new AtlasEntity(demo.typeName, NameAttribute, demo.name)
      entity.setGuid(demo.qualifiedName)
      entity.setAttribute("id", new Id(demo.qualifiedName, 1, "PlayListDataset"))
      entity.setAttribute(QualifiedName, demo.qualifiedName)
      entity.setAttribute("description", demo.description)
      entity.setAttribute("dataShape", demo.dataShape)
      entity.setAttribute("sourceSystem", demo.sourceSystem)
      entity.setAttribute("createTime", demo.createTime)
      entity.setAttribute("filePattern", demo.filePattern)
      entity.setAttribute("fileFormat", demo.fileFormat.toString)
      entity.setAttribute("location", demo.location)
      entity.setAttribute("fields", demo.fields.map(_.toAtlas).asJava)
      entity.setAttribute("retention", demo.retention)
      entity.setAttribute("validity", demo.validity)
      entity.setAttribute("purity", demo.purity.getOrElse(0))
      entity.setAttribute("outputFormat", demo.outputFormat)
      entity.setAttribute("owner", "Arun")

      entity
    }
  }
  implicit val demoUserAtlas: AtlasEntityTypes[PlayListUser] = new AtlasEntityTypes[PlayListUser] {
    override def toAtlas(demo: PlayListUser): AtlasEntity = {
      val entity = new AtlasEntity(demo.typeName, NameAttribute, demo.name)
      entity.setGuid(demo.qualifiedName)
      entity.setAttribute("id", new Id(demo.qualifiedName, 1, "PlayListUser"))
      entity.setAttribute(QualifiedName, demo.qualifiedName)
      entity.setAttribute("sourceSystem", demo.sourceSystem)
      entity.setAttribute("description", demo.description)
      entity.setAttribute("email", demo.email)
      entity.setAttribute("owner", "Arun")
      entity
    }
  }

  implicit val demoFieldAtlas: AtlasEntityTypes[PlayListField] = new AtlasEntityTypes[PlayListField] {
    override def toAtlas(demo: PlayListField): AtlasEntity = {
      val entity = new AtlasEntity(demo.typeName, NameAttribute, demo.name)
      entity.setGuid(demo.qualifiedName)
      entity.setAttribute("id", new Id(demo.qualifiedName, 1, "PlayListField"))
      entity.setAttribute(QualifiedName, demo.qualifiedName)
      entity.setAttribute("dataType", demo.dataType)
      entity.setAttribute("description", demo.description)
      entity.setAttribute("owner", "Arun")
      entity.setAttribute("primaryKey", new JBoolean(demo.primaryKey))
      entity
    }
  }

  implicit val demoProcessAtlas: AtlasEntityTypes[PlayListPipeline] = new AtlasEntityTypes[PlayListPipeline] {
    override def toAtlas(demo: PlayListPipeline): AtlasEntity = {
      val entity = new AtlasEntity(demo.typeName, NameAttribute, demo.name)
      entity.setGuid(demo.qualifiedName)
      entity.setAttribute("id", new Id(demo.qualifiedName, 1, "PlayListPipeline"))
      entity.setAttribute(QualifiedName, demo.qualifiedName)
      entity.setAttribute("operations", demo.operations.toArray)
      entity.setAttribute("inputs", demo.inputs.map(_.toAtlas).asJava)
      entity.setAttribute("outputs", demo.outputs.map(_.toAtlas).asJava)
      entity.setAttribute("startTime", demo.startTime)
      entity.setAttribute("endTime", demo.endTime)
      entity.setAttribute("user", demo.user)
      entity
    }
  }
}

