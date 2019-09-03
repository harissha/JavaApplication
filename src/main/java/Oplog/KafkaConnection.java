package Oplog;

/*import it.cits.ebfoundation.cachemanager.exception.ServiceException;
import it.cits.ebfoundation.cachemanager.util.PropertyUtil;*/
import javaApiPractice.PropertyUtil;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;

public class KafkaConnection {
    private static Logger gLogger = Logger.getLogger(KafkaConnection.class);
    private static KafkaProducer<String, String> gProducer;
    private static KafkaConnection gKafkaProducer;
    private int total = 0;
    static {
        gKafkaProducer = new KafkaConnection();
    }

    public KafkaConnection() {
        initialize();
    }

    public static KafkaConnection getInstance() throws ServiceException {
        if (gKafkaProducer == null) {
            throw new ServiceException("Kafka Producer not initialized");
        }
        return gKafkaProducer;
    }

    private static void initialize() {
        final Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, PropertyUtil.getProperty("BOOTSTRAP_SERVERS_CONFIG"));
        prop.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, PropertyUtil.getProperty("SECURITY_PROTOCOL_CONFIG"));
        prop.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, PropertyUtil.getProperty("SSL_TRUSTSTORE_LOCATION_CONFIG"));
        prop.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, PropertyUtil.getProperty("SSL_TRUSTSTORE_PASSWORD_CONFIG"));
        prop.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, PropertyUtil.getProperty("SSL_TRUSTSTORE_TYPE_CONFIG"));
        prop.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, PropertyUtil.getProperty("SSL_KEYSTORE_LOCATION_CONFIG"));
        prop.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, PropertyUtil.getProperty("SSL_KEYSTORE_PASSWORD_CONFIG"));
        prop.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, PropertyUtil.getProperty("SSL_KEYSTORE_TYPE_CONFIG"));
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "it.cits.ebfoundation.cachemanager.util.SimplePartitioner");

        prop.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        gProducer = new KafkaProducer<>(prop);
    }

    public void publishMessage(final List<ProducerRecord<String, String>> aKeyedMsg) {
        this.total = this.total + aKeyedMsg.size();
        gLogger.info("PUBLISHED : " + total);
        System.out.println("PUBLISHED : " + total);
        //System.out.println(aKeyedMsg.size());
        aKeyedMsg.stream().forEach(System.out::println);
        //aKeyedMsg.forEach(gProducer::send);

    }

    public void sendToKafka(final ProducerRecord<String, String> aKeyedMsg) {
        total++;
        gLogger.info("PUBLISHED :" + total);
        gLogger.info("PUBLISHED :" + aKeyedMsg);
        //System.out.println();
        System.out.println("PUBLISHED :" + aKeyedMsg);
        //gProducer.send(aKeyedMsg);
    }
    

    public int getPartitions(final String topic) {
        return gProducer.partitionsFor(topic).size();
    }

    public void publishMessage(final ProducerRecord<String, String> aKeyedMsg) {
        total++;
        gLogger.info("PUBLISHED DELETED :" + total);
        gLogger.info("PUBLISHED DELETE :" + aKeyedMsg);
        //gProducer.send(aKeyedMsg);
    }


    public static void closeProducer() {
    	gProducer.flush();
        gProducer.close();
    }

    @SuppressWarnings("unused")
    public static void main(final String[] args) throws Exception {
        final KafkaConnection kafka = KafkaConnection.getInstance();
        final StringBuilder builder = new StringBuilder("\"message\":\"This is sample message\"");
        final String topic = "kafka11-test1";
        final ProducerRecord<String, String> keyedMsg = new ProducerRecord<>(topic, builder.toString());
        kafka.publishMessage(keyedMsg);
        kafka.closeProducer();
    }
}
