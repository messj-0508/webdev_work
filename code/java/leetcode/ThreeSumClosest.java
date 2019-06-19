import java.util.Arrays;

public class ThreeSumClosest {

  public static void main(String[] args) {
    int[] a = {-1, 2, 1, -4};
    System.out.println(new ThreeSumClosest().threeSumClosest(a, 1));
  }

  public int threeSumClosest(int[] a, int target) {
    Arrays.sort(a);
    int min = Integer.MAX_VALUE, ans = -1;
    for (int i = 0, l = a.length; i < l - 2; i++) {
      if (i == 0 || !(a[i] == a[i - 1])) {
        int j = i + 1, k = l - 1;
        while (k > j) {
          if (j != i + 1 && (a[j] == a[j - 1])) {
            j++;
            continue;
          }
          int sum = a[i] + a[j] + a[k];
          if (sum < target) {
            int diff = Math.abs(sum - target);
            if (diff < min) {
              min = diff;
              ans = sum;
            }
            j++;
          } else if (sum > target) {
            int diff = Math.abs(sum - target);
            if (diff < min) {
              min = diff;
              ans = sum;
            }
            k--;
          } else {
            return sum;
          }
        }
      }
    }
    return ans;
  }
}
