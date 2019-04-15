package programs;


/*
    Some quick tips out of my head

don't use multiple threads (like Swing does, for example, by mandating that everything is done in the EDT)
don't hold several locks at once. If you do, always acquire the locks in the same order
don't execute foreign code while holding a lock
use interruptible locks


Encapsulate, encapsulate, encapsulate! Probably the most dangerous mistake you can make with locks is exposing your lock to the world (making it public). There is no telling what can happen if you do this as anyone would be able to acquire the lock without the object knowing (this is also why you shouldn't lock this). If you keep your lock private then you have complete control and this makes it more manageable.


Avoid locks by using lock-free data structures (e.g. use a ConcurrentLinkedQueue instead of a synchronized ArrayList)
Always acquire the locks in the same order, e.g. assign a unique numerical value to each lock and acquire the locks with lower numerical value before acquiring the locks with higher numerical value
Release your locks after a timeout period (technically this doesn't prevent deadlocks, it just helps to resolve them after they've occurred)


Don't use locks.
If you must, keep your locks local. Global locks can be really tricky.
Do as little as possible when you hold the lock.
Use stripes to only lock segments of your data
Prefer Immutable types. Many times this means copying data instead of sharing data.
Use compare and set (CAS) mechanics instead, See AtomicReference for example.


Given a design choice, use message-passing where there only locks are in the queue push/pop. This is not always possible but, if it is, you will have very few deadlocks. You can still get them, but you have to try really hard :)


There is pretty much just one big rule when it comes to preventing deadlocks:
If you need to have multiple locks in your code, make sure everyone always acquire them in the same order.
Keeping your code free from locks should pretty much always be your goal though. You can try to get rid of them by using immutable or thread-local objects and lock-free data structures.



This is a classic example of deadlock:

public void methodA(){

  synchronized(lockA){
  //...

   synchronized(lockB){
   //...
  }
 }
}

public void methodB(){

  synchronized(lockB){
  //...

   synchronized(lockA){
   //...
   }
  }
}

This methods would probably create a great deadlock if called by many threads. This is because the objects are locked in different order. This is one of the most common reasons of deadlocks, so if you want to avoid them, be sure that the locks are aquired in order.



Deadlock in java is a programming situation where two or more threads are blocked forever. Java deadlock situation arises with at least two threads and two or more resources.

How to Detect Deadlock in Java
To detect a deadlock in Java, we need to look at the java thread dump of the application, we can generate thread dump using VisualVM profiler or using jstack utility.

For analyzing deadlock, we need to look out for the threads with the state as BLOCKED and then the resources it’s waiting to lock. Every resource has a unique ID using which we can find which thread is already holding the lock on the object.

How to avoid deadlock in java
These are some of the guidelines using which we can avoid most of the deadlock situations.

Avoid deadlock by breaking circular wait condition: In order to do that, you can make arrangement in the code to impose the ordering on acquisition and release of locks. If lock will be acquired in a consistent order and released in just opposite order, there would not be a situation where one thread is holding a lock which is acquired by other and vice-versa.
Avoid Nested Locks: This is the most common reason for deadlocks, avoid locking another resource if you already hold one. It’s almost impossible to get a deadlock situation if you are working with only one object lock.
Lock Only What is Required: You should acquire lock only on the resources you have to work on, if we are only interested in one of its fields, then we should lock only that specific field not complete object.
Avoid waiting indefinitely: You can get a deadlock if two threads are waiting for each other to finish indefinitely using thread join. If your thread has to wait for another thread to finish, it’s always best to use join with maximum time you want to wait for the thread to finish.



Avoid nested locks. This is the most common reason for deadlock. Avoid locking another resource if you already hold one.It is almost impossible to get deadlock if you are working with only one object lock.

Lock only what is required. Like lock particular field of object instead of locking whole object if it serves your purpose.

Do not wait indefinitely.



Unless required, do not share data over multiple threads. If data can't be changed after creation/initialization, stick to final variables.
If you can't avoid shared data among multiple threads, use granular synchronized blocks or Locks.
If you are using only synchronized code blocks, make sure that locks are acquired/released in a certain order.
Look out for other alternatives : volatile or AtomicXXX variables or Lock API



Avoid Nested Locks
Avoid Unnecessary Locks
Use thread join()


https://javarevisited.blogspot.com/2018/08/how-to-avoid-deadlock-in-java-threads.html




How to avoid deadlock in Java Threads?
How to avoid deadlock in Java? is one of the popular Java interview question and flavor of the season for multi-threading, asked mostly at a senior level with lots of follow up questions. Even though question looks very basic but most of the Java developers get stuck once you start going deep.

Interview questions start with "What is a deadlock?"
The answer is simple when two or more threads are waiting for each other to release the resource they need (lock) and get stuck for infinite time, the situation is called deadlock. It will only happen in case of multitasking or multi-threading.


How do you detect deadlock in Java?
Though this could have many answers, my version is first I would look at the code if I see a nested synchronized block or calling one synchronized method from other, or trying to get a lock on a different object then there is a good chance of deadlock if a developer is not very careful.

Another way is to find it when you actually get dead-locked while running the application, try to take a thread dump, in Linux you can do this by command "kill -3", this will print status of all threads in application log file and you can see which thread is locked on which object.

You can analyze that thread dump with using tools like fastthread.io which allows you to upload your thread dump and analyze it.

Another way is to use the jConsole/VisualVM, it will show you exactly which threads are getting locked and on which object.

If you are interested to learn about troubleshooting tools and process to analyze your thread dump, I suggest you take a look at Analyzing Java Thread Dumps course on Pluralsight by Uriah Levy. An advanced practical course to learn more about Java thread dumps, and familiarize you with other popular advanced troubleshooting tools.




Write a Java program which will result in deadlock?
Once you answer the earlier question, they may ask you to write code which will result in a deadlock in Java?

here is one of my version

/**
 * Java program to create a deadlock by imposing circular wait.
 *
 * @author WINDOWS 8
 *
 */

