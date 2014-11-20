package calculatorApp;

/**
 * @author SanaSheikh
 * @param <T> - will be filled with concrete data type
 * This class contains the methods needed for a stack class 
 */
public class Stack <T>  {
	private Node<T> head;
	private Node<T> current;
	
	//creating new stack sets head to null
	public Stack () {
		head = null;
	}
	/**
	 * @param data - pushes data to the front of the linked list 
	 */
	public void push (T data) {
		current = new Node (data);
		current.setNext(head);
		head = current;
	}
	/**
	 * 
	 * @return - return and delete data that is on the top of the stack 
	 */
	public T pop () {
		if (isEmpty()) {
			return null;
		}
		T data = head.getData();
		head = head.getNext();
		return data;
	}
	/**
	 * 
	 * @return - data that is on the top of the stack 
	 */
	public T peek () {
		if (isEmpty()) {
			return null;
		}
		T data = head.getData();
		return data;
	}
	/**
	 * 
	 * @return - boolean if the stack is empty or not 
	 */
	public boolean isEmpty () {
		if (head == null) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * returns the String representation of the stack 
	 */
	public String toString () {
		String x = "";
		current = head;
		while (current != null) {
			x = x + current.getData() + " ";
			current = current.getNext();
		}
		return x;
	}
	

}
