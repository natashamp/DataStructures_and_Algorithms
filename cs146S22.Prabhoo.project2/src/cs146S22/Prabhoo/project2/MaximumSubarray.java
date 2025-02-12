package cs146S22.Prabhoo.project2;

public class MaximumSubarray {

	public class Stay {
		int arrive;
		int depart;
		int maxSum;

		public Stay(int maxSum, int arrive, int depart) {
			this.arrive = arrive;
			this.depart = depart;
			this.maxSum = maxSum;
		}

		@Override
		public String toString() {
			if (maxSum == 0) {
				return "maxSum = " + maxSum + ": Will not be visiting";
			}
			return "maxSum = " + maxSum + " arrive time = " + arrive + ", depart time = " + depart;
		}
	}

	// consider all possible pairs of arriving and departing dates. The outer loop
	// picks the beginning element, the inner loop
	// finds the maximum possible sum with first element picked by the outer loop
	// and compares this maximum with the overall maximum
	public String bruteForce(int[] arr) {
		int maxSum = 0;
		int arrive = -1; // return -1 if the sum is zero
		int depart = -1;
		for (int i = 0; i < arr.length; i++) {
			int sum = 0;
			for (int j = i; j < arr.length; j++) {
				sum += arr[j];
				if (sum > maxSum) {
					maxSum = sum;
					arrive = i;
					depart = j;
				}
			}
		}
		Stay s = new Stay(maxSum, arrive, depart);
		return s.toString();
	}

	// dividing the given array and returning the max sub array sum of both halves
	// and combine the parts
	public Stay DivideAndConquer(int[] arr, int left, int right) {
		if (right == left) {
			if (arr[left] > 0) {
				Stay s = new Stay(arr[left], left, right);
				return s;
			}
			Stay s = new Stay(0, left, right);
			return s;
		}

		int arrive = -1; // return -1 if the sum is zero
		int depart = -1;
		int mid = (left + right) / 2; // split array

		int leftMax = 0; // find max of left sub array
		int sum = 0;
		int i = mid;
		for (i = mid; i >= left; i--) { // Calculating leftMax where ending at mid
			sum = sum + arr[i];
			if (sum > leftMax) {
				leftMax = sum;
				arrive = i;
			}
		}
		Stay leftMaxStay = new Stay(leftMax, i, mid);

		int rightMax = 0; // find max of right sub array
		sum = 0;
		int j = mid + 1;
		for (j = mid + 1; j <= right; j++) { // Starting at mid + 1, exclude middle element
			sum = sum + arr[j];
			if (sum > rightMax) {
				rightMax = sum;
				depart = j;
			}
		}
		Stay rightMaxStay = new Stay(rightMax, mid + 1, j);

		// find the max of the left or right sub array and put recurse in a Stay object
		Stay recursiveLeft = DivideAndConquer(arr, left, mid);
		Stay recursiveRight = DivideAndConquer(arr, mid + 1, right);

		// find maximum maxSum of the left and right sub arrays
		Stay maxLeftandRight = null;
		if (recursiveLeft.maxSum > recursiveRight.maxSum) {
			maxLeftandRight = recursiveLeft;
		} else {
			maxLeftandRight = recursiveRight;
		}

		// Compare the sum of the max of the left and right sub arrays and return the
		// greater maxSum
		int sumOfMax = rightMax + leftMax;
		if (maxLeftandRight.maxSum >= sumOfMax) {
			return maxLeftandRight;
		}
		Stay s = new Stay(sumOfMax, arrive, depart);
		return s;

	}

	//wrapper method for divide and conquer
	public String DivideAndConquer(int[] arr) {
		if (arr == null || arr.length == 0) { //base case
			return "no or empty input array given";
		}
		
		Stay r = DivideAndConquer(arr, 0, arr.length - 1);
		return r.toString();
	}

	public String KadaneAlgorithm(int[] array) {
		int maxSum = 0; // maximum of sub array
		int arrive = -1; // arrive time, return -1 if the sum is zero
		int depart = -1; // depart time, return -1 if the sum is zero
		
		int tempMax = 0; // Contains the temporary maxSum 
		int tempArrive = 0; // contains 
		
		for (int i = 0; i < array.length; i++) {
			tempMax += array[i];
			if (tempMax < 0) { // reset if negative
				tempMax = 0;
				tempArrive = i + 1;
			}
			if (tempMax > maxSum) { // find max
				maxSum = tempMax;
				depart = i;
				arrive = tempArrive;
			}
			
		}
		
		Stay s = new Stay(maxSum, arrive, depart);
		return s.toString();
	}

	public static void main(String[] args) {
		MaximumSubarray maxSubArr = new MaximumSubarray();
		int[] arr = { 1, -2, 3, -4, 6, 8, 12, -5, 87 };
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println(" ");
		System.out.println("Brute Force Method: " + maxSubArr.bruteForce(arr));
		System.out.println("Divide and Conquer: " + maxSubArr.DivideAndConquer(arr));
		System.out.println("Kadane Algorithm: " + maxSubArr.KadaneAlgorithm(arr));

		System.out.println(" ");

		// if all numbers are negative then we want have an empty sub array and the max
		// should return 0
		int[] arr2 = { -1, -2, -3, -4, -6, -8, -12, -5, -9, -87 };
		arr = arr2;
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println(" ");
		System.out.println("Brute Force Method: " + maxSubArr.bruteForce(arr));
		System.out.println("Kadane Algorithm: " + maxSubArr.KadaneAlgorithm(arr));
		System.out.println("Divide and Conquer: " + maxSubArr.DivideAndConquer(arr));

	}
}
