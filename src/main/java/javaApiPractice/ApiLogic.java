package javaApiPractice;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiLogic {

    public static void main(String[] args){

        String msg = "{\n" +
                "\t\"scope\": [\n" +
                "\t\t\"TestResourceService:list_meeting\",\n" +
                "\t\t\"TestResourceService:schedule_meeting\"\n" +
                "\t],\n" +
                "\t\"realm\": \"oauthtestorg1\",\n" +
                "\t\"user_type\": \"user\",\n" +
                "\t\"cis_uuid\": \"b3dd5d57-df85-4abe-85de-93c061df784c\",\n" +
                "\t\"token_type\": \"Bearer\",\n" +
                "\t\"expires_in\": 7199,\n" +
                "\t\"user_id\": \"user1@oauthtestorg2.com\",\n" +
                "\t\"client_id\": \"C1caa36e0076145dede3e5582bf4998188893809b198ffd4d8228ad65c81a61f5\",\n" +
                "\t\"reference_id\": \"c82d88a3-cd12-4707-b003-0299a338bb6e\",\n" +
                "\t\"access_token\": \"MTRiMTgzOTctZDVjMS00NzI5LWE4NGYtNTJiMjUxMTkzZDQzNWY4MzE3OGYtOGVi\",\n" +
                "\t\"cisRole\": [\n" +
                "\t\t\"id_full_admin\",\n" +
                "\t\t\"Full_Admin\",\n" +
                "\t\t\"id_user_admin\",\n" +
                "\t\t\"User_Admin\"\n" +
                "\t],\n" +
                "\t\"oauth2userentitleservice\": [\n" +
                "\t\t\"social\",\n" +
                "\t\t\"meetings\",\n" +
                "\t\t\"webex-squared\"\n" +
                "\t],\n" +
                "\t\"managedOrgs\": [{\n" +
                "\t\t\"orgId\": \"adec1fb9-50fe-4bea-b75b-9488fce273b3\",\n" +
                "\t\t\"role\": \"id_full_admin\"\n" +
                "\t}]\n" +
                "}";

        /*JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(msg);*/

        JSONObject jsonObj = null;
        JSONArray jsonArray = null;
        try{
            jsonObj = new JSONObject(msg);

            System.out.println(jsonObj.getJSONArray("scope"));
            jsonArray = jsonObj.getJSONArray("scope");
            System.out.println(String.valueOf(jsonArray).contains("identity:organization"));

        }catch (JSONException je){
            je.printStackTrace();
        }

        KafkaProducer11 kafkaProducer = null;
        String bootstrapServerDetails ="";
        String kafkaTopic ="";
        Integer partition = 1;
        String key =null;
        String message = "";
        try {
            if (String.valueOf(jsonArray).contains("identity:organization")) {
                bootstrapServerDetails ="something1";
                kafkaTopic ="topicName1";
                kafkaProducer = KafkaProducer11.getKafkaInstance(bootstrapServerDetails, kafkaTopic);
            }else{
                bootstrapServerDetails ="something1";
                kafkaTopic ="topicName2";
                kafkaProducer = KafkaProducer11.getKafkaInstance(bootstrapServerDetails, kafkaTopic);
            }

            ProducerRecord<String, String> keyedMsg = new ProducerRecord<>(kafkaTopic, partition, key, message);
            kafkaProducer.publishMessage(keyedMsg);

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(jsonObj);


    }


}


