package com.oracle.intern.toylanguage.implementation.statement;

import java.util.List;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.abstraction.CleanerHelper;
import com.oracle.intern.toylanguage.implementation.FuncNode;
import com.oracle.intern.toylanguage.implementation.factories.RefFactory;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;

public class DeclarationStatement implements Node{

	private static FuncNode scope;
	
	
	public static void setScope(FuncNode scope){
		DeclarationStatement.scope = scope;
	}
	
	
	
	
	private String child;
	

	
	public DeclarationStatement(String child) {
		super();
		this.child = child;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}
	
	

	public Optional<Integer> execute() {
		
		if(this.scope.getMemory().containsKey(this.child)) {
			throw new RuntimeException(this.child + " this variable already declared");
		}else {
			this.scope.getMemory().put(this.child, null);
		}
		
		return Optional.empty();
  }
	

	public String print(int depth) {
		String print="";
		print+=PrintHelper.printTabsDepth(depth);
		print+="Decl(\""+ this.child +"\")";
		return print;
	}

	@Override
	public String translateToJava(int depth) {
		// TODO Auto-generated method stub
		return PrintHelper.tabsString(depth)+"int "+this.child+";";
	}
	
	@Override
	public List<String> optimize(List<String> list) {
		// TODO Auto-generated method stub
		if(list.contains(this.child)) {
			return list;
		}else{
			return null;
		}
	}

	@Override
	public String toString() {
		return "int " + child + ";";
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

	
}
