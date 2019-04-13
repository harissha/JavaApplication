package geeksforgeeks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* This is a simple Java program.
   FileName : "HelloWorld.java". */
public class HelloWorld {
    // Your program begins with a call to main().
    // Prints "Hello, World" to the terminal window.
    public static void main(String args[])
    {
        System.out.println("Hello World");


        /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter an integer");
        try{
        int a = Integer.parseInt(br.readLine());
        System.out.println("Enter a String");
        String b = br.readLine();
        }catch(IOException ioe){}*/


        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        long row =0;
        long col =0;
        long wall =0;
        long wall2 =0;
        long dragon =0;
        ArrayList<Long> list = new ArrayList<>();

        for(int n=0; n<t;n++) {

            //for (int m = 0; m < 2; m++) {
                //int str1 = sc.nextInt();
                //sum=sum+str1;2

                //if(m==0) {
                    row = sc.nextLong();
                    col = sc.nextLong();
               // }
                System.out.println("row col = "+row+":"+col);

               // if(m==1) {
                    wall = sc.nextLong();
                    wall2 = sc.nextLong();
                //}
                System.out.println("wall = "+wall+":"+wall2);

                //dragon = wall+1;

                dragon = (wall2+1)*(wall+1);

                /*for(int i=0; i<wall2; i++){
                    dragon = dragon + wall + 1;
                }*/

            System.out.println("dragon : "+dragon);

                list.add(dragon);

            //}

        }

        //list.stream().forEach();

        //list.stream().forEach(System.out::println);
        System.out.println(list);
        for(int i=0; i<list.size(); i++){
                System.out.println(list.get(i));
        }
        for(Long l : list){
            System.out.println(l);
        }
    }
}
