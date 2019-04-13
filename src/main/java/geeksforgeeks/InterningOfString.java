package geeksforgeeks;

public class InterningOfString {

    public static void main(String[] args)
    {
        // S1 refers to Object in the Heap Area
        String s1 = new String("GFG"); // Line-1

        // S2 refers to Object in SCP Area
        String s2 = s1.intern(); // Line-2

        // Comparing memory locations
        // s1 is in Heap
        // s2 is in SCP
        System.out.println(s1 == s2);

        // Comparing only values
        System.out.println(s1.equals(s2));

        // S3 refers to Object in the SCP Area
        String s3 = "GFG"; // Line-3

        System.out.println(s2 == s3);


        // S4 refers to Object in the Heap Area
        String s4 = new String("GFG"); // Line-1

        // S5 now refers to Object in SCP Area
        String s5 = s1.concat("GFG"); // Line-2

        // S6 refers to Object in SCP Area
        String s6 = s2.intern(); // Line-3

        System.out.println(s2 == s3);

        // S7 refers to Object in the SCP Area
        String s7 = "GFGGFG"; // Line-4

        System.out.println(s3 == s4);
    }
}
