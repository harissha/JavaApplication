package blockingQueueImplementation;

import MongoOplogToKafka.ServiceException;
import thread.CITSThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class BlockingMessageMain implements Callable<Boolean> {

    // Creating BlockingQueue of size 10
    // BlockingQueue supports operations that wait for the queue to become non-empty when retrieving an element, and
    // wait for space to become available in the queue when storing an element.
    private final BlockingQueue<Message> msgQueue = new ArrayBlockingQueue<>(10);
    private final Phaser phaser;

    public BlockingMessageMain(final Phaser aPhaser) {
        phaser  = aPhaser;
    }

    public Boolean call() throws ServiceException{

        MessageProducer messageProducer = new MessageProducer(phaser, msgQueue);
        MessageConsumer messageConsumer = new MessageConsumer(phaser, msgQueue);

        phaser.register();
        CITSThreadPoolExecutor.getInstance().addTask(messageProducer);
        CITSThreadPoolExecutor.getInstance().addTask(messageConsumer);

        return true;
    }


    public static void main(String Args[]){

        try {
            final Phaser thePhaser = new Phaser(1);



            /*// starting producer to produce messages in queue
            new Thread(messageProducer).start();

            // starting consumer to consume messages from queue
            new Thread(messageConsumer).start();*/

            BlockingMessageMain blockingMessageMain = new BlockingMessageMain(thePhaser);

            thePhaser.register();
            CITSThreadPoolExecutor.getInstance().addTask(blockingMessageMain);

            thePhaser.arriveAndDeregister();
            thePhaser.awaitAdvance(0);
            CITSThreadPoolExecutor.getInstance().shutdown();

        }catch(ServiceException se){
            se.printStackTrace();
        }

    }
}
