package com.oracle.intern.toylanguage.implementation.expression;

import java.util.List;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.statement.Reference;

public class DivisionOperator extends ArithmeticBinaryOperation{

	
	
	public DivisionOperator(Node leftChild, Node rightChild) {
		super(leftChild, rightChild);
		// TODO Auto-generated constructor stub
		this.keyword = "Div";
		this.operator ='/';
	}

	@Override
	public Optional<Integer> execute() {
		
		int value = this.leftChild.execute().get();
		
		int quotion = this.rightChild.execute().get();
		if(quotion == 0) throw new RuntimeException("Division By Zero");
		else value /=quotion;
		
		return Optional.of(value);
	
	}




}
