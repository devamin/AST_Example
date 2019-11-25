package com.oracle.intern.toylanguage.implementation.expression;

import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;

public class AdditionOperator extends ArithmeticBinaryOperation{

	
	
	public AdditionOperator(Node leftChild, Node rightChild) {
		super(leftChild, rightChild);
		// TODO Auto-generated constructor stub
		this.keyword = "Add";
		this.operator ='+';

	}

	@Override
	public Optional<Integer> execute() {
		
		int value = 0;
		
		value += this.leftChild.execute().get();
		
		value += this.rightChild.execute().get();
		
		return Optional.of(value);
	
	}
	
	
	
}
