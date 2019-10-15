package MongoOplogToKafka;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

import static java.util.Arrays.asList;

public class FetchMongoResumeToken implements Callable<Boolean> {

    private BsonDocument gResumeToken;
    private Phaser gPhaser;
    private BlockingQueue<BsonDocument> gProducerQueue;
    private MongoCollection<Document> gWatchCollection;
    private MongoCollection<Document> gMonitoringCollection;

    public FetchMongoResumeToken(Phaser phaser, MongoCollection<Document> watchCollection, MongoCollection<Document> monitoringCollection, BlockingQueue<BsonDocument> queue){

        gPhaser = phaser;
        gWatchCollection = watchCollection;
        gMonitoringCollection = monitoringCollection;
        gProducerQueue = queue;
    }

    public Boolean call(){

        Block<ChangeStreamDocument<Document>> processChanges = new Block<ChangeStreamDocument<Document>>() {

            Long count = 0L;
            @Override
            public void apply(final ChangeStreamDocument<Document> changeStreamDocument) {
                try {
                    count++;
                    System.out.println("BlockingProducer: Message - " + count + " produced.");
                    gResumeToken = changeStreamDocument.getResumeToken();
                    gProducerQueue.put(gResumeToken);
                    //updateMonitoringDataPeriodically(gMonitoringCollection, gResumeToken, getgEndTimestamp());

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };

        gWatchCollection.watch(
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
                //.resumeAfter(getResumeTokenFromCollection(gMonitoringCollection))
                .fullDocument(FullDocument.UPDATE_LOOKUP)
                .first();

        return true;
    }
}
