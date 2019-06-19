import java.util.*;

public class AllPathsFromSourceToTarget {
  /**
   * Main method
   *
   * @param args
   */
  public static void main(String[] args) {
    int[][] graph = {{1, 2}, {3}, {3}, {}};
    System.out.println(new AllPathsFromSourceToTarget().allPathsSourceTarget(graph));
  }

  public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
    Set<Integer> done = new HashSet<>();
    Stack<Integer> stack = new Stack<>();
    List<List<Integer>> result = new ArrayList<>();
    dfs(result, done, 0, stack, graph);
    return result;
  }

  private void dfs(
      List<List<Integer>> result, Set<Integer> done, int i, Stack<Integer> stack, int[][] graph) {
    done.add(i);
    stack.push(i);
    int[] children = graph[i];
    if (children.length == 0) {
      List<Integer> childList = new ArrayList<>(stack);
      result.add(childList);
    } else {
      for (int c : children) {
        dfs(result, done, c, stack, graph);
      }
    }
    stack.pop();
    done.remove(i);
  }
}
