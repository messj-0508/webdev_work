public class RemoveDuplicates {
  public static void main(String[] args) throws Exception {
    int[] nums = {1, 1, 2};
    int N = new RemoveDuplicates().removeDuplicates(nums);
    for (int i = 0; i < N; i++) System.out.print(nums[i] + " ");
  }

  public int removeDuplicates(int[] nums) {
    if (nums.length == 1) return 1;
    int size = 1;
    for (int j = 0, i = 1; i < nums.length; i++) {
      if (nums[i] != nums[i - 1]) {
        size++;
        j++;
        nums[j] = nums[i];
      }
    }
    return size;
  }
}
