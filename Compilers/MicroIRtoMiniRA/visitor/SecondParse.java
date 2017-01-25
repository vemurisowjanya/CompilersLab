//
// Generated by JTB 1.3.2
//

package visitor;

import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order. Your visitors may extend this class.
 */
public class SecondParse<R, A> implements GJVisitor<R, A> {
	//
	// Auto class visitors--probably don't need to be overridden.
	//
	public ArrayList<Set<String>> use = FirstParse.use;
	public ArrayList<String> def = FirstParse.def;
	public HashMap<String, Integer> labelToIndex = FirstParse.labelToIndex;
	public ArrayList<Set<Integer>> succ = new ArrayList<Set<Integer>>();
	ArrayList<Set<String>> liveIn = new ArrayList<Set<String>>();
	ArrayList<Set<String>> liveOut = new ArrayList<Set<String>>();
	int i = 0;
	int numNodes = FirstParse.numNodes;
	public HashMap<String, HashMap<String, Integer>> funcStartIntervals = new HashMap<String, HashMap<String,Integer>>();
	public HashMap<String, HashMap<String, Integer>> funcEndIntervals = new HashMap<String, HashMap<String,Integer>>();
	HashMap<String, Integer> start = FirstParse.start;
	HashMap<String, Integer> end = FirstParse.end;
	String name;

	String[] allRegs = {"t0","t1","t2","t3","t4","t5","t6","t7","t8","s0","s1","s2","s3","s4","s5","s6","s7"};
	
	
	public static  HashMap<String,HashMap<String,String>>  funcAllocTempRegs = new HashMap<String,HashMap<String,String>>();
	
	public static HashMap<String,HashMap<String,Integer>>  funcAllocTempLocation = new HashMap<String,HashMap<String,Integer>>();

	public static HashMap<String,Integer> numArgs = FirstParse.numArgs;
	public static HashMap<String,Integer> maxArgs = FirstParse.maxArgs;
	
