import java.util.ArrayList;
import java.util.List;

public class Permutations {

  public static void main(String[] args) throws Exception {
    int[] nums = {1, 2, 3};
    List<List<Integer>> result = new Permutations().permute(nums);
  }

  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    nextPermutation(0, nums, result);
    return result;
  }

  private void nextPermutation(int i, int[] nums, List<List<Integer>> result) {
    if (i == nums.length - 1) {
      List<Integer> list = new ArrayList<>();
      for (int n : nums) list.add(n);
      result.add(list);
    } else {
      for (int j = i, l = nums.length; j < l; j++) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
        nextPermutation(i + 1, nums, result);
        temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
      }
    }
  }
}
