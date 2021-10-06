public class MergeSort {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 19, 4, 2, 30, 5, 9, 100, -3, 0};
        MergeSort ms = new MergeSort();
        ms.mergeSort(nums, 0, nums.length - 1);
        for(int i = 0; i < nums.length; i++){
            System.out.print(nums[i] + " ");
        }

    }


    public void mergeSort(int[] nums, int left, int right){
        if(left >= right){
            return;
        }
        int mid = partition(nums, left, right);
        mergeSort(nums, left, mid - 1);
        mergeSort(nums, mid + 1, right);

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
