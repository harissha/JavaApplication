package pluralSight.practice;


/*import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;*/



/*import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.cisco.eb.populatemethods.beans.UserBean;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.cisco.eb.populatemethods.constants.Constants;
import com.cisco.eb.populatemethods.exception.ServiceException;
import com.cisco.eb.populatemethods.util.LogUtil;
import com.cisco.eb.populatemethods.util.PropertyUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;*/

public class Elastic {

    /*SearchResponse response = client.prepareSearch("your_index_goes_here")
            .setTypes("YourTypeGoesHere")
            .setQuery(QueryBuilders.termQuery("some_field", "some_value"))
            .setSize(0) // Don't return any documents, we don't need them.
            .get();

    SearchHits hits = response.getHits();
    long hitsCount = hits.getTotalHits();*/


   /* protected static final LogUtil log = new LogUtil(ElasticHttpClient.class);
    private static final String UPDATE_OPERATION = "update";
    static final String ELASTIC_URL = Constants.ELASTIC_URL;
    static final String ELASTIC_INDEX_TYPE_CI = Constants.ELASTIC_INDEX_TYPE_CI;
    static final String ELASTIC_INDEX_TYPE_CCS = Constants.ELASTIC_INDEX_TYPE_CCS;

    protected String buildElasticUrl(String operation, String strIndexName, String id, String elasticIndexType)
            throws ServiceException, UnsupportedEncodingException, URISyntaxException {
        String strElasticUrl = null;
        URIBuilder uriBuilder = null;
        if (operation.equalsIgnoreCase(UPDATE_OPERATION)) {
            strElasticUrl = PropertyUtil.getProperty(ELASTIC_URL) ;
            uriBuilder = new URIBuilder(strElasticUrl);
        }

        return uriBuilder.build().toString();
    }


    public ElasticSearchResult executeElasticRequest(List<UserBean> userDetailsList)
            throws ServiceException, UnsupportedEncodingException, URISyntaxException {

        UserBean userdetails = userDetailsList.get(0);
        String strElasticUrl = buildElasticUrl(UPDATE_OPERATION, userdetails.getElasticIndex(), null, userdetails.getElasticIndexType());

        CloseableHttpClient httpclient = HttpClients.createDefault();
        ElasticSearchResult response = new ElasticSearchResult();
        try {

            HttpPost post = new HttpPost(strElasticUrl);
            post.addHeader("content-type", "application/json");
            post.addHeader("Accept", "text/json");

            post.addHeader("Authorization", "Basic cmdvdWRib2I6U2hpbWExOTAh");

            String str = new String();

            for(UserBean user : userDetailsList) {
                str = str+"{ \"update\" : {\"_id\" : \""+user.getElasticId()+"\", \"_type\" : \""+user.getElasticIndexType()+"\", \"_index\" : \""+user.getElasticIndex()+"\"} }\n";
                str =  str+"{ \"doc\" : { \"caseManagementModes\" : "+new Gson().toJson(user.getMethods())+"}}\n";
            }

            post.setEntity(new StringEntity(str.toString(), "UTF-8"));
            log.info("Executing request " + post.getRequestLine());

            ResponseHandler<ElasticSearchResult> responseHandler = new ResponseHandler<ElasticSearchResult>() {

                @Override
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
                        log.error("Empty Response with code: " + result.getResponseCode());
                        result.setNumberOfRecordsUpdated(-1);
                        result.setResponseCode(status);
                        result.setElasticQuerySuccess(false);
                    }
                    return result;
                }

            };
            response = httpclient.execute(post, responseHandler);
            log.info("--------- Elastic response -------------------------------");
            log.info(response);
        } catch (Exception e) {
            log.error("Error calling elastic search", e);
            response.setNumberOfRecordsUpdated(-1);
            response.setElasticQuerySuccess(false);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error("Error closing http client connection", e);
            }
        }
        return response;

    }*/

    public static void main(String[] args){

         long incrementBy = 499999;
         long rangeStart =25300000001L;
         long rangeEnd = 25800000000L;
         long min = rangeStart;
         long max = incrementBy+rangeStart;

         long a ;
         int count=0;



         for(long i=1; i<= (rangeEnd-rangeStart)/incrementBy; i++){

             System.out.println("Min : "+min+" ------ Max : "+max);
             min +=incrementBy+1;
             max+=incrementBy+1;
             count++;
         }

        System.out.println(count);
         a = rangeEnd/incrementBy;
        System.out.println(a);




    }


}







