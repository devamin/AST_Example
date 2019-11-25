/*




 * 	Assignement 5 : More advanced Optimisation
 *   
 *   
		function test7(a, b) {
			val result1;
			result1 = 0;
			val result2;
			result2 = 0;
			for (i1: 0...b) {
				result1 = result1 + a;
			}
			val dummy;
			dummy = 1337;
			for (i2: 0...b) {
				result2 = result2 + a;
			}
			return result1 + result2;
		}


 * 	/test 1 : printTo Java before optimize it 
 * 
 *	/test 2 : optimize 
 *	the final process of optimisation that I create has two phases 	
 *
 *	func.optimize()
 *	1 : Firstly Cleaning the code by removing any Assignment or Insrtuction Or ForStatement that does not affect our end result
 *	
 *	func.loopOptimizer()
 *	2 : secondly is LoopOptimisation Process where I try to combine and merge ForStatements If they are disjoint
 *		and if the block in between is independent and also dijoint to the secondFor and so on.
 *		
 *	
 *
 *	/test 3 : printTo Java After optimisation
 *
 *	/test 4 : AssertionEqual(ExpectedValue, ActualBeforeOpt && ActualAfterOpt)
 * 
 * */

package com.oracle.intern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.FuncNode;
import com.oracle.intern.toylanguage.implementation.expression.AdditionOperator;
import com.oracle.intern.toylanguage.implementation.statement.AssignStatement;
import com.oracle.intern.toylanguage.implementation.statement.DeclarationStatement;
import com.oracle.intern.toylanguage.implementation.statement.ForStatement;
import com.oracle.intern.toylanguage.implementation.statement.Literal;
import com.oracle.intern.toylanguage.implementation.statement.Reference;
import com.oracle.intern.toylanguage.implementation.statement.ReturnStatement;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Test8 {

	private FuncNode funcNode = new FuncNode("test5", "a", "b");

	private List<Node> getListOfInstructionBeforeOptimisation() {
		ArrayList<Node> listOfInst = new ArrayList<>();

		// val result1;
		DeclarationStatement declare_result1 = new DeclarationStatement("result1");
		// result1=0;
		AssignStatement assign_result1 = new AssignStatement("result1", new Literal(0));
		// val result2;
		DeclarationStatement declare_result2 = new DeclarationStatement("result2");
		// result2 = 0;
		AssignStatement assign_result2 = new AssignStatement("result2", new Literal(0));

		// block1
		// result1= resutl1 + a;
		List<Node> block1 = new ArrayList<Node>();

		block1.add(new AssignStatement("result1", new AdditionOperator(Reference.get("result1"), Reference.get("a"))));
		//for(i1:0...b)
		ForStatement for1 = new ForStatement(new DeclarationStatement("i1"), new Literal(0), Reference.get("b"),
				block1);

		//val dummy;
		DeclarationStatement declare_dummy = new DeclarationStatement("dummy");
		//dummy =0;
		AssignStatement assign_dummy = new AssignStatement("dummy", new Literal(0));
		
		// block2
		// result2 = result2 +a;
		List<Node> block2 = new ArrayList<Node>();
		block2.add(new AssignStatement("result2", new AdditionOperator(Reference.get("result2"), Reference.get("a"))));
		//for(i2:0...b)
		ForStatement for2 = new ForStatement(new DeclarationStatement("i2"), new Literal(0), Reference.get("b"),
				block2);

		// return result1+result2;
		ReturnStatement returnStatement = new ReturnStatement(
				new AdditionOperator(Reference.get("result1"), Reference.get("result2")));

		listOfInst.add(declare_result1);
		listOfInst.add(assign_result1);

		listOfInst.add(declare_result2);
		listOfInst.add(assign_result2);

		listOfInst.add(for1);

		listOfInst.add(declare_dummy);
		listOfInst.add(assign_dummy);
		
		listOfInst.add(for2);

		listOfInst.add(returnStatement);
		return listOfInst;
	}

	private void setAsNeverOptimized() {
		this.funcNode.setListOfInstruction(getListOfInstructionBeforeOptimisation());
	}

	@BeforeAll
	public void init() {

		// let's add the block of instructions to our main function
		this.funcNode.setListOfInstruction(getListOfInstructionBeforeOptimisation());
	}

	@Test
	@Order(1)
	void toJavaBeforeOptimisation() {

		System.out.println("\n\n test1.java before Optimisation\n\n");
		System.out.println(this.funcNode.toJava());
	}

	@Test
	@Order(2)
	void optimize() {
		// clean and delete any an necessary instruction
		this.funcNode.optimize();
		// Merge ForStatments if Possible
		this.funcNode.loopOptimizer();
	}

	@Test
	@Order(3)
	void toJavaAfterOptimisation() {

		System.out.println("\n\n test1.java after Optimisation\n\n");
		System.out.println(this.funcNode.toJava());

	}

	@Test
	@Order(4)
	void executionTest() {
		// value for test6(5) == 1337+5 = 1342
		int expected = generatedJavatest(5, 5);

		setAsNeverOptimized();

		int actualBeforeOptimisation = this.funcNode.call(5, 5);

		assertEquals(expected, actualBeforeOptimisation);

		this.funcNode.cleanAndOptimizeLoops();

		int actualAfterOptimisation = this.funcNode.call(5, 5);

		assertEquals(expected, actualAfterOptimisation);

	}
	
	
	public static int generatedJavatest(int a,int b){
		//before Optimisation
		//exactly same as the input ToyCode coded in java
		int result1;
		result1 = 0;
		int result2;
		result2 = 0;
		
		for(int i1=0;i1<b;i1++){

			result1 = result1+a;

		}
		int dummy;
		dummy = 0;
		for(int i2=0;i2<b;i2++){

			result2 = result2+a;

		}
		return result1+result2;

	}
}
