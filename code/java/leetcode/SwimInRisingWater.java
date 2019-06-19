import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SwimInRisingWater {

  private final int[] R = {0, 0, 1, -1};
  private final int[] C = {1, -1, 0, 0};

  class Pair {
    int r, c;

    Pair(int r, int c) {
      this.r = r;
      this.c = c;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Pair)) return false;
      Pair pair = (Pair) o;
      return r == pair.r && c == pair.c;
    }

    @Override
    public int hashCode() {
      return Objects.hash(r, c);
    }
  }

  public static void main(String[] args) {
    int[][] grid = {
      {0, 1, 2, 3, 4},
      {24, 23, 22, 21, 5},
      {12, 13, 14, 15, 16},
      {11, 17, 18, 19, 20},
      {10, 9, 8, 7, 6}
    };
    System.out.println(new SwimInRisingWater().swimInWater(grid));
  }

  public int swimInWater(int[][] grid) {
    int l = 0, h = (grid.length * grid.length);
    int ans = 0;
    while (l <= h) {
      int m = l + (h - l) / 2;
      Set<Pair> done = new HashSet<>();
      if (dfs(grid, 0, 0, done, m)) {
        ans = m;
        h = m - 1;
      } else {
        l = m + 1;
      }
    }
    return ans;
  }

  private boolean dfs(int[][] grid, int r, int c, Set<Pair> done, int V) {
    if (r == grid.length - 1 && c == grid[0].length - 1) return true;
    done.add(new Pair(r, c));
    for (int i = 0; i < 4; i++) {
      int newR = r + R[i];
      int newC = c + C[i];
      if (newR >= 0 && newR < grid.length && newC >= 0 && newC < grid[0].length) {
        int childH = Math.max(V, grid[newR][newC]);
        int curH = Math.max(V, grid[r][c]);
        if (curH == childH) {
          Pair child = new Pair(newR, newC);
          if (!done.contains(child)) {
            if (dfs(grid, newR, newC, done, V)) return true;
          }
        }
      }
    }
    return false;
  }
}
