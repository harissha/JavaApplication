package practice;


import java.util.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.*;

public class MongoConnection {
	
	 private String bookId = null;
	 private String bookName = null;
	 private String Author = null;
	 private String ISBN = null;
	
	public static void main(String[] args)
	 {
	  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
	  
	  MongoDatabase database = mongoClient.getDatabase("bookcollection");
	  
	  //System.out.println("database : "+database.getName());
	  
	  //System.out.println(database.getCollection("books").toString());
	  /*System.out.println(database.getName());
	  System.out.println(database.getName());
	  System.out.println(database.getName());*/
	  
	  //MongoCollection collection= database.getCollection("books");
	  
	  //Document myDoc = (Document) collection.find(eq("bookId", 1.0)).first();
	  //System.out.println(myDoc.toJson());
	  
	  MongoCollection<Document> collection = database
				.getCollection("books");

		List<Document> documents = (List<Document>) collection.find().into(
				new ArrayList<Document>());
		
		Map<String, String> m= new HashMap<String, String>();
		
		/*System.out.println("Hi");
		for(Map.Entry<String,String> entry: m.entrySet()){
			System.out.println("Hi");
		    System.out.println(entry.getKey() + " : " + entry.getValue());
		}*/
		
		/*Bson bson = Filters.eq("type", "work");
		List<Document> list = collection.find(bson).into(new ArrayList<>());
		System.out.println(list.size());*/

		
		Document query = new Document("_id", new Document("$gt", 100));
		long count = collection.count(new Document("_id", "1001"));
		
		System.out.println(collection.find(Document.parse("{_id : {$lt : 100}}")));
		
		
		System.out.println("count : "+count);
		
             for(Document document : documents){
            	 System.out.println("Hi1");
                 System.out.println(document.get("_id")); 
                              
             }
             
             ArrayList<String> l= new ArrayList<String>();
             
             
             
             System.out.println(documents.get(0).keySet());
             
             l.addAll(documents.get(0).keySet());
             
             System.out.println(documents.get(0).values()); 
             
             
             
            
             
             for(int i=0; i<l.size(); i++){
            	 
             System.out.println(l.get(i)+" = "+l.get(i));
             
             
             }
             
	  
	  //System.out.println(collection.toString());
	  
	 
	  
	  
	  
	  
	  
	 /* List books = Arrays.asList(1251,2512);
	  Document person = new Document("_id", "ABHSIHEK")
	                              .append("name", "java roots")
	                              .append("address", new BasicDBObject("street", "sad java")
	                                                           .append("city", "mongos")
	                                                           .append("state", "mongod")
	                                                           .append("zip", 5151))
	                              .append("books", books);
	  
	  MongoCollection collection= database.getCollection("people");
	     collection.insertOne(person);*/




		 MongoDatabase database1 = mongoClient.getDatabase("practiceDb");

		 MongoCollection<Document> collection1 = database1.getCollection("test");

		 Bson filter1 = null;
		 Bson query1 = null;

		 filter1 = eq("name", "Sundar");
		 System.out.println(filter1);

		 //query1 = combine(set("age", 23), set("gender", "Male"));



		 Long value = 1440L;
		 Long min = value*60*1000;

		 Date date = new Date();
		 Date date1 = new Date(date.getTime()+min);


		 BasicDBObject filter2 = new BasicDBObject();
		 filter2.put("Status","PENDING");

		 BasicDBObject query2 = new BasicDBObject();
		 BasicDBObject fieldToBeUpdated = new BasicDBObject();
		 fieldToBeUpdated.put("startDate", date1);
		 query2.put("$set", fieldToBeUpdated);

		 System.out.println(filter2+"-------------"+query2);

		 collection1.updateMany(filter2,query2);

		 BasicDBObject document3 = new BasicDBObject();
		 document3.put("database", "mkyongDB");
		 document3.put("table", "hosting");

		 //collection1.insertOne();



		 List<Document> documents1 = (List<Document>) collection1.find().into(
				 new ArrayList<Document>());

		 System.out.println(documents1);

		 // Performing a read operation on the collection.
		 FindIterable<Document> fi = collection1.find();
		 MongoCursor<Document> cursor = fi.iterator();
		 try {
			 while(cursor.hasNext()) {
				 System.out.println(cursor.next().toJson());
			 }
		 } finally {
			 cursor.close();
		 }
	     
	  mongoClient.close();
	   }	
	
	/*@Override
    public String toString() {
        return "MongoDBConnectionParams [userName=" + userName + ", password=" + password + ", hostAddress="
                + hostAddress + ", hostPort=" + hostPort + ", databaseName=" + databaseName + ", collectionName="
                + collectionName + "]";
    }*/
	
}
