import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Hello {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		TimeZone time = TimeZone.getTimeZone("GMT+8"); // 设置为东八区
		time = TimeZone.getDefault();// 这个是国际化所用的
    	TimeZone.setDefault(time);// 设置时区
    	Calendar calendar = Calendar.getInstance();// 获取实例
    	Date date1 = calendar.getTime(); //获取Date对象
    	String str = new String();
    	str = sdf.format(date1);//对象进行格
    	System.out.println(str);
		System.out.println("-----------------");

		Date date = new Date();
		String times = sdf.format(date);
		System.out.println(times);
		System.out.println("-------------------");

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int seconds = c.get(Calendar.SECOND);
		if (seconds > 10) {
			int second = seconds;
			System.out.println(year + "-" + month + "-" + day + " " + hour
					+ ":" + minute + ":" + second);

		} else {
			String second = "0" + seconds;
			System.out.println(year + "-" + month + "-" + day + " " + hour
					+ ":" + minute + ":" + second);

		}
	}

}
