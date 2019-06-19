public class FindPeakElement {
  public static void main(String[] args) throws Exception {
    int[] nums = {3, 4, 3, 2, 1};
    System.out.println(new FindPeakElement().findPeakElement(nums));
  }

  public int findPeakElement(int[] nums) {
    if (nums.length == 1) return 0;
    if (nums[0] > nums[1]) return 0;
    else if (nums[nums.length - 1] > nums[nums.length - 2]) return nums.length - 1;

    int l = 0, h = nums.length - 1;
    int ans = 0;
    while (l <= h) {
      int m = l + (h - l) / 2;
      if (m - 1 >= 0 && m + 1 < nums.length) {
        if (nums[m] > nums[m - 1] && nums[m] > nums[m + 1]) {
          return m;
        }
      }
      if (m - 1 >= 0 && nums[m - 1] > nums[m]) { // search left
        h = m - 1;
      } else {
        ans = l; // mark this as the answer and search right
        l = m + 1;
      }
    }
    return ans;
  }
}
