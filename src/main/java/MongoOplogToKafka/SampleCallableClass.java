package MongoOplogToKafka;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class SampleCallableClass implements Callable<Boolean> {
    private Phaser gPhaser;
    private static final Logger gLogger = Logger.getLogger(SampleCallableClass.class);
    private ChangeStreamDocument<Document> changes;


    public SampleCallableClass(Phaser phaser) {
        gPhaser = phaser;
    }


    @Override
    public Boolean call() throws Exception {

        try {
            System.out.println("your function code goes here............. in try block");

            gPhaser.arriveAndDeregister();
        } catch (final Exception theEx) {
            gLogger.error("Error encountered while processing..." + ExceptionUtils.getStackTrace(theEx));
        }
        return true;
    }

}
