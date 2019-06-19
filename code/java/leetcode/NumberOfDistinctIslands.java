import java.util.HashSet;
import java.util.Set;


public class NumberOfDistinctIslands {
  private int[] R = {0, 1, 0, -1};
  private int[] C = {1, 0, -1, 0};
  private boolean[][] done;
  private Set<String> islands;

  public static void main(String[] args) throws Exception {
    int[][] N = {{1, 1, 1, 1}, {1, 0, 1, 0}, {0, 0, 0, 0}, {0, 1, 1, 1}, {1, 1, 0, 1}};
    System.out.println(new NumberOfDistinctIslands().numDistinctIslands(N));
  }

  public int numDistinctIslands(int[][] grid) {
    done = new boolean[grid.length][grid[0].length];
    islands = new HashSet<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (!done[i][j] && grid[i][j] == 1) {
          StringBuilder sb = new StringBuilder();
          dfs(i, j, grid, sb);
          islands.add(sb.toString());
        }
      }
    }
    return islands.size();
  }

  private void dfs(int r, int c, int[][] grid, StringBuilder sb) {
    done[r][c] = true;
    for (int i = 0; i < 4; i++) {
      int newR = r + R[i];
      int newC = c + C[i];
      if (newR >= 0 && newC >= 0 && newR < grid.length && newC < grid[0].length) {
        if (!done[newR][newC] && grid[newR][newC] == 1) {
          if (i == 0) {
            sb.append("R");
          } else if (i == 1) {
            sb.append("D");
          } else if (i == 2) {
            sb.append("L");
          } else {
            sb.append("U");
          }
          dfs(newR, newC, grid, sb);
        }
      }
    }
    sb.append("B");
  }
}
