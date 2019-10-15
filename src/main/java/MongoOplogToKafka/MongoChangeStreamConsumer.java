package MongoOplogToKafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class MongoChangeStreamConsumer implements Callable<Boolean> {

    private BlockingQueue<String> gConsumerQueue;
    private String msg;
    private Phaser gPhaser;

    public MongoChangeStreamConsumer(Phaser phaser, BlockingQueue<String> queue){

        gPhaser = phaser;
        gConsumerQueue = queue;

    }


    public Boolean call(){

        try{
            while((msg = gConsumerQueue.take()) != "exit" ){
                System.out.println("BlockingConsumer: Message - " + msg + " consumed.");
            }
            gPhaser.arriveAndDeregister();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
        return true;
    }

}
