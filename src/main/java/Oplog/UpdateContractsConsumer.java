package Oplog;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import javaApiPractice.PropertyUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import thread.CITSThreadPoolExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Phaser;

public class UpdateContractsConsumer implements Callable<Boolean> {

    private final BlockingQueue<Document> gUpsertLogQueue;
    private Phaser gPhaser;
    private HashSet<Document> gUpsertContractSet = new HashSet<>();
    final BlockingQueue<HashSet<Document>> gUpsertProcessingQueue = new LinkedBlockingQueue<>(50);
    private static final Logger gLogger = Logger.getLogger(UpdateContractsConsumer.class);
    private static KafkaConnection gKafkaProducer = null;
    private int gMongoBatchSize;


    static {
        try {
            gKafkaProducer = KafkaConnection.getInstance();
        } catch (final ServiceException e) {
            gLogger.error("Exception in IndexEntitlementConsumer static block ServiceException " + e);
        }
    }

    public UpdateContractsConsumer(final Phaser aPhaser, final BlockingQueue<Document> aUpsertQueue) {
        gPhaser = aPhaser;
        gUpsertLogQueue = aUpsertQueue;
    }

    @Override
    public Boolean call() throws Exception {
        gMongoBatchSize = Integer.parseInt(PropertyUtil.getProperty("mongo.db.batch.size"));
        while (gUpsertLogQueue!= null) {
            if (gUpsertLogQueue.peek() != null) {
                final Document theOplogObject = gUpsertLogQueue.take();
                gLogger.info("OPLOG : " + theOplogObject);

                System.out.println("OPLOG : " + theOplogObject);
                gUpsertContractSet.add(theOplogObject);


                if (!gUpsertContractSet.isEmpty()) {
                    gUpsertProcessingQueue.put(gUpsertContractSet);
                    startProcess();

                    gUpsertContractSet = new HashSet<>();

                }
            } else {

                if (!gUpsertContractSet.isEmpty()) {
                    gUpsertProcessingQueue.put(gUpsertContractSet);

                    startProcess();
                    gUpsertContractSet = new HashSet<>();
                }
            }
        }
        gPhaser.arriveAndDeregister();
        return true;
    }

    private void startProcess() throws ServiceException {
        final PopulateContractConsumer populateContractConsumer = new PopulateContractConsumer(gPhaser, gUpsertProcessingQueue);
        gPhaser.register();
        CITSThreadPoolExecutor.getInstance().addTask(populateContractConsumer);
    }

