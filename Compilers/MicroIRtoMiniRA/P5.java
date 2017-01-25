import syntaxtree.*;
import visitor.*;

public class P5 {
	public static void main(String [] args) {
		try {
			Node root = new microIRParser(System.in).Goal();
			root.accept(new FirstParse<Integer,String>(),null);
			root.accept(new SecondParse<Integer,String>(),null);
			root.accept(new Generate<Integer,String>(),null);
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
}


