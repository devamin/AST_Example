/*
 * 
	In this test I will try to write an AST more complicated
	with more instructions and forstatements and run the Optimization
	and the execution test to verify the speed gained from the optimization 

	ToyLang cpde :
	
	function test2(a,b,c,d){
		val r1;
		r1 = 0;
		
		val r2;
		r2 = a*b-2/d-1;
		
		val r3;
		r3 = r2 +r1;
		
		
		for(i:0...b){
			r3 = a+b;
			a = i+2;
		}
		r3 = 12;
		for(i2:0 ...b){
			 r2 = i2 +d;
		}
		
	
	
	}

  
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
class AdvancedTest {

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
		ForStatement for1 = new ForStatement(new DeclarationStatement("i1"), new Literal(0), Reference.get("a"),
				block1);

		//val dummy;
		DeclarationStatement declare_dummy = new DeclarationStatement("dummy");
		//dummy =0;
		AssignStatement assign_dummy = new AssignStatement("dummy", new Literal(0));
		
		// block2
		// result2 = result2 +a;
		List<Node> block2 = new ArrayList<Node>();
		block2.add(new AssignStatement("result2", new AdditionOperator(Reference.get("result2"), Reference.get("result1"))));
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
		int expected = generatedJavatest(6, 5);

		setAsNeverOptimized();

		int actualBeforeOptimisation = this.funcNode.call(6, 5);

		assertEquals(expected, actualBeforeOptimisation);

		this.funcNode.cleanAndOptimizeLoops();

		int actualAfterOptimisation = this.funcNode.call(6, 5);

		assertEquals(expected, actualAfterOptimisation);

	}
	
	
	public static int generatedJavatest(int a,int b){
		//before Optimisation
		//exactly same as the input ToyCode coded in java
		int result1;
		result1 = 0;
		int result2;
		result2 = 0;
		for(int i1=0;i1<a;i1++){

			result1 = result1+a;

		}
		for(int i2=0;i2<b;i2++){

			result2 = result2+result1;

		}
		return result1+result2;

	}

}
