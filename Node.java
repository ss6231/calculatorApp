package calculatorApp;

/**
 * 
 * @author SanaSheikh
 * @param <T> - will be filled with concrete data type
 * This class contains the methods needed for a linked list 
 * 
 */
public class Node <T>  {
	private Node <T> next;
	private T data;
	//constructor sets data and next equal to null
	public Node () {
		data = null;
		next = null;
	}
	//overloaded constructor sets given data
	public Node (T data) {
		this.data = data;
	}
	/**
	 * 
	 * @return - the next node
	 */
	public Node<T> getNext () {
		return next;
	}
	/**
	 * 
	 * @return - current data 
	 */
	public T getData () {
		return data;
	}
	/**
	 * @param node - given node as parameter, set next
	 * equal to given node 
	 */
	public void setNext (Node<T> node) {
		next = node;
	}
	/**
	 * 
	 * @param data - set current data to the parameter data 
	 */
	public void setData (T data) {
		this.data = data;
	}
	

}
