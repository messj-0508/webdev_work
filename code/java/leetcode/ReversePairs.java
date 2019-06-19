import java.util.*;


public class ReversePairs {

  class Pair {
    int i, n;

    Pair(int i, int n) {
      this.i = i;
      this.n = n;
    }

    int getN() {
      return n;
    }

    int getI() {
      return i;
    }
  }

  public static void main(String[] args) throws Exception {
    int[] A = {2, 4, 3, 5, 1};
    System.out.println(new ReversePairs().reversePairs(A));
  }

  public int reversePairs(int[] nums) {
    List<Pair> list = new ArrayList<>();
    Ftree ft = new Ftree(nums.length);
    for (int i = 0; i < nums.length; i++) {
      list.add(new Pair(i, nums[i]));
      ft.update(i, 1);
    }
    Collections.sort(list, (Comparator.comparing(Pair::getN).reversed().thenComparing(Pair::getI)));
    int[] indexMap = new int[nums.length];
    for (int i = 0, l = list.size(); i < l; i++) {
      indexMap[list.get(i).getI()] = i;
    }
    int ans = 0;
    for (int i = nums.length - 1; i >= 0; i--) {
      ft.update(indexMap[i], -1);
      int index = binarySearch(list, (long) nums[i] * 2);
      if (index > -1) {
        ans += ft.getRangeSum(index);
      }
    }
    return ans;
  }

  private int binarySearch(List<Pair> list, long n) {
    int l = 0, h = list.size() - 1;
    int ans = -1;
    while (l <= h) {
      int m = l + (h - l) / 2;
      if (list.get(m).n > n) {
        ans = m;
        l = m + 1;
      } else {
        h = m - 1;
      }
    }
    return ans;
  }

  private class Ftree {
    private int[] a;

    Ftree(int n) {
      a = new int[n + 1];
    }

    void update(int p, int v) {
      for (int i = p + 1; i < a.length; i += (i & (-i))) {
        a[i] += v;
      }
    }

    int getRangeSum(int p) {
      int sum = 0;
      for (int i = p + 1; i > 0; i -= (i & (-i))) {
        sum += a[i];
      }
      return sum;
    }
  }
}
