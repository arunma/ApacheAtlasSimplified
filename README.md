# Apache Atlas Simplified

Apache Atlas is a great piece of software. While the project aims to be the Governance framework for Big Data platforms, it could be used for storing the metadata and tracking lineage for any piece of software that deals with data that requires this feature.

This project holds the minimal set of things that you require to create a custom model in Atlas and populate a lineage.  As it stands, it has the following  components :

 1. A custom Atlas model (extended_data.json) - creates DemoProcess, DemoDataSet, DemoUser, DemoField
 2. Simplified main program for creating sample entries for : 
    1. SourceDataSet - with fields, owners and subscribers
    2. DestinationDataSet1
    3. DestinationDataSet2
    4. DemoProcess that ties the source to destination datasets
 3. Case classes representing the Demo entities
 4. A Typeclass that converts the case classes into Referenceable and AtlasEntity
 5. TODO: Kafka subscriber of the Atlas ENTITY_ENTITIES (as a showcase of how Ranger works) 


## Upload model
 
```$bash
curl -u admin:admin -ik -H "Content-Type: application/json" -X POST http://localhost:21000/api/atlas/v2/types/typedefs -d @end_content_fact.json
```
## Note

1. There are two versions of the program catering to both Atlas Version 1 and Version 2 APIs.
2. The main programs have implementations for both REST and Kafka interfaces of Atlas

### Prerequisites

1. Atlas Installation (1.1.0) - Embedded HBase and Kafka would do just fine.

### Testcases

Much to the disappointment of my colleagues, this project does NOT have a single testcase. YOLO.


### Screenshots

TODO