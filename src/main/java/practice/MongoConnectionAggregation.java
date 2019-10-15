package practice;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.Arrays;

public class MongoConnectionAggregation {


    public static void main(String[] args)
    {
        int batchSize = 10;
        MongoClient mongoClient = null;
        MongoCollection mongoCollection = null;
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://subscription_user:webex123@mngdb-ebf-stg-01.cisco.com:27048,mngdb-ebf-stg-02.cisco.com:27048,mngdb-ebf-stg-03.cisco.com:27048/subscription_db.lines?replicatSet=ebf-stg-rep");

        try {
            mongoClient = new MongoClient(mongoClientURI);
        }catch(Exception e) {
            e.printStackTrace();
        }
        mongoCollection = mongoClient.getDatabase(mongoClientURI.getDatabase()).getCollection(mongoClientURI.getCollection());

        String matchQuery = "{\n" +
                "\t\t$match: {\n" +
                "\t\t\t$or: [{\n" +
                "\t\t\t\tsource: 'SBP',\n" +
                "\t\t\t\tisSupportSku: {\n" +
                "\t\t\t\t\t$ne: 'Y'\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\tsupportSku: {\n" +
                "\t\t\t\t\t$exists: true\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\tpillarCode: 1,\n" +
                "\t\t\t\tisServiceable: 1\n" +
                "\t\t\t}, \n" +
                "\t\t\t{\n" +
                "\t\t\t\tsource: {\n" +
                "\t\t\t\t\t$nin: ['SBP', 'TNC']\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}]\n" +
                "\t\t}\n" +
                "\t}";

        String addFieldsQuery = "{\n" +
                "\t\t$addFields: {\n" +
                "\t\t\tallContainers: {\n" +
                "\t\t\t\t$map: {\n" +
                "\t\t\t\t\tinput: '$containers',\n" +
                "\t\t\t\t\tas: 'container',\n" +
                "\t\t\t\t\t'in': {\n" +
                "\t\t\t\t\t\tcontainerId: '$$container.containerId',\n" +
                "\t\t\t\t\t\tcontainerType: '$$container.containerType',\n" +
                "\t\t\t\t\t\tparentContainerId: {\n" +
                "\t\t\t\t\t\t\t$substr: ['$$container.parentContainerId', 0, -1]\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\tparentContainerName: '$$container.parentContainerName',\n" +
                "\t\t\t\t\t\tchildContainerId: {\n" +
                "\t\t\t\t\t\t\t$substr: ['$$container.childContainerId', 0, -1]\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\tchildContainerName: '$$container.childContainerName',\n" +
                "\t\t\t\t\t\tdefaultVisibilityLevel: '$$container.defaultVisibilityLevel',\n" +
                "\t\t\t\t\t\tallowVisibilityOverride: '$$container.allowVisibilityOverride'\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}";

        String unwindQuery = "{\n" +
                "\t\t$unwind: '$containers'\n" +
                "\t}";

        String projectQuery = "{\n" +
                "\t\t$project: {\n" +
                "\t\t\t_id: 0,\n" +
                "\t\t\tlinesCollapseId: '$_id',\n" +
                "\t\t\tidd: {\n" +
                "\t\t\t\t$concat: ['$_id', '|',\n" +
                "\t\t\t\t\t'$containers.containerId'\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t'containerId': '$containers.containerId',\n" +
                "\t\t\tcontainerType: '$containers.containerType',\n" +
                "\t\t\tparentContainerId: {\n" +
                "\t\t\t\t$substr: ['$containers.parentContainerId', 0, -1]\n" +
                "\t\t\t},\n" +
                "\t\t\tparentContainerName: '$containers.parentContainerName',\n" +
                "\t\t\tchildContainerId: {\n" +
                "\t\t\t\t$substr: ['$containers.childContainerId', 0, -1]\n" +
                "\t\t\t},\n" +
                "\t\t\tchildContainerName: '$containers.childContainerName',\n" +
                "\t\t\t'subscriptionRefId': 1,\n" +
                "\t\t\theaderId: '$subscriptionRefId',\n" +
                "\t\t\t'subscriptionCode': 1,\n" +
                "\t\t\t'ccwOrderLineId': {\n" +
                "\t\t\t\t$substr: ['$ccwOrderLineId', 0, -1]\n" +
                "\t\t\t},\n" +
                "\t\t\t'productDescription': 1,\n" +
                "\t\t\t'productQty': {\n" +
                "\t\t\t\t$substr: ['$productQty', 0, -1]\n" +
                "\t\t\t},\n" +
                "\t\t\t'quantityMeasure': 1,\n" +
                "\t\t\t'productName': 1,\n" +
                "\t\t\t'inventoryItemId': {\n" +
                "\t\t\t\t$substr: ['$inventoryItemId', 0, -1]\n" +
                "\t\t\t},\n" +
                "\t\t\t'serviceTier': 1,\n" +
                "\t\t\t'sortServiceTier': 1,\n" +
                "\t\t\t'gspName': '$GSPName',\n" +
                "\t\t\t'gspDescription': '$GSPDescription',\n" +
                "\t\t\t'gspId': {\n" +
                "\t\t\t\t$substr: ['$GSPId', 0, -1]\n" +
                "\t\t\t},\n" +
                "\t\t\t'basicGSPName': 1,\n" +
                "\t\t\t'atoSkuCcwOrderLineId': {\n" +
                "\t\t\t\t$substr: ['$atoSkuCcwOrderLineId', 0, -1]\n" +
                "\t\t\t},\n" +
                "\t\t\t'isServiceable': 1,\n" +
                "\t\t\t'atoSkuLine': '$atoSku',\n" +
                "\t\t\tcontainerCategory: {\n" +
                "\t\t\t\t$cond: {\n" +
                "\t\t\t\t\tif: {\n" +
                "\t\t\t\t\t\t$setIsSubset: [\n" +
                "\t\t\t\t\t\t\t['$source'],\n" +
                "\t\t\t\t\t\t\t['WebEx 11',\n" +
                "\t\t\t\t\t\t\t\t'SparkConsumer'\n" +
                "\t\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\tthen: 'generic',\n" +
                "\t\t\t\t\telse: null\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t'syncCreationDate': 1,\n" +
                "\t\t\t'syncLastUpdatedDate': 1,\n" +
                "\t\t\tdefaultVisibilityLevel: '$containers.defaultVisibilityLevel',\n" +
                "\t\t\tallowVisibilityOverride: '$containers.allowVisibilityOverride',\n" +
                "\t\t\tcontainers: '$allContainers'\n" +
                "\t\t}\n" +
                "\t}";

        AggregateIterable<Document> output = mongoCollection.aggregate(Arrays.asList(
                Document.parse(matchQuery),
                Document.parse(addFieldsQuery),
                Document.parse(unwindQuery),
                Document.parse(projectQuery)
        )).batchSize(batchSize).allowDiskUse(true);

        for (Document document : output) {
            System.out.println(document);
        }


        mongoClient.close();
    }
}
