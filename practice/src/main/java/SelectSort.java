public class SelectSort {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 19, 4, 2, 30, 5, 9, 100, -3, 0};
        SelectSort s = new SelectSort();
        s.select(nums);
        for(int i = 0; i < nums.length; i++){
            System.out.print(nums[i] + " ");
        }
    }

    public void select(int[] nums){
        if(nums == null || nums.length <= 1) return;
        for(int i = 0; i < nums.length - 1; i++){
            int minIndex = i;
            for(int j = i + 1; j < nums.length; j++){
                if(nums[minIndex] > nums[j]){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                swap(nums, minIndex, i);
            }
        }
    }


    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
