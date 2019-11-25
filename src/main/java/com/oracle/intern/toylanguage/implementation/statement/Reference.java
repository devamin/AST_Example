package com.oracle.intern.toylanguage.implementation.statement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.CleanerHelper;
import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.FuncNode;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;

public class Reference implements Node,CleanerHelper{
	
	
	private String child;

	private static FuncNode scope;
	
	private static Map<String,Reference> references = new HashMap<>();
	
	public static void setScope(FuncNode scope) {
		Reference.scope = scope;
	}
	
	public static Reference getRef(String child) {
		if(references.containsKey(child)) 
			return references.get(child);
		else {
			if(!scope.getMemory().containsKey(child)) {
				throw new RuntimeException(child + " Variable is not Declared");
			}
			Reference ref = new Reference(child);
			references.put(child,ref);
			return ref;
		}
	}
	
	public static Reference get(String child) {
		return new Reference(child);
	}
	
	public static void clearReferences() {
		references.clear();
	}
	
	public void clear() {
		
	}
	
	
	
	private Reference(String child) {
		this.child = child;
	}
	
	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
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
		Reference other = (Reference) obj;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		return true;
	}

	public Optional<Integer> execute() {
		Optional<Integer> value = null;
		
		if(this.scope.getMemory().containsKey(this.child))
			value =  Optional.ofNullable(scope.getMemory().get(this.child));	
		
		if(value == null) {
			System.out.println(scope.getMemory());
			throw new RuntimeException(this.child + " is not declared");
		}

		
		if(!value.isPresent()) {
			System.out.println(scope.getMemory());
			throw new RuntimeException(this.child + " is not instantiated");
		}
		
		return value;
	}
	
	public void checkRef() {
		if(!scope.getMemory().containsKey(this.child)) {
			throw new RuntimeException(this.child + " is not Declared");
		}
	}
	
	public void setValue(int value) {
		if(scope.getMemory().containsKey(this.child))
			scope.getMemory().put(this.child,value);		
	}



	@Override
	public String print(int depth) {
		// TODO Auto-generated method stub
		return PrintHelper.printTabsDepth(depth)+"Ref(\""+this.child+"\")";
	}

	@Override
	public String translateToJava(int depth) {
		// TODO Auto-generated method stub
		return this.child;
	}

	@Override
	public List<String> optimize(List<String> list) {
			list.add(this.child);
		return list;
	}

	
	@Override
	public List<String> getUsedRefs() {
		return Arrays.asList(this.child);
	}

	@Override
	public String toString() {
		return child;
	}
	
	
	
	
}
