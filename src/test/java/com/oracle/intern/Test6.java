/*


 * 	Assignement 4 : Simple Optimisation 2
 *   
 *   
		function test6(a) {  
		  	val b;  
		   	b = 1337;  
  			return a + b; 
		}  


 * 	/test 1 : printTo Java before optimize it 
 *	/test 2 : optimize 
 *	/test 3 : printTo Java After optimisation
 *	/test 4 : AssertionEqual(ExpectedValue, ActualBeforeOpt && ActualAfterOpt)
 * 
 * */


package com.oracle.intern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
class Test6 {

	private FuncNode funcNode = new FuncNode("test6", "a");

	
	private List<Node> getListOfInstructionBeforeOptimisation(){
		ArrayList<Node> listOfInst = new ArrayList<>();
		// val b;
		DeclarationStatement declare_b = new DeclarationStatement("b");
		
		// b = 1337;
		AssignStatement assign_b = new AssignStatement("b", new Literal(1337));
		
		//return a+1;
		ReturnStatement returnStatement = new ReturnStatement(
					new AdditionOperator(Reference.get("a"), Reference.get("b"))
				);
				
		listOfInst.add(declare_b);
		listOfInst.add(assign_b);
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
		this.funcNode.optimize();
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
		//value for test6(5) == 1337+5 = 1342
		int expected = 1337+5;
		
		setAsNeverOptimized();
		
		int actualBeforeOptimisation = this.funcNode.call(5);
				
		assertEquals(expected, actualBeforeOptimisation);

		this.funcNode.optimize();
		
		int actualAfterOptimisation = this.funcNode.call(5);
		
		assertEquals(expected, actualAfterOptimisation);
		
	}
	
	
}
