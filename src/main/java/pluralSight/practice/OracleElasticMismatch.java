package pluralSight.practice;

import main.java.pluralSight.dataStructure.ElasticHttpClient;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import static pluralSight.practice.Oracle.countRows;
import static pluralSight.practice.Oracle.getInstance;

public class OracleElasticMismatch implements Callable<Integer> {

    public OracleElasticMismatch(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public ElasticHttpClient getEl() {
        return el;
    }

    public void setEl(ElasticHttpClient el) {
        this.el = el;
    }

    public RangeMismatch getRangeMismatch() {
        return rangeMismatch;
    }

    public void setRangeMismatch(RangeMismatch rangeMismatch) {
        this.rangeMismatch = rangeMismatch;
    }

    public WriteCSVFile getWriteCSVFile() {
        return writeCSVFile;
    }

    public void setWriteCSVFile(WriteCSVFile writeCSVFile) {
        this.writeCSVFile = writeCSVFile;
    }

    public List getRangeMismatchList() {
        return rangeMismatchList;
    }

    public void setRangeMismatchList(List rangeMismatchList) {
        this.rangeMismatchList = rangeMismatchList;
    }

    public Long getOracleCount() {
        return oracleCount;
    }

    public void setOracleCount(Long oracleCount) {
        this.oracleCount = oracleCount;
    }

    public Long getEsCount() {
        return esCount;
    }

    public void setEsCount(Long esCount) {
        this.esCount = esCount;
    }

    public int getAbatch() {
        return abatch;
    }

    public void setAbatch(int abatch) {
        this.abatch = abatch;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getIncrementBy() {
        return incrementBy;
    }

    public void setIncrementBy(long incrementBy) {
        this.incrementBy = incrementBy;
    }

    public long getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(long rangeStart) {
        this.rangeStart = rangeStart;
    }

    public long getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(long rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    private Connection conn = null;
    private ElasticHttpClient el = null;
    private RangeMismatch rangeMismatch = null;
    private WriteCSVFile writeCSVFile = null;
    private List rangeMismatchList = new ArrayList();
    private Long oracleCount =0L;
    private Long esCount =0L;
    private int abatch = 0;

    private String tableName = "XXCCS_DM_NOWAR_ASSET";
    private long incrementBy = 499999;
    private long rangeStart =1;
    private long rangeEnd = 5500500000L;
    private long min = 1;
    private long max = 500000;



    @Override
    public Integer call() throws Exception {


        try {
            el = ElasticHttpClient.getInstance();
            conn = getInstance();
            setEl(el);
            setConn(conn);

/*            long incrementBy = 499999;

            long rangeStart =1;
            long rangeEnd = 5500500000L;

            long min = 0;
            long max = 0;*/

           /* for(long i=rangeStart; i<=rangeEnd; i++){
                min = i;
                i+=incrementBy;
                max = i;*/
                System.out.println("min : "+getMin());
                System.out.println("max : "+getMax());
                System.out.println("--------- Oracle response --------------");
                System.out.println("rowCount = " + countRows(conn, getTableName(), min, max));
                oracleCount = countRows(conn, getTableName(), min, max);
                esCount = el.executeElasticRequest("http://cits-eb-es-lt-009:8000/elstsearch/duet_no_warranty_v1/assets_type/_count",min, max);
                System.out.println("--------- Elastic response -------------");
                System.out.println("docCount = " +el.executeElasticRequest("http://cits-eb-es-lt-009:8000/elstsearch/duet_no_warranty_v1/assets_type/_count",min, max));

                if (oracleCount.equals(esCount)){
                    System.out.println("........................................................................count matching");
                }else {

                    System.out.println("****************************************************************************COUNT NOT MATCHING***********************");
                    System.out.println("count not matching for the range min = "+min);
                    System.out.println("count not matching for the range max = "+max);

                    rangeMismatch = new RangeMismatch(min, max, oracleCount, esCount);
                    rangeMismatchList.add(rangeMismatch);

                    System.out.println("*********************************************************************************************************************");
                }
                abatch++;
                System.out.println("-----------------------------------------------CurrentBatchIndex = "+abatch);

            //}
            System.out.println("tableName=" + tableName);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            // release database resources
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return abatch;
        }
    }

    public static void main(String[] args){

        long min = 1;
        long max = 500000;

        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);

        //create a list to hold the Future object associated with Callable
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();

        //Create OracleElasticMismatch instance
        OracleElasticMismatch oracleElasticMismatch = new OracleElasticMismatch(min, max);

        for(long i=oracleElasticMismatch.getRangeStart(); i<=oracleElasticMismatch.getRangeEnd(); i++){
            min=i;
            i+=oracleElasticMismatch.getIncrementBy();
            max=i;
            //submit Callable tasks to be executed by thread pool
            Future<Integer> future = executor.submit(new OracleElasticMismatch(min,max));
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for(Future<Integer> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date()+ "::"+" Future result is ---> " + fut.get() + "; And Task done is " + fut.isDone());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if(oracleElasticMismatch.getRangeMismatchList().size()!=0){
            oracleElasticMismatch.setWriteCSVFile(new WriteCSVFile(oracleElasticMismatch.getRangeMismatchList()));
        }
        oracleElasticMismatch.getWriteCSVFile();
        //shut down the executor service now
        executor.shutdown();
    }

}
