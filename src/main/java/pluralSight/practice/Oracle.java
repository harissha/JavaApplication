package pluralSight.practice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.java.pluralSight.dataStructure.ElasticHttpClient;


public class Oracle {


    private static Connection instance;

    private Oracle(){}

    //static block initialization for exception handling
    static{
        try{
            instance = new Oracle().getConnection();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static Connection getInstance() throws Exception{
        return instance;
    }




    public Connection getConnection() throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@(DESCRIPTION=(CONNECT_TIMEOUT=5)(TRANSPORT_CONNECT_TIMEOUT=3)(RETRY_COUNT=1)(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=scan-prd-2102)(PORT=1541))(ADDRESS=(PROTOCOL=TCP)(HOST=scan-prd-2101)(PORT=1541)))(CONNECT_DATA=(SERVICE_NAME=CSFPRD_SRVC_DM.cisco.com)(SERVER=DEDICATED)))";
        String username = "XXCTS_DM_U";
        String password = "Ze_U2s6K";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static long countRows(Connection conn, String tableName, long min, long max) throws SQLException {
        // select the number of rows in the table
        Statement stmt = null;
        ResultSet rs = null;
        long rowCount = 0L;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select rows_inserted from XXCTS_DM_o.XXCCS_DM_insert_LOG where table_name='" + tableName + "' and min_val >= " + min + " and max_val <= " +max + "");
            // get the number of rows from the result set
            rs.next();
            rowCount = rs.getInt("rows_inserted");
        } finally {
            rs.close();
            stmt.close();
        }

        return rowCount;
    }

    public static void main(String[] args) {
        Connection conn = null;
        ElasticHttpClient el = null;
        RangeMismatch rangeMismatch = null;
        WriteCSVFile writeCSVFile = null;
        List rangeMismatchList = new ArrayList();
        Long oracleCount =0L;
        Long esCount =0L;
        int abatch = 0;
        try {
            el = ElasticHttpClient.getInstance();
            conn = getInstance();
            String tableName = "XXCCS_DM_NOWAR_ASSET";
            long incrementBy = 499999;

            long rangeStart =1;
            long rangeEnd = 5500500000L;

            long min = 0;
            long max = 0;

            for(long i=rangeStart; i<=rangeEnd; i++){
                min = i;
                i+=incrementBy;
                max = i;
                System.out.println("min : "+min);
                System.out.println("max : "+max);
                System.out.println("--------- Oracle response --------------");
                System.out.println("rowCount = " + countRows(conn, tableName, min, max));
                oracleCount = countRows(conn, tableName, min, max);
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

            }
            System.out.println("tableName=" + tableName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // release database resources
            try {
                conn.close();
                if(rangeMismatchList.size()!=0){
                    writeCSVFile = new WriteCSVFile(rangeMismatchList);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



   /* public static void main(String args[]) throws SQLException {
        Connection con = null;
        Statement stmt = null;

        try{
//step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

//step2 create  the connection object
            con=DriverManager.getConnection(
                    "jdbc:oracle:thin:@(DESCRIPTION=(CONNECT_TIMEOUT=5)(TRANSPORT_CONNECT_TIMEOUT=3)(RETRY_COUNT=1)(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=scan-prd-2102)(PORT=1541))(ADDRESS=(PROTOCOL=TCP)(HOST=scan-prd-2101)(PORT=1541)))(CONNECT_DATA=(SERVICE_NAME=CSFPRD_SRVC_DM.cisco.com)(SERVER=DEDICATED)))", "XXCTS_DM_U", "Ze_U2s6K");

//step3 create the statement object
            stmt=con.createStatement();

//step4 execute query
            ResultSet rs=stmt.executeQuery("select rows_inserted from XXCTS_DM_o.XXCCS_DM_insert_LOG where table_name='XXCCS_DM_NOWAR_ASSET' and min_val >= 500001 and max_val <= 1000000");

           *//* while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(1)+"  "+rs.getString(3));
*//*
            *//*int rowCount = -1;
            while(rs.next()) {
                rowCount = rs.getRow();
            }
            System.out.println(rowCount);*//*

            rs.next();
            System.out.println(rs.getInt("rows_inserted"));

        }catch(Exception e){ System.out.println(e);}
        finally{
            //step5 close the connection object
            con.close();
        }

    }
*/




   /* public static void main(String[] argv) {

        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@(DESCRIPTION=(CONNECT_TIMEOUT=5)(TRANSPORT_CONNECT_TIMEOUT=3)(RETRY_COUNT=1)(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=scan-prd-2102)(PORT=1541))(ADDRESS=(PROTOCOL=TCP)(HOST=scan-prd-2101)(PORT=1541)))(CONNECT_DATA=(SERVICE_NAME=CSFPRD_SRVC_DM.cisco.com)(SERVER=DEDICATED)))", "XXCTS_DM_U", "Ze_U2s6K");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }*/
}