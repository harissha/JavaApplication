package pluralSight.dataStruture;

import java.util.Objects;

public class Node {

    public int value;
    public Node next;

    @Override
    public boolean equals(Object o) {

        // checking if both the object references are
        // referring to the same object.
        if (this == o) return true;

        // it checks if the argument is of the
        // type Geek by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if (o == null || getClass() != o.getClass()) return false;

        // type casting of the argument.
        Node node = (Node) o;

        // comparing the state of argument with
        // the state of 'this' Object.
        return value == node.value &&
                Objects.equals(next, node.next);
    }

    @Override
    // This method returns the hash code value
    // for the object on which this method is invoked.
    public int hashCode() {

        // We are returning the Geek_id
        // as a hashcode value.
        // we can also return some
        // other calculated value or may
        // be memory address of the
        // Object on which it is invoked.
        // it depends on how you implement
        // hashCode() method.
        return Objects.hash(value, next);
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    public Node(){

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public static void main(String[] args){
        Node node = new Node();
        System.out.println(node.getClass());
    }



}
