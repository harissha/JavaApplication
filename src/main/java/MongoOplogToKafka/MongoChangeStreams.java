package MongoOplogToKafka;

import com.mongodb.Block;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import org.bson.Document;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static java.util.Arrays.asList;

public class MongoChangeStreams{

    private HashSet<Document> gSet = new HashSet<>();
    private BlockingQueue<Document> theUpsertQueue = new LinkedBlockingQueue<>(500);
        /*private static final String PREFIX = "\"";
        private static final String SUFFIX = "\",";
        private static final String SEPARATOR = "\" : \"";*/

    /*public static String getMessage(EdsAuditLog edsAuditLog) {


        final StringBuilder theReturnValue = new StringBuilder("{ \"op\" : \"index\",");

        theReturnValue.append(PREFIX).append("shipToSiteName").append(SEPARATOR)
                .append(edsAuditLog.getShipToSiteName()).append(SUFFIX);

        theReturnValue.append(PREFIX).append("shipToSiteName").append(SEPARATOR)
                .append(edsAuditLog.getShipToSiteName()).append(SUFFIX);
        theReturnValue.append(PREFIX).append("shipToSiteName").append(SEPARATOR)
                .append(edsAuditLog.getShipToSiteName()).append(SUFFIX);
        theReturnValue.append(PREFIX).append("shipToSiteName").append(SEPARATOR)
                .append(edsAuditLog.getShipToSiteName()).append(SUFFIX);
        theReturnValue.append(PREFIX).append("shipToSiteName").append(SEPARATOR)
                .append(edsAuditLog.getShipToSiteName()).append(SUFFIX);

        theReturnValue.append(PREFIX).append("shipToSiteName").append(SEPARATOR)
                .append(edsAuditLog.getShipToSiteName()).append(PREFIX);
        theReturnValue.append("}");


        return String.valueOf(theReturnValue);
    }*/

    /*private static Object parseMongoOutput(final String aJsonString, final Class aClass) {
        final Gson theGsonObject = new Gson();
        return theGsonObject.fromJson(aJsonString, aClass);
    }*/


