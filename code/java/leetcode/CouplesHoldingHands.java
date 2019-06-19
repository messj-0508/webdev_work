public class CouplesHoldingHands {

  public static void main(String[] args) throws Exception {
    int[] A = {1, 3, 4, 0, 2, 5};
    System.out.println(new CouplesHoldingHands().minSwapsCouples(A));
  }

  public int minSwapsCouples(int[] row) {
    int N = row.length;
    int count = 0;
    for (int i = 0; i < N; i += 2) {
      int pos = find(row, i);
      if ((pos % 2) == 0) {
        if (row[pos + 1] != i + 1) {
          int nexNumPos = find(row, i + 1);
          swap(row, pos + 1, nexNumPos);
          count++;
        }
      } else {
        if (row[pos - 1] != i + 1) {
          int nexNumPos = find(row, i + 1);
          swap(row, pos - 1, nexNumPos);
          count++;
        }
      }
    }
    return count;
  }

  private int find(int[] A, int n) {
    for (int i = 0; i < A.length; i++) {
      if (A[i] == n) {
        return i;
      }
    }
    return -1;
  }

  private void swap(int[] A, int i, int j) {
    int temp = A[i];
    A[i] = A[j];
    A[j] = temp;
  }
}
