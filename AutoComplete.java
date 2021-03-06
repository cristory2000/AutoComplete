//TO-DO Add necessary imports

import java.io.*;
import java.util.*;

//import jdk.internal.vm.compiler.word.WordBase;

public class AutoComplete{

  //starting point for the DLB when traversing
  DLBNode root;

  public AutoComplete(String dictFile) throws java.io.IOException {
    //TO-DO Initialize the instance variables  
    Scanner fileScan = new Scanner(new FileInputStream(dictFile));
    while(fileScan.hasNextLine()){
      StringBuilder word = new StringBuilder(fileScan.nextLine());
      //adds word from file into DLB
      add(word);
    }
    fileScan.close();
   
  }
  //method for a word paramter which was used in a2test.java
  public void add(StringBuilder word)
  {

    if (word == null) throw new IllegalArgumentException("calls put() with a null key"); 
    //calls another add with more parameters for easier add similar to lab 5
    //A2test.java uses the one paramater add method
    root=add(root, word ,0);
  }
  private DLBNode add(DLBNode node,StringBuilder key,int pos)
  {
      DLBNode result=node;
    //DLB is empty at rood node
      if (node == null){
        result = new DLBNode(key.charAt(pos),0);
        //If there is still more characters in the key go down the trie to add full word
        if(pos < key.length()-1){
          result.child = add(result.child,key,pos+1);
        } else {
          result.isWord = true;
        }
        //If the node is not null and equals the data keep traveling down the trie to add full word
      } else if(node.data== key.charAt(pos)) {
        if(pos < key.length()-1){
          result.child=add(result.child,key,pos+1);
        
        } else {
          result.isWord = true; //update
        }
    //If node does not equals key than go to sibiling to check if there is an equivalent node there or append one
      } else {
      result.sibling=add(result.sibling, key, pos); 
    }
    
    return result;
  
  }

  //increment the score of word
  public void notifyWordSelected(StringBuilder word){
   //gets node pointing to the end of the word and increments it score
    DLBNode curr=getNode(root, word.toString(), 0);
    curr.score++;//
  }


  //get the score of word
  public int getScore(StringBuilder word){
    //gets node pointing to the end of the word and returns its score
    DLBNode resultNode=getNode(root, word.toString(), 0);
    return resultNode.score;
  
  }
  private void collect(DLBNode x, Queue<String> queue,StringBuilder current) 
  {
    //Used Collect method from lab 5 to get all the words starting from a node
    //Will be used to get suggestions
    //Uses queue structure to keep words
    if (x == null) return;
    DLBNode curr = x;
    while(curr != null){
      current.append(curr.data);
      if(curr.isWord==true){
        queue.enqueue(current.substring(0, current.length()));
      }
      collect(curr.child, queue, current);
      current.deleteCharAt(current.length()-1);
      curr = curr.sibling;
  }
  
}
  /**
   * Part 2: retrieve word suggestions in sorted order.
   */
  //retrieve a sorted list of autocomplete words for word. The list should be sorted in descending order based on score.
  public ArrayList<Suggestion> retrieveWords(StringBuilder word){
    ArrayList<Suggestion> options = new ArrayList<>();
    Queue<String> collector = new Queue<>();
    StringBuilder worder=new StringBuilder();
    DLBNode nodes = getNode(root, word.toString(), 0);
  //If no node is to be found
    if(nodes==null)
  {
    return options;
  } 
  //if the given node itself is a word enqueue in collector because collect method will miss it
  if(nodes.isWord==true)
   {
     collector.enqueue(word.toString());
   }
   //collect all words from the end of the word to be made into prefix suggestions
   collect(nodes.child, collector,worder.append(word));
   //uses an iterator to go thorugh and remove words from queue in fifo 
   Iterator<String> it = collector.iterator();
     while(it.hasNext())
    {
    
      String o= collector.dequeue();
      StringBuilder sb= new StringBuilder();
      //Appends word from queue to a stringbuilder so it can passed into getnode and made into suggestion class.
      sb.append(o);
      DLBNode node= getNode(root, o, 0);
      if(node==null)
      {
        break;
      }
      Suggestion s = new Suggestion(sb,node.score);
      options.add(s);
      it.next();
    }
    Collections.sort(options);
      return options;
    }

  /**
   * Helper methods for debugging.
   */

  //Print the subtree after the start string
  public void printTree(String start){
    System.out.println("==================== START: DLB Tree Starting from "+ start + " ====================");
    DLBNode startNode = getNode(root, start, 0);
    if(startNode != null){
      printTree(startNode.child, 0);
    }
    System.out.println("==================== END: DLB Tree Starting from "+ start + " ====================");
  }

  //A helper method for printing the tree
  private void printTree(DLBNode node, int depth){
    if(node != null){
      for(int i=0; i<depth; i++){
        System.out.print(" ");
      }
      System.out.print(node.data);
      if(node.isWord){
        System.out.print(" *");
      }
        System.out.println(" (" + node.score + ")");
      printTree(node.child, depth+1);
      printTree(node.sibling, depth);
    }
  }

  //return a pointer to the node at the end of the start string. Called from printTree.
  private DLBNode getNode(DLBNode node, String start, int index){
    DLBNode result = node;
    if(node != null){
      if((index < start.length()-1) && (node.data.equals(start.charAt(index)))) {
          result = getNode(node.child, start, index+1);
      } else if((index == start.length()-1) && (node.data.equals(start.charAt(index)))) {
          result = node;
      } else {
          result = getNode(node.sibling, start, index);
      }
    }
    return result;
  }



  //A helper class to hold suggestions. Each suggestion is a (word, score) pair. 
  //This class should be Comparable to itself.
  public class Suggestion implements Comparable<Suggestion> {
      public StringBuilder word;
    public  int score;
      //TO-DO Fill in the fields and methods for this class. Make sure to have them public as they will be accessed from the test program A2Test.java.
      public Suggestion()
      {
        
      }
      public Suggestion(StringBuilder word)
      {
        this.word=word;
      }
      public Suggestion(StringBuilder word,int score)
      {
        this.word=word;
        this.score=score;
      }
    @Override
    public int compareTo(AutoComplete.Suggestion o) {
      if(o.score==score)
      {
        //the autograder expects them to be sorted alphabetically if they are equal
        int result=word.toString().compareTo(o.word.toString());
        
        return result;
      }
      if(score>o.score)
      {
        return -1;
      }
      if(score<o.score)
      {
        return 1;
      }
      // TODO Auto-generated method stub
      return 0;
    }
  }

  //The node class.
  private class DLBNode{
    private Character data;
    private int score;
    private boolean isWord;
    private DLBNode sibling;
    private DLBNode child;

    private DLBNode(Character data, int score){
        this.data = data;
        this.score = score;
        isWord = false;
        sibling = child = null;
    }
  }
}
