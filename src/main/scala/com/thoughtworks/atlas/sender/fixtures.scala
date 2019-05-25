package com.thoughtworks.atlas.sender

import java.util.Date

import com.thoughtworks.atlas.sender.models.{PlayListField, _}

object fixtures {

  val sourceOwner = PlayListUser("DataSourceOwner", "DataSourceOwner", "Steward of the Source dataset", "Source", "sourceowner@songs.com", "Source Owner")
  val streamsByCityOwner = PlayListUser("StreamsByCityOwner ", "StreamsByCityOwner", "Steward of the Streams By City Dataset", "StreamCityApp", "songsByCityOwner@songs.com", "Streams By City Owner")
  val streamsByCountryOwner = PlayListUser("StreamsByCountryOwner ", "StreamsByCountryOwner", "Steward of the Streams By Country Dataset", "StreamCountryApp", "songsByCountryOwner@songs.com", "Streams By Country Owner")

  val sonySubscriber = PlayListUser("Sony Music", "Sony Music", "Sony Music Entertainment Group ", "", "alex@sony.com", "")
  val warnerSubscriber = PlayListUser("Warner Music", "Warner Music", "Warner Music Group", "", "tania@warner.com", "")

  val users = List(sourceOwner, streamsByCityOwner, streamsByCountryOwner, sonySubscriber, warnerSubscriber)


  val songsSourceDatasetQf = "PlaylistDataset-2019-05-20"
  val songsByCityDatasetQf = "StreamsByCity-2019-05-20"
  val songsByCountryDatasetQf = "StreamsByCountry-2019-05-20"

  val songsSourceFields = List(
    PlayListField("userId@Source", "userId", "User Account Identifier", "String", songsSourceDatasetQf, primaryKey = true),
    PlayListField("trackGid@Source", "trackId", "Track Identifier of the Song", "String", songsSourceDatasetQf),
    PlayListField("playMs@Source", "playMs", "Number of milliseconds the song was played", "Double", songsSourceDatasetQf),
    PlayListField("streamSource@Source", "streamSource", "Playlist/Artist's Page - the Source of the song", "String", songsSourceDatasetQf),
    PlayListField("device@Source", "device", "Device that this Song was played", "String", songsSourceDatasetQf),
    PlayListField("city@Source", "city", "Location - City in which the song was played", "String", songsSourceDatasetQf),
    PlayListField("country@Source", "country", "Location - Country in which the song was played", "String", songsSourceDatasetQf)
  )

  val songsByCityFields = List(
    PlayListField("trackGid@Source", "trackId", "Track Identifier of the Song", "String", songsByCityDatasetQf),
    PlayListField("playMs@Source", "playMs", "Number of milliseconds the song was played", "Double", songsByCityDatasetQf),
    PlayListField("streamSource@Source", "streamSource", "Playlist/Artist's Page - the Source of the song", "String", songsByCityDatasetQf),
    PlayListField("device@Source", "device", "Device that this Song was played", "String", songsByCityDatasetQf),
    PlayListField("city@Source", "city", "Location - City in which the song was played", "String", songsByCityDatasetQf)
  )


  val songsByCountryFields = List(
    PlayListField("trackGid@Source", "trackId", "Track Identifier of the Song", "String", songsByCountryDatasetQf),
    PlayListField("playMs@Source", "playMs", "Number of milliseconds the song was played", "Double", songsByCountryDatasetQf),
    PlayListField("streamSource@Source", "streamSource", "Playlist/Artist's Page - the Source of the song", "String", songsByCountryDatasetQf),
    PlayListField("device@Source", "device", "Device that this Song was played", "String", songsByCountryDatasetQf),
    PlayListField("country@Source", "country", "Location - Country in which the song was played", "String", songsByCountryDatasetQf)
  )

  val fields = songsSourceFields ++ songsByCityFields ++ songsByCountryFields


  val songsSourceDataset = PlayListDataset(
    songsSourceDatasetQf,
    "PlayListDataset",
    "Master information for what tracks have been listened by users at which location",
    "Fact",
    "PlayListAppSource",
    new Date(),
    "songs*",
    Delimited(","),
    "source file location",
    Option(100.0),
    Parquet,
    songsSourceFields,
    List(sonySubscriber, warnerSubscriber),
    owner = "ECF Data Owner"
  )


  val songsByCityDataset = PlayListDataset(
    songsByCityDatasetQf,
    "StreamsByCity",
    "Filtered dataset consisting of tracks listened by major cities",
    "Fact",
    "PlayListAppSource",
    new Date(),
    "streams-by-city*",
    Delimited(","),
    "by city file location",
    Option(100.0),
    Parquet,
    songsByCityFields,
    List(warnerSubscriber),
    owner = "Streams By City Owner"
  )


  val songsByCountryDataset = PlayListDataset(
    songsByCountryDatasetQf,
    "StreamsByCountry",
    "Filtered dataset consisting of tracks listened by countries",
    "Fact",
    "PlayListAppSource",
    new Date(),
    "streams-by-country*",
    Delimited(","),
    "by country file location",
    Option(100.0),
    Parquet,
    songsByCountryFields,
    List(sonySubscriber),
    owner = "Streams By Country Owner"
  )


  val songsPipeline = PlayListPipeline(
    "PlayListPipeline@2019-05-20",
    "PlayListPipeline",
    "PlayListPipeline that generates the StreamsByCity and StreamsByCountry datasets from the PlayList dataset",
    List("Remove Special characters", "Replace Null values with defaults", "Check datatypes", "Generate 'by city'", "Generate 'by country'"),
    List(fixtures.songsSourceDataset),
    List(fixtures.songsByCityDataset, fixtures.songsByCountryDataset),
    new Date(),
    new Date(System.currentTimeMillis + 10000),
    "SongsPipelineDaemonUser"
  )
}
