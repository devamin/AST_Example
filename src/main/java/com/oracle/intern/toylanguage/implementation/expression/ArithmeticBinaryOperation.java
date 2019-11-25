package com.oracle.intern.toylanguage.implementation.expression;

import java.util.ArrayList;
import java.util.List;

import com.oracle.intern.toylanguage.abstraction.CleanerHelper;
import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;
import com.oracle.intern.toylanguage.implementation.statement.Literal;
import com.oracle.intern.toylanguage.implementation.statement.Reference;

public abstract class ArithmeticBinaryOperation implements Node,CleanerHelper{

	protected Node leftChild;
	protected Node rightChild;
	protected String keyword; 
	protected char operator;
	
	public ArithmeticBinaryOperation(Node leftChild, Node rightChild) {
		super();
		if(this instanceof DivisionOperator || this instanceof MultiplicationOperator)
			{
			if(leftChild instanceof AdditionOperator || leftChild instanceof SubstractionOperator || rightChild instanceof AdditionOperator || rightChild instanceof SubstractionOperator) {
				throw new RuntimeException("AST Arthimitic operations not correct");
			}
			}
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	

	@Override
	public List<String> optimize(List<String> list) {
		// TODO Auto-generated method stub
		this.leftChild.optimize(list);
		
		this.rightChild.optimize(list);
		
		return list;
	}

	
	public String print(int depth) {
		String print="";
		print+=PrintHelper.printTabsDepth(depth);
		print+=this.keyword+"(\n";
		print+=this.leftChild.print(depth+1);
		print+=",\n";
		print+=this.rightChild.print(depth+1)+"\n";
		print+=PrintHelper.printTabsDepth(depth)+")";
		return print;
	}
	
	public String translateToJava(int depth) {
		return this.leftChild.translateToJava(depth) + this.operator + this.rightChild.translateToJava(depth);
	}
	
	public List<String> getUsedRefs() {
		ArrayList<String> usedRefs = new ArrayList<String>();
		if(this.leftChild instanceof CleanerHelper)
		usedRefs.addAll(((CleanerHelper) this.leftChild).getUsedRefs());
		if(this.rightChild instanceof CleanerHelper)
		usedRefs.addAll(((CleanerHelper) this.rightChild).getUsedRefs());
		
		return usedRefs;
	}
	
	
	
	public void updateIteratorToMerge(String toUpdate,String iterator) {
		if(leftChild instanceof Reference) {
			if(((Reference)this.leftChild).getChild().equals(toUpdate)) {
				((Reference)this.leftChild).setChild(iterator);
			}	
		}
		else if(leftChild instanceof ArithmeticBinaryOperation)
			((ArithmeticBinaryOperation)leftChild).updateIteratorToMerge(toUpdate,iterator);
		
		
		
		if(rightChild instanceof Reference) {
			if(((Reference)this.rightChild).getChild().equals(toUpdate)) {
				((Reference)this.rightChild).setChild(iterator);
			}
		}
		else if(rightChild instanceof ArithmeticBinaryOperation) {
			((ArithmeticBinaryOperation)rightChild).updateIteratorToMerge(toUpdate,iterator);
		}
		
		
	}
	
	public void clear() {
		this.leftChild.clear();
		this.rightChild.clear();
	}
	
	
	public String toString() {
		return this.leftChild.toString() + " " + this.operator + " " + this.rightChild.toString();
	}
	
	
}