	public R visit(NodeList n, A argu) {
		R _ret = null;
		int _count = 0;
		for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
			e.nextElement().accept(this, argu);
			_count++;
		}
		return _ret;
	}

	public R visit(NodeListOptional n, A argu) {
		if (n.present()) {
			R _ret = null;
			int _count = 0;
			for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
				e.nextElement().accept(this, argu);
				_count++;
			}
			return _ret;
		} else
			return null;
	}

	public R visit(NodeOptional n, A argu) {
		if (n.present())
			return n.node.accept(this, argu);
		else
			return null;
	}

	public R visit(NodeSequence n, A argu) {
		R _ret = null;
		int _count = 0;
		for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
			e.nextElement().accept(this, argu);
			_count++;
		}
		return _ret;
	}

	public R visit(NodeToken n, A argu) {
		return null;
	}

	public void get_live() {
		ArrayList<Set<String>> prevliveIn = new ArrayList<Set<String>>();
		ArrayList<Set<String>> prevliveOut = new ArrayList<Set<String>>();

		for (int i = 0; i <= numNodes; i++) {
			liveIn.add(new HashSet<String>());
			liveOut.add(new HashSet<String>());
			prevliveIn.add(new HashSet<String>());
			prevliveOut.add(new HashSet<String>());
		}

		int flag;

		do {
			flag = 0;
			Collections.copy(prevliveIn, liveIn);
			Collections.copy(prevliveOut, liveOut);
			for (int j = 0; j <= numNodes; j++) {
				Set<String> difference = new HashSet<String>(liveOut.get(j));
				difference.remove(def.get(j));
				Set<String> union = new HashSet<String>(use.get(j));
				union.addAll(difference);
				liveIn.set(j, union);

				Set<String> outUnion = new HashSet<String>();
				for (Integer s : succ.get(j)) {
					outUnion.addAll(liveIn.get(s));
				}
				liveOut.set(j, outUnion);
			}

			for (int i = 0; i <= numNodes; i++) {
				if (!(prevliveIn.get(i).equals(liveIn.get(i)) && prevliveOut.get(i).equals(liveOut.get(i)))) {
					flag = 1;
					break;
				}
			}

		} while (flag == 1);

	}

	public void temp_ranges() {
		for (String name : start.keySet()) {
			HashMap<String,Integer> StartIntervals = new HashMap<String, Integer>();
			HashMap<String,Integer> EndIntervals = new HashMap<String,Integer>();
			HashMap<String, ArrayList<Integer>> regNodes = new HashMap<String, ArrayList<Integer>>();
			for (int i = start.get(name); i <= end.get(name); i++) {
				for (String str : liveIn.get(i)) {
					if (regNodes.get(str) == null)
						regNodes.put(str, new ArrayList<Integer>());
					regNodes.get(str).add(i);
				}
			}

			for (String temp : regNodes.keySet()) {
				ArrayList<Integer> tempNodes = regNodes.get(temp);
				int start = tempNodes.get(0);
				int end = tempNodes.get(tempNodes.size()-1);
				StartIntervals.put(temp,start);
				EndIntervals.put(temp,end);
			}
			funcStartIntervals.put(name, StartIntervals);
			funcEndIntervals.put(name, EndIntervals);
		}
	}


	@SuppressWarnings("unchecked")
	public void register_alloc() {
		for (String name : start.keySet()) {
			HashMap<String, Integer> StartIntervals = funcStartIntervals.get(name);
			HashMap<String, Integer> EndIntervals = funcEndIntervals.get(name);

			HashMap<String,String> tempRegs = new HashMap<String,String>();
			int temp = numArgs.get(name);
			int stack = temp<4?0:temp-4;
			HashMap<String,Integer> tempLocation = new HashMap<String,Integer>();		

			LinkedList<String> registers = new LinkedList<String>();
			for(int i=0;i<17;i++)
				registers.add(allRegs[i]);
			
			List<Map.Entry<String, Integer>> sortStart = new ArrayList(StartIntervals.entrySet());
			Collections.sort(sortStart, new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					int result = 0;
					if(o1!=null && o2!=null){
						Integer a1 = ((Map.Entry<String,Integer>)o1).getValue();
						Integer a2 = ((Map.Entry<String,Integer>)o2).getValue();
						result = a1.compareTo(a2);
					}
					return result;
				}				
			});
			
			List<Map.Entry<String, Integer>> sortEnd = new ArrayList(EndIntervals.entrySet());
			Collections.sort(sortEnd, new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					int result = 0;
					if(o1!=null && o2!=null){
						Integer a1 = ((Map.Entry<String,Integer>)o1).getValue();
						Integer a2 = ((Map.Entry<String,Integer>)o2).getValue();
						result = a1.compareTo(a2);
					}
					return result;
					
				}				
			});
			
			
			ArrayList<String> active = new ArrayList<String>();
			for(Map.Entry<String, Integer> i:sortStart){

				//	expireOldIntervals				
				for(Map.Entry<String, Integer> j:sortEnd){
					if(active.contains(j.getKey())){
					if(j.getValue() >= i.getValue())
						break;
					
					active.remove(j.getKey());
					registers.add(tempRegs.get(j.getKey()));
				}
				}


				if(active.size()==17)
				{
					String spillId = active.get(active.size()-1);
					if(EndIntervals.get(spillId) > EndIntervals.get(i))
					{
						tempRegs.put(i.getKey(), tempRegs.get(spillId));
						
						tempLocation.put(spillId, stack);
						stack++;
						active.remove(spillId);
						
						//add i to active
						int prev = active.size();
						for(int l=0;l<active.size();l++){
							if(EndIntervals.get(active.get(l)) >= EndIntervals.get(i.getKey())){
								active.add(l,i.getKey());
								break;
							}
						}
						if(prev == active.size())
							active.add(i.getKey());
						
					}
					else{
						tempLocation.put(i.getKey(),stack);
						stack++;
					}

				}

				else
					tempRegs.put(i.getKey(),registers.removeFirst());

				//add i to active
				int prev = active.size();
				for(int l=0;l<active.size();l++){
					if(EndIntervals.get(active.get(l)) >= EndIntervals.get(i.getKey())){
						active.add(l,i.getKey());
						break;
					}
				}
				if(prev == active.size())
					active.add(i.getKey());
				
			}
			
			funcAllocTempRegs.put(name, tempRegs);
			funcAllocTempLocation.put(name, tempLocation);
		}
	}


	//
	// User-generated visitor methods below
	//

	/**
	 * f0 -> "MAIN" f1 -> StmtList() f2 -> "END" f3 -> ( Procedure() )* f4 ->
	 * <EOF>
	 */
	public R visit(Goal n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		Set<Integer> succSet = new HashSet<Integer>();
		succ.add(succSet);
		i++;
		n.f3.accept(this, argu);
		n.f4.accept(this, argu);
		get_live();
		temp_ranges();
		register_alloc();
		return _ret;
	}

	/**
	 * f0 -> ( ( Label() )? Stmt() )*
	 */
	public R visit(StmtList n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> Label() f1 -> "[" f2 -> IntegerLiteral() f3 -> "]" f4 -> StmtExp()
	 */
	public R visit(Procedure n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		n.f3.accept(this, argu);
		n.f4.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> NoOpStmt() | ErrorStmt() | CJumpStmt() | JumpStmt() | HStoreStmt()
	 * | HLoadStmt() | MoveStmt() | PrintStmt()
	 */
	public R visit(Stmt n, A argu) {
		R _ret = null;
		Set<Integer> succSet = new HashSet<Integer>();
		succ.add(succSet);
		n.f0.accept(this, argu);
		i++;
		return _ret;
	}

	/**
	 * f0 -> "NOOP"
	 */
	public R visit(NoOpStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		succ.get(i).add(i + 1);
		return _ret;
	}

	/**
	 * f0 -> "ERROR"
	 */
	public R visit(ErrorStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		succ.get(i).add(i + 1);
		return _ret;
	}

	/**
	 * f0 -> "CJUMP" f1 -> Temp() f2 -> Label()
	 */
	public R visit(CJumpStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		String label = n.f2.accept(this, (A) "id").toString();
		succ.get(i).add(i + 1);
		succ.get(i).add(labelToIndex.get(label));
		return _ret;
	}

	/**
	 * f0 -> "JUMP" f1 -> Label()
	 */
	public R visit(JumpStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		String label = n.f1.accept(this, (A) "id").toString();
		succ.get(i).add(labelToIndex.get(label));
		return _ret;
	}

	/**
	 * f0 -> "HSTORE" f1 -> Temp() f2 -> IntegerLiteral() f3 -> Temp()
	 */
	public R visit(HStoreStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		n.f3.accept(this, argu);
		succ.get(i).add(i + 1);
		return _ret;
	}

	/**
	 * f0 -> "HLOAD" f1 -> Temp() f2 -> Temp() f3 -> IntegerLiteral()
	 */
	public R visit(HLoadStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		n.f3.accept(this, argu);
		succ.get(i).add(i + 1);
		return _ret;
	}

	/**
	 * f0 -> "MOVE" f1 -> Temp() f2 -> Exp()
	 */
	public R visit(MoveStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		succ.get(i).add(i + 1);
		return _ret;
	}

	/**
	 * f0 -> "PRINT" f1 -> SimpleExp()
	 */
	public R visit(PrintStmt n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		succ.get(i).add(i + 1);
		return _ret;
	}

	/**
	 * f0 -> Call() | HAllocate() | BinOp() | SimpleExp()
	 */
	public R visit(Exp n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> "BEGIN" f1 -> StmtList() f2 -> "RETURN" f3 -> SimpleExp() f4 ->
	 * "END"
	 */
	public R visit(StmtExp n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		n.f3.accept(this, (A) "proc");
		i++;
		n.f4.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> "CALL" f1 -> SimpleExp() f2 -> "(" f3 -> ( Temp() )* f4 -> ")"
	 */
	public R visit(Call n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		n.f3.accept(this, argu);
		n.f4.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> "HALLOCATE" f1 -> SimpleExp()
	 */
	public R visit(HAllocate n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> Operator() f1 -> Temp() f2 -> SimpleExp()
	 */
	public R visit(BinOp n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> "LE" | "NE" | "PLUS" | "MINUS" | "TIMES" | "DIV"
	 */
	public R visit(Operator n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> Temp() | IntegerLiteral() | Label()
	 */
	public R visit(SimpleExp n, A argu) {
		R _ret = null;
		if (argu != null && argu.toString().equals("proc")) {
			Set<Integer> succSet = new HashSet<Integer>();
			succ.add(succSet);
		}
		n.f0.accept(this, (A) "id");
		return _ret;
	}

	/**
	 * f0 -> "TEMP" f1 -> IntegerLiteral()
	 */
	public R visit(Temp n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> <INTEGER_LITERAL>
	 */
	public R visit(IntegerLiteral n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		return _ret;
	}

	/**
	 * f0 -> <IDENTIFIER>
	 */
	public R visit(Label n, A argu) {
		R _ret = null;
		n.f0.accept(this, argu);
		if (argu == null) {
			Set<Integer> succSet = new HashSet<Integer>();
			succ.add(succSet);
			succ.get(i).add(i + 1);
			i++;
		}
		_ret = (R) n.f0.tokenImage;
		return _ret;
	}
}