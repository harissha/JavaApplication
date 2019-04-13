package pluralSight.practice;

/*import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFException;
import com.unboundid.ldif.LDIFReader;*/

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.ldif.LdifEntry;
import org.apache.directory.api.ldap.model.ldif.LdifReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LdifReaderProgram {

    public static void main(String[] args) {

        /*LDIFReader ldifReader = null;
        try{
            ldifReader = new LDIFReader("C:\\Users\\harissha\\Desktop\\SWC\\datasetConsumerAllZone-y13sj1yst003-201805030002.ldif");

            try{
                List<Entry> readEntries = new ArrayList<>();


                readEntries.add(ldifReader.readEntry());
                for(Entry e : readEntries){
                    System.out.println(e);
                }

                System.out.println(ldifReader.readLDIFRecord());
                System.out.println(ldifReader.readLDIFRecord().getDN());

            }catch (LDIFException |LDAPException le) {
                le.printStackTrace();
            }


        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally {
            try{
            ldifReader.close();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }*/


        LdifReader ldifReader = new LdifReader();

        List<LdifEntry> ldifEntries = null;

        try {
            ldifEntries = ldifReader.parseLdifFile("C:\\Users\\harissha\\Desktop\\SWC\\datasetConsumerAllZone-y13sj1yst003-201805030002.ldif");

            ldifReader.close();
        }catch(IOException | LdapException e){
            e.printStackTrace();
        }

        if ( ( ldifEntries != null ) && !ldifEntries.isEmpty() )
        {
            // this ldif will have only one entry

            for(LdifEntry ldiEntryData:ldifEntries) {
                // LdifEntry ldifEntry = ldifEntries.get( 0 );

                System.out.println( "Adding entry {} "+ ldiEntryData.get("wx11Password"));
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println( "Adding entry {} "+ ldiEntryData.getDn());
                System.out.println( "Adding entry {} "+ ldiEntryData.getDn().getParent());
                System.out.println( "Adding entry {} "+ ldiEntryData.getDn().getRdn());
                System.out.println("___________________________________________________________________________________________");

            }
        }

    }
}