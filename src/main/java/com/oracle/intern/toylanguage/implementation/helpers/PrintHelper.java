package com.oracle.intern.toylanguage.implementation.helpers;

public class PrintHelper {

	
	public static String printTabsDepth(int x) {
		String str= "";
		for(int i =0;i<x;i++)
			str+="    ";
		return str;
	}
	
	public static String tabsString(int x) {
		String tab = "";
		for(int i =0;i<x;i++)
			tab+="\t";
		
		return tab;
	}
}
