package com.oracle.intern.toylanguage.implementation.expression;

import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;

public class SubstractionOperator extends ArithmeticBinaryOperation{

	
	
	public SubstractionOperator(Node leftChild, Node rightChild) {
		super(leftChild, rightChild);
		// TODO Auto-generated constructor stub
		this.keyword = "Sub";
		this.operator ='-';

	}

	@Override
	public Optional<Integer> execute() {
		
		int value = this.leftChild.execute().get();
		
		value -= this.rightChild.execute().get();
		
		return Optional.of(value);
	}


	
}
