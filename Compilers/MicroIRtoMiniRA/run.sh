echo "/////////"
java P5 <testcases/Factorial.microIR >outminiRA/Factorial.miniRA
java -jar kgi.jar <outminiRA/Factorial.miniRA 
# >test1
# java -jar kgi.jar <miniRA_cases/Factorial.miniRA >test2
# diff test1 test2

echo "///////////"
java P5 <testcases/BubbleSort.microIR >outminiRA/BubbleSort.miniRA
java -jar kgi.jar <outminiRA/BubbleSort.miniRA 
# >test1
# java -jar kgi.jar <miniRA_cases/BubbleSort.miniRA >test2
# diff test1 test2

echo "///////////"
java P5 <testcases/BinaryTree.microIR >outminiRA/BinaryTree.miniRA
java -jar kgi.jar <outminiRA/BinaryTree.miniRA 
# >test1
# java -jar kgi.jar <miniRA_cases/BinaryTree.miniRA >test2
# diff test1 test2

echo "////////////"
java P5 <testcases/LinearSearch.microIR >outminiRA/LinearSearch.miniRA
java -jar kgi.jar <outminiRA/LinearSearch.miniRA
#  >test1
# java -jar kgi.jar <miniRA_cases/LinearSearch.miniRA >test2
# diff test1 test2

echo "/////////////"
java P5 <testcases/LinkedList.microIR >outminiRA/LinkedList.miniRA
java -jar kgi.jar <outminiRA/LinkedList.miniRA 
# >test1
# java -jar kgi.jar <miniRA_cases/LinkedList.miniRA >test2
# diff test1 test2

echo "//////////////"
java P5 <testcases/MoreThan4.microIR >outminiRA/MoreThan4.miniRA
java -jar kgi.jar <outminiRA/MoreThan4.miniRA 
# >test1
# java -jar kgi.jar <miniRA_cases/MoreThan4.miniRA >test2
# diff test1 test2

echo "/////////////"
java P5 <testcases/QuickSort.microIR >outminiRA/QuickSort.miniRA
java -jar kgi.jar <outminiRA/QuickSort.miniRA
#  >test1
# java -jar kgi.jar <miniRA_cases/QuickSort.miniRA >test2
# diff test1 test2

echo "/////////////"
java P5 <testcases/TreeVisitor.microIR >outminiRA/TreeVisitor.miniRA
java -jar kgi.jar <outminiRA/TreeVisitor.miniRA
# >test1
# java -jar kgi.jar <miniRA_cases/TreeVisitor.miniRA >test2
# diff test1 test2
