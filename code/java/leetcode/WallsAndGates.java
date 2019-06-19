import java.util.ArrayDeque;
import java.util.Queue;

public class WallsAndGates {

  private static final int[] R = {0, 0, 1, -1};
  private static final int[] C = {1, -1, 0, 0};

  private class Cell {
    int r, c;

    Cell(int r, int c) {
      this.r = r;
      this.c = c;
    }
  }

  public static void main(String[] args) throws Exception {
    int[][] A = {
      {Integer.MAX_VALUE, -1, 0, Integer.MAX_VALUE},
      {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, -1},
      {Integer.MAX_VALUE, -1, Integer.MAX_VALUE, -1},
      {0, -1, Integer.MAX_VALUE, Integer.MAX_VALUE}
    };
    new WallsAndGates().wallsAndGates(A);
  }

  public void wallsAndGates(int[][] rooms) {
    Queue<Cell> queue = new ArrayDeque<>();
    for (int i = 0; i < rooms.length; i++) {
      for (int j = 0; j < rooms[0].length; j++) {
        if (rooms[i][j] == 0) { // treat each co-ordinates of gate as a source
          Cell cell = new Cell(i, j);
          queue.offer(cell);
        }
      }
    }
    while (!queue.isEmpty()) {
      Cell top = queue.poll();
      for (int i = 0; i < 4; i++) {
        int newR = top.r + R[i];
        int newC = top.c + C[i];
        if (newR >= 0 && newC >= 0 && newR < rooms.length && newC < rooms[0].length) {
          if (rooms[newR][newC] == Integer.MAX_VALUE) {
            rooms[newR][newC] = rooms[top.r][top.c] + 1;
            queue.offer(new Cell(newR, newC));
          }
        }
      }
    }
  }
}
