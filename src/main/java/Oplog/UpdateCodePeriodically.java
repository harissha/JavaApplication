package Oplog;

import java.util.Date;

public class UpdateCodePeriodically {

    private static Long startTimestamp;
    static{
        startTimestamp = System.currentTimeMillis();
    }

    public Long repeatFunction(Long endTimestamp1, Long interval1){

        while(true){
            if(endTimestamp1 <= System.currentTimeMillis()){
            System.out.println("Hello : "+new Date()+" : "+new Date().getTime());
            endTimestamp1 = endTimestamp1 + interval1;
            }
        }

    }

    public static void main(String args[]){

        UpdateCodePeriodically updateCodePeriodically = new UpdateCodePeriodically();
        Long interval = 60000L;
        System.out.println(startTimestamp);
        System.out.println(interval);
        Long endTimestamp = startTimestamp + interval;
        System.out.println(endTimestamp);
        endTimestamp = updateCodePeriodically.repeatFunction(endTimestamp,interval);

    }
}
