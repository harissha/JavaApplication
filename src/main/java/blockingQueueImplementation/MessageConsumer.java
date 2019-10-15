package blockingQueueImplementation;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class MessageConsumer implements Callable<Boolean> {

    private BlockingQueue<Message> consumerMsgQueue;
    private Phaser consumerPhaser;

    public MessageConsumer(Phaser aPhaser, BlockingQueue<Message> queue){
        this.consumerPhaser = aPhaser;
        this.consumerMsgQueue = queue;
    }

    public Boolean call(){
        try {
                Message msg;

                while((msg = consumerMsgQueue.take()).getMsg() != "exit" ){
                    Thread.sleep(10);
                    System.out.println("BlockingConsumer: Message - " + msg.getMsg() + " consumed.");
                    consumerPhaser.arriveAndDeregister();
                }
        }catch(InterruptedException  ie){
            ie.printStackTrace();
        }
        return true;
    }

}
