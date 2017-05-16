package study._2017_.algorithm.stackAndQueue;

import java.util.Stack;

/**
 * @Subject 实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。
 * @Requirement 1.pop、push、getMin 操作的时间复杂度都是O(1)； 2.设计的栈类型可以使用现成的栈结构。
 * @Author HL
 * @Date 2017年5月16日 下午2:37:28
 */
public class Subject_1_1 {
	private Stack<Integer> stackData;
	private Stack<Integer> stackMin;

	public Subject_1_1() {
		this.stackData = new Stack<Integer>();
		this.stackMin = new Stack<Integer>();
	}

	public void push(int newNum) {
		if (this.stackMin.isEmpty()) {
			this.stackMin.push(newNum);
		} else if (newNum <= this.getMin()) {
			this.stackMin.push(newNum);
		}
		this.stackData.push(newNum);
	}

	public int pop() {
		if (this.stackData.isEmpty()) {
			throw new RuntimeException("The stackData is Empty!!!");
		}
		int value = this.stackData.pop();
		if (value == this.getMin()) {
			this.stackMin.pop();
		}
		return value;
	}

	public int getMin() {
		if (this.stackMin.isEmpty()) {
			throw new RuntimeException("The stackMin is Empty!!!");
		}
		return this.stackMin.peek();
	}

	public void show() {
		System.out.println("stackData:\t" + this.stackData);
		System.out.println("stackMin:\t" + this.stackMin);
		System.out.println("getMin:\t" + this.getMin());
	}

}
