package javaApiPractice;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;


public class KafkaProducer11 {
    private static Logger gLogger = Logger.getLogger(KafkaProducer11.class);
    private static KafkaProducer<String, String> gProducer;
    private static KafkaProducer11 gKafkaProducer;
    private static String gBootstrapServers = "";
    private static String gKafkaTopic = "";
    private int total = 0;
    static {
        try {
            gKafkaProducer = new KafkaProducer11();
        } catch (final Exception theEx) {
            theEx.printStackTrace();
        }
    }

    public static KafkaProducer11 getInstance() throws Exception {
        if (gKafkaProducer == null) {
            throw new Exception("Kafka Producer not initialized");
        }
        return gKafkaProducer;
    }

    private KafkaProducer11() throws Exception {
        gBootstrapServers = PropertyUtil.getProperty("BOOTSTRAP_SERVERS_CONFIG");
        gKafkaTopic = PropertyUtil.getProperty("kafkaTopic");
        initialize();
    }


    public static KafkaProducer11 getKafkaInstance(String bootstrapServerDetails, String kafkaTopic) throws Exception{

        try {
            if (gKafkaProducer == null) {
                throw new Exception("Kafka Producer not initialized");
            }else {
                gKafkaProducer = new KafkaProducer11(bootstrapServerDetails, kafkaTopic);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return gKafkaProducer;
    }

    private KafkaProducer11(String bootstrapServerDetails, String kafkaTopic) throws Exception {
        gBootstrapServers = bootstrapServerDetails;
        gKafkaTopic = kafkaTopic;
        initialize();
    }

   /* private void confirmBootstrapServerToBeUsed(String bootstrapServer){
        if(bootstrapServer)
    }*/

    private void initialize() throws Exception {
        final Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, gBootstrapServers);
        prop.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, PropertyUtil.getProperty("SECURITY_PROTOCOL_CONFIG"));
        prop.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, PropertyUtil.getProperty("SSL_TRUSTSTORE_LOCATION_CONFIG"));
        prop.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, PropertyUtil.getProperty("SSL_TRUSTSTORE_PASSWORD_CONFIG"));
        prop.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, PropertyUtil.getProperty("SSL_TRUSTSTORE_TYPE_CONFIG"));
        prop.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, PropertyUtil.getProperty("SSL_KEYSTORE_LOCATION_CONFIG"));
        prop.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, PropertyUtil.getProperty("SSL_KEYSTORE_PASSWORD_CONFIG"));
        prop.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, PropertyUtil.getProperty("SSL_KEYSTORE_TYPE_CONFIG"));
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.test.SimplePartitionerKafka11");
        prop.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        gProducer = new KafkaProducer<>(prop);
    }

   /* private void initialize() throws Exception {
        final Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kfk-ebf-dev-01:9097,kfk-ebf-dev-02:9097,kfk-ebf-dev-03:9097");
        prop.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        prop.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "C:\\\\Users\\\\rajsrin2\\\\kafka-cert\\\\stage\\\\client\\\\client.truststore.jks");
        prop.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "CiscoEBtc123stage");
        prop.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "JKS");
        prop.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "C:\\\\Users\\\\rajsrin2\\\\kafka-cert\\\\stage\\\\client\\\\webex_stage\\\\client.keystore.jks");
        prop.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "EBwebex1234stage");
        prop.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "JKS");
        // prop.put(StreamsConfig.STATE_DIR_CONFIG, PropertyUtil.getProperty("UDC_STATE_DIR_CONFIG"));
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.test.SimplePartitionerKafka11");

        prop.put(ProducerConfig.LINGER_MS_CONFIG, 100);
//        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768);
        // prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, PropertyUtil.getProperty("AUTO_OFFSET_RESET_CONFIG"));
        gProducer = new KafkaProducer<>(prop);
    }*/

    public void publishMessage(final List<ProducerRecord<String, String>> aKeyedMsgList) throws Exception {
        total = total + aKeyedMsgList.size();
        aKeyedMsgList.forEach(gProducer::send);
    }

    public void publishMessage(final ProducerRecord<String, String> aKeyedMsg) throws Exception {
        //gLogger.info("DELETE PUBLISHED :" + aKeyedMsg);
        gProducer.send(aKeyedMsg);
    }

    public void closeProducer() {
        gProducer.close();
    }

