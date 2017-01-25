//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class Intermediate<R> implements GJNoArguVisitor<R> {
	//
	// Auto class visitors--probably don't need to be overridden.
	//
	public Integer temporary_count = 0;

	private String currClass = ""; 

	private LinkedHashMap<String, Structure> varList = Storage.varList;
	private LinkedHashMap<String,ArrayList<String>> methodTable = Storage.methodTable;
	private Stack<String> scope = new Stack<String>();
	private LinkedHashMap<String, String> parentTable = Storage.parentTable;
	private LinkedHashMap<String,ArrayList<String>> formalParameterList = Storage.formalParamList;

	public static LinkedHashMap<String,Structure> classProcedures = new LinkedHashMap<String,Structure>();
	public static LinkedHashMap<String,Structure> classVars = new LinkedHashMap<String,Structure>();

	public String fullScope(){
		String sum = "";
		String a = "";
		for(int i=0; i< scope.size(); i++)
		{
			a = scope.elementAt(i); 
			sum = sum + a + ":"; 
		}
		return sum; 	
	}


	public R visit(NodeList n) {
		R _ret=null;
		int _count=0;
		for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
			e.nextElement().accept(this);
			_count++;
		}
		return _ret;
	}

	public R visit(NodeListOptional n) {
		if ( n.present() ) {
			R _ret=null;
			int _count=0;
			for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
				e.nextElement().accept(this);
				_count++;
			}
			return _ret;
		}
		else
			return null;
	}

	public R visit(NodeOptional n) {
		if ( n.present() )
			return n.node.accept(this);
		else
			return null;
	}

	public R visit(NodeSequence n) {
		R _ret=null;
		int _count=0;
		for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
			e.nextElement().accept(this);
			_count++;
		}
		return _ret;
	}

	public R visit(NodeToken n) { return null; }

	//
	// User-generated visitor methods below
	//

	/**
	 * f0 -> MainClass()
	 * f1 -> ( TypeDeclaration() )*
	 * f2 -> <EOF>
	 */
	public R visit(Goal n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> "public"
	 * f4 -> "static"
	 * f5 -> "void"
	 * f6 -> "main"
	 * f7 -> "("
	 * f8 -> "String"
	 * f9 -> "["
	 * f10 -> "]"
	 * f11 -> Identifier()
	 * f12 -> ")"
	 * f13 -> "{"
	 * f14 -> PrintStatement()
	 * f15 -> "}"
	 * f16 -> "}"
	 */
	public R visit(MainClass n) {
		R _ret=null;
		currClass = n.f1.f0.tokenImage ;
		scope.push(currClass);
		findVtableAndFields(currClass);

		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);
		n.f14.accept(this);
		n.f15.accept(this);
		n.f16.accept(this);
		scope.pop();
		return _ret;
	}

	/**
	 * f0 -> ClassDeclaration()
	 *       | ClassExtendsDeclaration()
	 */
	public R visit(TypeDeclaration n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> ( VarDeclaration() )*
	 * f4 -> ( MethodDeclaration() )*
	 * f5 -> "}"
	 */
	public R visit(ClassDeclaration n) {
		R _ret=null;

		currClass = n.f1.f0.tokenImage ;
		scope.push(currClass);
		findVtableAndFields(currClass);
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		scope.pop();
		return _ret;
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "extends"
	 * f3 -> Identifier()
	 * f4 -> "{"
	 * f5 -> ( VarDeclaration() )*
	 * f6 -> ( MethodDeclaration() )*
	 * f7 -> "}"
	 */
	public R visit(ClassExtendsDeclaration n) {
		R _ret=null;
		currClass = n.f1.f0.tokenImage; 
		scope.push(currClass);

		findVtableAndFields(currClass);

		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		scope.pop();
		return _ret;
	}

	void findVtableAndFields(String name){
		String actualName = name;
		ArrayList<String> getAllParents = new ArrayList<String>();
		while(name!=null){
			getAllParents.add(name);
			name = parentTable.get(name);
		}

		LinkedHashMap<String,Integer> varMapping = new LinkedHashMap<String,Integer>();
		LinkedHashMap<String,ArrayList<String>> MethodAndParamsLists = new LinkedHashMap<String,ArrayList<String>>();

		LinkedHashMap<String,String> methodClassMapping = new LinkedHashMap<String,String>();
		for(int i=getAllParents.size()-1;i>=0;i--){
			String className = getAllParents.get(i);

			ArrayList<String> methodList = methodTable.get(className+":");
			LinkedHashMap<String,Integer> variableList = varList.get(className+":").tempMapping;
			for(int j=0;j<methodList.size();j++){

				String methodName = methodList.get(j);
				methodClassMapping.put(methodName, className);
				MethodAndParamsLists.put(methodName, formalParameterList.get(className+":"+methodName+":"));

			}
			for(String key:variableList.keySet()){
				varMapping.put(className+"?"+key,temporary_count);
				temporary_count++;
			}
		}

		temporary_count=0;

		Structure fields = new Structure();
		fields.tempMapping = varMapping;

		Structure methods = new Structure();

		for(String key:methodClassMapping.keySet()){
			methods.tempMapping.put(methodClassMapping.get(key)+"?"+key,temporary_count);
			methods.formalParamList.put(methodClassMapping.get(key)+"?"+key,MethodAndParamsLists.get(key));
			temporary_count++;
		}

		classVars.put(actualName,fields);
		classProcedures.put(actualName, methods);

		temporary_count=0;
	}



	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 * f2 -> ";"
	 */
	public R visit(VarDeclaration n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "public"
	 * f1 -> Type()
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( FormalParameterList() )?
	 * f5 -> ")"
	 * f6 -> "{"
	 * f7 -> ( VarDeclaration() )*
	 * f8 -> ( Statement() )*
	 * f9 -> "return"
	 * f10 -> Expression()
	 * f11 -> ";"
	 * f12 -> "}"
	 */
	public R visit(MethodDeclaration n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		n.f11.accept(this);
		n.f12.accept(this);
		return _ret;
	}

	/**
	 * f0 -> FormalParameter()
	 * f1 -> ( FormalParameterRest() )*
	 */
	public R visit(FormalParameterList n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 */
	public R visit(FormalParameter n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

	/**
	 * f0 -> ","
	 * f1 -> FormalParameter()
	 */
	public R visit(FormalParameterRest n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

	/**
	 * f0 -> ArrayType()
	 *       | BooleanType()
	 *       | IntegerType()
	 *       | Identifier()
	 */
	public R visit(Type n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "int"
	 * f1 -> "["
	 * f2 -> "]"
	 */
	public R visit(ArrayType n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "boolean"
	 */
	public R visit(BooleanType n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "int"
	 */
	public R visit(IntegerType n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> Block()
	 *       | AssignmentStatement()
	 *       | ArrayAssignmentStatement()
	 *       | IfStatement()
	 *       | WhileStatement()
	 *       | PrintStatement()
	 */
	public R visit(Statement n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "{"
	 * f1 -> ( Statement() )*
	 * f2 -> "}"
	 */
	public R visit(Block n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> Identifier()
	 * f1 -> "="
	 * f2 -> Expression()
	 * f3 -> ";"
	 */
	public R visit(AssignmentStatement n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		return _ret;
	}

	/**
	 * f0 -> Identifier()
	 * f1 -> "["
	 * f2 -> Expression()
	 * f3 -> "]"
	 * f4 -> "="
	 * f5 -> Expression()
	 * f6 -> ";"
	 */
	public R visit(ArrayAssignmentStatement n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		return _ret;
	}

	/**
	 * f0 -> IfthenElseStatement()
	 *       | IfthenStatement()
	 */
	public R visit(IfStatement n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "if"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 */
	public R visit(IfthenStatement n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "if"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 * f5 -> "else"
	 * f6 -> Statement()
	 */
	public R visit(IfthenElseStatement n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "while"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 */
	public R visit(WhileStatement n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "System.out.println"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> ";"
	 */
	public R visit(PrintStatement n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		return _ret;
	}

	/**
	 * f0 -> OrExpression()
	 *       | AndExpression()
	 *       | CompareExpression()
	 *       | neqExpression()
	 *       | PlusExpression()
	 *       | MinusExpression()
	 *       | TimesExpression()
	 *       | DivExpression()
	 *       | ArrayLookup()
	 *       | ArrayLength()
	 *       | MessageSend()
	 *       | PrimaryExpression()
	 */
	public R visit(Expression n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "&&"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(AndExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "||"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(OrExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "<="
	 * f2 -> PrimaryExpression()
	 */
	public R visit(CompareExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "!="
	 * f2 -> PrimaryExpression()
	 */
	public R visit(neqExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "+"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(PlusExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "-"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(MinusExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "*"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(TimesExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "/"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(DivExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "["
	 * f2 -> PrimaryExpression()
	 * f3 -> "]"
	 */
	public R visit(ArrayLookup n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> "length"
	 */
	public R visit(ArrayLength n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( ExpressionList() )?
	 * f5 -> ")"
	 */
	public R visit(MessageSend n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		return _ret;
	}

	/**
	 * f0 -> Expression()
	 * f1 -> ( ExpressionRest() )*
	 */
	public R visit(ExpressionList n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

	/**
	 * f0 -> ","
	 * f1 -> Expression()
	 */
	public R visit(ExpressionRest n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

	/**
	 * f0 -> IntegerLiteral()
	 *       | TrueLiteral()
	 *       | FalseLiteral()
	 *       | Identifier()
	 *       | ThisExpression()
	 *       | ArrayAllocationExpression()
	 *       | AllocationExpression()
	 *       | NotExpression()
	 *       | BracketExpression()
	 */
	public R visit(PrimaryExpression n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> <INTEGER_LITERAL>
	 */
	public R visit(IntegerLiteral n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "true"
	 */
	public R visit(TrueLiteral n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "false"
	 */
	public R visit(FalseLiteral n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> <IDENTIFIER>
	 */
	public R visit(Identifier n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "this"
	 */
	public R visit(ThisExpression n) {
		R _ret=null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "new"
	 * f1 -> "int"
	 * f2 -> "["
	 * f3 -> Expression()
	 * f4 -> "]"
	 */
	public R visit(ArrayAllocationExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "new"
	 * f1 -> Identifier()
	 * f2 -> "("
	 * f3 -> ")"
	 */
	public R visit(AllocationExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "!"
	 * f1 -> Expression()
	 */
	public R visit(NotExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "("
	 * f1 -> Expression()
	 * f2 -> ")"
	 */
	public R visit(BracketExpression n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> Identifier()
	 * f1 -> ( IdentifierRest() )*
	 */
	public R visit(IdentifierList n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

	/**
	 * f0 -> ","
	 * f1 -> Identifier()
	 */
	public R visit(IdentifierRest n) {
		R _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		return _ret;
	}

}