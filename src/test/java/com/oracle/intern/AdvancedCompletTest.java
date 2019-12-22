/*
 * 
	In this test
	I will try to write an AST more complicated
	with more instructions and forstatements and run the Optimization
	and the execution test to verify the speed gained from the optimization 

	ToyLang cpde :
	
	function test2(a,b){
		val r1;
		r1 = 5;
		
		val r2;
		r2 = a*b-200;
		
		val r3;
		r3 = 0;
		
		
		for(i:0...b){
			r3 = a+b;
			a = i+2;
		}
		for(i2:0 ...b){
			 r2 = r2+a+i2;
		}
		val helper;
		helper = 12;
		for(i3:0...b){
			r3= helper-i3;
			r1 = r1+i3;
		}
		helper = 20;
		return r1+r3+r1;
	}

  
 * */

package com.oracle.intern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.FuncNode;
import com.oracle.intern.toylanguage.implementation.expression.AdditionOperator;
import com.oracle.intern.toylanguage.implementation.expression.MultiplicationOperator;
import com.oracle.intern.toylanguage.implementation.expression.SubstractionOperator;
import com.oracle.intern.toylanguage.implementation.statement.AssignStatement;
import com.oracle.intern.toylanguage.implementation.statement.DeclarationStatement;
import com.oracle.intern.toylanguage.implementation.statement.ForStatement;
import com.oracle.intern.toylanguage.implementation.statement.Literal;
import com.oracle.intern.toylanguage.implementation.statement.Reference;
import com.oracle.intern.toylanguage.implementation.statement.ReturnStatement;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdvancedCompletTest {

	private FuncNode funcNode = new FuncNode("testAdvanced", "a", "b");

	private List<Node> getListOfInstructionBeforeOptimisation() {
		ArrayList<Node> listOfInst = new ArrayList<>();

		//val r1;
		DeclarationStatement declare_r1 = new DeclarationStatement("r1");
		// r1=0;
		AssignStatement assing_r1 = new AssignStatement("r1", new Literal(5));

		//val r1;
		DeclarationStatement declare_r2 = new DeclarationStatement("r2");
		// r1=0;
		AssignStatement assing_r2 = new AssignStatement("r2",
				new SubstractionOperator(new MultiplicationOperator(Reference.get("a"), Reference.get("b")), new Literal(200))
				);

		//val r1;
		DeclarationStatement declare_r3 = new DeclarationStatement("r3");
		// r1=0;
		AssignStatement assing_r3 = new AssignStatement("r3", new Literal(0));
		
//		for(i:0...b){
//			r3 = a+b;
//			a = i+2;
//		}	
		
		ArrayList<Node> block1 = new ArrayList<Node>();
		//r1 = a+b;
		block1.add(new AssignStatement("r3", new AdditionOperator(Reference.get("a"), Reference.get("b"))));
		// a=i+2;
		block1.add(new AssignStatement("a",new AdditionOperator(Reference.get("i"), new Literal(2))));
		//for 
		ForStatement for1 = new ForStatement(new DeclarationStatement("i"), new Literal(0), Reference.get("b"), block1);
		
//		for(i2:0...b){
//		r2 = r2 + r3 + i2;
//		}
		ArrayList<Node> block2 = new ArrayList<Node>();
		block2.add(new AssignStatement("r2", new AdditionOperator(Reference.get("r2"), 
									new AdditionOperator(Reference.get("a"), Reference.get("i2"))
									)));
		ForStatement for2 = new ForStatement(new DeclarationStatement("i2"), new Literal(0), Reference.get("b"), block2);
		
//		val helper;
// 		helper =12;

		DeclarationStatement declare_helper = new DeclarationStatement("helper");
		AssignStatement assign_helper = new AssignStatement("helper", new Literal(12));
		
//		for(i3:0...b) {
//			r1 = helper - i3;
//		}
		
// 		helper = 20;
		ArrayList<Node> block3 = new ArrayList<Node>();
		block3.add(new AssignStatement("r3", new SubstractionOperator(Reference.get("helper"), Reference.get("i3"))));
		block3.add(new AssignStatement("r1", new AdditionOperator(Reference.get("r1"),Reference.get("i3"))));
		ForStatement for3 = new ForStatement(new DeclarationStatement("i3"), new Literal(0), Reference.get("b"), block3);
		
		AssignStatement assign_helper20 = new AssignStatement("helper", new Literal(20));
		
		

		
		ReturnStatement returnStatement = new ReturnStatement(new AdditionOperator(Reference.get("r2"), 
													new AdditionOperator(Reference.get("r3"), Reference.get("r1"))
											));
		
		listOfInst.add(declare_r1);
		listOfInst.add(assing_r1);
		

		listOfInst.add(declare_r2);
		listOfInst.add(assing_r2);
		

		listOfInst.add(declare_r3);
		listOfInst.add(assing_r3);


		listOfInst.add(for1);
		listOfInst.add(for2);
		
		listOfInst.add(declare_helper);
		listOfInst.add(assign_helper);
		
		listOfInst.add(for3);
		
		listOfInst.add(assign_helper20);

		
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

		System.out.println("\n\b to Java before Optimisation\n\n");
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

		System.out.println("\n\n to Java after Optimisation\n\n");
		System.out.println(this.funcNode.toJava());

	}

	@Test
	@Order(4)
	void executionTest() {
		int expected = generatedJavatest(30, 99);

		setAsNeverOptimized();

		int actualBeforeOptimisation = this.funcNode.call(30,99);

		assertEquals(expected, actualBeforeOptimisation);

		this.funcNode.cleanAndOptimizeLoops();

		int actualAfterOptimisation = this.funcNode.call(30,99);

		assertEquals(expected, actualAfterOptimisation);

	}
	
	@DisplayName("exec time before and after optimisation")
	@Test
	@Order(5)
	public void executionTimeTest() {

		
		setAsNeverOptimized();
		long startTime = System.nanoTime();
		this.funcNode.call(1000,100000);
		long endTime = System.nanoTime();
		long durationBefore = (endTime - startTime);
		System.out.println("time spent before optimisation: " + durationBefore);
		
		
		this.funcNode.cleanAndOptimizeLoops();
		startTime = System.nanoTime();
		this.funcNode.call(1000,100000);
		endTime = System.nanoTime();
		long durationAfter = (endTime - startTime);
		
		System.out.println("time spent after Optimisation : " + durationAfter);
		
		System.out.println("gained time : " + (durationBefore-durationAfter));

		
	}
	
	
	

	
	
	public static int generatedJavatest(int a,int b){
		//before Optimisation
		//exactly same as the input ToyCode coded in java
		int r1;
		r1 = 5;
		int r2;
		r2 = a*b-200;
		int r3;
		r3 = 0;
		for(int i=0;i<b;i++){

			r3 = a+b;
			a = i+2;

		}
		for(int i2=0;i2<b;i2++){

			r2 = r2+a+i2;

		}
		int helper;
		helper = 12;
		for(int i3=0;i3<b;i3++){

			r3 = helper-i3;
			r1 = r1+i3;

		}
		helper = 20;
		return r2+r3+r1;


	}

}
