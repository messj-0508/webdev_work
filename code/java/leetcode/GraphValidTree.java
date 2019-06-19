import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class GraphValidTree {

  private List[] graph;
  private BitSet done;
  /**
   * Main method
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    int[][] graph = {{1, 0}};
    System.out.println(new GraphValidTree().validTree(2, graph));
  }

  public boolean validTree(int n, int[][] edges) {
    graph = new List[n];
    done = new BitSet();
    for (int i = 0; i < n; i++) {
      graph[i] = new ArrayList<>();
    }
    for (int i = 0; i < edges.length; i++) {
      int u = edges[i][0];
      int v = edges[i][1];
      graph[u].add(v);
      graph[v].add(u);
    }

    int count = 0;
    for (int i = 0; i < n; i++) {
      if (!done.get(i)) {
        if (!dfs(graph, 0, -1)) {
          return false;
        }
        count++; // count number of connected components
      }
    }
    return count <= 1;
  }

  private boolean dfs(List[] graph, int u, int p) {
    done.set(u);
    List<Integer> children = graph[u];
    if (children != null) {
      for (int c : children) {
        if (p != c) { // should not be equal to parent
          if (!done.get(c)) {
            if (!dfs(graph, c, u)) {
              return false;
            }
          } else {
            return false;
          }
        }
      }
    }
    return true;
  }
}
