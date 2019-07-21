package practice;

import java.util.*;

public class SpaceCurrencyConversion {
    public String amount = "IVXLCDM";
    public String spaceAmount = "glob prok Iron  ";
    public HashMap<String, Integer> map;
    public HashMap<String, Integer> map1;
    public HashMap<String, Double> map2;
    /*private enum RomanValues{

        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

        // declaring private variable for getting values
        private int action;

        // getter method
        public int getAction()
        {
            return this.action;
        }

        // enum constructor - cannot be public or protected
        private RomanValues(int action)
        {
            this.action = action;
        }

    }*/

    public void loadRomanValues(){
        map = new HashMap<>(7);
        map.put("I",1);
        map.put("V",5);
        map.put("X",10);
        map.put("L",50);
        map.put("C",100);
        map.put("D",500);
        map.put("M",1000);


        /*for(Map.Entry m : map.entrySet()){
            System.out.println(m.getKey()+":"+m.getValue());
        }
        System.out.println("---------------------------");*/

        map1 = new HashMap<>(7);
        map1.put("glob",1);
        map1.put("prok",5);
        map1.put("pish",10);
        map1.put("tegi",50);

        // 2 Silver = 34 credits; 1 silver = 17 credits
        // 4 Gold = 57800 credits; 1 gold = 14450 credits
        // 20 Iron = 3910 credits; 1 iron = 195.5 credits

        /*for(Map.Entry m : map1.entrySet()){
            System.out.println(m.getKey()+":"+m.getValue());
        }*/

        map2 = new HashMap<>(7);
        map2.put("Silver",17.0);
        map2.put("Gold",14450.0);
        map2.put("Iron",195.5);

        /*System.out.println("---------------------------");
        for(Map.Entry m : map2.entrySet()){
            System.out.println(m.getKey()+":"+m.getValue());
        }*/



    }

    public void SpaceCurrencyException(){
        try{
            throw new customException("I have no idea what you are talking about");
        }catch(customException ce){
            System.out.println(ce.getMessage());
        }
    }

    public Integer getRomanValues(char ch ){
        return map.getOrDefault(String.valueOf(ch), 0);
    }

    public Integer getSpaceCurrencyValues(String str ){
        return map1.getOrDefault(String.valueOf(str), 0);
    }

    public Double getSpaceCurrencyMetals(String str ){
        return map2.getOrDefault(String.valueOf(str), 0.0);
    }


    public long convertedRomanAmount(){
        long sum = 0;
        char[] readingStringInCharacters = amount.toCharArray();
        loadRomanValues();
        for(int i=0; i<readingStringInCharacters.length-1; i++){
            if(getRomanValues(readingStringInCharacters[i]) >= getRomanValues(readingStringInCharacters[i+1])){
                sum += getRomanValues(readingStringInCharacters[i]);
            }
            else{
                sum -= getRomanValues(readingStringInCharacters[i]);
            }
        }
        sum += getRomanValues(readingStringInCharacters[readingStringInCharacters.length-1]);
        return sum;
    }

    String[] readingString = null;
    public long convertedSpaceCurrency(){
        long sum = 0;
        readingString = spaceAmount.trim().split(" ");
        loadRomanValues();
        for(int i=0; i<readingString.length-1; i++){
            //if(readingString[i])!= map1.get()
            if(getSpaceCurrencyValues(readingString[i]) == 0){
                sum = 0;
                break;
            }
            if(getSpaceCurrencyValues(readingString[i]) >= getSpaceCurrencyValues(readingString[i+1])){
                sum += getSpaceCurrencyValues(readingString[i]);
            }
            else{
                sum -= getSpaceCurrencyValues(readingString[i]);
            }

        }
        //sum += getSpaceCurrencyValues(readingString[readingString.length-1]);
        //System.out.println(sum);
        return sum;
    }

    public double convertedSpaceCurrencyCredits(){
        return convertedSpaceCurrency()*getSpaceCurrencyMetals(readingString[readingString.length-1]);
    }

    public static void main(String args[]){

        SpaceCurrencyConversion scc = new SpaceCurrencyConversion();
        //System.out.println(scc.convertedRomanAmount());
        //System.out.println("-----------------------------------");
        if(scc.convertedSpaceCurrencyCredits()==0.0){
            scc.SpaceCurrencyException();
        }else{
            System.out.println(scc.convertedSpaceCurrencyCredits());
        }

    }

}


class customException extends Exception{
    customException(String s){
        super(s);
    }
}
