package MongoOplogToKafka;

import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.Date;
import java.util.TimerTask;


public class UpdateMongoPeriodically extends TimerTask {

    private MongoCollection<Document> gCollection;
    private BsonDocument gBsonDocument;

    public UpdateMongoPeriodically(MongoCollection<Document> collection, BsonDocument bsonDocument) {
        this.gCollection = collection;
        this.gBsonDocument = bsonDocument;
    }

    @Override
    public void run() {

        Long timestamp = new Date().getTime();
        Document resumeTokenDoc = new Document();
        resumeTokenDoc.put("resumeToken", gBsonDocument);
        resumeTokenDoc.put("timestamp", timestamp);
        Document monitorData = new Document("$set", resumeTokenDoc);
        //gCollection.updateOne(Filters.eq("application", "MONGO_CHANGE_STREAMS"), monitorData);
    }
}
