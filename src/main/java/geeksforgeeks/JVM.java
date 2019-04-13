package geeksforgeeks;

// A Java program to demonstrate working of a Class type
// object created by JVM to represent .class file in
// memory.
import java.lang.reflect.Field;
import java.lang.reflect.Method;

// Java code to demonstrate use of Class object
// created by JVM
public class JVM {
    public static void main(String[] args)
    {
        Student s1 = new Student();

        // Getting hold of Class object created
        // by JVM.
        Class c1 = s1.getClass();

        // Printing type of object using c1.
        System.out.println("c1.getName() : "+c1.getName());
        System.out.println("s1.getClass().getName() : "+s1.getClass().getName());
        System.out.println();

        // getting all methods available for class in an array
        Method n[] = c1.getMethods();
        System.out.println("n : "+n);
        for (Method method : n)
            System.out.println("method.getName() : "+method.getName());
        System.out.println();

        // getting all declared methods of class in an array
        Method m[] = c1.getDeclaredMethods();
        System.out.println("m : "+m);
        for (Method method : m)
            System.out.println("declared method.getName() : "+method.getName());
        System.out.println();

        // getting all fields in an array
        Field e[] = c1.getFields();
        System.out.println("e : "+e);
        for (Field field : e){
            System.out.println("Hi");
            System.out.println("field.getName() : "+field.getName());
        }

        // getting all declared fields of class in an array
        Field f[] = c1.getDeclaredFields();
        System.out.println("f : "+f);
        for (Field field : f)
            System.out.println("declared field.getName() : "+field.getName());

        // String class is loaded by bootstrap loader, and
        // bootstrap loader is not Java object, hence null
        System.out.println(String.class.getClassLoader());

        // Test class is loaded by Application loader
        System.out.println(Student.class.getClassLoader());
    }
}

// A sample class whose information is fetched above using
// its Class object.
class Student
{
    private String name;
    private int roll_No;

    public String getName()  {  return name;   }
    public void setName(String name) { this.name = name; }
    public int getRoll_no()  { return roll_No;  }
    public void setRoll_no(int roll_no) { this.roll_No = roll_no; }
}



