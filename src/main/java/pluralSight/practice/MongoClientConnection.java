package pluralSight.practice;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoClientConnection {

    static String database = "bookcollection";
    static String collection = "books";
    //static String user = "guest";
    //static char[] password = {'g','u','e','s','t'};

    static String user = "";
    static char[] password = {};
    //static MongoClient mongoClient = null;

    private static MongoClient mongoClient;

    public MongoClientConnection() {
    }

    //static block initialization for exception handling
    static{
        try{
            mongoClient = new MongoClient(getUri());
            System.out.println("Connect to Mongo Client successful");
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating Mongo Client Singleton instance");
        }
    }

    /*
     *
     * parseMongoOutput --- Method for converting string into json.
     * @author Rajkumar Srinivasan
     * This method contains the logic for converting
     * given string into json format and returns json data.
     * It uses Gson liberary.
     ** @param String aJsonString,  Class aClass
     * @exception None
     * @return  Object
     */
    private static Object parseMongoOutput(final String aJsonString, final Class aClass) {
        final Gson theGsonObject = new Gson();
        try{
            theGsonObject.fromJson(aJsonString, aClass);
        }
        catch(Exception e){
            System.out.println("Exception while parsing JSON "+aJsonString+" to "+aClass+"; "+e);
        }
        return theGsonObject.fromJson(aJsonString, aClass);
    }

    /*public List<DBObject> getResultsInDescendingOrderByDate(int limit) {
        List<DBObject> myList = null;
        DBCursor myCursor=myCollection.find().sort(new BasicDBObject("date",-1)).limit(10);
        myList = myCursor.toArray();
        return myList;
    }*/

    public static MongoClientURI getUri() throws Exception {
        //String driver = "oracle.jdbc.driver.OracleDriver";
        String database = "bookcollection";
        String collection = "books";

        //static String user = "guest";
        //static char[] password = {'g','u','e','s','t'};

        String user = "";
        char[] password = {};

         /*MongoClientURI uri = new MongoClientURI("mongodb://"+user+":"
                +String.valueOf(password)
                +"@localhost/?authSource="+database);*/

        MongoClientURI uri = new MongoClientURI("mongodb://"
                +"localhost/?authSource="+database);

        return uri;
    }

    private static MongoDatabase connectToDatabase() {
        MongoDatabase mongoDatabase = null;
        try{
            // Now connect to your databases
            mongoDatabase = mongoClient.getDatabase(database);
            System.out.println("Connect to Database successful");
        }
        catch(Exception e){
            System.out.println(e.getMessage().getClass()+" : "+e.getMessage());
        }
        return mongoDatabase;
    }

    private static MongoCollection connectToCollection(MongoDatabase mongoDatabase) {
        MongoCollection mongoCollection = null;
        try{
            // Now connect to your databases
            mongoCollection = mongoDatabase.getCollection(collection);
            System.out.println("Connect to Collection successful");
        }
        catch(Exception e){
            System.out.println(e.getMessage().getClass()+" : "+e.getMessage());
        }
        return mongoCollection;
    }


    public static void main(String[] args) {


      /*  MongoCredential credential = MongoCredential.createCredential(user,
                database,
                password);

        *//*MongoClientURI uri = new MongoClientURI("mongodb://"+user+":"
                +String.valueOf(password)
                +"@localhost/?authSource="+database);*//*

        MongoClientURI uri = new MongoClientURI("mongodb://"
                +"localhost/?authSource="+database);

        //MongoClient mongoClient = null;
        mongoClient = new MongoClient( uri);*/

        try{

            // To connect to mongodb server



           /* // Now connect to your databases
            MongoDatabase db = mongoClient.getDatabase(database);
            System.out.println("Connect to database successfully");
            MongoCollection coll = db.getCollection(collection);*/

            MongoCollection mongoCollection = connectToCollection(connectToDatabase());
            System.out.println(mongoCollection.count());

            MongoCursor<Document> cursor = mongoCollection.find().iterator();

            /*final BasicDBObject theClause = (BasicDBObject) JSON.parse(theMongoQuery.get("clause"));
            final BasicDBObject theFields = (BasicDBObject) JSON.parse(theMongoQuery.get("fields"));
            final DBCursor theSotCursor = theSoTCollection.find(theClause, theFields).batchSize(theMongoBatchSize);
*/

            while (cursor.hasNext()) {
                Document document = cursor.next();
                /*System.out.println(document);
                System.out.println(document.get("_id"));
                System.out.println(document.toJson().toString());*/

                MongoCollectionData mongoCollectionData =(MongoCollectionData) parseMongoOutput(document.toJson().toString(),MongoCollectionData.class);
                System.out.println("mongoCollectionData : "+ mongoCollectionData);
                System.out.println(mongoCollectionData.getId());

            }

            /*for(Document document : documents){
                System.out.println("Hi1");
                System.out.println(document.get("_id"));

            }*/


            /*Document query = new Document("_id", new Document("$gt", 100));
            long count = coll.count(new Document("_id", "1001"));
            System.out.println("count : "+count);*/



        }catch(Exception e){
            System.err.println( e.getClass().getName() + " : " + e.getMessage() );
        }finally {
            if(mongoClient != null){
                mongoClient.close();
            }

        }
    }
}
