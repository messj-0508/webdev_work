import java.util.Arrays;

public class MaximumLengthOfPairChain {

  public static void main(String[] args) throws Exception {
    int[][] A = {{1, 2}, {2, 3}, {3, 4}};
    System.out.println(new MaximumLengthOfPairChain().findLongestChain(A));
  }

  public int findLongestChain(int[][] pairs) {
    Arrays.sort(
        pairs,
        (o1, o2) -> o1[1] == o2[1] ? Integer.compare(o1[0], o2[0]) : Integer.compare(o1[1], o2[1]));
    int count = 1;
    int[] curr = pairs[0];
    for (int i = 1; i < pairs.length; i++) {
      if (pairs[i][0] > curr[1]) {
        count++;
        curr = pairs[i];
      }
    }
    return count;
  }
}
