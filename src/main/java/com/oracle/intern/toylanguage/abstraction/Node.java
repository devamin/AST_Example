package com.oracle.intern.toylanguage.abstraction;

import java.util.List;
import java.util.Optional;

import com.oracle.intern.toylanguage.implementation.statement.Reference;

public interface Node {

//	public boolean isTerminal();
	public Optional<Integer> execute();
	public String print(int depth);
	public String translateToJava(int depth);
	public List<String> optimize(List<String> list);
	public void clear();
	

}
