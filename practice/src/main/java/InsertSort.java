public class InsertSort {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 19, 4, 2, 30, 5, 9, 100, -3, 0};
        InsertSort i = new InsertSort();
        int n = nums.length;
        i.insertSort(nums);
        for(int j = 0; j < nums.length; j++){
            System.out.print(nums[j] + " ");
        }

    }

    public void insertSort(int[] nums){
        if(nums == null || nums.length <= 1) return;
        for(int i = 1; i < nums.length; i++){
            if(nums[i] < nums[i - 1]){
                int temp = nums[i];
                int index = i;
                while(index > 0 && temp < nums[index - 1]){
                    nums[index] = nums[index - 1];
                    index--;
                }
                nums[index] = temp;
            }
        }
    }
}
