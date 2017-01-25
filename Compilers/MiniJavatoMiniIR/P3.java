import syntaxtree.*;
import visitor.*;

public class P3 {
	public static void main(String [] args) {
		try {
			Node root = new MiniJavaParser(System.in).Goal();
			root.accept(new Storage()); 
			root.accept(new Intermediate());
			root.accept(new Generate<Integer,String>(),null);
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
} 



