package com.oracle.intern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.FuncNode;
import com.oracle.intern.toylanguage.implementation.expression.AdditionOperator;
import com.oracle.intern.toylanguage.implementation.expression.DivisionOperator;
import com.oracle.intern.toylanguage.implementation.expression.MultiplicationOperator;
import com.oracle.intern.toylanguage.implementation.expression.SubstractionOperator;
import com.oracle.intern.toylanguage.implementation.factories.RefFactory;
import com.oracle.intern.toylanguage.implementation.statement.AssignStatement;
import com.oracle.intern.toylanguage.implementation.statement.DeclarationStatement;
import com.oracle.intern.toylanguage.implementation.statement.ForStatement;
import com.oracle.intern.toylanguage.implementation.statement.Literal;
import com.oracle.intern.toylanguage.implementation.statement.Reference;
import com.oracle.intern.toylanguage.implementation.statement.ReturnStatement;

public class TestCompiler3 {

	public static void main(String[] args) {
		List<Node> listOfInst = new ArrayList<>();

		FuncNode func = new FuncNode("test2", "a", "b");		
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
		List<Node> block1 = Arrays.asList(new AssignStatement("result1",new AdditionOperator(Reference.get("result1"), Reference.get("a"))));
		ForStatement for1 = new ForStatement(new DeclarationStatement("i1"), new Literal(0), Reference.get("b"), block1);
		
		//block2
		// result2 = result2 +a;
		List<Node> block2 = Arrays.asList(new AssignStatement("result2",new AdditionOperator(Reference.get("result2"), Reference.get("a"))));
		ForStatement for2 = new ForStatement(new DeclarationStatement("i2"), new Literal(0), Reference.get("b"), block2);
		
		//return result1+result2;
		ReturnStatement returnStatement = new ReturnStatement(new AdditionOperator(Reference.get("result1"), Reference.get("result2")));

		
		
		listOfInst.add(declare_result1);
		listOfInst.add(assign_result1);

		listOfInst.add(declare_result2);
		listOfInst.add(assign_result2);
		
		listOfInst.add(for1);
		
		listOfInst.add(for2);
		
		listOfInst.add(returnStatement);
		
		func.setListOfInstruction(listOfInst);
		
		System.out.println(func.toJava());

		System.out.println("\n \nResult Before Optimisation \n\n");
		
		System.out.println(func.call(4,5));
		
		func.optimize(new ArrayList<String>());
		
		func.loopOptimizer();
		
		System.out.println(" \n \n \n after LoopOptimisation \n \n \n");

		System.out.print(func.toJava());
		
		
		System.out.println("\n \nResult After Optimisation \n\n");
//the scope and everyThing is cleared after finishing call;
		
		System.out.println(func.call(4,5));
		
//		func.print();

//		
//		
//		System.out.println(func.call(10,10).get());
//		

//		func.printScope();

		System.out.println(" \n \n \n to JAVA \n \n \n");
//		System.out.println(func.toJava());
	}

}
