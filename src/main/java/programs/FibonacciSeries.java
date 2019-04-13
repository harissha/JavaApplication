package programs;

public class FibonacciSeries {
    public static void main(String[] args) {

        int n = 10, t1 = 0, t2 = 1;
        System.out.print("First " + n + " terms: ");

        for (int i = 1; i <= n; ++i)
        {
            System.out.print(t1 + " + ");

            int sum = t1 + t2;
            t1 = t2;
            t2 = sum;
        }

        int c1 = 0, c2 = 1;
        System.out.print("\nTill " + n + " numbers: ");
        for (int i = 1; c1 <= n; ++i)
        {
            System.out.print(c1 + " + ");

            int sum = c1 + c2;
            c1 = c2;
            c2 = sum;
        }

    }
}
