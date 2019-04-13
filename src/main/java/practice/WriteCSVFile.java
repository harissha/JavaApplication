package practice;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class WriteCSVFile {
    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    //File header
    private static final String HEADER = "MIN_VAL, MAX_VAL, ORACLE_COUNT, ELASTIC_COUNT";

    public WriteCSVFile(List rangeMismatchObjects)
    {
        String csvFile = "C:/Users/harissha/Desktop/RangeMismatch.csv";
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter(csvFile);

            //Adding the header
            fileWriter.append(HEADER);
            //New Line after the header
            fileWriter.append(LINE_SEPARATOR);

            //Iterate the empList
            Iterator it = rangeMismatchObjects.iterator();
            while(it.hasNext())
            {
                RangeMismatch rangeMismatch = (RangeMismatch)it.next();
                fileWriter.append(String.valueOf(rangeMismatch.getMin()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(rangeMismatch.getMax()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(rangeMismatch.getOracleCount()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(rangeMismatch.getEsCount()));
                fileWriter.append(LINE_SEPARATOR);
            }
            System.out.println("Write to CSV file Succeeded!!!");
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        finally
        {
            try
            {
                fileWriter.close();
            }
            catch(IOException ie)
            {
                System.out.println("Error occured while closing the fileWriter");
                ie.printStackTrace();
            }
        }
    }

    public static void main(String args[])
    {

    }
}
