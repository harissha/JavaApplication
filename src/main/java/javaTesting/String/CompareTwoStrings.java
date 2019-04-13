package javaTesting.String;

public class CompareTwoStrings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "AVATAR";
        String b = "Avatar";

        if(a.equals(b)){
            System.out.println("Both strings are equal.");
        } else {
            System.out.println("Both strings are not equal.");
        }

        if(a.equalsIgnoreCase(b)){
            System.out.println("Both strings are equal.");
        } else {
            System.out.println("Both strings are not equal.");
        }


	}

}
