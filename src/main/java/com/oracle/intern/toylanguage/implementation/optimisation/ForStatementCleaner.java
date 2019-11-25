package com.oracle.intern.toylanguage.implementation.optimisation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.oracle.intern.toylanguage.abstraction.Node;
import com.oracle.intern.toylanguage.implementation.statement.AssignStatement;
import com.oracle.intern.toylanguage.implementation.statement.ForStatement;

public class ForStatementCleaner {

	private ArrayList<String> used = new ArrayList<String>();
	private ArrayList<String> assigned = new ArrayList<String>();
	private ForStatement forStatement;
	private static ForStatementCleaner for1 = null;
	private static ForStatementCleaner for2 = null;

	private ForStatementCleaner(ForStatement forStatment) {
		this.forStatement = forStatment;
		List<Node> block = forStatment.getBlock();
		for (int i = 0; i < block.size(); i++) {
			if (block.get(i) instanceof AssignStatement) {

				AssignStatement assign = (AssignStatement) block.get(i);

				this.assigned.add(assign.getRef().getChild());

				this.used.addAll(assign.getUsedRefs());

			}
		}
	}

	public ArrayList<String> getUsed() {
		return used;
	}

	public void setUsed(ArrayList<String> used) {
		this.used = used;
	}

	public ArrayList<String> getAssigned() {
		return assigned;
	}

	public void setAssigned(ArrayList<String> assigned) {
		this.assigned = assigned;
	}

	public ForStatement getForStatement() {
		return forStatement;
	}

	public void setForStatement(ForStatement forStatement) {
		this.forStatement = forStatement;
	}

	private static void TwoForMerged() {
		for2.assigned.addAll(for1.assigned);
		for2.used.addAll(for1.used);
		for1 = for2;
		for2 = null;
	}
	
	public static boolean canWeMergeForStatments(ForStatement forStatment1) {
		if (for1 == null) {
			for1 = new ForStatementCleaner(forStatment1);
			return false;
		} else {
			for2 = new ForStatementCleaner(forStatment1);
			// let's merge if we can

			// check the start and the end
			if (for1.getForStatement().getFrom().equals(for2.getForStatement().getFrom())
					&& for1.getForStatement().getTo().equals(for2.getForStatement().getTo())) {
				// relation between for1 and for2
				//
				if (Collections.disjoint(for1.getUsed(), for2.getAssigned())
						&& Collections.disjoint(for1.getAssigned(), for2.getUsed())) {
					// we can merge them
					return true;
				}
			}
			for1 = for2;
			for2 = null;
			return false;
		}

	}

	public static boolean doesTheBlockAboveAffectIt(List<Node> blockInBetween) {
		if (for1 != null && for2 != null) {
			if (blockInBetween.size() > 0) {

				ArrayList<String> assignedInBlock = new ArrayList<String>();
				ArrayList<String> usedInBlock = new ArrayList<String>();

				for (Node inst : blockInBetween) {
					if (inst instanceof AssignStatement) {
						AssignStatement assign = (AssignStatement) inst;
						assignedInBlock.add(assign.getRef().getChild());
						usedInBlock.addAll(assign.getUsedRefs());
					}
				}
				
				if(!(Collections.disjoint(for2.assigned, usedInBlock) && Collections.disjoint(for2.used,assignedInBlock ))) {
					for1 = for2;
					for2 = null;
					return false;
				}
			}
				TwoForMerged();
				return true;
		}
		return false;
	}
	
	public static void clear() {
		for1 = null;
		for2 = null;
	}

//	public static ForStatment merge(ForStatment for1,ForStatment for2) {
//	}

}
