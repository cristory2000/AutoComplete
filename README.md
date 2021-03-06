# CS 1501 – Algorithm Implementation – Assignment #2

## Overview

* __Purpose__:  To implement a simple automatic word-completion system.
* __*Task 1*__: Implement algorithms for DLB insertion and word score maintenance.
* __*Task 2*__: Implement algorithms for retrieving word suggestions.

## Background

Autocomplete is a commonly used feature in mobile phones, text editors, and search engines. As the user in these systems type in letters, the system shows an updated list of word suggestions to help the user complete the word thy are typing. The core of an efficient autocompletion system is a fast algorithm for retrieving word suggestions based on the user input. The word suggestions should be all words in the dictionary for which the user input is a prefix.

In this assignment we will build a simple autocompletion system using a DLB trie. The system will order the suggested words based on a _score_ that represents the number of times a word has been selected as a suggestion. The system can learn new words that are not in its dictionary.

## TASK 1 - DLB Insert and Score Maintenance

In the first part of this assignment, you will implement algorithms for:

- inserting a `StringBuilder` object into a DLB trie by filling in the following method, which is defined in `AutoComplete.java`. You may want to add a private recursive helper method or use iteration. You may find the code of Lab 5 particularly useful for this method.

```java
//add word to the tree
public void add(StringBuilder word){
  //TO-DO Implement this method
}
```

- incrementing the _score_ of a word. Each DLB trie node should contain a `score` field to keep track of the number of times a word has been selected as a suggestion. The purpose of these scores is to show the words that are more commonly used first in the list of suggestions. You will have to implement the following method, which is defined in `AutoComplete.java`. You may want to add a private recursive helper method or use iteration.

```java
//increment the score of word
public void notifyWordSelected(StringBuilder word){
  //TO-DO Implement this method
}
```

- retrieving the _score_ of a word. You will have to implement the following method, which is also defined in `AutoComplete.java`. You may want to add a private recursive helper method or use iteration.

```java
//get the score of word
public int getScore(StringBuilder word){
  //TO-DO Implement this method
  return -1;
}
```
The `DLBNode` class is already defined for you in `AutoComplete.java`.

```java
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
 ```
The constructor for `AutoComplete` is partially provided for you.

```java
public AutoComplete(String dictFile) throws java.io.IOException {
    //TO-DO Initialize the instance variables  
    Scanner fileScan = new Scanner(new FileInputStream(dictFile));
    while(fileScan.hasNextLine()){
      StringBuilder word = new StringBuilder(fileScan.nextLine());
      //TO-DO call the public add method or the private helper method if you have one
    }
    fileScan.close();
  }
```
## TASK 2 - Retrieving Word Suggestions

In the second part of the assignment you will implement algorithms for retrieving all words that have the same prefix from the DLB trie. The prefix is supplied as input to the following method, which is also defined in `AutoComplete.java`. You may want to add a private recursive helper method or use iteration.

```java
 //retrieve a sorted list of autocomplete words for word. The list should be sorted in descending order based on score.
 public ArrayList<Suggestion> retrieveWords(StringBuilder word){
   //TO-DO Implement this method
   return null;
  }
```
You will need to define an inner public class, named `Suggestion`, inside `AutoComplete`.

```java
//A helper class to hold suggestions. Each suggestion is a (word, score) pair.
//This class should be Comparable to itself.
public class Suggestion ..... {
     //TO-DO Fill in the fields and methods for this class. Make sure to have them public as they will be accessed from the test program `A2Test.java`.
}
```

## Testing and Debugging

To test your code for Task 1 and Task 2, use `A2Test.java`. This program needs a dictionary file. I have included the same dictionary, `dict8.txt`, of Assignment 1. I highly recommend that you start by creating your own small dictionary to test your code on first. Sample output is provided below. The file `small.txt` contained the following words:

```
abc
a
aba
xyz
x
efg
ef
```

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n A2Test small.txt
Listening for transport dt_socket at address: 62884
Incrementing score of x
Incrementing score of ef
Incrementing score of ef
Incrementing score of a
Incrementing score of aba
Incrementing score of x
Incrementing score of aba
Incrementing score of ef
Incrementing score of a
Incrementing score of abc
Part 1: Testing word scores:
efg: 0
xyz: 0
a: 2
abc: 1
a: 2
abc: 1
ef: 3
abc: 1
aba: 2
x: 2
Part 2: Testing suggestions:
Type one or more letters then press enter to get auto-complete suggestions...
a
0: a: 2
1: aba: 2
2: abc: 1
3: No selection
Select a number (-1 to exit): 0
Type one or more letters then press enter to get auto-complete suggestions...
a
0: a: 3
1: aba: 2
2: abc: 1
3: No selection
Select a number (-1 to exit): 3
Type one or more letters then press enter to get auto-complete suggestions...
zxy
0: Add zxy to the dictionary.
1: No selection
Select a number (-1 to exit): 0
Type one or more letters then press enter to get auto-complete suggestions...
zx
0: zxy: 1
1: Add zx to the dictionary.
2: No selection
Select a number (-1 to exit): 2
Type one or more letters then press enter to get auto-complete suggestions...
abc
0: abc: 1
1: No selection
Select a number (-1 to exit): -1
```


Please note that there are two methods `testScores` and `testSuggestions` that test Task 1 and Task 2, respectively. You can comment out the call to the second method while working on Task 1.

Debugging is a skill that improves with practice. You have to spend time debugging your code using [JDB](https://canvas.pitt.edu/courses/108541/pages/debugging-java-programs-using-jdb). To help you in debugging, I have included a method, `printTree`, inside `AutoComplete.java` for printing the full tree or a subtree starting from a given prefix. Here is an example of printing the subtree starting from the prefix `fun`.

```java
printTree("fun");
```
The output would look like the following. The numbers between parentheses are the score values and the asterisks mark nodes that are at the end of words.

```
==================== START: DLB Tree Starting from fun ====================
c (0)
 t (0)
  i (0)
   o (0)
    n * (0)
  o (0)
   r * (0)
d * (0)
e (0)
 r (0)
  a (0)
   l * (0)
  e (0)
   a (0)
    l * (0)
g (0)
 a (0)
  l * (0)
 i * (0)
  b (0)
   l (0)
    e * (0)
 o (0)
  i (0)
   d * (0)
 u (0)
  s * (0)
k * (0)
n (0)
 e (0)
  l * (0)
 y * (0)
==================== END: DLB Tree Starting from fun ====================
```

## Writeup
 `a2.md` summarizes your project in the following ways:
1.	Discuss how you solved the autocomplete problem in some detail. Include
    * how you set up the data structures necessary for the problem and
    * how your algorithms for both tasks proceeded.  
    * Also indicate any coding or debugging issues you faced and how you resolved them.  If you were not able to get the program to work correctly, still include your approach and speculate as to what still needs to be corrected.
2.	Include an asymptotic analysis of the worst-case run-time of the insertion, score incrementing, score retrieval, and suggestions retrieval.  Some values to consider in this analysis may include:
    * Number of words in the dictionary
    * Number of characters in a word and/or prefix
    * The alphabet size
    * Number of matching word suggestions
