//TO-DO Add necessary imports

import java.io.*;
import java.util.*;

//import jdk.internal.vm.compiler.word.WordBase;

public class AutoComplete{

    DLBNode root;
  //TO-DO: Add instance variable: you should have at least the tree root

  public AutoComplete(String dictFile) throws java.io.IOException {
    //TO-DO Initialize the instance variables  
    Scanner fileScan = new Scanner(new FileInputStream(dictFile));
    while(fileScan.hasNextLine()){
      StringBuilder word = new StringBuilder(fileScan.nextLine());
      add(word);
      //TO-DO call the public add method or the private helper method if you have one
    }
    fileScan.close();
   
  }

  /**
   * Part 1: add, increment score, and get score
   */

  //add word to the tree
  public void add(StringBuilder word)
  {
    if (word == null) throw new IllegalArgumentException("calls put() with a null key"); 
    root=add(root, word ,0);
    //TO-DO Implement this method
  }
  private DLBNode add(DLBNode node,StringBuilder key,int pos)
  {
      DLBNode result=node;
    if (node == null){
        result = new DLBNode(key.charAt(pos),0);
        if(pos < key.length()-1){
          result.child = add(result.child,key,pos+1);
        } else {
          result.isWord = true;
        }
    } else if(node.data== key.charAt(pos)) {
        if(pos < key.length()-1){
          result.child=add(result.child,key,pos+1);
        
        } else {
          result.isWord = true; //update
        }
    } else {
      result.sibling=add(result.sibling, key, pos); 
    }
    
    return result;
  
  }

  //increment the score of word
  public void notifyWordSelected(StringBuilder word){
    DLBNode curr=getNode(root, word.toString(), 0);
    curr.score++;//
  }


  //get the score of word
  public int getScore(StringBuilder word){
    DLBNode resultNode=getNode(root, word.toString(), 0);
    return resultNode.score;
    //TO-DO Implement this method
  
  }
  private void collect(DLBNode x, Queue<String> queue,StringBuilder current) 
  {
    if (x == null) return;
    DLBNode curr = x;
    while(curr != null){
      current.append(curr.data);
      if(curr.isWord==true){
        queue.enqueue(current.substring(0, current.length()));
        //System.out.println(queue.toString()+"whatttt");
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
  //System.out.println(nodes);
  if(nodes==null)
  {
    return options;
  } 
  if(nodes.isWord==true)
   {
     collector.enqueue(word.toString());
   }
   if(nodes.child==null)
   {
    //collect(nodes.sibling, collector,worder.append(word));
   }   
   else{
    collect(nodes.child, collector,worder.append(word));
   } 
  
   // printTree(root,0);
    Iterator<String> it = collector.iterator();
     while(it.hasNext())
    {
      
      String o= collector.dequeue();
      StringBuilder sb= new StringBuilder();
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
   Collections.reverse(options);

    //TO-DO Implement this method
  
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
        int result=o.toString().compareTo(word.toString());
        
        return result;
      }
      if(score>o.score)
      {
        return 1;
      }
      if(score<o.score)
      {
        return -1;
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
