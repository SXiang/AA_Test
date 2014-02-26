package anr.testdriver.restapi.analytic.tables;

public class Tandom_Selection {
	public static void main(String[] args)
	{
		//int[] input_arr = {1,2,3,4,5,6,7,8,9,10,11,12};
		int[] input_arr = {1,1,1,1,9,1,10};
		//{5,7,9,57,4,7,6,7,9,1,4,56,546,48,75,41,3,486,7,4,5153,0}
		int result =r_selection(input_arr, 0,6, 5 );
		System.out.println(result);
	}
	
	static int r_selection(int[] arr, int pivot, int r, int i)
	{
		int pivot_inx = new java.util.Random().nextInt(arr.length-1);
		if(pivot == r)
		{
			return arr[pivot];
			
		}
		int q =rdmSelection(arr,pivot,r,pivot_inx);
		int k=q-pivot+1;
		if (i==k)
		{
			return arr[q];
			
		}
		else if (i<k)
			return r_selection(arr,pivot, q-1,i);
		else 
			return r_selection(arr,q+1,r,i-k);
				
		
	}
	static int rdmSelection(int[] arr, int small, int big, int want)
	{
		int pivot_inx = new java.util.Random().nextInt(arr.length-1);
		if(small==big)
		{
			return arr[small];
		}
		int q =r_partition(arr,small,big,pivot_inx);
		int k=q-small+1;
		if(want==k)
			return arr[q];
		else if (want<k)
			return r_selection(arr,small, q-1,want);
		else 
			return r_selection(arr,q+1,big,want-k);
				
	}
	static int r_partition(int[] arr, int small, int big, int pivotInx)
	{	
		int pivot = arr[pivotInx];
		swap(pivot, arr[big-1]);
		int storeInx=small;
		for (int i=0; i<big-1;i++)
		{
			if(arr[i]<pivot)
			{
				swap(arr[storeInx], arr[i]);
				storeInx++;
			}
			swap(arr[big-1],arr[storeInx]);
		}
		
		return storeInx;
	}
	static void swap(int i, int j)
	{
		int temp=i;
		i=j;
		j=temp;
		
		
	}
}
