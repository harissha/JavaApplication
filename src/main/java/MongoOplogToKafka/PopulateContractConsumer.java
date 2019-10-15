package MongoOplogToKafka;

import javaApiPractice.PropertyUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class PopulateContractConsumer implements Callable<Boolean> {
    private final BlockingQueue<HashSet<Document>> gQueue;
    private final Phaser phaser;
    private final List<ProducerRecord<String, String>> getKafkaMessageList = null;
    private String gKafkaTopic = PropertyUtil.getProperty("kafkaTopic");
    private String gKafkaPartition = PropertyUtil.getProperty("kafka.partition.size");
    private ProducerRecord<String, String> gKeyedMsg;
    private String gKafkaMessage;

    private static final JsonWriterSettings gWriterSettings = new JsonWriterSettings(JsonMode.SHELL, true);
    private static final Logger gLogger = Logger.getLogger(PopulateContractConsumer.class);
    private static KafkaConnection gKafkaProducer;

    static {
        try {
            gKafkaProducer = KafkaConnection.getInstance();
        } catch (final ServiceException e) {
            gLogger.error("Exception in IndexEntitlementConsumer static block ServiceException " + e);
        }
    }

    public PopulateContractConsumer(final Phaser aPhaser, final BlockingQueue<HashSet<Document>> aQueue) {
        phaser  = aPhaser;
        this.gQueue = aQueue;
    }


    @Override
    public Boolean call() throws Exception {

        try {
            final HashSet<Document> theAttributeSet = this.gQueue.take();

            for (Document doc : theAttributeSet) {
                gKafkaMessage = doc.toJson(gWriterSettings)
                        .replaceAll("ObjectId\\((.*)\\)", "$1")
                        .replaceAll("ISODate\\((.*)\\)", "$1");
                gKeyedMsg = new ProducerRecord<>(gKafkaTopic, new Random().nextInt(Integer.valueOf(this.gKafkaPartition)), null, gKafkaMessage);
                getKafkaMessageList.add(gKeyedMsg);
            }
            gKafkaProducer.publishMessage(getKafkaMessageList);
            phaser.arriveAndDeregister();

        } catch (final Exception theEx) {
            gLogger.error("Error encountered while processing the AttributeSet..." + ExceptionUtils.getStackTrace(theEx));
        }
        return true;
    }

}
