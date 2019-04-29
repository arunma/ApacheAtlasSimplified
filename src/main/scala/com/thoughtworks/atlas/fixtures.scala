package com.thoughtworks.atlas

import java.util.Date

import com.thoughtworks.atlas.models._

object fixtures {

  val ownerSource = DemoUser("SourceOwner", "SourceOwner", "Steward of the source dataset", "CCA", "source@source.com")
  val ownerDest1 = DemoUser("DestOwner1", "DestOwner1", "Steward of the destination dataset1", "Dest1", "dest1@dest.com")
  val ownerDest2 = DemoUser("DestOwner2", "DestOwner2", "Steward of the destination dataset2", "Dest2", "dest2@dest.com")
  val subscriber1 = DemoUser("Subscriber 1", "Subscriber 1", "Subscriber 1 of the source dataset", "Risk", "subscriber1@company.com")
  val subscriber2 = DemoUser("Subscriber 2", "Subscriber 2", "Subscriber 2 of the source dataset", "Finance", "subscriber2@company.com")

  val users = List(ownerSource, ownerDest1, ownerDest2, subscriber1, subscriber2)

  val sourceFields = List(
    DemoField("accountID@Source", "accountID", "Account Id of the client", "String", primaryKey = true),
    DemoField("maritalStatus@Source", "maritalStatus", "Single/Married", "String"),
    DemoField("creditLimit@Source", "creditLimit", "Single/Married", "Double"),
    DemoField("gender@Source", "gender", "Gender of the client", "String"),
    DemoField("education@Source", "education", "High School/Polytechnics/Undergraduate/Master", "String"),
    DemoField("income@Source", "income", "Single/Married", "Double"),
    DemoField("accountSetupStatus@Source", "accountSetupStatus", "Active or In progress", "String"),
    DemoField("age@Source", "age", "Age of the client", "Integer")
  )

  val destFields1 = List(
    DemoField("accountID@Dest1", "accountID", "Account Id of the client", "String", primaryKey = true),
    DemoField("maritalStatus@Dest1", "maritalStatus", "Single/Married", "String"),
    DemoField("creditLimit@Dest1", "creditLimit", "Single/Married", "Double"),
    DemoField("gender@Dest1", "gender", "Gender of the client", "String")
  )


  val destFields2 = List(
    DemoField("education@Dest2", "education", "High School/Polytechnics/Undergraduate/Master", "String"),
    DemoField("income@Dest2", "income", "Single/Married", "Double"),
    DemoField("accountSetupStatus@Dest2", "accountSetupStatus", "Active or In progress", "String"),
    DemoField("age@Dest2", "age", "Age of the client", "Integer")
  )

  val fields = sourceFields ++ destFields1 ++ destFields2

  val sourceDataSet = DemoDataSet(
    "demographics-2019-04-26",
    "demographics",
    "Input data for Customer Demographics Information",
    "Fact",
    "SourceApp",
    new Date(),
    "demographics*",
    Delimited(","),
    "file location",
    Option(100.0),
    Parquet,
    sourceFields,
    List(subscriber1, subscriber2)
  )

  val destDataSet1 = DemoDataSet(
    "dest1-demographics-2019-04-26",
    "dest1-demographics",
    "Destination data 1 for Customer Demographics Information",
    "Fact",
    "SourceApp",
    new Date(),
    "dest1-demographics*",
    Delimited(","),
    "file location",
    Option(100.0),
    Parquet,
    destFields1,
    List(subscriber2)
  )


  val destDataSet2 = DemoDataSet(
    "dest2-demographics-2019-04-26",
    "dest2-demographics",
    "Destination data 2 for Customer Demographics Information",
    "Fact",
    "SourceApp",
    new Date(),
    "dest2-demographics*",
    Delimited(","),
    "file location",
    Option(100.0),
    Parquet,
    destFields2,
    List(subscriber1)
  )


  val demoProcess = DemoProcess(
    "TransformationProcess@2019-04-25",
    "TransformationProcess",
    "Transformation process that converts the SourceApp data into the Destination 1 and Destination 2 datasets",
    List("Remove Special characters", "Replace Null values with defaults", "Check datatypes", "Persist to S3"),
    List(fixtures.sourceDataSet),
    List(fixtures.destDataSet1, fixtures.destDataSet2),
    new Date(),
    new Date(System.currentTimeMillis + 10000),
    "Daily Job User"
  )


}
