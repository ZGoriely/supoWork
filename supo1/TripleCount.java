package supoWork.supo1;

import java.util.Arrays;

public class TripleCount {

	public static int countTriples(int[] nums, int k) {
		
		// Sort the numbers in O(nlgn)
		Arrays.sort(nums);
		int len = nums.length;
		int count = 0;
		
		// Find the triples in O(n^2lgn)
		for (int i = 0; i < len; i++) {
			/*
			 * Only keep searching while it is possible for a third number to exist such that i+j+h=k
			 * For each i and j, binary split the remaining sorted list until a portion of the list satisfies the condition
			 */
			int j = i+1;
			while (j<len-1 && nums[i]+nums[j]<=k) {
				int lenRight = len-j;
				// Split remaining list in half
				int pos = (lenRight)/2;
				boolean boundaryFound = false;
				while (!boundaryFound) {
					// Break if either all or none of the remaining list satisfy the property
					if (pos==0 || pos>=lenRight) {
						boundaryFound = true;
						break;
					}
					// Break if only one element left
					int sum = nums[i]+nums[j]+nums[pos+j];
					if (pos==lenRight-1) {
						if (sum > k) pos = 0;
						boundaryFound = true;
						break;
					}
					// Break if boundary found
					int rSum = nums[i]+nums[j]+nums[pos+j+1];
					if (sum <= k && rSum > k) {
						boundaryFound = true;
						break;
					}
					// Else recurse onto left or right half of remaining list
					else {
						if (sum<=k) {
							pos = pos+pos/2;
						}
						else {
							pos = pos/2;
						}
					}
				}
				/* debugging:
				for (int p=j+1; p<=pos+j; p++) {
					System.out.println(nums[i]+" + "+nums[j]+" + " + nums[p]+" ≤ " + k);
				}
				*/
				count+=pos*6;
				j++;
			}			
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		int[] numInput = new int[] {1, 7, 35, 6, 4, 42};
		int kInput = 30;
		int count = TripleCount.countTriples(numInput, kInput);
		System.out.println(count);	
	}

}
