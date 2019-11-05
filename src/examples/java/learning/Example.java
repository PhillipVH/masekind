package learning;

import java.util.Arrays;

import za.ac.sun.cs.coastal.Symbolic;

public class Example {

	public static boolean target(char[] A) {
		int state = 0;
		for (int idx = 0; idx < A.length; idx++) {
			char current = A[idx];
			if (state == 0) {
				if ((current == (char)97)) {
					state = 1;
					continue;
				}
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)98 && current <= (char)65535)) {
					state = 5;
					continue;
				}
			}
			if (state == 1) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)99 && current <= (char)65535)) {
					state = 5;
					continue;
				}
				if ((current == (char)98)) {
					state = 3;
					continue;
				}
				if ((current == (char)97)) {
					state = 2;
					continue;
				}
			}
			if (state == 2) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)98 && current <= (char)65535)) {
					state = 5;
					continue;
				}
				if ((current == (char)97)) {
					state = 2;
					continue;
				}
			}
			if (state == 3) {
				if ((current == (char)99)) {
					state = 4;
					continue;
				}
				if ((current >= (char)0 && current <= (char)98) || (current >= (char)100 && current <= (char)65535)) {
					state = 5;
					continue;
				}
			}
			if (state == 4) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 5;
					continue;
				}
			}
			if (state == 5) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 5;
					continue;
				}
			}
		}
		if ((state == 0) || (state == 1) || (state == 2) || (state == 4) || false) { 
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean parse(char[] A) {
		int state = 4;
		for (int idx = 0; idx < A.length; idx++) {
			char current = A[idx];
			if (state == 0) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)99 && current <= (char)65535)) {
					state = 5;
					continue;
				}
				if ((current == (char)98)) {
					state = 2;
					continue;
				}
				if ((current == (char)97)) {
					state = 1;
					continue;
				}
			}
			if (state == 1) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)98 && current <= (char)65535)) {
					state = 5;
					continue;
				}
				if ((current == (char)97)) {
					state = 1;
					continue;
				}
			}
			if (state == 2) {
				if ((current >= (char)0 && current <= (char)98) || (current >= (char)100 && current <= (char)65535)) {
					state = 5;
					continue;
				}
				if ((current == (char)99)) {
					state = 3;
					continue;
				}
			}
			if (state == 3) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 5;
					continue;
				}
			}
			if (state == 4) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)98 && current <= (char)65535)) {
					state = 5;
					continue;
				}
				if ((current == (char)97)) {
					state = 0;
					continue;
				}
			}
			if (state == 5) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 5;
					continue;
				}
			}
		}
		if ((state == 0) || (state == 1) || (state == 3) || (state == 4) || false) { 
			return true;
		} else {
			return false;
		}
	}

	
	public static boolean parse3(char[] A) {
		int state = 2;
		for (int idx = 0; idx < A.length; idx++) {
			char current = A[idx];
			if (state == 0) {
				if ((current == (char)99)) {
					state = 1;
					continue;
				}
				if ((current >= (char)0 && current <= (char)98) || (current >= (char)100 && current <= (char)65535)) {
					state = 4;
					continue;
				}
			}
			if (state == 1) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 4;
					continue;
				}
			}
			if (state == 2) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)98 && current <= (char)65535)) {
					state = 4;
					continue;
				}
				if ((current == (char)97)) {
					state = 3;
					continue;
				}
			}
			if (state == 3) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)99 && current <= (char)65535)) {
					state = 4;
					continue;
				}
				if ((current == (char)97)) {
					state = 1;
					continue;
				}
				if ((current == (char)98)) {
					state = 0;
					continue;
				}
			}
			if (state == 4) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 4;
					continue;
				}
			}
		}
		if ((state == 1) || (state == 2) || (state == 3) || false) { 
			return true;
		} else {
			return false;
		}
	}

	
	public static boolean parse2(char[] A) {
		int state = 0;
		for (int idx = 0; idx < A.length; idx++) {
			char current = A[idx];
			if (state == 0) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)98 && current <= (char)65535)) {
					state = 4;
					continue;
				}
				if ((current == (char)97)) {
					state = 2;
					continue;
				}
			}
			if (state == 1) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 4;
					continue;
				}
			}
			if (state == 2) {
				if ((current >= (char)0 && current <= (char)97) || (current >= (char)99 && current <= (char)65535)) {
					state = 4;
					continue;
				}
				if ((current == (char)98)) {
					state = 3;
					continue;
				}
			}
			if (state == 3) {
				if ((current == (char)99)) {
					state = 1;
					continue;
				}
				if ((current >= (char)0 && current <= (char)98) || (current >= (char)100 && current <= (char)65535)) {
					state = 4;
					continue;
				}
			}
			if (state == 4) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 4;
					continue;
				}
			}
		}
		if ((state == 0) || (state == 1) || (state == 2) || false) { 
			return true;
		} else {
			return false;
		}
	}


	public static boolean parse1(char[] A) {
		int state = 0;
		for (int idx = 0; idx < A.length; idx++) {
			char current = A[idx];
			if (state == 0) {
				if ((current >= (char)0 && current <= (char)96) || (current >= (char)98 && current <= (char)65535)) {
					state = 1;
					continue;
				}
				if ((current == (char)97)) {
					state = 0;
					continue;
				}
			}
			if (state == 1) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 1;
					continue;
				}
			}
		}
		if ((state == 0) || false) { 
			return true;
		} else {
			return false;
		}
	}

	
	public static boolean parse0(char[] A) {
		int state = 0;
		for (int idx = 0; idx < A.length; idx++) {
			char current = A[idx];
			if (state == 0) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 1;
					continue;
				}
			}
			if (state == 1) {
				if ((current >= (char)0 && current <= (char)65535)) {
					state = 1;
					continue;
				}
			}
		}
		if ((state == 0) || false) { 
			return true;
		} else {
			return false;
		}
	}

	public static boolean check(char[] input) {
		boolean targetResult = target(input);
		boolean learntResult = parse(input);
		if (targetResult != learntResult) {
			Symbolic.mark("Counter Example: " + Arrays.toString(input));
			return false;
		}
		
		return true;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] inputValues = new char[]{'.', '.', '.', '.' };
		boolean result = check(inputValues);
		if (!result) {
			Symbolic.printPC("Not Accepted");
		}
	}

}

/*
 * After first iteration we get
 *  
X$2<=100 && X$2>=51 && X$1<51   && X$0<51
X$2<=20  && X$2>=0  && X$1<=100 && X$1>=51 && X$0<51
X$2 >20  && X$2>=0  && X$1<=100 && X$1>=51 && X$0<51
X$2<=100 && X$2>=51 && X$1>100  && X$1>=51 && X$0<51
X$2>20   && X$2>=0  && X$1<=20  && X$1>=0  && X$0<=100 && X$0>=51
X$1>20   && X$1>=0  && X$0<=100 && X$0>=51
X$2<=100 && X$2>=51 && X$1<51   && X$0>100 && X$0>=51
X$2<=20  && X$2>=0  && X$1<=100 && X$1>=51 && X$0>100 && X$0>=51
X$2>20   && X$2>=0  && X$1<=100 && X$1>=51 && X$0>100 && X$0>=51
X$2<=100 && X$2>=51 && X$1>100  && X$1>=51 && X$0>100 && X$0>=51
*/


