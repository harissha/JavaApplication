package programs;

import java.util.stream.IntStream;

public class PrimeNumbers {

    public static boolean isPrime1(int number) {
        return !IntStream.rangeClosed(2, number/2).anyMatch(i -> number%i == 0);
    }

    public static boolean isPrime2(int number) {
        return IntStream.rangeClosed(2, number/2).noneMatch(i -> number%i == 0);
    }

    public static boolean isPrime3(int number) {

        // Even numbers
        if (number % 2 == 0) {
            return number == 2;
        }

        // Odd numbers
        int limit = (int)(0.1 + Math.sqrt(number));
        for (int i = 3; i <= limit; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /* * Java method to check if an integer number is prime or not. * @return true if number is prime, else false */
    public static boolean isPrime(int number) {
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 2; i < sqrt; i++) {
            if (number % i == 0) {
                // number is perfectly divisible - no prime
                 return false; }
        } return true;
    }


}
