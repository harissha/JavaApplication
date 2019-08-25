package Oplog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import javaApiPractice.PropertyUtil;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.json.JSONObject;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static java.util.Arrays.asList;

public class MongoChangeStreams{

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

    public static void main(String args[]){
        MongoCollection<Document> eventCollection =
                new MongoClient(
                        new MongoClientURI("mongodb://eds_auditlog_user:cisco123@mngdb-ebf-stg-01.cisco.com:27048,mngdb-ebf-stg-02.cisco.com:27048,mngdb-ebf-stg-03.cisco.com:27048/eds_auditlog_db?replicatSet=ebf-stg-rep")
                        //new MongoClientURI("mongodb://localhost:27017/bookcollection?replicatSet=rs")
                ).getDatabase("eds_auditlog_db").getCollection("EdsAuditLog");

        /*long i = 0;
        while (true) {
            Document doc = new Document();
            doc.put("i", i++);
            doc.put("even", i%2);
            eventCollection.insertOne(doc);
            System.out.println("inserted: " + doc);
            Thread.sleep(2000L + (long)(1000*Math.random()));
        }*/

        /*ChangeStreamIterable<Document> changes = eventCollection.watch(asList(
                Aggregates.match( and( asList(
                        in("operationType", asList("insert", "update", "replace", "delete"))

                        *//*,
                        eq("fullDocument.even", 1L)*//*

                        ))
                )));

        changes.forEach(new Block<ChangeStreamDocument<Document>>() {
            @Override
            public void apply(ChangeStreamDocument<Document> t) {
                System.out.println("received: " + t.getFullDocument());
            }
        });*/

        Block<ChangeStreamDocument<Document>> printBlock = new Block<ChangeStreamDocument<Document>>() {
            public void apply(final ChangeStreamDocument<Document> changeStreamDocument) {

                //System.out.println(" MyService::: "+changeStreamDocument.getFullDocument());
                /*EdsAuditLog edsAuditLog = (EdsAuditLog) MongoChangeStreams
                        .parseMongoOutput(changeStreamDocument.getFullDocument().toJson(), EdsAuditLog.class);*/

                JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);
                //JsonWriterSettings writerSettings = JsonWriterSettings.builder().outputMode(JsonMode.SHELL).build();
                /*System.out.println("full doc :: "+changeStreamDocument.getFullDocument().toJson(writerSettings)
                        *//*.replaceAll("ObjectId(.*)", "$1")
                        .replaceAll("ISODate(.*)","$1")
                        .replaceAll("\\((.*)\\)", "$1")*//*
                        .replaceAll("ObjectId\\((.*)\\)", "$1")
                        .replaceAll("ISODate\\((.*)\\)","$1")

                );*/

                //System.out.println("full doc :: "+changeStreamDocument.getFullDocument().toJson());
                //System.out.println(edsAuditLog);


                String kafkaMessage
                        = changeStreamDocument.getFullDocument()
                            .toJson(writerSettings)
                            .replaceAll("ObjectId\\((.*)\\)", "$1")
                            .replaceAll("ISODate\\((.*)\\)","$1");

                /*JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(kafkaMessage);
                json.remove("_id");*/

                        //= changeStreamDocument.getFullDocument().toJson();
                        //= MongoChangeStreams.getMessage(edsAuditLog);



                final String topic = PropertyUtil.getProperty("kafkaTopic");
                System.out.println(topic+" =\n"+kafkaMessage);
                Integer partition = null;
                String key =null;
                KafkaPublisher kafkaPublisher= null;
                try {
                    kafkaPublisher = KafkaPublisher.getInstance();
                    ProducerRecord<String, String> keyedMsg = new ProducerRecord<>(topic, partition, key, kafkaMessage);
                    //kafkaPublisher.sendToKafka(keyedMsg);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        };

        eventCollection.watch(asList(Aggregates.match(Filters.in("operationType", asList("insert", "update", "replace")))))
                .fullDocument(FullDocument.UPDATE_LOOKUP).forEach(printBlock);

    }
}
