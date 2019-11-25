package com.oracle.intern.toylanguage.implementation.statement;

import java.util.List;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;

public class ReturnStatement implements Node{

	private Node child;
	
	
	public ReturnStatement(Node child) {
		super();
		this.child = child;
	}

	public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
	}
	
	@Override
	public Optional<Integer> execute() {
		return this.child.execute();
	}

	public String print(int depth) {
		String print = PrintHelper.printTabsDepth(depth);
		print+="Return(\n";
		print+=this.child.print(depth+1);
		print+="\n";
		print+=PrintHelper.printTabsDepth(depth);
		print+=")";
		return print;
	}

	@Override
	public String translateToJava(int depth) {
		// TODO Auto-generated method stub
		
		return PrintHelper.tabsString(depth) + "return " + this.child.translateToJava(depth)+";";
	}
	
	

	@Override
	public List<String> optimize(List<String> list) {
		
		this.child.optimize(list);
		
		return list;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}

