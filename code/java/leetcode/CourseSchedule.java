import java.util.*;

public class CourseSchedule {
  private Map<Integer, List<Integer>> graph;
  private BitSet visited;
  private Queue<Integer> toposorted;

  public static void main(String[] args) throws Exception {
    int[][] pre = {{1, 0}};
    System.out.println(new CourseSchedule().canFinish(2, pre));
  }

  public boolean canFinish(int numCourses, int[][] prerequisites) {
    graph = new HashMap<>();
    visited = new BitSet();
    toposorted = new ArrayDeque<>();
    // build graph
    for (int[] children : prerequisites) {
      graph.putIfAbsent(children[0], new ArrayList<>());
      graph.get(children[0]).add(children[1]);
    }
    graph.keySet().stream().filter(v -> !visited.get(v)).forEach(this::dfs);

    visited.clear();

    while (!toposorted.isEmpty()) {
      int v = toposorted.poll();
      if (visited.get(v)) return false;
      relax(v);
    }
    return true;
  }

  private void relax(int v) {
    visited.set(v);
    List<Integer> children = graph.get(v);
    if (children != null) {
      for (int c : children) visited.set(c);
    }
  }

  private void dfs(int v) {
    visited.set(v);
    List<Integer> children = graph.get(v);
    if (children != null) {
      for (int c : children) if (!visited.get(c)) dfs(c);
    }
    toposorted.offer(v);
  }
}
