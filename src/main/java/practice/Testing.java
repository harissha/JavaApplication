package practice;

import MongoOplogToKafka.MongoDBConnectionFactory;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.BsonDocument;
import org.bson.Document;

public class Testing {

    public static void main(String args[]){

        MongoCollection<Document> theCollection = MongoDBConnectionFactory.getCollection("oplog_tracking_monitoring");

        BsonDocument bsonDocument= null;
        Document document = new Document();
        Document docFind = new Document();
        docFind.put("application", "MONGO_CHANGE_STREAMS");
        FindIterable<Document> fi = theCollection.find(docFind);
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                document = cursor.next();
            }
            System.out.println(document);
        }catch(Exception e){
            e.printStackTrace();
            //gLogger.info(ExceptionUtils.getStackTrace(e));
        } finally {
            cursor.close();
        }

        try {
            bsonDocument = ((Document) document.get("resumeToken")).toBsonDocument(BsonDocument.class, theCollection.getCodecRegistry());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
