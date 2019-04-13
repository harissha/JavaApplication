package javaTesting.Regex;

import java.util.*;

public class IpAddressSolution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Some valid IP address:
			000.12.12.034
			121.234.12.12
			23.45.12.56
			
			Some invalid IP address:
			000.12.234.23.23
			666.666.23.23
			.213.123.23.32
			23.45.22.32.
			I.Am.not.an.ip*/
		
		/*Sample Input
		000.12.12.034
		121.234.12.12
		23.45.12.56
		00.12.123.123123.123
		122.23
		Hello.IP
		
		Sample Output
		true
		true
		true
		false
		false
		false*/
		
		Scanner in = new Scanner(System.in);
        while(in.hasNext())
        {
            String IP = in.next();
            System.out.println(IP.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9])$"));
        }
        in.close();

	}

}
