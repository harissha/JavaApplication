package javaTesting.binaryTree;


//Binary Tree (Array implementation)

/*
We are going to talk about sequential representation of the trees.
To represent tree using array, numbering of nodes can start either from 0–(n-1) or 1–n .

       A(0)
     /   \
    B(1)  C(2)
  /   \      \
 D(3)  E(4)   F(5)

        OR,

      A(1)
     /   \
    B(2)  C(3)
  /   \      \
 D(4)  E(5)   F(6)
For first case(0—n-1),
if (say)father=p;
then left_son=(2*p)+1;
and right_son=(2*p)+2;

For second case(1—n),
if (say)father=p;
then left_son=(2*p);
and right_son=(2*p)+1;
where father,left_son and right_son are the values of indices of the array.
*/

// JAVA implementation of tree using array
// numbering starting from 0 to n-1.

import java.util.*;
import java.lang.*;
import java.io.*;

class Tree {
    public static void main(String[] args)
    {
        Array_imp obj = new Array_imp();
        obj.Root("A");
        //    obj.set_Left("B", 0);
        obj.set_Right("C", 0);
        obj.set_Left("D", 1);
        obj.set_Right("E", 1);
        obj.set_Left("F", 2);
        obj.print_Tree();
    }
}

class Array_imp {
    static int root = 0;
    static String[] str = new String[10];

    /*create root*/
    public void Root(String key)
    {
        str[0] = key;
    }

    /*create left son of root*/
    public void set_Left(String key, int root)
    {
        int t = (root * 2) + 1;

        if(str[root] == null){
            System.out.printf("Can't set child at %d, no parent found\n",t);
        }else{
            str[t] = key;
        }
    }

    /*create right son of root*/
    public void set_Right(String key, int root)
    {
        int t = (root * 2) + 2;

        if(str[root] == null){
            System.out.printf("Can't set child at %d, no parent found\n",t);
        }else{
            str[t] = key;
        }
    }

    public void print_Tree()
    {
        for (int i = 0; i < 10; i++) {
            if (str[i] != null)
                System.out.print(str[i]);
            else
                System.out.print("-");

        }
    }
}
