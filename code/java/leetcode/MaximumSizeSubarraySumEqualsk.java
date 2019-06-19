import java.util.HashMap;
import java.util.Map;


public class MaximumSizeSubarraySumEqualsk {


  public static void main(String[] args) throws Exception {
    int[] A = {1, -1, 5, -2, 3};
    System.out.println(new MaximumSizeSubarraySumEqualsk().maxSubArrayLen(A, 10));
  }

  public int maxSubArrayLen(int[] nums, int k) {
    Map<Integer, Integer> index = new HashMap<>();
    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      index.putIfAbsent(sum, i);
    }
    sum = 0;
    int ans = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      if (sum == k) {
        ans = Math.max(ans, i + 1);
      } else {
        int exp = sum - k;
        if (index.containsKey(exp)) {
          int farLeft = index.get(exp);
          if (farLeft < i) {
            ans = Math.max(ans, i - index.get(exp));
          }
        }
      }
    }
    return ans;
  }
}