/*
public class DeadLockDemo {

    /*
     * This method request two locks, first String and then Integer
     */

/*
    public void method1() {
        synchronized (String.class) {
            System.out.println("Aquired lock on String.class object");

            synchronized (Integer.class) {
                System.out.println("Aquired lock on Integer.class object");
            }
        }
    }

    /*
     * This method also requests same two lock but in exactly
     * Opposite order i.e. first Integer and then String.
     * This creates potential deadlock, if one thread holds String lock
     * and other holds Integer lock and they wait for each other, forever.
     */

/*
    public void method2() {
        synchronized (Integer.class) {
            System.out.println("Aquired lock on Integer.class object");

            synchronized (String.class) {
                System.out.println("Aquired lock on String.class object");
            }
        }
    }
}


    If method1() and method2() both will be called by two or many threads , there is a good chance of deadlock because if thread 1 acquires lock on Sting object while executing method1() and thread 2 acquires lock on Integer object while executing method2() both will be waiting for each other to release lock on Integer and String to proceed further which will never happen.

        This diagram exactly demonstrates our program, where one thread holds a lock on one object and waiting for other object locks which are held by other thread.

        How do you avoid deadlock in Java?


        You can see that Thread 1 wants the lock on object 2 which is held by Thread 2, and Thread 2 wants a lock on Object 1 which is held by Thread 1. Since no thread is willing to give up, there is a deadlock and the Java program is stuck.

        The idea is that you should know the right way to use common concurrent patterns, and if you are not familiar with them then Applying Concurrency and Multi-threading to Common Java Patterns by Jose Paumard is a good starting point to learn.



        How to avoid deadlock in Java?
        Now the interviewer comes to the final part, one of the most important in my view; How do you fix a deadlock in code? or How to avoid deadlock in Java?

        If you have looked above code carefully then you may have figured out that real reason for deadlock is not multiple threads but the way they are requesting a lock, if you provide an ordered access then the problem will be resolved.

        Here is my fixed version, which avoids deadlock by avoiding circular wait with no preemption, one of the four conditions which need for deadlock.

public class DeadLockFixed {

    /**
     * Both method are now requesting lock in same order, first Integer and then String.
     * You could have also done reverse e.g. first String and then Integer,
     * both will solve the problem, as long as both method are requesting lock
     * in consistent order.
     */
  /*
    public void method1() {
        synchronized (Integer.class) {
            System.out.println("Aquired lock on Integer.class object");

            synchronized (String.class) {
                System.out.println("Aquired lock on String.class object");
            }
        }
    }

    public void method2() {
        synchronized (Integer.class) {
            System.out.println("Aquired lock on Integer.class object");

            synchronized (String.class) {
                System.out.println("Aquired lock on String.class object");
            }
        }
    }
}


    Now there would not be any deadlock because both methods are accessing lock on Integer and String class literal in the same order. So, if thread A acquires a lock on Integer object, thread B will not proceed until thread A releases Integer lock, same way thread A will not be blocked even if thread B holds String lock because now thread B will not expect thread A to release Integer lock to proceed further.


    https://javarevisited.blogspot.com/2018/08/how-to-avoid-deadlock-in-java-threads.html

*/


class DeadLockDemo {
    /* * This method request two locks, first String and then Integer */
    public void method1() {
        synchronized (String.class) {
            System.out.println("Aquired lock on String.class object");
            synchronized (Integer.class) {
                System.out.println("Aquired lock on Integer.class object");
            }
        }
    }

    /* * This method also requests same two lock but in exactly
    * * Opposite order i.e. first Integer and then String.
    * * This creates potential deadlock, if one thread holds String lock
    * * and other holds Integer lock and they wait for each other, forever.
    * */
    public void method2() {
        synchronized (Integer.class) {
            System.out.println("Aquired lock on Integer.class object");
            synchronized (String.class) {
                System.out.println("Aquired lock on String.class object");
            }
        }
    }
}


public class AvoidingDeadlock {

    /** * Both method are now requesting lock in same order, first Integer and then String.
     * * You could have also done reverse e.g. first String and then Integer,
     * * both will solve the problem, as long as both method are requesting lock
     * * in consistent order.
     * */
    public void method1() {
        synchronized (Integer.class) {
            System.out.println("Aquired lock on Integer.class object");
            synchronized (String.class) {
                System.out.println("Aquired lock on String.class object");
            }
        }
    }

    public void method2() {
        synchronized (Integer.class) {
            System.out.println("Aquired lock on Integer.class object");
            synchronized (String.class) {
                System.out.println("Aquired lock on String.class object");
            }
        }
    }





}
