package blockingQueueImplementation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class MessageProducer implements Callable<Boolean> {

    private BlockingQueue<Message> producerMsgQueue;
    private final Phaser producerPhaser;


    public MessageProducer(final Phaser aPhaser, BlockingQueue<Message> queue){
        this.producerPhaser = aPhaser;
        this.producerMsgQueue = queue;
    }

    @Override
    public Boolean call(){
        // producing messages
        for (int i = 1; i <= 100000; i++) {
            Message msg = new Message("i'm msg " + i);
            try {
                Thread.sleep(10);
                producerMsgQueue.put(msg);
                System.out.println("BlockingProducer: Message - " + msg.getMsg() + " produced.");
            } catch (Exception e) {
                System.out.println("Exception:" + e);
            }
        }

        // adding exit message
        Message msg = new Message("All done from Producer side. Produced 5 Messages");
        try {
            producerMsgQueue.put(msg);
            System.out.println("BlockingProducer: Exit Message - " + msg.getMsg());
            producerPhaser.arriveAndDeregister();
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

        return true;
    }
}
