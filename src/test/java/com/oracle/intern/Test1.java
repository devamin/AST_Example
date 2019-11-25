/*
 * 	Assignement 0 : Toy Language 
 *   
 *   
		function test1(a, b) { // a function named test1 with 2 parameters, named a and b  
		  var c;               // declaration of local variable c  
		  c = a + b + 1;       // assigning the value of an expression to variable c  
		  return c;            // returning a value (this expression happens to be a reference) 
		}  

 * 	/test 1 : printThe Sexpression Compared to the one in the PDF
 *	/test 2 : print the code in JAVA CODE
 *	/test 3 : execution test equalassertion
 * 
 * */


package com.oracle.intern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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
import com.oracle.intern.toylanguage.implementation.statement.Literal;
import com.oracle.intern.toylanguage.implementation.statement.Reference;
import com.oracle.intern.toylanguage.implementation.statement.ReturnStatement;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Test1 {

	private FuncNode funcNodeTest1 = new FuncNode("test1", "a", "b");

	@BeforeAll
	public void init() {

		ArrayList<Node> listOfInst = new ArrayList<>();
		// var c;
		DeclarationStatement declare_c = new DeclarationStatement("c");
		// c = a+b+1;
		AssignStatement assign_c = new AssignStatement("c", 
				//Addition Expression 
				//a+b+1
				new AdditionOperator(
						new AdditionOperator(Reference.get("a"), Reference.get("b"))
						, new Literal(1))
				);
		
		// return c;
		ReturnStatement returnStatment = new ReturnStatement(Reference.get("c"));
		
		//Add All instruction to the block of our main function
		
		listOfInst.add(declare_c);
		listOfInst.add(assign_c);
		listOfInst.add(returnStatment);
		
		

		// let's add the block of instructions to our main function
		this.funcNodeTest1.setListOfInstruction(listOfInst);
	}

	@Test
	@Order(1)
	void printAstSExpression() {
		// I printed both the example in the PDF and generated to notice the similarity
		// I can not do an equal assertion on printing because one simple space can fail everything.
		System.out.println("\n\n test1 S-Expressions \n\n");

		System.out.println("generated : ---------------");
		System.out.println(this.funcNodeTest1.print());
		
		
		System.out.println("in the PDF : --------------");
		System.out.println(
				"Func(\n" + "    [\"a\", \"b\"],\n" + "    [\n" + "        Decl(\"c\"),\n" + "        Assign(\"c\",\n"
						+ "            Add(\n" + "                Add(\n" + "                Ref(\"a\"),\n"
						+ "                Ref(\"b\")\n" + "                ),\n" + "                Literal(1)\n"
						+ "            )\n" + "        ),\n" + "        Return(Ref(\"c\"))\n" + "    ]\n" + ")");
	}

	
	@Test
	@Order(2)
	void toJava() {
		
		System.out.println("\n\n test1.java \n\n");
		System.out.println(this.funcNodeTest1.toJava());
	}
	
	@Test
	@Order(3)
	void execution() {
		// for test1(1,-1)
		int expected = 1;
		
		int actual = this.funcNodeTest1.call(1,-1);
		
		assertEquals(expected, actual);
		
		
	}
	
	
}
