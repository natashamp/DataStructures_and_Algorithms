package cs146S22.Prabhoo.project2;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.junit.jupiter.api.Test;

class TestMaximumSubarray {

	@Test
	public void givenTestCases() throws IOException {
		MaximumSubarray maxSubArr = new MaximumSubarray();
		FileInputStream fileIn = new FileInputStream(
				"/Users/natashaprabhoo/eclipse-workspace/cs146S22.Prabhoo.project2/src/cs146S22/Prabhoo/project2/maxSumtest.txt");
		DataInputStream dataIn = new DataInputStream(fileIn);
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(dataIn));
		String data;
		for (int i = 0; i < 10; i++) {
			try {
				while ((data = bufferRead.readLine()) != null) {
					if (data.equals("")) {
						continue;
					}

					String[] numbers = data.trim().split("\\s+"); // Split space
					int[] array = new int[100];
					for (int j = 0; j < 100; j++) {

						array[j] = Integer.valueOf(numbers[j].trim());
					}
					int maxSumFile = Integer.valueOf(numbers[100]);
					int arriveFile = Integer.valueOf(numbers[101]);
					int departFile = Integer.valueOf(numbers[102]);

					System.out.println("Brute Force Method: " + maxSubArr.bruteForce(array));
					System.out.println("Kadane Algorithm Method: " + maxSubArr.KadaneAlgorithm(array));
					System.out.println("Divide and Conquer: " + maxSubArr.DivideAndConquer(array));
					System.out.println(" ");
					assertEquals(
							"maxSum = " + maxSumFile + " arrive time = " + arriveFile + ", depart time = " + departFile,
							maxSubArr.bruteForce(array));
					assertEquals(
							"maxSum = " + maxSumFile + " arrive time = " + arriveFile + ", depart time = " + departFile,
							maxSubArr.DivideAndConquer(array));
					assertEquals(
							"maxSum = " + maxSumFile + " arrive time = " + arriveFile + ", depart time = " + departFile,
							maxSubArr.KadaneAlgorithm(array));
					System.out.println(" ");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void randomTest() {
		MaximumSubarray maxSubArr = new MaximumSubarray();
		int[] n = { 100, 200, 500, 1000, 2000, 5000, 10000 };
		long start, stop, elapsedTime;
		for (int arrayLength : n) {
			System.out.println("Random Array Length: "+ arrayLength);
			int[] randomArray = createRandomArray(arrayLength);
			
			start = java.lang.System.nanoTime();
			maxSubArr.bruteForce(randomArray);
			stop = java.lang.System.nanoTime();
			elapsedTime = stop - start;
			System.out.println("Brute Force Elapsed Time: " + elapsedTime);

			start = java.lang.System.nanoTime();
			maxSubArr.DivideAndConquer(randomArray);
			stop = java.lang.System.nanoTime();
			elapsedTime = stop - start;
			System.out.println("Divide and Conquer Elapsed Time: " + elapsedTime);


			start = java.lang.System.nanoTime();
			maxSubArr.KadaneAlgorithm(randomArray);
			stop = java.lang.System.nanoTime();
			elapsedTime = stop - start;
			System.out.println("Kadane Algorithm Elapsed Time: " + elapsedTime);

			System.out.println();
		}

	}

	public int[] createRandomArray(int length) {
		// Set seed value as 20, so that every time you get the same sequence of random
		// numbers
		int[] returnArray = new int[length];
		Random r = new Random();
		r.setSeed(20);
		for (int i = 0; i < length; i++) {
			int random = r.nextInt();
			returnArray[i] = random;
			// System.out.println(returnArray[i]);
		}
		return returnArray;
	}
}
