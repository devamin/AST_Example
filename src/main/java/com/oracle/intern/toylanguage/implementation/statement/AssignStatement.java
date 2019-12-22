package com.oracle.intern.toylanguage.implementation.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.CleanerHelper;
import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.abstraction.Statement;
import com.oracle.intern.toylanguage.implementation.expression.ArithmeticBinaryOperation;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;

public class AssignStatement implements Node,CleanerHelper,Statement{
	
	private String name;
	private Node rightChild;
	private Reference ref;
	
	public AssignStatement(String name, Node rightChild) {
		super();
		this.name = name;
		this.rightChild = rightChild;
		this.ref = Reference.get(name);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public Reference getRef() {
		return ref;
	}

	public void setRef(Reference ref) {
		this.ref = ref;
	}

	public Optional<Integer> execute() {
		int value = this.rightChild.execute().get();
		this.ref.checkRef();
		this.ref.setValue(value);
		return Optional.empty();
	}

	public String print(int depth) {
		String print="";
		print +=PrintHelper.printTabsDepth(depth);
		print +="Assign(\""+this.ref.getChild()+"\",\n";
		print +=this.rightChild.print(depth+1)+"\n";
		print +=PrintHelper.printTabsDepth(depth)+")";
		return print;
	}
	

	@Override
	public String translateToJava(int depth) {
		// TODO Auto-generated method stub
		return PrintHelper.tabsString(depth)+this.name + " = " + this.rightChild.translateToJava(depth)+";";
	}


	@Override
	public List<String> optimize(List<String> list) {
		if(list.contains(this.ref.getChild())) {
			this.rightChild.optimize(list);
			return list;
		}else {
			return null;
		}
	}

	@Override
	public List<String> getUsedRefs() {
		if(this.rightChild instanceof CleanerHelper) {
			CleanerHelper rchild = (CleanerHelper) this.rightChild;
			return rchild.getUsedRefs();
		}
		return new ArrayList<String>();
	}
	
	public void updateIteratorToMerge(String toUpdate,String iterator) {
		if(this.rightChild instanceof Reference) {
			if(((Reference)this.rightChild).getChild().equals(toUpdate)) {
				((Reference)this.rightChild).setChild(iterator);
			}
		}else {
			((ArithmeticBinaryOperation)this.rightChild).updateIteratorToMerge(toUpdate,iterator);
		}
	}

	@Override
	public String toString() {
		return name + "=" + rightChild ;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
