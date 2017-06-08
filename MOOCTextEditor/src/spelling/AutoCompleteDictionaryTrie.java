package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		word=word.toLowerCase();
		char[] insert=word.toCharArray();
		if(insert.length==0)
			return false;
		TrieNode temp=root.insert(insert[0]);
		if(temp==null){
			temp=root.getChild(insert[0]);
		}
		TrieNode temp1;
		for(int i=1;i<insert.length;i++){
			temp1=temp;
			temp=temp.insert(insert[i]);
			if(temp==null){
				temp=temp1.getChild(insert[i]);
			}
		}
	    if(temp.endsWord()==true){
		return false;	
		}
		size++;
		temp.setEndsWord(true);
		
	    return true  ;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		s=s.toLowerCase();
		char[] insert=s.toCharArray();
		if(insert.length==0)
			return false;
		TrieNode temp=root.getChild(insert[0]);
		if(temp==null)
			return false;
		for(int i=1;i<insert.length;i++){
				temp=temp.getChild(insert[i]);
			if(temp==null)
				return false;
		}
	    if(temp.endsWord()==true && temp.getText().equals(s)){
		return true;	
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> retString=new LinkedList<String>();
    	 
    	 prefix=prefix.toLowerCase();
 		 char[] insert=prefix.toCharArray();
 		TrieNode temp;
 		 if(insert.length>0){
 		 temp=root.getChild(insert[0]);
 		 if(temp==null)
 			return retString;
 		 for(int i=1;i<insert.length;i++){
 				temp=temp.getChild(insert[i]);
 			if(temp==null)
 				return retString;
 		}
     }else temp=root;
 		 Queue<TrieNode> q= new LinkedList<TrieNode>();
 		 q.add(temp);
 		 int number=0;
 		 TrieNode alpha;
 		 while(!q.isEmpty()&& number<numCompletions){
 			 TrieNode ho=q.remove();
 			 if(ho!=null){
 				 if(ho.endsWord()){
 					 retString.add(ho.getText());
 					 number++;
 				 }
 				 Object[] addChar= ho.getValidNextCharacters().toArray();
 				
 				 for(int i=0;i<addChar.length;i++){
 					 alpha=ho.getChild((Character) addChar[i]);
 					 q.add(alpha);
 				 }
 			 }
 		 }
         return retString;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}