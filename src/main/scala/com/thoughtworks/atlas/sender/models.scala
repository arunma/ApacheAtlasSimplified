package com.thoughtworks.atlas.sender

import java.util.Date

import org.apache.atlas.v1.model.instance.Referenceable

object models {

  sealed trait AtlasAsset {
    val qualifiedName: String
    def typeName: String
    val name: String
    val description: String
  }

  //Dataset
  case class PlayListDataset(
                                  override val qualifiedName: String,
                                  override val name: String,
                                  override val description: String,
                                  dataShape: String,
                                  sourceSystem: String,
                                  createTime: Date,
                                  filePattern: String,
                                  fileFormat: FileFormat,
                                  location: String,
                                  purity: Option[Double],
                                  outputFormat: FileFormat,
                                  fields: List[PlayListField] = List.empty[PlayListField],
                                  subscribers: List[PlayListUser] = List.empty[PlayListUser],
                                  owner: String,
                                  retention: Int = 90,
                                  validity: Int = 90,
                                  override val typeName: String = "PlayListDataset"
                                     ) extends Referenceable with AtlasAsset

  //Field
  case class PlayListField(
                                     override val qualifiedName: String,
                                     override val name: String,
                                     override val description: String,
                                     dataType: String,
                                     owner: String,
                                     primaryKey: Boolean = false,
                                     override val typeName: String = "PlayListField"
                                   ) extends Referenceable with AtlasAsset


  //User
  case class PlayListUser(
                                    override val qualifiedName: String,
                                    override val name: String,
                                    override val description: String,
                                    sourceSystem: String,
                                    email: String,
                                    owner: String,
                                    override val typeName: String = "PlayListUser"
                                  ) extends Referenceable with AtlasAsset


  //Pipeline
  case class PlayListPipeline(
                               override val qualifiedName: String,
                               override val name: String,
                               override val description: String,
                               operations: List[String],
                               inputs: List[PlayListDataset],
                               outputs: List[PlayListDataset],
                               startTime: Date,
                               endTime: Date,
                               user: String,
                               override val typeName: String = "PlayListPipeline"
                                      ) extends Referenceable with AtlasAsset


  sealed trait FileFormat

  case class Delimited(delimiter: String) extends FileFormat

  case object Avro extends FileFormat

  case object Parquet extends FileFormat

}