// CCS
  /*  public static void main(final String[] args) throws Exception {
        final KafkaProducer kafka = KafkaProducer.getInstance();
        kafka.initialize();
        final String builderCCS = "{\"OP_TYPE\":\"I\",\"ENTITY_LAST_MODIFIED_DATE\":\"2008-12-29T07:20:58\",\"LAST_MODIFIED_DATE\":\"2015-06-06T04:11:22\",\"SITE_ID\":\"12347134\",\"CONTACT_ID\":\"481976852\",\"STATUS\":\"Active\",\"ROLE\":\"Host\",\"FIRST_NAME\":\"sahithyaperfect\",\"LAST_NAME\":\"machine_kafka\",\"EMAIL\":\"ccarServicePerf345XEF@reardencomm-dev.talaris.com\",\"SITEURL\":\"reardencomm-dev.webex.com.45\",\"LOCALE\":\"en-us\",\"COUNTRY\":\"100\",\"@version\":\"1\",\"@timestamp\":\"2018-03-28T07:03:46.714Z\"}";

        // "{\"OP_TYPE\":\"I\",\"ENTITY_LAST_MODIFIED_DATE\":\"2018-03-02T21:37:45\",\"LAST_MODIFIED_DATE\":\"2018-03-02T21:37:45\",\"SITE_ID\":\"12347134\",\"CONTACT_ID\":\"7776665556\",\"STATUS\":\"Active\",\"ROLE\":\"Host\",\"FIRST_NAME\":\"codedrop6\",\"LAST_NAME\":\"ccs-incr6\",\"EMAIL\":\"testqa6@ccs-incr-poe.cisco.com\",\"WORK_PHONE_NUMBER\":null,\"MOBILE_PHONE_NUMBER\":null,\"SITEURL\":\"reardencomm-dev.webex.com\",\"LOCALE\":\"en-us\",\"COUNTRY\":null,\"@version\":\"1\",\"@timestamp\":\"2018-03-02T21:37:48.987Z\"}";
                //"{\"meta\":{\"creator\":\"cn=AtlasServiceAccount.prod,ou=System Services,dc=Identity\",\"created\":\"2017-03-24T22:54:40.347Z\",\"modifier\":\"cn=brokerUser,ou=System Services,dc=Identity\",\"lastModified\":\"2018-03-12T19:06:30.343Z\",\"version\":\"W\\/\\\"49362047335\\\"\"},\"changeType\":\"create\",\"schemas\":[\"urn:scim:schemas:core:1.0\",\"urn:scim:schemas:extension:cisco:commonidentity:1.0\"],\"name\":{\"givenName\":\"Aaron\",\"familyName\":\"Koontz\"},\"active\":true,\"changeNumber\":\"000001621B9B604775910440D891\",\"id\":\"c3ffa53c-2af9-4ccd-a55d-c8115489e01b\",\"userName\":\"aaron.koontz@empower-retirement.com\",\"orgId\":\"9eebd2a6-bdec-455f-a444-ae836d650e69\",\"objectType\":null}";

        //"{\"OP_TYPE\":\"U\",\"ENTITY_LAST_MODIFIED_DATE\":\"2009-12-15T20:28:29\",\"LAST_MODIFIED_DATE\":\"2015-06-06T06:41:19\",\"SITE_ID\":\"12347134\",\"CONTACT_ID\":\"484874897\",\"STATUS\":\"Active\",\"ROLE\":\"Host\",\"FIRST_NAME\":\"drsvp3064\",\"LAST_NAME\":\"testing_test\",\"WORK_PHONE_NUMBER\":null,\"MOBILE_PHONE_NUMBER\":null,\"SITEURL\":\"reardencomm-dev.webex.com\",\"LOCALE\":\"en-us\",\"COUNTRY\":null}";
        final String topic = "UNIFIED_AUTHR_CCS_PROF_INCR";
        System.out.println(topic+"=\n"+builderCCS);
        final KeyedMessage<String, String> keyedMsg = new KeyedMessage<>(topic, builderCCS.toString());
        kafka.publishMessage(keyedMsg);
        kafka.closeProducer();
    }
*/

