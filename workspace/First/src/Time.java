import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class Time {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    String  s1="郑源-爱情里没有谁错谁对";
    int index=s1.indexOf("-");
    if (index!=-1) {
		String s2=s1.substring(0, index);
		System.out.println(s2);
	}
	}

}
