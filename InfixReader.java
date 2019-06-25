import java.io.*;

/**
 * The InfixReader class reads an infix expression from the user, converts the 
 * infix expression to postfix expression and evaluates the postfix expression.
 * The class displays the postfix expression and the evaluation of the expression.
 * 
 * @author Luv Khanna
 * 
 */
public class InfixReader {

	/**
	 * The main method creates an object called myAnswer of the InfixReader class and
	 * calls the doConversion method with the help of the object
	 * 
	 * @param args 
	 * 				a String array
	 */
	public static void main(String[] args) {
		InfixReader myAnswer = new InfixReader();
		myAnswer.doConversion();
	}
	/**
	 * doConversion method converts the infix expression to a postfix expression and 
	 * displays the postfix expression. doConversion method also calls the evalPostfix
	 * method and sends the postfix expression by call by value 
	 */
	public void doConversion() {
		String[] infix = readInfix();
		String postfix="";
		Stack st = new Stack();
		for(int i=0;i<infix.length;i++)
		{
			if(infix[i].equals("^") || infix[i].equals("(") || infix[i].equals(")") ||
					infix[i].equals("*") || infix[i].equals("/") || 
					infix[i].equals("+") || infix[i].equals("-") )
			{
				char operator=infix[i].charAt(0);
				if(operator=='(')
				{
					st.push(operator);
				}
				else if(operator=='^')
				{
					boolean flag=true;
					while(flag)
					{
						char lastoperator=st.lastoperator();
						if(lastoperator=='^')
						{
							char ch=st.pop();
							postfix=postfix+ch+" ";
						}
						else
						{
							st.push(operator);
							flag=false;
						}
					}
				}
				else if(operator=='*' || operator=='/')
				{
					boolean flag=true;
					while(flag)
					{
						char lastoperator=st.lastoperator();
						if(lastoperator=='^' || lastoperator=='/' ||
								lastoperator=='*')
						{
							char ch=st.pop();
							postfix=postfix+ch+" ";
						}
						else
						{
							st.push(operator);
							flag=false;
						}
					}
				}
				else if(operator=='+' || operator=='-')
				{
					boolean flag=true;
					while(flag)
					{
						char lastoperator=st.lastoperator();
						if(lastoperator=='^' || lastoperator=='/' ||
								lastoperator=='*' || lastoperator=='+' || 
								lastoperator=='-')
						{
							char ch=st.pop();
							postfix=postfix+ch+" ";
						}
						else
						{
							st.push(operator);
							flag=false;
						}
					}
				}
				else
				{
					boolean flag=true;
					while(flag)
					{
						char ch=st.pop();
						if(ch=='(')
						{
							flag=false;
						}
						else 
							postfix=postfix+ch+" ";
					}
				}
			}
			else if(infix[i].equals(""))
			{
				
			}
			else
			{
				postfix=postfix+infix[i]+" ";
			}
		}
		boolean flag=true;
		while(flag)
		{
			char ch=st.pop();
			if(ch==' ')
			{
				flag=false;
			}
			else
				postfix=postfix+ch+" ";
		}
		System.out.println("Postfix: " + postfix);
		evalPostfix(postfix);
	}

	/**
	 * evalPostfix method evaluates the postfix expression and prints the result of the 
	 * expression 
	 * 
	 * @param postfix
	 * 					the string that contains the expression to be evaluated
	 */
	public void evalPostfix(String postfix) {
		Stack st = new Stack();
		String temporary="";
		for(int i=0;i<postfix.length();i++)
		{
			char c=postfix.charAt(i);
			if(c==' ')
			{
				int temporarylength=temporary.length();
				if (temporarylength==1)
				{
					if(temporary.equals("+") || temporary.equals("-") ||
							temporary.equals("*") || temporary.equals("/") ||
							temporary.equals("^"))
					{
						int answer;
						int number1=st.evalpop();
						int number2=st.evalpop();
						if(temporary.equals("+"))
						{
							answer=number2+number1;
						}
						else if(temporary.equals("-"))
						{
							answer=number2-number1;
						}
						else if(temporary.equals("*"))
						{
							answer=number2*number1;
						}
						else if(temporary.equals("/"))
						{
							answer=number2/number1;
						}
						else
						{
							answer=(int)Math.pow(number2, number1);
						}
						st.evalpush(answer);
					}
					else
					{
						int number=Integer.parseInt(temporary);
						st.evalpush(number);
					}
				}
				else
				{
					int number=Integer.parseInt(temporary);
					st.evalpush(number);
				}
				temporary="";
			}
			else
			{
				temporary=temporary+c;
			}
		}
		int finalanswer=st.evalpop();
		System.out.println("Result: "+finalanswer);
	}

	public String[] readInfix() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputLine;
		try {
			System.out.print("Input infix: ");
			inputLine = input.readLine();
			return inputLine.split(" ");
		} catch (IOException e) {
			System.err.println("Input ERROR.");
		}
		return new String[] {};
	}

}
/**
 * Stack class implements the idea of a stack. The class pushes and pops numbers or
 * characters into different stacks as required to get the desired result
 * 
 * @author Luv Khanna
 *
 */
class Stack {
	private char[] list;
	private int[] evallist;
	private int size;
	private int evalsize;
	/**
	 * A constructor to instantize the private arrays used 
	 * and initialize the private variables to 0 
	 */
	public Stack()
	{
		list= new char[100000];
		evallist= new int[100000];
		size=0;
		evalsize=0;
	}
	/**
	 * push method adds a character to the character stack
	 * 
	 * @param c
	 * 			the character that has to be added to the stack
	 */
	public void push(char c) {
		list[size]=c;
		size++;
	}
	/**
	 * evalpush method adds a number to the numeric stack.
	 * 
	 * @param n
	 * 			the number that has to be added to the stack
	 */
	public void evalpush(int n) {
		evallist[evalsize]=n;
		evalsize++;
	}
	/**
	 * pop method removes and returns the uppermost/last character of the stack.
	 * pop method returns a null value if the stack is empty.
	 * 
	 * @return last character of the stack
	 */
	public char pop() {
		if(size!=0)
		{
			size--;
			return list[size];
		}
		char garbage=' ';
		return garbage;
	}
	/**
	 * evalpop method removes and returns the uppermost/last number of the stack. 
	 * evalpop method returns a garbage value if the stack is empty
	 * 
	 * @return last number of the stack
	 */
	public int evalpop() {
		if(evalsize!=0)
		{
			evalsize--;
			return evallist[evalsize];
		}
		int garbage=-9999999;
		return garbage;
	}
	/**
	 * lastoperator method only returns the uppermost/last character of the stack.
	 * lastoperator method returns a null value if the stack is empty
	 * 
	 * @return last character of the stack
	 */
	public char lastoperator()
	{
		if (size==0)
		{
			char garbage=' ';
			return garbage;
		}
		return list[size-1];
	}
}