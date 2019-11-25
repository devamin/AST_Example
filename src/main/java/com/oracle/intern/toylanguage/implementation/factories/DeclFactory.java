package com.oracle.intern.toylanguage.implementation.factories;

import java.util.HashMap;
import java.util.Map;

import com.oracle.intern.toylanguage.implementation.FuncNode;
import com.oracle.intern.toylanguage.implementation.statement.DeclarationStatement;
import com.oracle.intern.toylanguage.implementation.statement.Reference;

public class DeclFactory {
	
	//scope to access to our memory of function
	private static FuncNode scope;
	
	
	//Singleton Factory of Reference 
	private static DeclFactory SINGLETON_INSTANCE=null;
	
	
	private DeclFactory(FuncNode scope) {
		DeclFactory.scope = scope;
	}
	
	public static DeclFactory create(FuncNode scope) {
		if(SINGLETON_INSTANCE == null) 
			SINGLETON_INSTANCE = new DeclFactory(scope);
		return SINGLETON_INSTANCE;
	}
	
	
	
	public static DeclarationStatement declare(String name) {
		
		return new DeclarationStatement(name);
	}
	
	
	
}
