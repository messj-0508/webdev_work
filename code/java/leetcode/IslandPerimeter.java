public class IslandPerimeter {

  int[] R = {1, -1, 0, 0};
  int[] C = {0, 0, 1, -1};
  boolean[][] done;
  int perimeter;

  public static void main(String[] args) throws Exception {
    int[][] grid = {{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}};
    System.out.println(new IslandPerimeter().islandPerimeter(grid));
  }

  public int islandPerimeter(int[][] grid) {
    done = new boolean[grid.length][grid[0].length];
    perimeter = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == 1 && !done[i][j]) {
          dfs(i, j, grid);
          break;
        }
      }
    }

    return perimeter;
  }

  private void dfs(int r, int c, int[][] grid) {
    done[r][c] = true;
    for (int i = 0; i < 4; i++) {
      int newR = r + R[i];
      int newC = c + C[i];
      if (newR < 0 || newC < 0 || newR >= grid.length || newC >= grid[0].length) {
        perimeter++;
      } else if (grid[newR][newC] == 0) {
        perimeter++;
      } else {
        if (!done[newR][newC]) {
          dfs(newR, newC, grid);
        }
      }
    }
  }
}
