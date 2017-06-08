/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// TODO: Add more tests here
		try {
			int b=emptyList.remove(1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		try {
			int b=emptyList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		String b=shortList.remove(1);
		assertEquals("Remove: check a is correct ", "B", b);
		assertEquals("Remove: check element 0 is correct ", "A", shortList.get(0));
		assertEquals("Remove: check size is correct ", 1, shortList.size());
		
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		list1.add(33);
		assertEquals("Checking if the size is correct after addition",4,list1.size());
		emptyList.add(2);
		assertEquals("Checking the size in empty list",1,emptyList.size());
		assertEquals("Checking if the last element in the list",(Integer)33,list1.tail.prev.data);
		assertEquals("Checking if the last element in the list",(Integer)2,emptyList.tail.prev.data);
	
		
		try {
			shortList.add(null);
			fail("Check out of bounds");
		}
		catch (NullPointerException e) {
		
		}

		
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		emptyList.add(4);
		assertEquals(1,emptyList.size());
		shortList.remove(0);
		assertEquals(1,shortList.size());
		longerList.add(44);
		assertEquals(11,longerList.size());
		
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		try {
			emptyList.add(1,3);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		shortList.add(1,"JAVA");
		// test short list, first contents, then out of bounds
		assertEquals("Check second", "JAVA", shortList.get(1));
		assertEquals("Check third", "B", shortList.get(2));
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check size", 3, shortList.size());
		
		try {
			longerList.add(-1,4);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		
		
		
		list1.add(3,44);
		assertEquals("Check second",(Integer) 44, list1.get(3));
		assertEquals("Check third",(Integer) 42, list1.get(2));
		
		assertEquals("Check size", 4, list1.size());
		
		longerList.add(0,55);
		assertEquals("Check second",(Integer) 55, longerList.get(0));
		assertEquals("Check third",(Integer) 0, longerList.get(1));
		assertEquals("Check size", 11,longerList.size());
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		try {
			emptyList.set(1,3);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		int alpha=list1.set(1, 24);
		assertEquals("Check set via get",(Integer)24,list1.get(1));
		assertEquals("Check size of set",3,list1.size());
		assertEquals("Check  set via return",21,alpha);
		
		longerList.set(0, 11);
		assertEquals("Check set",(Integer)11,longerList.get(0));
		shortList.set(1, "C");
		assertEquals("Check set","C",shortList.get(1));
		
	}
	
	
	// TODO: Optionally add more test methods.
	
}
