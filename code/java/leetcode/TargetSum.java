public class TargetSum {

  private static int n;

  public static void main(String[] args) throws Exception {
    int[] A = {1, 1, 1, 1, 1};
    n = 0;
    new TargetSum().findTargetSumWays(A, 3);
    System.out.println(n);
  }

  public int findTargetSumWays(int[] nums, int S) {
    backtrack(nums, S, 0, 0);
    return n;
  }

  private void backtrack(int[] nums, int target, int sum, int i) {
    if (i == nums.length) {
      if (sum == target) {
        n++;
      }
    } else {
      backtrack(nums, target, sum + nums[i], i + 1);
      backtrack(nums, target, sum - nums[i], i + 1);
    }
  }
}
