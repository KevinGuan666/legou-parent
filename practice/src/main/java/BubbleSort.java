import java.util.concurrent.atomic.AtomicInteger;

public class BubbleSort {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 19, 4, 2, 30, 5, 9, 100, -3, 0};
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubble(nums);
        for(int i = 0; i < nums.length; i++){
            System.out.print(nums[i] + " ");
        }

    }

    public void bubble(int[] nums){
        if(nums == null || nums.length <= 1){
            return;
        }

        for(int i = 0; i < nums.length - 1; i++){
            boolean flag = true;
            for(int j = 0; j < nums.length - i - 1; j++){
                if(nums[j] > nums[j + 1]){
                    swap(nums, j, j + 1);
                    flag = false;
                }
            }
            if(flag) return;
        }
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
