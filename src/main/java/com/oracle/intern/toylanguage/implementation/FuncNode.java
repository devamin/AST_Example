package com.oracle.intern.toylanguage.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.helpers.PrintHelper;
import com.oracle.intern.toylanguage.implementation.optimisation.ForStatementOptimiserHelper;
import com.oracle.intern.toylanguage.implementation.statement.DeclarationStatement;
import com.oracle.intern.toylanguage.implementation.statement.ForStatement;
import com.oracle.intern.toylanguage.implementation.statement.Reference;
import com.oracle.intern.toylanguage.implementation.statement.ReturnStatement;

public class FuncNode implements Node {

	private List<Node> listOfInstruction;
	private Map<String, Integer> memory = new HashMap<String, Integer>();
	private String name;
	private String args[];

	public FuncNode(String name, String... args) {
		this.name = name;

		this.args = args;
		locateArgsToMemory();
	}

	private void locateArgsToMemory() {
		for (String arg : this.args) {
			memory.put(arg, null);
		}
	}

	public Map<String, Integer> getMemory() {
		return this.memory;
	}

	

	public List<Node> getListOfInstruction() {
		return listOfInstruction;
	}

	public void setListOfInstruction(List<Node> listOfInstruction) {
		this.listOfInstruction = listOfInstruction;
		this.wireScopeWith();
//		this.optimize(new ArrayList<String>());
//		this.loopOptimisator();
//		
	}
	
	
	public void cleanAndOptimizeLoops() {
		this.optimize();
		this.loopOptimizer();
	}

	public void printScope() {

		System.out.println(this.memory);

	}

	public String print() {
		return print(0);
	}

	public String print(int depth) {
		String sExpressions = "Func(\n";
		
		sExpressions += this.printArgs(depth+1);
		
		sExpressions += this.printBody(depth+1);
		
		sExpressions+=")\n";
		return sExpressions;
	}

	public String printArgs(int depth) {
		String argStr = "";

		argStr += PrintHelper.printTabsDepth(depth)+"[";

		for (int i = 0; i < args.length; i++) {
			argStr+="\"" + args[i] + "\"";
			if (i != args.length - 1)
				argStr+=", ";
		}

		argStr+="],\n";
		return argStr;
	}

	public String printBody(int depth) {
		String body="";
		body +=PrintHelper.printTabsDepth(depth)+"[\n";
		for (int i = 0; i < listOfInstruction.size(); i++) {
			body+=listOfInstruction.get(i).print(depth + 1);
			if (i != listOfInstruction.size() - 1)
				body+=",\n";
			else
				body+="\n";
		}
		body+=PrintHelper.printTabsDepth(depth)+"]\n";
		return body;
	}

	private void wireScopeWith() {
		// Setup Reference Factory management wired them;
		Reference.setScope(this);
		DeclarationStatement.setScope(this);
	}

	@Override
	public Optional<Integer> execute() {
		Optional<Integer> result = Optional.empty();
		for (Node inst : listOfInstruction) {
			inst.execute();
			if (inst instanceof ReturnStatement)
				result = inst.execute();
		}

		return result.isPresent() ? result : Optional.of(-1);
	}

	public int call(int... args) {
		clear();
		setArgsInCalling(args);
		Optional<Integer> result = this.execute();
		return result.get();
	}

	public void clear() {
		this.memory.clear();
		
		Reference.clearReferences();
		
		locateArgsToMemory();
		ForStatementOptimiserHelper.clear();
		
		for(Node inst : listOfInstruction) {
			inst.clear();
		}
		
	}

	private void setArgsInCalling(int args[]) {

		for (int i = 0; i < args.length; i++) {
			this.memory.put(this.args[i], args[i]);
		}
	}

	public String toJava() {
		return this.translateToJava(0);
	}

	public String translateToJava(int depth) {

		String java = "";

		java += this.translateTopToJava();

		java += this.translateBodyToJava(1);

		java += "\n\t}\n";

		java += "}";

		return java;
	}

	public String translateTopToJava() {
		String top = "public class Main {\n" + "\tpublic static int " + this.name + "(";

		for (int i = 0; i < args.length; i++) {
			top += "int " + args[i];
			if (i != args.length - 1)
				top += ",";
		}

		top += "){\n\n";

		return top;
	}

	public String translateBodyToJava(int depth) {
		String body = "";

		for (Node node : listOfInstruction) {
			body += node.translateToJava(depth + 1) + "\n";
		}

		return body;
	}
	
	public void optimize() {
		this.optimize(new ArrayList<>());
	}

	public List<String> optimize(List<String> list) {

		// start from the return
		for (int i = this.listOfInstruction.size() - 1; i >= 0; i--) {
			if (this.listOfInstruction.get(i).optimize(list) == null) {
				this.listOfInstruction.remove(i);
			}
		}

		return null;
	}

	public void loopOptimizer() {
		int for1Index = -1;
		int for2Index = -1;

		ArrayList<Node> blockInBetween = new ArrayList<Node>();

		for (int i = this.listOfInstruction.size() - 1; i >= 0; i--) {

			// Check if it's a for loop
			if (for1Index != -1) {
				if (!(listOfInstruction.get(i) instanceof ForStatement))
					blockInBetween.add(listOfInstruction.get(i));
			}

			if (this.listOfInstruction.get(i) instanceof ForStatement) {

				if (for1Index == -1) {
					for1Index = i;
				} else {
					for2Index = i;
				}

				if (ForStatementOptimiserHelper.canWeMergeForStatments((ForStatement) listOfInstruction.get(i))
						&& ForStatementOptimiserHelper.doesTheBlockAboveAffectIt(blockInBetween)) {

					ForStatement firstFor = (ForStatement) listOfInstruction.get(for1Index);
					ForStatement secondFor = (ForStatement) listOfInstruction.get(for2Index);

					secondFor.merge(firstFor);
					listOfInstruction.remove(for1Index);

					if (blockInBetween.size() > 0) {
						int removeFrom = for2Index + 1;
						for (int j = 0; j < blockInBetween.size(); j++) {
							this.listOfInstruction.remove(removeFrom);
						}
					}
					Collections.reverse(blockInBetween);
					listOfInstruction.addAll(for2Index, blockInBetween);

					for1Index = for2Index + blockInBetween.size();
					i = for1Index;
					for2Index = -1;
					blockInBetween.clear();

				} else {
					if (for1Index != -1 && for2Index != -1) {
						for1Index = for2Index;
						for2Index = -1;

						blockInBetween.clear();

					}

				}
			}
		}
	}

}
