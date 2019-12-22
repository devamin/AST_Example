package com.oracle.intern.toylanguage.implementation.statement;

import java.util.List;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.abstraction.Statement;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;
import com.oracle.intern.toylanguage.implementation.optimisation.ForStatementOptimiserHelper;


public class ForStatement implements Node,Statement{
	
	private DeclarationStatement iterator;
	private Node from;
	private Node to;
	private List<Node> block;
	private boolean isParamsDeclared =false;
	
	public ForStatement(DeclarationStatement iterator, Node from, Node to, List<Node> block) {
		super();
		this.iterator = iterator;
		this.from = from;
		this.to = to;
		this.block = block;
	}
	
	
	

	public Node getFrom() {
		return from;
	}




	public void setFrom(Node from) {
		this.from = from;
	}




	public Node getTo() {
		return to;
	}




	public void setTo(Node to) {
		this.to = to;
	}




	public List<Node> getBlock() {
		return block;
	}




	public void setBlock(List<Node> block) {
		this.block = block;
	}




	@Override
	public Optional<Integer> execute() {
		
		if(!isParamsDeclared) {
			iterator.execute();
			isParamsDeclared = true;
		}
		
		Reference iteratorRef = Reference.getRef(iterator.getChild());
		
		int fromValue = from.execute().get();

		
		for(int i=fromValue;i<to.execute().get();i++) {
			iteratorRef.setValue(i);
			for(Node inst : block) {
				inst.execute();
			}
		}
		
		return Optional.empty();
	}
	

	@Override
	public String print(int depth) {
		String print="";
		
		print+=PrintHelper.printTabsDepth(depth)+"For([\n";
		print+=this.iterator.print(depth+1)+",\n";
		print+=this.from.print(depth+1)+",\n";
		print+=this.to.print(depth+1)+"\n";
		print+=PrintHelper.printTabsDepth(depth);
		print+="],\n";
		for(Node inst:block) {
			print += inst.print(depth+1);
		}
		print+="\n" + PrintHelper.printTabsDepth(depth)+")";
		return print;
	}

	@Override
	public String translateToJava(int depth) {
		String forJava = PrintHelper.tabsString(depth);
		forJava +="for(int " 
		+ this.iterator.getChild() + "=" + this.from.translateToJava(depth) + ";" 
				+ this.iterator.getChild() +"<"+this.to.translateToJava(depth) + ";"
				+ this.iterator.getChild() +"++){\n\n";
		
		for(Node inst:block) {
			forJava +=inst.translateToJava(depth+1)+"\n";
		}
		
		forJava+="\n"+PrintHelper.tabsString(depth)+"}";
		
		
		
		
		return forJava;
	}
	
	private void updateIteratorToMerge(String toUpdate,String newIterator) {
		
		for(Node inst : this.getBlock()) {
			if(inst instanceof AssignStatement) {
				((AssignStatement)inst).updateIteratorToMerge(toUpdate,newIterator);
				
			}	
			
			if(inst instanceof ForStatement)
				((ForStatement)inst).updateIteratorToMerge(toUpdate,newIterator);
		}
	}

	
	public ForStatement merge(ForStatement for2) {
	
		for2.updateIteratorToMerge(for2.iterator.getChild(), this.iterator.getChild());
		this.block.addAll(for2.getBlock());
		
		return this;
	}

	@Override
	public List<String> optimize(List<String> list) {
		// TODO Auto-generated method stub
		
		if(this.to instanceof Reference) {
			this.to.optimize(list);
		}
		if(this.from instanceof Reference) {
			this.from.optimize(list);
		}
		
		for(int i=0;i<block.size();i++) {
			if(this.block.get(i).optimize(list)==null) {
				this.block.remove(i);
				
			}
		}
		
		if(this.block.size()==0) {
			return null;
		}
		return list;
	}




	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.isParamsDeclared=false;
		for(Node inst : block) {
			inst.clear();
		}
	}

//	public boolean  optimize() {
//		if(this.block.size()==0) {
//			return false;
//		}
//		for(int i=0;i<block.size();i++) {
//			if(!block.get(i).optimize()) {
//				block.remove(i);
//			}
//		}
//		return true;
//	}
}
