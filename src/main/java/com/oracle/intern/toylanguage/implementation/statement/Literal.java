package com.oracle.intern.toylanguage.implementation.statement;

import java.util.List;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;

public class Literal implements Node{
	private int value;	

	public Literal(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public Optional<Integer> execute() {
		// TODO Auto-generated method stub
		return Optional.of(this.value);
	}
	

	public String print(int depth) {
		return PrintHelper.printTabsDepth(depth)+"Literal("+this.value+")";
	
	}

	@Override
	public String translateToJava(int depth) {
		// TODO Auto-generated method stub
		return ""+this.value;
	}

	
	@Override
	public List<String> optimize(List<String> list) {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Literal other = (Literal) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + value;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
