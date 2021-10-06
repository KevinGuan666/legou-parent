public class QuickSort {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 19, 4, 2, 30, 5, 9, 100, -3, 0};
        QuickSort q = new QuickSort();
        int n = nums.length;
        q.quickSort(nums, 0, n - 1);
        for(int i = 0; i < nums.length; i++){
            System.out.print(nums[i] + " ");
        }
    }

    public void quickSort(int[] nums, int left, int right){
        if(left >= right){
            return;
        }
        int mid = partition(nums, left, right);
        quickSort(nums, left, mid - 1);
        quickSort(nums, mid + 1, right);

    }

    public int partition(int[] nums, int left, int right){
        int pivot = nums[right];
        int index = left - 1;
        while(left < right){
            if(nums[left] < pivot){
                index++;
                swap(nums, left, index);
            }
            left++;
        }
        index++;
        swap(nums, index, right);
        return index;
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
