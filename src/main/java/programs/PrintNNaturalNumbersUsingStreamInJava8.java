package programs;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrintNNaturalNumbersUsingStreamInJava8 {
    public static void main(String[] args) {

        // instantiate Scanner class
        Scanner scanner = new Scanner(System.in);

        // get the limit value of n from user
        System.out.print("Enter limit of value n : ");
        int n = scanner.nextInt();

        System.out.println("\nFirst " + n + " Natural numbers using Java 8 Stream : ");
        IntStream.rangeClosed(1, n).forEach(System.out::println);

        System.out.println("\nFirst " + n + " Natural numbers in reverse order : ");
        IntStream.range(1, 11)
                .map(x -> x * -1)
                .sorted()
                .map(Math::abs)
                .forEach(System.out::println);

        System.out.println("\nFirst " + n + " Natural numbers using for-loop : ");
        for(int i=1; i<=n; i++) {
            System.out.println(i);
        }

        System.out.println("\nFirst " + n + " Natural numbers in reverse order : ");
        for(int i=n; i>=1; i--) {
            System.out.println(i);
        }

        System.out.println("\nFirst " + n + " Natural numbers using while-loop : ");
        // local variable
        int i =1;
        while(i<=n){
            System.out.println(i);
            i++;
        }

        System.out.println("\nFirst " + n + " Natural numbers using list : ");
        List<Integer> list = Arrays.asList(1,2,3,4);
        list.stream()
                .sorted(Integer::compare)
                .forEach(System.out::println);

        System.out.println("\nFirst " + n + " Natural numbers in reverse order : ");
        List<Integer> list1 = Arrays.asList(1,2,3,4);
        list1.stream()
                //.boxed() // Converts Intstream to Stream<Integer>
                .sorted(Collections.reverseOrder()) // Method on Stream<Integer>
                .forEach(System.out::println);

        System.out.println("\nFirst " + n + " Natural numbers in reverse order : ");
        List<Integer> list2 = Arrays.asList(1,2,3,4);
        Lists.reverse(list2).stream().forEach(System.out::println);

        System.out.println("\nFirst " + n + " Natural numbers in reverse order : ");
        List<Integer> list3 = Arrays.asList(1,2,3,4);
        list3.stream()
                .sorted((x,y)-> -1)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // finally close - its a good programming practice to wrap inside try-catch block
        scanner.close();
    }
}
