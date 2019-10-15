package MongoOplogToKafka;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import javaApiPractice.PropertyUtil;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBConnectionFactory {

    static MongoDBConnectionFactory instance = null;

    private MongoDBConnectionFactory(){}

    public static MongoDBConnectionFactory getInstance()
    {
        if (instance == null)
            instance = new MongoDBConnectionFactory();
        return instance;
    }

    private static final Logger logger = Logger.getLogger(MongoDBConnectionFactory.class);
    private static final Map<String, MongoCollection<Document>> collections = new LinkedHashMap<>();

    private static final String OPLOG_COLLECTION_NAME = "oplog.rs";

    private static final List<MongoClient> gMongoClient = new ArrayList<>();

    public static MongoCollection getCollection(final String collectionName) {
        return getCollection(collectionName, null);
    }

    public static MongoCollection getCollection(final String collectionName, Class aClass) {
        if (collections.containsKey(collectionName)) {
            return collections.get(collectionName);
        } else {
        	
            return getMongoCollection(PropertyUtil.getProperty(collectionName + ".mongo.url"), aClass);
        }
    }

    private static MongoCollection getMongoCollection(final String aConnectionParams, Class aClass) {
        System.out.println("aConnectionParams : "+aConnectionParams);
        MongoClientURI theURI = new MongoClientURI(aConnectionParams, getMongoDBOptions());
        System.out.println("theURI : "+theURI);
        final String collectionName = theURI.getCollection();
        MongoCollection collection;
        if (null == collectionName) {
            logger.error("missing collection name, check URL 'mongodb://user:pass@host1:port1,host2:port2,host3:port3/database.collection?replicatSet=replicatSetName'");
            throw new MongoException("missing collection name, check URL 'mongodb://user:pass@host1:port1,host2:port2,host3:port3/database.collection?replicatSet=replicatSetName'");
        }
        MongoClient theTempClient = new MongoClient(theURI);
        gMongoClient.add(theTempClient);
        if (aClass == null) {
            collection = theTempClient.getDatabase(theURI.getDatabase()).getCollection(collectionName);
        } else {
            collection = theTempClient.getDatabase(theURI.getDatabase()).getCollection(collectionName, aClass).withCodecRegistry(getCodec());
        }
        collections.put(collectionName, collection);
        logger.info("CollectionName " + collectionName + " Collections = " + collections);
        return collection;
    }


    public static void closeMongo() {
        gMongoClient.forEach(MongoClient::close);
    }

    private static CodecRegistry getCodec() {
        return fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }

    private static MongoClientOptions.Builder getMongoDBOptions() {
        final MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
         builder.connectionsPerHost(Integer.parseInt(PropertyUtil.getProperty("mongo.db.poolsize")))
                        .readPreference(ReadPreference.primaryPreferred())
                        .build();
         return builder;
    }

    /*public static MongoCollection<Document> getOplogCollection(final OplogDetailsVO aOplog) {

        final MongoCredential cred = MongoCredential
                .createScramSha1Credential(aOplog.getOplogUser(), "admin", aOplog.getOplogPwd().toCharArray());
        final String[] servers = aOplog.getMngdbServer().split(",");
        final List<ServerAddress> seeds = new LinkedList<>();
        for (final String server : servers) {
            String[] theHost = server.split(":");
            seeds.add(new ServerAddress(theHost[0], Integer.parseInt(theHost[1])));
        }

        MongoClient client = new MongoClient(seeds, Arrays.asList(cred));
        gMongoClient.add(client);
        return client.getDatabase("local").getCollection(OPLOG_COLLECTION_NAME);
    }*/
}
