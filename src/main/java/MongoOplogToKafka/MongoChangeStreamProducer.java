package MongoOplogToKafka;

import com.mongodb.Block;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.in;
import static java.util.Arrays.asList;

public class MongoChangeStreamProducer implements Callable<Boolean> {

    private final JsonWriterSettings gWriterSettings = new JsonWriterSettings(JsonMode.SHELL, true);
    private MongoCollection<Document> eventCollection;
    private Phaser gPhaser;
    private BlockingQueue<String> gQueue;
    public MongoChangeStreamProducer(Phaser phaser, MongoCollection<Document> collection, BlockingQueue<String> queue){
        gPhaser = phaser;
        eventCollection = collection;
        gQueue = queue;
    }

    public Boolean call(){

        try {
            ChangeStreamIterable<Document> changes = eventCollection.watch(asList(
                    Aggregates.match(and(asList(
                            in("operationType", asList("insert", "update", "replace", "delete"))
                            ))
                    )));

            changes.forEach(new Block<ChangeStreamDocument<Document>>() {

                @Override
                public void apply(ChangeStreamDocument<Document> t) {

                    System.out.println("received: " + t.getFullDocument());
                    gQueue.add(
                            t.getFullDocument()
                             .toJson(gWriterSettings)
                             .replaceAll("ObjectId\\((.*)\\)", "$1")
                             .replaceAll("ISODate\\((.*)\\)", "$1")
                    );

                }
            });

            /*Block<ChangeStreamDocument<Document>> processChanges = new Block<ChangeStreamDocument<Document>>() {

                Long count = 0L;
                @Override
                public void apply(final ChangeStreamDocument<Document> changeStreamDocument) {
                    try {
                        count++;
                        gProducerMessage = getKafkaMessage(changeStreamDocument);
                        gProducerMessageSet.add(gProducerMessage);

                        *//*if (!gProducerMessageSet.isEmpty() && ((gProducerMessageSet.size() % gMongoBatchSize) == 0)) {
                            gLOGGER.info("Going to Process : " + gProducerMessageSet.size());
                            System.out.println("Going to Process : " + gProducerMessageSet.size());
                            gProducerQueue.addAll(gProducerMessageSet);
                            gProducerMessageSet = new HashSet<>();
                        }
                        if (!gProducerMessageSet.isEmpty()) {
                            gProducerQueue.addAll(gProducerMessageSet);
                            gProducerMessageSet = new HashSet<>();
                        }*//*

                        //Thread.sleep(10);
                        gProducerQueue.add(gProducerMessage);

                        System.out.println("BlockingProducer: Message - " + count + " produced.");
                        gResumeToken = changeStreamDocument.getResumeToken();
                        updateMonitoringDataPeriodically(gMonitoringCollection, gResumeToken, getgEndTimestamp());

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };

            gWatchCollection.watch(
                    asList(
                            Aggregates.match(
                                    Filters.in(Constants.OPERATION_TYPE.getValue(),
                                            asList(
                                                    Constants.INSERT.getValue(),
                                                    Constants.UPDATE.getValue(),
                                                    Constants.REPLACE.getValue()
                                            )
                                    )
                            )
                    )
            )
                    .resumeAfter(getResumeTokenFromCollection(gMonitoringCollection))
                    .fullDocument(FullDocument.UPDATE_LOOKUP)
                    .forEach(processChanges);*/

            gPhaser.arriveAndDeregister();
        }catch (Exception e){
            e.printStackTrace();
        }


        return true;
    }
}