    public void changeStream(){

        //final Phaser phaser = new Phaser(1);

        try {
        /*MongoCollection<Document> eventCollection =
                new MongoClient(
                        new MongoClientURI("mongodb://eds_auditlog_user:cisco123@mngdb-ebf-stg-01.cisco.com:27048,mngdb-ebf-stg-02.cisco.com:27048,mngdb-ebf-stg-03.cisco.com:27048/eds_auditlog_db?replicatSet=ebf-stg-rep")
                        //new MongoClientURI("mongodb://localhost:27017/bookcollection?replicatSet=rs")
                ).getDatabase("eds_auditlog_db").getCollection("EdsAuditLog");*/


            MongoCollection<Document> eventCollection = MongoDBConnectionFactory.getCollection("oplog_tracking");
            //MongoCollection<Document> monitoringData = MongoDBConnectionFactory.getCollection("oplog_tracking_monitoring");

        /*//Long bsonTimestamp = null;
        // Performing a read operation on the collection.
        FindIterable<Document> fi = monitoringData.find();
        MongoCursor<Document> cursor = fi.iterator();
        BsonTimestamp bsonTimestamp = null;
        BsonDocument bsonDocument = null;
        BSONTimestamp bsonTimestamp1= null;
        Timestamp timestamp = null;
        Document doc = null;
        try {
            while(cursor.hasNext()) {
                //doc = new Document("ts", bsonTimestamp1);
                //System.out.println("mon Timestamp : "+cursor.next().get("timestamp"));
                bsonTimestamp1 = new BSONTimestamp((int)(((Long) cursor.next().get("timestamp"))/1000), 1 );
                *//*
                bsonTimestamp = new BsonTimestamp(Instant.ofEpochSecond((Long) cursor.next().get("timestamp")).toString());*//*
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        final CodecRegistry registry;
        doc = new Document("operationTime", bsonTimestamp1);
        bsonDocument = doc.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());
        System.out.println("BSONTimestamp : "+bsonDocument);
*/

        /*long i = 0;
        while (true) {
            Document doc = new Document();
            doc.put("i", i++);
            doc.put("even", i%2);
            eventCollection.insertOne(doc);
            System.out.println("inserted: " + doc);
            Thread.sleep(2000L + (long)(1000*Math.random()));
        }*/

        ChangeStreamIterable<Document> changes = eventCollection.watch(asList(
                Aggregates.match( and( asList(
                        in("operationType", asList("insert", "update", "replace", "delete"))

                        /*,
                        eq("fullDocument.even", 1L)
*/
                        ))
                )));

        changes.forEach(new Block<ChangeStreamDocument<Document>>() {

            @Override
            public void apply(ChangeStreamDocument<Document> t) {
                System.out.println("received: " + t.getFullDocument());
            }
        });


        /*
            //BsonDocument resumeToken1 = null;
            Block<ChangeStreamDocument<Document>> printBlock = new Block<ChangeStreamDocument<Document>>() {

                //BsonDocument resumeToken2 = null;
                public void apply(final ChangeStreamDocument<Document> changeStreamDocument) {

                    //System.out.println(" MyService::: "+changeStreamDocument.getFullDocument());
                *//*EdsAuditLog edsAuditLog = (EdsAuditLog) MongoChangeStreams
                        .parseMongoOutput(changeStreamDocument.getFullDocument().toJson(), EdsAuditLog.class);*//*

                    JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);
                    //JsonWriterSettings writerSettings = JsonWriterSettings.builder().outputMode(JsonMode.SHELL).build();
                    *//*System.out.println("full doc :: "+changeStreamDocument.getFullDocument().toJson(writerSettings)
                     *//**//*.replaceAll("ObjectId(.*)", "$1")
                        .replaceAll("ISODate(.*)","$1")
                        .replaceAll("\\((.*)\\)", "$1")*//**//*
                        .replaceAll("ObjectId\\((.*)\\)", "$1")
                        .replaceAll("ISODate\\((.*)\\)","$1")

                );*//*

                    //System.out.println("full doc :: "+changeStreamDocument.getFullDocument().toJson());
                    //System.out.println(edsAuditLog);

                *//*resumeToken2=changeStreamDocument.getResumeToken();
                System.out.println("resumeToken2 : "+resumeToken2);*//*
                    //resumeToken1 =resumeToken2;
                    *//*System.out.println(changeStreamDocument.getFullDocument());
                    theUpsertQueue.add(changeStreamDocument.getFullDocument());
                    System.out.println(changeStreamDocument.getFullDocument());
                    System.out.println(theUpsertQueue);
                    System.out.println(theUpsertQueue.size());*//*

                    String kafkaMessage
                            = changeStreamDocument.getFullDocument()
                            .toJson(writerSettings)
                            .replaceAll("ObjectId\\((.*)\\)", "$1")
                            .replaceAll("ISODate\\((.*)\\)", "$1");

                *//*JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(kafkaMessage);
                json.remove("_id");*//*

                *//*String kafkaMessage
                    = changeStreamDocument.getFullDocument().toJson();
                    //= MongoChangeStreams.getMessage(edsAuditLog);*//*


                    final String topic = PropertyUtil.getProperty("kafkaTopic");
                    System.out.println(topic + " =\n" + kafkaMessage);
                    Integer partition = null;
                    String key = null;
                    KafkaConnection kafkaConnection = null;
                    try {
                        kafkaConnection = KafkaConnection.getInstance();
                        ProducerRecord<String, String> keyedMsg = new ProducerRecord<>(topic, partition, key, kafkaMessage);
                        //kafkaConnection.sendToKafka(keyedMsg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };

        *//*BsonDocument resumeToken = next.getResumeToken();
        cursor = inventory.watch().resumeAfter(resumeToken).iterator();*//*
            eventCollection.watch(
                    asList(
                            Aggregates.match(
                                    Filters.in("operationType", asList("insert", "update", "replace"))
                            )
                    )
            )
            //.resumeAfter(bsonDocument)
            .fullDocument(FullDocument.UPDATE_LOOKUP)
            .forEach(printBlock);
*/
            /*System.out.println("theUpsertQueue : " + theUpsertQueue);
            final UpdateContractsConsumer theUpsertConsumer = new UpdateContractsConsumer(phaser, theUpsertQueue);

            phaser.register();
            CITSThreadPoolExecutor.getInstance().addTask(theUpsertConsumer);*/

        }finally {
            /*phaser.arriveAndDeregister();
            phaser.awaitAdvance(0);

            CITSThreadPoolExecutor.getInstance().shutdown();*/
            MongoDBConnectionFactory.closeMongo();
        }
    }

