/*
 * 	Assignment 1 : AST DataStructure & Assignment 2 : Simple Code Generation
 *   
 *   
		function test2(a) {  
		  val b;  
		  b = 0;  
		  for (i: 0...a) { // in the first iteration i will be 0, then 1, etc. until a-1  
		    b = a + b;  
		  }  
		  return b; 
		}  

 *   
 * 	/test 1 : printThe Sexpression Compared to the one in the PDF
 *	/test 2 : print the code in JAVA CODE
 *	/test 3 : execution test equalassertion
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
class Test2 {

	private FuncNode funcNode = new FuncNode("test2", "a");

	@BeforeAll
	public void init() {
		ArrayList<Node> listOfInst = new ArrayList<>();
		
		// val b;
		DeclarationStatement declare_b = new DeclarationStatement("b");
		// b = 0;
		AssignStatement assign_b = new AssignStatement("b", new Literal(0));
		//for loop
			//block inside the for
			//b = a + b;
		List<Node> block = Arrays.asList(new AssignStatement("b", new AdditionOperator(Reference.get("a"), Reference.get("b"))));
		ForStatement forLoop = new ForStatement(new DeclarationStatement("i"), new Literal(0), Reference.get("a"), block);	
		// end of for
		
		//return b;
		ReturnStatement returnStatment = new ReturnStatement(Reference.get("b"));
		
		//Add instruction to the listOf Instructions 
		listOfInst.add(declare_b);
		listOfInst.add(assign_b);
		listOfInst.add(forLoop);
		listOfInst.add(returnStatment);

		// let's add the block of instructions to our main function
		this.funcNode.setListOfInstruction(listOfInst);
	}

	@Test
	@Order(1)
	void printAstSExpression() {
		// I printed both the example in the PDF and generated to notice the similarity
		// I can not do an equal assertion on printing because one simple space can fail everything.
		System.out.println("\n\n test2 S-Expressions \n\n");

		System.out.println("generated : ---------------");
		System.out.println(this.funcNode.print());
		
	}

	
	@Test
	@Order(2)
	void toJava() {
		// the AST to JAVA Code 
		System.out.println("\n\n test2.java \n\n");
		System.out.println(this.funcNode.toJava());
	}
	
	@Test
	@Order(3)
	void execution() {
		// it's Math.pow(5,5)
		//  test2(5)
		int expected = 25;
		
		int actual = this.funcNode.call(5);
		
		assertEquals(expected, actual);
		
	}
	
	
}
