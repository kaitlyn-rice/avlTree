/* Author: Kaitlyn Rice
   Date: May 11, 2020
   Purpose: Use avl tree to count words in a file
*/

package avl;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Vocab {

  public static void main(String[] args) {

    try {
      // TODO: Change this to make it handle multiple input files.
      if (args.length > 0){
         File f = new File(args[0]);
         Scanner sc = new Scanner(f);
         Count c = wordCount(sc);
         System.out.println(c);
      }
      else {
         Scanner sc = new Scanner(System.in);
         Count c = wordCount(sc);
         System.out.println(c);
      }
    } catch (FileNotFoundException exc) {
      System.out.println("Could not find file " + args[0]);
    }
  }

  /* count the total and unique words in a document being read
  * by the given Scanner. return the two values in a Count object.*/
  private static Count wordCount(Scanner sc) {
    AVL tree = new AVL();
    Count c = new Count();

    // TODO: fill in the unique and total fields of c
    // before c is returned

    while (sc.hasNext()) {
      // read and parse each word
      String word = sc.next();
      c.total++;
      // remove non-letter characters, convert to lower case:
      word = word.replaceAll("[^a-zA-Z ]", "").toLowerCase();
      tree.avlInsert(word);
    }
    c.unique = tree.getSize();
    return c;
  }
}
