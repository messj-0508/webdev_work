public class ScoreAfterFlippingMatrix {

  public static void main(String[] args) {
    int[][] A = {{1, 0}, {1, 0}, {1, 0}, {1, 1}};
    System.out.println(new ScoreAfterFlippingMatrix().matrixScore(A));
  }

  public int matrixScore(int[][] A) {
    for (int[] a : A) {
      int temp1 = makeNum(a);
      flip(a);
      int temp2 = makeNum(a);
      if (temp1 > temp2) {
        // revert
        flip(a);
      }
    }
    for (int i = 0; i < A[0].length; i++) {
      int count = 0;
      for (int j = 0; j < A.length; j++) {
        if (A[j][i] == 1) {
          count++;
        }
      }
      if (count < (A.length - count)) {
        for (int j = 0; j < A.length; j++) {
          if (A[j][i] == 0) {
            A[j][i] = 1;
          } else {
            A[j][i] = 0;
          }
        }
      }
    }
    int sum = 0;
    for (int[] a : A) {
      sum += makeNum(a);
    }
    return sum;
  }

  private int makeNum(int[] a) {
    int n = 0;
    for (int i = 0; i < a.length; i++) {
      if (a[i] == 1) {
        n |= (1 << (a.length - i - 1));
      }
    }
    return n;
  }

  private void flip(int[] A) {
    for (int i = 0; i < A.length; i++) {
      if (A[i] == 1) {
        A[i] = 0;
      } else {
        A[i] = 1;
      }
    }
  }
}
