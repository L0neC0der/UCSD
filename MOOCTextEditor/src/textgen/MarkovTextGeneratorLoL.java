package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		if(sourceText.equals(""))
			return;
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[^ ]+");
		Matcher m = tokSplitter.matcher(sourceText);
		while (m.find()) {
			tokens.add(m.group());
		}
		starter=tokens.get(0);
		//wordList.add(0,new ListNode(starter));
		String prevword=starter;
		int index=0;
		boolean check=false;
		for(int i=1;i<tokens.size();i++){
			 check=false;
			// loop for checking if the prevword token already exist to decide whether to create new node or not
			for(int j=0;j<wordList.size();j++){
				check=wordList.get(j).getWord().equals(prevword);
				if(check==true)
				{
					index=j;
					break;
				}
					
			}
			if(check==true)
			{	
				
				((LinkedList<ListNode>)wordList).get(index).addNextWord(tokens.get(i));
			}
			else
			{
				wordList.add(new ListNode(prevword));
				((LinkedList<ListNode>)wordList).getLast().addNextWord(tokens.get(i));
			}
			prevword=tokens.get(i);
		}
		for(int j=0;j<wordList.size();j++){
			check=wordList.get(j).getWord().equals(prevword);
			if(check==true)
			{
				index=j;
				break;
			}
				
		}
		if(check==true)
		{	
			
			((LinkedList<ListNode>)wordList).get(index).addNextWord(starter);
		}
		else
		{
			wordList.add(new ListNode(prevword));
			((LinkedList<ListNode>)wordList).getLast().addNextWord(starter);
		}
		
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		
		if(wordList.isEmpty())
			return "";
		String output="";
		if(numWords>0)
		{
		
		String currword=starter;
		output+=currword;
		boolean check=false;
		int index=0;
		for(int i=1;i<numWords;i++)
		{
			for(int j=0;j<wordList.size();j++)
            {
            	check=wordList.get(j).getWord().equals(currword);
            	if(check==true)
            	{
            		index=j;
            		break;
            	}
			}
			currword=wordList.get(index).getRandomNextWord(rnGenerator);
			output=output+" "+currword;
			
		}
		}
		else if(numWords==0){
			return output;
		}
		else 
			return null;
		
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList.clear();
		if(sourceText.equals(""))
			return;
		
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[^ ]+");
		Matcher m = tokSplitter.matcher(sourceText);
		while (m.find()) {
			tokens.add(m.group());
		}
		starter=tokens.get(0);
		//wordList.add(0,new ListNode(starter));
		String prevword=starter;
		int index=0;
		boolean check=false;
		for(int i=1;i<tokens.size();i++){
			 check=false;
			// loop for checking if the prevword token already exist to decide whether to create new node or not
			for(int j=0;j<wordList.size();j++){
				check=wordList.get(j).getWord().equals(prevword);
				if(check==true)
				{
					index=j;
					break;
				}
					
			}
			if(check==true)
			{	
				
				((LinkedList<ListNode>)wordList).get(index).addNextWord(tokens.get(i));
			}
			else
			{
				wordList.add(new ListNode(prevword));
				((LinkedList<ListNode>)wordList).getLast().addNextWord(tokens.get(i));
			}
			prevword=tokens.get(i);
		}
		for(int j=0;j<wordList.size();j++){
			check=wordList.get(j).getWord().equals(prevword);
			if(check==true)
			{
				index=j;
				break;
			}
				
		}
		if(check==true)
		{	
			
			((LinkedList<ListNode>)wordList).get(index).addNextWord(starter);
		}
		else
		{
			wordList.add(new ListNode(prevword));
			((LinkedList<ListNode>)wordList).getLast().addNextWord(starter);
		}
		
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		String randoNextWord=nextWords.get(generator.nextInt(nextWords.size()));
	    return randoNextWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


