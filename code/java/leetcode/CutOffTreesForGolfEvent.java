import java.util.*;

public class CutOffTreesForGolfEvent {

  public static void main(String[] args) throws Exception {}

  private static final int[] R = {0, 0, 1, -1};
  private static final int[] C = {1, -1, 0, 0};

  static class Cell implements Comparable<Cell> {
    int r, c;
    int distance;
    int height;

    Cell(int r, int c) {
      this.r = r;
      this.c = c;
    }

    @Override
    public int compareTo(Cell o) {
      return Integer.compare(this.height, o.height);
    }
  }

  public int cutOffTree(List<List<Integer>> forest) {
    int distance = 0;
    List<Cell> trees = new ArrayList<>();
    for (int i = 0; i < forest.size(); i++) {
      for (int j = 0; j < forest.get(0).size(); j++) {
        if (forest.get(i).get(j) > 1) {
          Cell cell = new Cell(i, j);
          cell.height = forest.get(i).get(j);
          trees.add(cell);
        }
      }
    }
    Collections.sort(trees);
    int sR = 0, sC = 0;
    for (Cell t : trees) {
      int dist = bfs(forest, t.height, sR, sC);
      if (dist == -1) return -1;
      else distance += dist;
      sR = t.r;
      sC = t.c;
    }
    return distance;
  }

  private int bfs(List<List<Integer>> forest, int target, int sR, int sC) {
    if (forest.get(sR).get(sC) == target) {
      forest.get(sR).set(sC, 1);
      return 0;
    }
    Cell start = new Cell(sR, sC);
    start.distance = 0;
    Queue<Cell> queue = new ArrayDeque<>();
    queue.add(start);
    boolean[][] done = new boolean[forest.size()][forest.get(0).size()];
    done[sR][sC] = true;
    while (!queue.isEmpty()) {
      Cell cell = queue.poll();
      for (int i = 0; i < 4; i++) {
        int newR = cell.r + R[i];
        int newC = cell.c + C[i];
        Cell newCell = new Cell(newR, newC);
        if (newR >= 0
            && newR < forest.size()
            && newC >= 0
            && newC < forest.get(0).size()
            && forest.get(newR).get(newC) != 0
            && !done[newCell.r][newCell.c]) {
          newCell.distance = cell.distance + 1;
          if (forest.get(newR).get(newC) == target) {
            forest.get(newR).set(newC, 1);
            return newCell.distance;
          }
          done[newCell.r][newCell.c] = true;
          queue.offer(newCell);
        }
      }
    }
    return -1;
  }
}
