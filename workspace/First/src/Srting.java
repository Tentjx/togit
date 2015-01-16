
public class Srting {
public static void main(String[] args) {
	String s1="周杰伦-七里.香.mp3";

	String suffix=s1.substring(s1.lastIndexOf("."), s1.length());
	String s2=s1.replace(suffix, ".lrc");

	
	System.out.println(s2+"----"+suffix);
}
}
