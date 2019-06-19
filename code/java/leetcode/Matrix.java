import java.util.*;

public class Matrix {
  private static class Node {
    int r, c;
    int d;

    Node(int r, int c) {
      this.r = r;
      this.c = c;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Node)) return false;
      Node node = (Node) o;
      return r == node.r && c == node.c;
    }

    @Override
    public int hashCode() {
      return Objects.hash(r, c);
    }
  }

  private final int[] R = {0, 0, 1, -1};
  private final int[] C = {1, -1, 0, 0};
  private Set<Node> done;

  public static void main(String[] args) {
    int[][] temp = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
    int[][] result = new Matrix().updateMatrix(temp);
    System.out.println();
  }

  public int[][] updateMatrix(int[][] matrix) {
    int[][] temp = new int[matrix.length][matrix[0].length];
    done = new HashSet<>();
    Queue<Node> queue = new ArrayDeque<>();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        temp[i][j] = matrix[i][j];
        if (matrix[i][j] == 0) {
          Node node = new Node(i, j);
          queue.offer(node);
          done.add(node);
        }
      }
    }
    while (!queue.isEmpty()) {
      Node curr = queue.poll();
      for (int i = 0; i < 4; i++) {
        int newR = curr.r + R[i];
        int newC = curr.c + C[i];
        if (newR >= 0 && newR < matrix.length && newC >= 0 && newC < matrix[0].length) {
          Node child = new Node(newR, newC);
          if (!done.contains(child)) {
            done.add(child);
            child.d = curr.d + 1;
            temp[newR][newC] = child.d;
            queue.offer(child);
          }
        }
      }
    }
    return temp;
  }
}
