package practice;

import java.util.*;


public class solution {

    static long caloriesBurnt =0;
    static long runKm = 0;


    public static void main(String[] args) {

        List<Integer> l = new ArrayList<>();
        l.add(10);
        l.add(20);
        l.add(8);
        l.add(7);

        Collections.sort(l, Collections.reverseOrder());

        long c = rec(l);
        System.out.println(c);

        Iterator<Integer> iterator = l.iterator();
        long x = rec11(iterator);
        System.out.println(x);

    }


    public static long rec(List<Integer> l) {

        long caloriesBurnt =0;
        long runKm = 0;
        for(int i =0; i<l.size(); i++){
            caloriesBurnt = caloriesBurnt + (2*runKm) + l.get(i);
            System.out.println(runKm+"---------------"+caloriesBurnt);
            runKm = runKm + l.get(i);
        }
        return caloriesBurnt;
    }

    public static long rec11(Iterator<Integer> iterator) {

        if (!iterator.hasNext()) {
            return caloriesBurnt;
        }

        int x =  iterator.next();

        caloriesBurnt = caloriesBurnt + (2*runKm) + x;
        runKm = runKm + x;
        return rec11(iterator);
    }

    private static long rec12(List<Integer> l, int size,int currentElem) {


        if (currentElem>=size){
            return caloriesBurnt;
        }


        caloriesBurnt = caloriesBurnt + (2*runKm) + l.get(currentElem);
        runKm = runKm + l.get(currentElem);

        return rec12(l, size, ++currentElem);
    }

}
