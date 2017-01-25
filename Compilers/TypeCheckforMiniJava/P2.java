import syntaxtree.*;
import visitor.*;

public class P2 {
	public static void main(String [] args) {
		try {
			Node root = new MiniJavaParser(System.in).Goal();
			root.accept(new TypeStorage());
			root.accept(new TypeCheck());
			System.out.println("Program type checked successfully");
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
}
