package practice;


import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PrimeNoUsingMultithreading {
    public static void main(String[] args) throws Exception
    {
        FutureTask[] primeNumberTasks = new FutureTask[2];
        Callable callable = new Prime();
        primeNumberTasks[0] = new FutureTask(callable);
        Thread t = new Thread(primeNumberTasks[0]);
        t.start();
    }
}

class Prime implements Callable
{
    long j;
    public Object call() throws Exception
    {
        for(long i=0;i<=100;i++)
        {
            for(j=2;j<=i;j++)
            {
                if(i%j==0)
                    break;
            }
            if(j==i)
            {
                System.out.println("Thread " +
                        Thread.currentThread().getId() +
                        " is running"+"---------------------------"+i);
            }
        }
        return j;
    }
}