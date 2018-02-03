package supoWork.supo2;

public class Heapsort {

	public static void heapSort (int[] A) {
		// Form heap from array 
		for (int i = A.length/2; i >= 0; i--) {
			heapify(A, A.length, i);
		}
		
		// Repeatedly extract the max
		for (int i = A.length-1; i >= 0; i--) {
			swap(A, i, 0);
			heapify(A, i, 0);
		}
	}

	private static void heapify(int[] A, int end, int node) {
		// Ensures that the sub-heap with node as a root is a heap
		int left = node*2+1;
		int right = node*2+2;
		
		// Node has no children in range 
		if (left >= end) return;
		
		// Node has one child in range
		if (right >= end) {
			if (A[node] < A[left]) swap(A, node, left);
			return;
		}
		
		// Node has two children
		// Node satisfies heap property
		if (A[node] >= A[left] && A[node] >= A[right]) return;
		
		// Node doesn't satisfy heap property, swap with larger
		int larger = (A[left] > A[right]) ? left : right;
		swap(A, node, larger);
		heapify(A, end, larger);
	}
	
	private static void swap (int[] A, int i, int j) {
		// Swaps two values
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
	public static void main (String[] args) {
		int[] test = new int[]{1,1,2,1,4};
		Heapsort.heapSort(test);
		for (int i : test) System.out.print(i);
	}

}
