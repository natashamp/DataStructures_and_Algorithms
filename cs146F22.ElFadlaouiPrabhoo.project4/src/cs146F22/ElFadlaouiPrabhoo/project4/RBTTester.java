package cs146F22.ElFadlaouiPrabhoo.project4;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class RBTTester {
	// Test the Red Black Tree
	RedBlackTree rbtDictionary = new RedBlackTree();
	@Test
	public void givenCases() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		assertEquals("DBACFEHGIJ", makeString(rbt));
		String str = "Color: 1, Key:D Parent: \n" + "Color: 1, Key:B Parent: D\n" + "Color: 1, Key:A Parent: B\n"
				+ "Color: 1, Key:C Parent: B\n" + "Color: 1, Key:F Parent: D\n" + "Color: 1, Key:E Parent: F\n"
				+ "Color: 0, Key:H Parent: F\n" + "Color: 1, Key:G Parent: H\n" + "Color: 1, Key:I Parent: H\n"
				+ "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
	}
	// add tester for spell checker
	@Test
	public void testingTreeOne() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		assertEquals("BAD", makeString(rbt));

		String str = "Color: 1, Key:B Parent: \n" + "Color: 0, Key:A Parent: B\n" + "Color: 0, Key:D Parent: B\n";
		assertEquals(str, makeStringDetails(rbt));
	}

	@Test
	public void testingLookup() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("Y");
		rbt.insert("L");
		rbt.insert("P");
		rbt.insert("X");
		assertEquals("BALDXPY", makeString(rbt));
		assertEquals("P", rbt.lookup("P"));
		assertEquals(null, rbt.lookup("V"));

	}

	@Test
	public void testingPoem() throws IOException {
		long startTime, stopTime, elapsedTime;
		// RedBlackTree rbt = new RedBlackTree();
		
		startTime =  System.currentTimeMillis();
		// reading the dictionary file
		FileReader frDic = new FileReader("Dictionary.txt");
		Scanner scDic = new Scanner(frDic);

		FileReader frDicA = new FileReader("DictionaryAddedWords.txt");
		Scanner scDicA = new Scanner(frDicA);

		// reading each line of the dictionary file and insert each word into the red
		// black tree

		while (scDic.hasNextLine()) {
			String line = scDic.nextLine();
			rbtDictionary.insert(line);
		}
		while (scDicA.hasNextLine()) {
			String lineA = scDicA.nextLine();
			rbtDictionary.insert(lineA);
		}

		// read the poem file
		FileReader frPoem = new FileReader("poem.txt");
		Scanner scPoem = new Scanner(frPoem);
		// for each word in the poem each check look it up in the red black tree
		int countErrors = 0;
		while (scPoem.hasNextLine()) {
			String line2 = scPoem.nextLine();
			Scanner lineScanner = new Scanner(line2);
			lineScanner.useDelimiter(" ");

			while (lineScanner.hasNext()) {
				String word = lineScanner.next();
				word = word.toLowerCase();
				word = word.replace(",", "");
				word = word.replace(".", "");
				word = word.replace("-", "");

				if (!word.equals("")) {
					if (rbtDictionary.lookup(word) == null) {
						countErrors++;
						System.out.println("Spelling Error : " + word + "");
					}
				}
			}
		}
		stopTime =  System.currentTimeMillis();
		elapsedTime = stopTime - startTime ;
		System.out.println("Elapsed Time of Poem Testing: "+ elapsedTime);
		// if it is not in the red black tree say spelling error
		String errorString = "There is " + countErrors + " spelling errors";
		System.out.println(errorString);
		assertEquals("There is 3 spelling errors", errorString);

	}

	public static String makeString(RedBlackTree t) {
		class MyVisitor implements RedBlackTree.Visitor {
			String result = "";

			public void visit(RedBlackTree.Node n) {
				result = result + n.key;
			}
		}
		;
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}

	public static String makeStringDetails(RedBlackTree t) {
		{
			class MyVisitor implements RedBlackTree.Visitor {
				String result = "";

				public void visit(RedBlackTree.Node n) {
					if (!(n.key).equals("")) {
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: "
								+ (n.parent == null ? "" : n.parent.key) + "\n";
					}
				}
			}
			;
			MyVisitor v = new MyVisitor();
			t.preOrderVisit(v);
			return v.result;
		}
	}

	@Test
	public void testingRuntime() throws IOException {

		long startTime; // before we calculate the run time 
		long stopTime; // after we calculate the run time
		long elapsedTime;

		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("Y");
		rbt.insert("L");
		rbt.insert("P");
		rbt.insert("X");

		//	timing the lookup calls
		startTime =  java.lang.System.nanoTime();
		rbt.lookup("L");
		stopTime =  java.lang.System.nanoTime();
		elapsedTime = stopTime - startTime ;
		System.out.println("Elapsed Time of Lookup Test #1 in nanosec: "+ elapsedTime);


		startTime =  java.lang.System.nanoTime();
		rbtDictionary.lookup("ababua");
		stopTime =  java.lang.System.nanoTime();
		elapsedTime = stopTime - startTime ;
		System.out.println("Elapsed Time of Lookup Test #2 in nanosec: "+ elapsedTime);

		startTime = java.lang.System.nanoTime();
		rbtDictionary.lookup("slay");
		stopTime =  java.lang.System.nanoTime();
		elapsedTime = stopTime - startTime ;
		System.out.println("Elapsed Time of Lookup Test #3 in nanosec: "+ elapsedTime);

	}

}
