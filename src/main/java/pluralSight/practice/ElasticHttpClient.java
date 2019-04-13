package main.java.pluralSight.dataStructure;


import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pluralSight.practice.ElasticSearchResult;

import java.io.IOException;


public class ElasticHttpClient {

	private static ElasticHttpClient instance;

	private ElasticHttpClient(){}

	//static block initialization for exception handling
	static{
		try{
			instance = new ElasticHttpClient();
		}catch(Exception e){
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}

	public static ElasticHttpClient getInstance(){
		return instance;
	}

	public long executeElasticRequest(String esUrl, long min, long max)
        {

        //UserBean userdetails = userDetailsList.get(0);
        //String strElasticUrl = esUrl;

        CloseableHttpClient httpclient = HttpClients.createDefault();
		ElasticSearchResult response = new ElasticSearchResult();
		Long aLong1 =0L;
		try {
			
			HttpPost post = new HttpPost(esUrl);
			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "text/json");
			post.addHeader("Authorization", "Basic aGFyaXNzaGE6SEFSSSRINzJr");

			String query = new String();
			query = "{\n" +
					"  \"query\": {\n" +
					"    \"range\": {\n" +
					"      \"instanceId.long\": {\n" +
					"        \"gte\": " + min + ",\n" +
					"        \"lte\": " + max + "\n" +
					"      }\n" +
					"    }\n" +
					"  }\n" +
					"}";

			/*for(UserBean user : userDetailsList) {
                    str = str+"{ \"update\" : {\"_id\" : \""+user.getElasticId()+"\", \"_type\" : \""+user.getElasticIndexType()+"\", \"_index\" : \""+user.getElasticIndex()+"\"} }\n";
                    str =  str+"{ \"doc\" : { \"caseManagementModes\" : "+new Gson().toJson(user.getMethods())+"}}\n";
            }*/

            post.setEntity(new StringEntity(query.toString(), "UTF-8"));


			//log.info("Executing request " + post.getRequestLine());
			//System.out.println("Executing request " + post.getRequestLine());
			
			ResponseHandler<ElasticSearchResult> responseHandler = new ResponseHandler<ElasticSearchResult>() {

				public ElasticSearchResult handleResponse(final HttpResponse response) throws IOException {
					ElasticSearchResult result = new ElasticSearchResult();
					int status = response.getStatusLine().getStatusCode();
					result.setResponseCode(status);
					HttpEntity entity = response.getEntity();
					String strResponseString = EntityUtils.toString(entity);
					result.setResponseCode(status);
					result.setResponseAsString(strResponseString);
					if (strResponseString != null) {

					} else {
						//log.error("Empty Response with code: " + result.getResponseCode());
						result.setNumberOfRecordsUpdated(-1);
						result.setResponseCode(status);
						result.setElasticQuerySuccess(false);
					}
					return result;
				}

			};
            response = httpclient.execute(post, responseHandler);
			//log.info("--------- Elastic response -------------------------------");
			//log.info(response);

			//System.out.println("--------- Elastic response -------------------------------");
			//System.out.println("Elastic response : "+response.getResponseAsString());
			/*Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(response.getResponseAsString());
			String prettyJsonString = gson.toJson(je);*/
			//System.out.println(prettyJsonString);


			JsonParser parser = new JsonParser();
			aLong1 = ((JsonObject) parser.parse(response.getResponseAsString())).get("count").getAsLong();
			//System.out.println("Total count : "+aLong1);

			/*SearchResponse response1 = esclient.prepareSearch("indexName").setTypes("typeName").setSource(queryStringObject.toString()).execute().actionGet();
			SearchHits hits = response1.getHits();
*/
			/*JsonArray esJsonArray = ((JsonObject) parser.parse(response.getResponseAsString())).get("hits").getAsJsonObject().get("hits").getAsJsonArray();

			//System.out.println("esJsonArray : "+esJsonArray);
			ArrayList<Long> aList = new ArrayList<>();

			for (JsonElement esJsonElement : esJsonArray) {
				JsonObject esJson = (JsonObject) esJsonElement;
				esJson = esJson.get("_source").getAsJsonObject();
				aList.add(esJson.get("instanceId").getAsLong());
			}*/

			//System.out.println("aList : "+aList);



        } catch (Exception e) {
			//log.error("Error calling elastic search", e);
			//response.setNumberOfRecordsUpdated(-1);
			//response.setElasticQuerySuccess(false);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				//log.error("Error closing http client connection", e);
				System.out.println("Error closing http client connection "+ e);
			}
		}
		return aLong1;

	}

	public static void main(String[] args){

		ElasticHttpClient el = new ElasticHttpClient();
		el.executeElasticRequest("http://cits-eb-es-lt-009:8000/elstsearch/duet_no_warranty_v1/assets_type/_count",7000001, 7500000);

	}

}
