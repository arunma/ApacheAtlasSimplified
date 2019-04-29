package com.thoughtworks.atlas

import java.util.Date

import org.apache.atlas.v1.model.instance.Referenceable

object models {

  sealed trait AtlasAsset {
    val qualifiedName: String

    def typeName: String

    val name: String
    val description: String
  }

  private val OwnerNameHardCoded = "Arun"

  case class DemoDataSet(
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
                          fields: List[DemoField] = List.empty[DemoField],
                          subscribers: List[DemoUser] = List.empty[DemoUser],
                          retention: Int = 90,
                          validity: Int = 90,
                          override val typeName: String = "DemoDataSet",
                          owner: String = OwnerNameHardCoded
                        ) extends Referenceable with AtlasAsset


  case class DemoProcess(
                          override val qualifiedName: String,
                          override val name: String,
                          override val description: String,
                          operations: List[String],
                          inputs: List[DemoDataSet],
                          outputs: List[DemoDataSet],
                          startTime: Date,
                          endTime: Date,
                          user: String,
                          override val typeName: String = "DemoProcess"
                        ) extends Referenceable with AtlasAsset

  case class DemoField(
                        override val qualifiedName: String,
                        override val name: String,
                        override val description: String,
                        dataType: String,
                        primaryKey: Boolean = false,
                        override val typeName: String = "DemoField",
                        owner: String = OwnerNameHardCoded
                      ) extends Referenceable with AtlasAsset


  case class DemoUser(
                       override val qualifiedName: String,
                       override val name: String,
                       override val description: String,
                       sourceSystem: String,
                       email: String,
                       override val typeName: String = "DemoUser",
                       owner: String = OwnerNameHardCoded
                     ) extends Referenceable with AtlasAsset


  sealed trait FileFormat

  case class Delimited(delimiter: String) extends FileFormat

  case object Avro extends FileFormat

  case object Parquet extends FileFormat

}
