import java.util.Comparator;
import java.util.PriorityQueue;

public class SmallestRotationWithHighestScore {

  private class Node {
    int i, n, r, v;

    Node(int i, int n, int r, int v) {
      this.i = i;
      this.n = n;
      this.r = r;
      this.v = v;
    }
  }

  public static void main(String[] args) {
    int[] A = {2, 3, 1, 4, 0};
    System.out.println(new SmallestRotationWithHighestScore().bestRotation(A));
  }

  public int bestRotation(int[] A) {
    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.r));
    int curr = 0;
    for (int i = 0; i < A.length; i++) {
      int num = A[i];
      int v = 0, r = Integer.MAX_VALUE;
      if (num <= i) {
        v = 1;
        curr++;
      }
      if (num != 0) {
        r = v == 0 ? i + 1 : (i - num + 1);
      }
      pq.offer(new Node(i, num, r, v));
    }
    int R = 0, max = curr, ans = 0;
    while (R < A.length) {
      while (pq.peek().r - R == 0) {
        Node top = pq.poll();
        top.v = (top.v + 1) % 2;
        top.i = (top.i - R < 0) ? (A.length + (top.i - R)) : (top.i - R);
        top.r = top.v == 0 ? top.i + 1 : (top.i - top.n + 1);
        top.r += R;
        curr = (top.v == 0) ? curr - 1 : curr + 1;
        pq.offer(top);
      }
      if (curr > max) {
        ans = R;
        max = curr;
      }
      R++;
    }
    return ans;
  }
}