   /* private Object parseMongoOutput(final String aJsonString, final Class<AttributeProductCacheVO> aClass) {
        gLogger.info("JsonString : "+aJsonString);
        Object abc = null;
        try {
            final Gson theGsonObject = new Gson();
             abc = theGsonObject.fromJson(aJsonString, aClass);
        } catch (Exception e) {
            gLogger.error(ExceptionUtils.getStackTrace(e));
        }
        return abc;
    }

    private List<AttributeProductCacheVO> getEntitlementsWithMetaData(Document aEntitlementObj) {
    	
        List<AttributeProductCacheVO> obj = new ArrayList<>(200);
        Document theEoObj = aEntitlementObj;
            if (aEntitlementObj.get("incDelete") == null) {
                theEoObj = getEntitlementByObjectId(((ObjectId) aEntitlementObj.get("_id")).toHexString());

            }
            if (theEoObj != null) {
                String theClassName = theEoObj.get("entitlementClass").toString();
                if (aEntitlementObj.get(ContractAttributes.MONGO_UNIQUE_ID.getValue()) !=null && !aEntitlementObj.get(ContractAttributes.MONGO_UNIQUE_ID.getValue()).equals("ObjectId(\'" + String.valueOf(aEntitlementObj.get("_id")) + "\')")) {
                    theEoObj.put(ContractAttributes.MONGO_UNIQUE_ID.getValue(), aEntitlementObj.get(ContractAttributes.MONGO_UNIQUE_ID.getValue()) + "::" + "ObjectId(\'" + aEntitlementObj.get("_id") + "\')");
                } else {
                    theEoObj.put(ContractAttributes.MONGO_UNIQUE_ID.getValue(), aEntitlementObj.get(ContractAttributes.MONGO_UNIQUE_ID.getValue()));
                }
                theEoObj.put(ContractAttributes.MONGO_TIMESTAMP.getValue(), aEntitlementObj.get(ContractAttributes.MONGO_TIMESTAMP.getValue()));
                ArrayList<Document> b = (ArrayList<Document>) theEoObj.get("methods");

                for (Document bson : b) {
                    String theMethodName = bson.getString("name");
                    String theMethodStatus = bson.getString("status");
                    final AttributeProductCacheVO theAttributeCacheVO = (AttributeProductCacheVO) parseMongoOutput(theEoObj.toJson(), AttributeProductCacheVO.class);

                    theAttributeCacheVO.setAttributeMetaDataVO(gMetaDataMap.getValue(theClassName, theMethodName));
                    theAttributeCacheVO.setEventType("INC");

                    if ("INACTIVE".equals(theMethodStatus) || theAttributeCacheVO.isDelete() || "TRUE".equals(theAttributeCacheVO.getIsBeyondGracePeriod())) {
                        // delete
                        theAttributeCacheVO.setDelete(true);
                        publishDeleteMessage(theAttributeCacheVO);
                    } else {
                        obj.add(theAttributeCacheVO);
                    }
                }
            }
       
        return obj;
    }

    private Document getEntitlementByObjectId(final String aObjectId) {

        MongoCollection<Document> theEOCollection = MongoDBConnectionFactory
                .getCollection(ContractAttributes.ENTITLEMENT.getValue());
        final BasicDBObject theClause = new BasicDBObject("_id", new ObjectId(aObjectId));

        return theEOCollection.find(theClause).first();
    }

    private Document getItemmasterByObjectId( final String aObjectId){
        MongoCollection<Document> theIMCollection = MongoDBConnectionFactory
                .getCollection(ContractAttributes.INVENTORY_ITEM.getValue());
        final Document theClause = new Document("_id", new ObjectId(aObjectId));

        return theIMCollection.find(theClause).first();
    }

	private void publishDeleteMessage(final AttributeProductCacheVO aAttributeCacheVO) {
        try {
            final MongoCollection theEOCollection = MongoDBConnectionFactory
                    .getCollection(ContractAttributes.ENTITLEMENT.getValue());
            final Document theClause = new Document("contractId", aAttributeCacheVO.getContractId())
                    .append("guId", aAttributeCacheVO.getGuId())
                    .append("inventoryItemId", aAttributeCacheVO.getInventoryItemId())
                    .append("status", "ACTIVE")
                    .append("isBeyondGracePeriod",new Document("$ne","TRUE"));
            if (theEOCollection.count(theClause) == 0) {
                gKafkaProducer.publishMessage(aAttributeCacheVO.getkafkaMessage());
            }

        } catch (final Exception e) {
            gLogger.error("Error publishing the kafka message :" + aAttributeCacheVO.getkafkaMessage());
            gLogger.info(ExceptionUtils.getStackTrace(e));
        }
    }

    private void getEntitlements(Document aItemMasterObj) throws ServiceException, InterruptedException {
        Object theInventoryItemIdBSON = null;
        theInventoryItemIdBSON = aItemMasterObj.get(ContractAttributes.INVENTORY_ITEM_ID.getValue());
        if(theInventoryItemIdBSON==null){
            theInventoryItemIdBSON=getItemmasterByObjectId(((ObjectId) aItemMasterObj.get("_id")).toHexString()).get(ContractAttributes.INVENTORY_ITEM_ID.getValue());
        }

        if (theInventoryItemIdBSON != null) {
            MongoCollection theEOCollection = MongoDBConnectionFactory
                    .getCollection(ContractAttributes.ENTITLEMENT.getValue());

            Document theClause = new Document(ContractAttributes.INVENTORY_ITEM_ID
                    .getValue(), theInventoryItemIdBSON);
            final Document theFields = new Document(ContractAttributes.COVERED_PRODUCT_LINE_ID
                    .getValue(), true);
            theFields.append(ContractAttributes.CONTRACT_ID.getValue(), true);
            theFields.append(ContractAttributes.GU_ID.getValue(), true);

            theEOCollection.find(theClause).projection(theFields).forEach((Block<Document>) obj -> {
			try {
                obj.put(ContractAttributes.MONGO_UNIQUE_ID.getValue(), aItemMasterObj.get(ContractAttributes.MONGO_UNIQUE_ID.getValue()));
                obj.put(ContractAttributes.MONGO_TIMESTAMP.getValue(), aItemMasterObj.get(ContractAttributes.MONGO_TIMESTAMP.getValue()));
				gUpsertContractSet.addAll(getEntitlementsWithMetaData(obj));
			    if ((!gUpsertContractSet.isEmpty()) && (gUpsertContractSet.size() >= gMongoBatchSize)) {
			        gUpsertProcessingQueue.put(gUpsertContractSet);
			        startProcess();
			        gUpsertContractSet = new ContractsSet<>();
			    }
			}catch(Exception e) {
			    gLogger.info(ExceptionUtils.getStackTrace(e));
			}
            });

            if (!gUpsertContractSet.isEmpty()) {
                gUpsertProcessingQueue.put(gUpsertContractSet);
                startProcess();
                gUpsertContractSet = new ContractsSet<>();
            }
        }
    }

    private void getMdfLeafNodeId(Document aMdfMetaDataObj) {

        if (aMdfMetaDataObj.get("_id") != null) {
            MongoCollection<Document> theMdfNodesCollection = MongoDBConnectionFactory
                    .getCollection(ContractAttributes.MDF_META_DATA.getValue());
            
            Document theClause = new Document("_id", aMdfMetaDataObj.get("_id"));

            aMdfMetaDataObj = theMdfNodesCollection.find(theClause).first();
            
        }
        if ((aMdfMetaDataObj != null) && !("obsolete".equals(String.valueOf(aMdfMetaDataObj.get("lifecycle"))))) {

            Object theMdfLeadNodeId = aMdfMetaDataObj.get(ContractAttributes.MDF_LEAF_NODE_ID.getValue());
            
            if (theMdfLeadNodeId != null) {
                MongoCollection<Document> theMdfPidCollection = MongoDBConnectionFactory
                        .getCollection(ContractAttributes.MDF_PID_MAPPING.getValue());

                final Document theClause = new Document(ContractAttributes.MDF_LEAF_NODE_ID
                        .getValue(), theMdfLeadNodeId);
                
                final Document theFields = new Document(ContractAttributes.INVENTORY_ITEM_ID
                        .getValue(), true);
                theFields.put(ContractAttributes.ITEM_FAMILY_ID.getValue(), true);
                theFields.put("_id", false);

                Object ds_tid = aMdfMetaDataObj.get(ContractAttributes.MONGO_UNIQUE_ID.getValue());
                Object ds_ts = aMdfMetaDataObj.get(ContractAttributes.MONGO_TIMESTAMP.getValue());
                Object syncCreationDate = aMdfMetaDataObj.get("syncCreationDate");
                Object syncLastUpdatedDate = aMdfMetaDataObj.get("syncLastUpdatedDate");
                theMdfPidCollection.find(theClause).projection(theFields).forEach((Block<Document>) obj -> {
                    try {
                        obj.put(ContractAttributes.MONGO_UNIQUE_ID.getValue(), ds_tid);
                        obj.put(ContractAttributes.MONGO_TIMESTAMP.getValue(), ds_ts);
                        obj.put(ContractAttributes.SYNC_CREATION_DATE.getValue(), syncCreationDate);
                        obj.put(ContractAttributes.SYNC_LAST_UPDATED_DATE.getValue(), syncLastUpdatedDate);
                        getEntitlements(obj);
                    } catch (ServiceException | InterruptedException e) {
                        gLogger.info(ExceptionUtils.getStackTrace(e));
                        Thread.currentThread().interrupt();
                    }
                });
                
            }
        }
    }*/
}