//CCO
  /*  public static void main(final String[] args) throws Exception {
        final KafkaProducer kafka = KafkaProducer.getInstance();
        kafka.initialize();
        final String builderCCO = "{\"table\":\"XXCCS_O.XXCCS_CPR_C3_USER_EXT_ATTRS\",\"op_type\":\"I\",\"op_ts\":\"2018-02-08 05:26:14.001252\",\"current_ts\":\"2018-02-07T21:26:19.842000\",\"pos\":\"00000065980060511798\",\"before\":{\"BATCH_ID\":null,\"PROCESS_FLAG\":\"P\",\"PROCESS_DATE\":\"2018-02-07:13:09:37\",\"PROCESS_STATUS\":\"S\",\"PROCESS_MSG\":\"PROCESSED\",\"USER_ID\":1911870289,\"USER_NAME\":\"shilpa-junit1testpoe\",\"LAST_UPDATE_DATE\":\"2018-02-07:13:09:37\",\"LAST_UPDATED_BY\":230294865,\"CREATION_DATE\":\"2018-02-07:13:09:37\",\"CREATED_BY\":230294865,\"LAST_UPDATE_LOGIN\":-1,\"LAST_LOGON_DATE\":\"2018-02-07:13:09:37\",\"CCO_UNIVERSAL_ID\":100646488,\"ACCESS_LEVEL\":2,\"CCIE_NUMEBR\":null,\"SVO_SUBMIT_FLAG\":null,\"CASE_MANAGEMENT\":\"0\",\"SJPROD_USER_ID\":null,\"USER_ADMIN\":null,\"ACCOUNT_STATUS\":\"ACTIVE\",\"PICA_CONTRACT_FLAG\":\"N\",\"SERVICE_CONTRACT_FLAG\":\"N\",\"PASSWORD_CHANGE_DATE\":null,\"TEST_USER_TOKEN\":null,\"LAST_MSG_ADB_SEQUENCE\":203012925,\"SMB_CONTRACT_FLAG\":\"N\",\"IPC_ACCESS\":null,\"SCC_ACCESS\":null,\"COMMERCE_ACCESS\":null,\"BUY_METHOD_CODE\":null,\"SALES_CHANNEL_CODE\":null,\"PRIMARY_CONTRACT\":\"1074539\",\"RELATIONSHIP_TYPE\":null,\"QUALIFICATION\":null,\"CERTIFICATION\":null,\"EA_ATTRIBUTE2\":null,\"SELL_EMAIL_TO_THIRD_PARTY\":\"No\",\"EA_ATTRIBUTE1\":null,\"EA_ATTRIBUTE4\":null,\"EA_ATTRIBUTE5\":null,\"DPL_FLAG\":\"FULL_ADDRESS_VALIDATED\",\"EA_ATTRIBUTE3\":null,\"COUNTRY_DESCRIPTION\":null,\"COUNTRY_CODE\":null,\"OK_MAIL\":\"No\",\"OK_FAX\":\"No\",\"EMAIL_ADDRESS\":\"shilpa-junit1testpoe@yopmail.com\",\"LANGUAGE_PREF\":null,\"OK_PHONE\":\"No\",\"PHONE_NUMBER\":null,\"OK_EMAIL\":\"No\",\"FIRST_NAME\":\"TestName11\",\"LAST_NAME\":\"TestLASTNAME\",\"USER_SEVERITY\":1,\"USER_TYPE\":\"CUSTOMER\",\"CASE_MANAGEMENT_MODE\":\"CREATE,VIEW,UPDATE\",\"FAST_START_USER_FLAG\":\"N\"},\"after\":{\"BATCH_ID\":null,\"PROCESS_FLAG\":\"P\",\"PROCESS_DATE\":\"2018-02-07:13:09:37\",\"PROCESS_STATUS\":\"S\",\"PROCESS_MSG\":\"PROCESSED\",\"USER_ID\":1911870289,\"USER_NAME\":\"shilpa-junit11testpoe\",\"LAST_UPDATE_DATE\":\"2018-02-08:05:26:14\",\"LAST_UPDATED_BY\":1151,\"CREATION_DATE\":\"2018-02-07:13:09:37\",\"CREATED_BY\":230294865,\"LAST_UPDATE_LOGIN\":-1,\"LAST_LOGON_DATE\":\"2018-02-08:05:26:14\",\"CCO_UNIVERSAL_ID\":100646488,\"ACCESS_LEVEL\":2,\"CCIE_NUMEBR\":null,\"SVO_SUBMIT_FLAG\":null,\"CASE_MANAGEMENT\":\"0\",\"SJPROD_USER_ID\":null,\"USER_ADMIN\":null,\"ACCOUNT_STATUS\":\"ACTIVE\",\"PICA_CONTRACT_FLAG\":\"N\",\"SERVICE_CONTRACT_FLAG\":\"Y\",\"PASSWORD_CHANGE_DATE\":null,\"TEST_USER_TOKEN\":null,\"LAST_MSG_ADB_SEQUENCE\":203012925,\"SMB_CONTRACT_FLAG\":\"N\",\"IPC_ACCESS\":null,\"SCC_ACCESS\":null,\"COMMERCE_ACCESS\":null,\"BUY_METHOD_CODE\":null,\"SALES_CHANNEL_CODE\":null,\"PRIMARY_CONTRACT\":\"1074539\",\"RELATIONSHIP_TYPE\":null,\"QUALIFICATION\":null,\"CERTIFICATION\":null,\"EA_ATTRIBUTE2\":null,\"SELL_EMAIL_TO_THIRD_PARTY\":\"No\",\"EA_ATTRIBUTE1\":null,\"EA_ATTRIBUTE4\":null,\"EA_ATTRIBUTE5\":null,\"DPL_FLAG\":\"FULL_ADDRESS_VALIDATED\",\"EA_ATTRIBUTE3\":null,\"COUNTRY_DESCRIPTION\":null,\"COUNTRY_CODE\":null,\"OK_MAIL\":\"No\",\"OK_FAX\":\"No\",\"EMAIL_ADDRESS\":\"shilpa-junit1testpoe@yopmail.com\",\"LANGUAGE_PREF\":null,\"OK_PHONE\":\"No\",\"PHONE_NUMBER\":null,\"OK_EMAIL\":\"No\",\"FIRST_NAME\":\"TestName11\",\"LAST_NAME\":\"TestLASTNAME\",\"USER_SEVERITY\":3,\"USER_TYPE\":\"CUSTOMER\",\"CASE_MANAGEMENT_MODE\":\"CREATE,VIEW,UPDATE\",\"FAST_START_USER_FLAG\":\"N\"}}";
        final String topic = "XXCCS_O.XXCCS_CPR_C3_USER_EXT_ATTRS";
        System.out.println(topic+"=\n"+builderCCO);
        final KeyedMessage<String, String> keyedMsg = new KeyedMessage<>(topic, builderCCO.toString());
        kafka.publishMessage(keyedMsg);
        kafka.closeProducer();
    }
*/

  //Harish
    public static void main(final String[] args) throws Exception {
        //final KafkaProducer11 kafka = KafkaProducer11.getInstance();
        String str = "cits-eb-kfk-stg1-001:9097,cits-eb-kfk-stg1-002:9097,cits-eb-kfk-stg1-003:9097,cits-eb-kfk-stg1-101:9097,cits-eb-kfk-stg1-102:9097,cits-eb-kfk-stg1-103:9097";
        final KafkaProducer11 kafka = KafkaProducer11.getKafkaInstance(str, "break");
        //kafka.initialize();
       final String builderCMM = "{\n" +
               "\t\"op\": \"index\",\n" +
               "\t\"I\": \"C:47477354:5388722:13751\",\n" +
               "\t\"containerGuId\": \"C:47477354|13751\",\n" +
               "\t\"guId\": \"13751\",\n" +
               "\t\"M\": \"Right to Software Download.Download software image\",\n" +
               "\t\"A\": \"5388722\",\n" +
               "\t\"C\": \"C:47477354\",\n" +
               "\t\"inventoryItemId\": \"5388722\",\n" +
               "\t\"eventType\": \"OTM\",\n" +
               "\t\"beCreateDate\": \"2018-10-29T15:49:04.935Z\",\n" +
               "\t\"beUpdateDate\": \"2018-10-29T15:49:04.935Z\",\n" +
               "\t\"itemFamilyId\": \"27762\",\n" +
               "\t\"itemFamilyName\": \"C6000\",\n" +
               "\t\"ds_tid\": \"ObjectId('C:47477354:5388722:13751')\",\n" +
               "\t\"ds_ts\": \"2018-10-29T15:49:04.935Z\",\n" +
               "\t\"p_tid\": \"b28c7374-6383-483c-a01f-842e1ec0d4d8\",\n" +
               "\t\"p_ts\": \"2018-10-29T10:19:28.211Z\",\n" +
               "\t\"mdfLeafNodeId\": \"_NULL_\"\n" +
               "}";

        //final String topic = PropertyUtil.getProperty("kafkaTopic");
        System.out.println(gKafkaTopic+"=\n"+builderCMM);
        Integer partition = 1;
        String key =null;
        //Long timestamp = null;
        final ProducerRecord<String, String> keyedMsg = new ProducerRecord<>(gKafkaTopic, partition, key, builderCMM);
        try{
            //kafka.publishMessage(keyedMsg);
            System.out.println(gBootstrapServers);
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println(keyedMsg);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            kafka.closeProducer();
        }
    }
}
