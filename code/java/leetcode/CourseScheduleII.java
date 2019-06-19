import java.util.*;

public class CourseScheduleII {
  private Map<Integer, List<Integer>> graph;
  private BitSet visited;
  private Queue<Integer> toposorted;

  public static void main(String[] args) throws Exception {
    int[][] pre = {{1, 0}};
    int[] result = new CourseScheduleII().findOrder(2, pre);
    for (int i : result) System.out.print(i + " ");
    System.out.println();
  }

  public int[] findOrder(int numCourses, int[][] prerequisites) {
    int j = 0;
    int[] courses = new int[numCourses];
    int[] result = new int[numCourses];
    for (int i = 0; i < numCourses; i++) courses[i] = j++;
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
    int i = 0;
    while (!toposorted.isEmpty()) {
      int v = toposorted.poll();
      if (visited.get(v)) return new int[0];
      relax(v);
      result[i++] = v;
      courses[v] = -1;
    }
    // add the remaining courses
    for (int c : courses) if (c != -1) result[i++] = c;
    return result;
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
