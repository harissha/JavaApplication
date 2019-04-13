package javaTesting.String;

public class StringToArrayConversion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String strings = "99,42,55,81,79,64,22";
        String str[] = strings.split(",");
        
        System.out.println(str);
        
        for(String s:str)
        {
        	System.out.println(s);
        }

	}

}
