public class GlobalAndLocalInversions {

  public static void main(String[] args) throws Exception {}

  public boolean isIdealPermutation(int[] A) {
    if (A.length == 0 || A.length == 1) return true;
    int max = Integer.MIN_VALUE;
    for (int i = 1; i < A.length; i++) {
      if (A[i] < max) {
        return false;
      } else {
        max = Math.max(max, A[i - 1]);
      }
    }
    return true;
  }
}
