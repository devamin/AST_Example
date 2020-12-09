I used java as a programming language to implement this task, I took advantage of the language and I based my software design by applying inheritance on my Nodes in the AST. and defining after each specification of each NodeType. This diagram has lot to say.

  

//404
![](https://lh4.googleusercontent.com/5ZyhbKbq6LPDOolLdxPJpzHBVGBDWu8eCfm-2X7T5PCJV-Aq4Qj1oR3OAJqTSd5jmxT9vpLVCe4oDITCeNExtOW8f262ShIjGhmiITSzLgQg8ONk2I1o2WdWk-F_pIRIqhZiWXiw)

  
  


This design helped me alot to apply the cleaning and optimisation feature easily.

  
  

## Project Structure

  


This project created by Maven on Eclipse IDE, I tried to organize the structure as I can to help you navigate on it. I used Packages to cluster common classes. I am so sorry I didn’t have enough time to comments my code. But I will try to explain as much as I can in this report.

Unit Test Folder contain all test case mentioned in the assignment, I added an advanced test contain almost all functionality, I commented all Test case in the top of the document please try to read it.


## Summarized test

  


I Created an advanced Unit Test that contain all functionality of my project this unit test take in input a toy language code :
```
function test2(a,b){
val r1;
r1 = 5;
val r2;
r2 = a\*b-200;
val r3;
r3 = 0;
for(i:0...b){
r3 = a+b;
a = i+2;
}
for(i2:0 ...b){
 r2 = r2+a+i2;}
val helper;
helper = 12;
for(i3:0...b){
r3= helper-i3;
r1 = r1+i3;
}
helper = 20;
return r1+r3+r1;
}
```

  


1- step : i formed the AST by the method getListOfInstructionBeforeOptimisation()

  


2- test : I generate the java code before optimisation and print it to the console.

  


3- test : I run the Clean and OptimiseLoops code & I printed the generated code in the console .

  


4- test : I tested the Equal Assertion for the expected with the code without optimisation.

and expected value with the optimized code.

  


5- test : I tested the time spent to run the code for both optimised and not optimised code.”funcNode.call(1000,100000)”.

the result is printed in the console and it was like this :

\-time spent before optimisation: 98858910

\-time spent after Optimisation : 39109225

\-gained time : 59749685

  
  


-   **the java code generated before Optimisation:**

  

```java
public class Main {
	public static int testAdvanced(int a,int b){
		int r1;
		r1 = 5;
		int r2;
		r2 = a*b-200;
		int r3;
		r3 = 0;
		for(int i=0;i<b;i++){
			r3 = a+b;
			a = i+2;
		}
		for(int i2=0;i2<b;i2++){
			r2 = r2+a+i2;
		}
		int helper;
		helper = 12;
		for(int i3=0;i3<b;i3++){
			r3 = helper-i3;
			r1 = r1+i3;
		}
		helper = 20;
		return r2+r3+r1;
	}
}

```
  
  
  
  
  
  
  
  
  


-   **the Java code generated after optimisation**

  


```java
public class Main {
	public static int testAdvanced(int a,int b){
		int r1;
		r1 = 5;
		int r2;
		r2 = a*b-200;
		int r3;
		r3 = 0;
		for(int i=0;i<b;i++){
			r3 = a+b;
			a = i+2;
		}
		int helper;
		helper = 12;
		for(int i2=0;i2<b;i2++){
			r2 = r2+a+i2;
			r3 = helper-i2;
			r1 = r1+i2;
		}
		return r2+r3+r1;
	}
}
```

  


****

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  


**THANK YOU**

  