    public void insertUpdateReplaceChanges(){

    }

    public void deleteChanges(){

    }

    public static void main(String args[]){

        /*try {
            MongoChangeStreams mongoChangeStreams = new MongoChangeStreams();
            mongoChangeStreams.changeStream();
        }catch (ServiceException se)
        {
            se.printStackTrace();
        }*/

        //BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        //Phaser phaser = new Phaser(1);

        try {
            //phaser.register();

            //MongoCollection<Document> gWatchCollection = MongoDBConnectionFactory.getCollection("oplog_tracking");

            MongoCollection<Document> monitoringCollection = MongoDBConnectionFactory.getCollection("oplog_tracking_monitoring");


            Block<ChangeStreamDocument<Document>> processChanges = new Block<ChangeStreamDocument<Document>>() {

                Long count = 0L;
                @Override
                public void apply(final ChangeStreamDocument<Document> changeStreamDocument) {
                    try {
                        count++;
                        //gProducerMessage = getKafkaMessage(changeStreamDocument);
                        //System.out.println("gProducerMessage : "+gProducerMessage);
                        //gProducerMessageSet.add(gProducerMessage);

                        /*if (!gProducerMessageSet.isEmpty() && ((gProducerMessageSet.size() % gMongoBatchSize) == 0)) {
                            gLOGGER.info("Going to Process : " + gProducerMessageSet.size());
                            System.out.println("Going to Process : " + gProducerMessageSet.size());
                            gProducerQueue.addAll(gProducerMessageSet);
                            gProducerMessageSet = new HashSet<>();
                        }
                        if (!gProducerMessageSet.isEmpty()) {
                            gProducerQueue.addAll(gProducerMessageSet);
                            gProducerMessageSet = new HashSet<>();
                        }*/

                        //Thread.sleep(10);

                        //if(gProducerQueue)
                        //gProducerQueue.put(gProducerMessage);

                        System.out.println("BlockingProducer: Message - " + count + " produced.");
                        //gResumeToken = changeStreamDocument.getResumeToken();
                        //updateMonitoringDataPeriodically(gMonitoringCollection, gResumeToken, getgEndTimestamp());
                        System.out.println("changeStreamDocument : "+changeStreamDocument);

                        System.out.println("changeStreamDocument.getOperationType() : "+changeStreamDocument.getOperationType());
                        System.out.println("changeStreamDocument.getOperationType().getValue() : "+changeStreamDocument.getOperationType().getValue());
                        System.out.println("changeStreamDocument.getOperationType().toString() : "+changeStreamDocument.getOperationType().toString());

                        if(changeStreamDocument.getOperationType().getValue().equals("replace")){
                            System.out.println("replaced successfully");
                        }
                        if(changeStreamDocument.getOperationType().getValue().equals("delete")){
                            System.out.println("deleted successfully");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };


            monitoringCollection.watch(
                    asList(
                            Aggregates.match(
                                    Filters.in("operationType",
                                            asList(
                                                    "insert", "update", "replace", "delete"
                                            )
                                    )
                            )
                    )
            )
                    //.resumeAfter(getResumeTokenForCollection(gMonitoringCollection, testresumeToken))
                    .fullDocument(FullDocument.UPDATE_LOOKUP)
                    .forEach(processChanges);
                    //.fullDocument(FullDocument.UPDATE_LOOKUP)
                    //.first();
                    //.getResumeToken();




            /*MongoChangeStreamProducer mongoChangeStreamProducer = new MongoChangeStreamProducer(phaser, eventCollection, blockingQueue);
            CITSThreadPoolExecutor.getInstance().addTask(mongoChangeStreamProducer);

            MongoChangeStreamConsumer mongoChangeStreamConsumer = new MongoChangeStreamConsumer(phaser, blockingQueue);
            CITSThreadPoolExecutor.getInstance().addTask(mongoChangeStreamConsumer);*/

           /* phaser.arriveAndDeregister();
            phaser.awaitAdvance(0);*/
        }catch (Exception se){
            se.printStackTrace();
        }





    }
}
