import java.util.ArrayList;
import java.util.List;

public class Combinations {

  public static void main(String[] args) throws Exception {
    List<List<Integer>> result = new Combinations().combine(3, 3);
  }

  public List<List<Integer>> combine(int n, int k) {
    int[] subArr = new int[k];
    List<List<Integer>> result = new ArrayList<>();
    getNext(0, 0, n, k, subArr, result);
    return result;
  }

  private void getNext(int i, int count, int n, int k, int[] subArr, List<List<Integer>> result) {
    if (k == 0) {
      List<Integer> subList = new ArrayList<>();
      for (int a : subArr) subList.add(a);
      result.add(subList);
    } else {
      for (int j = i + 1; j <= n; j++) {
        subArr[count] = j;
        getNext(j, count + 1, n, k - 1, subArr, result);
      }
    }
  }
}
