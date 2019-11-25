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

public class TestCompiler4 {

	public static void main(String[] args) {
		List<Node> listOfInst = new ArrayList<>();

		FuncNode func = new FuncNode("test2", "arg1", "arg2");

		DeclarationStatement a = new DeclarationStatement("a");
		AssignStatement assign_a = new AssignStatement("a", new Literal(0));
		
		DeclarationStatement b = new DeclarationStatement("b");
		AssignStatement assign_b = new AssignStatement("b", new Literal(0));
		
		DeclarationStatement c = new DeclarationStatement("c");
		AssignStatement assign_c = new AssignStatement("c", new Literal(0));
		
		DeclarationStatement d = new DeclarationStatement("d");
		AssignStatement assign_d = new AssignStatement("d", new Literal(0));
		
		DeclarationStatement a1 = new DeclarationStatement("a1");
		AssignStatement assign_a1 = new AssignStatement("a1", new Literal(10));
		
		ArrayList<Node> block1 = new ArrayList<>();
		block1.add(new AssignStatement("a", new AdditionOperator(Reference.get("i1"), Reference.get("a1"))));
		ForStatement for1 = new ForStatement(new DeclarationStatement("i1"), new Literal(0), new Literal(10), block1);
		
		
		DeclarationStatement b1 = new DeclarationStatement("b1");
		AssignStatement assign_b1 = new AssignStatement("b1", new Literal(12));
		
		ArrayList<Node> block2 = new ArrayList<>();
		block2.add(new AssignStatement("b", new AdditionOperator(Reference.get("i2"), Reference.get("b1"))));
		ForStatement for2 = new ForStatement(new DeclarationStatement("i2"), new Literal(0), new Literal(10), block2);
		
		
		DeclarationStatement c1 = new DeclarationStatement("c1");
		AssignStatement assign_c1 = new AssignStatement("c1", new Literal(12));
		
		ArrayList<Node> block3 = new ArrayList<>();
		block3.add(new AssignStatement("c", new AdditionOperator(Reference.get("i3"), Reference.get("a"))));
		block3.add(new AssignStatement("c1", new AdditionOperator(Reference.get("i3"), new Literal(1))));

		ForStatement for3 = new ForStatement(new DeclarationStatement("i3"), new Literal(0), new Literal(10), block3);

		
		
		listOfInst.add(a);
		
		listOfInst.add(assign_a);

		listOfInst.add(b);

		listOfInst.add(assign_b);

		listOfInst.add(c);

		listOfInst.add(assign_c);

		listOfInst.add(d);

		listOfInst.add(assign_d);

		listOfInst.add(a1);
		
		listOfInst.add(assign_a1);
		
		listOfInst.add(for1);

		listOfInst.add(b1);
		
		listOfInst.add(assign_b1);
		
		listOfInst.add(for2);
	
		listOfInst.add(c1);

		listOfInst.add(assign_c1);
		
		listOfInst.add(for3);
		
		listOfInst.add(new ReturnStatement(new AdditionOperator(Reference.get("a"), new MultiplicationOperator(Reference.get("b"), Reference.get("c")))));

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
