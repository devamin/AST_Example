package com.oracle.intern.toylanguage.implementation.factories;

import java.util.HashMap;
import java.util.Map;

import com.oracle.intern.toylanguage.implementation.FuncNode;
import com.oracle.intern.toylanguage.implementation.statement.Reference;

public class RefFactory {
	
//	//scope to access to our memory of function
//	private static FuncNode scope;
//	
//	
//	//Singleton Factory of Reference 
//	private static RefFactory SINGLETON_INSTANCE=null;
//	
//	
//	//To have one single Ref to our Variables
//	private static final Map<String,Reference> references = new HashMap<String, Reference>();
//
//	
//	
//	private RefFactory(FuncNode scope) {
//		RefFactory.scope = scope;
//	}
//	
//	
//	
//	public static RefFactory get(FuncNode scope) {
//		if(SINGLETON_INSTANCE == null) 
//			SINGLETON_INSTANCE = new RefFactory(scope);
//		return SINGLETON_INSTANCE;
//	}
//	
//	
//	
//	public static Reference createReference(String name) {
//		Reference ref = new Reference(name,scope);
//		references.put(name,ref);
//		return ref;
//	}
//	
//	public static Reference getReference(String name) {
//		if(references.containsKey(name))
//			return references.get(name);
//		else {
//			throw new RuntimeException(name + " variable not declared");
//		}
//	}
	
	
	
}
