package javaTesting.Collections.collections.sortMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Arrays_sort_Vs_Collections_sort {

    public static void main(String[] args)
    {
        // create an array of string objs
        String domains[] = {"Practice", "Geeks",
                "Code", "Quiz"};

        // Here we are making a list named as Collist
        List colList =
                new ArrayList(Arrays.asList(domains));

        // Collection.sort() method is used here
        // to sort the list elements.
        Collections.sort(colList);

        // Let us print the sorted list
        System.out.println("List after the use of" +
                " Collection.sort()  :\n" +
                colList);
    }
}
