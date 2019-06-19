import java.util.*;

public class LongestConsecutiveSequence {

  private Map<Integer, Set<Integer>> graph;
  private Set<Integer> done;

  public static void main(String[] args) throws Exception {
    int[] nums = {-1, 0, -3, -2, 1, 2, 3, 4, 5, 4};
    System.out.println(new LongestConsecutiveSequence().longestConsecutive(nums));
  }

  public int longestConsecutive(int[] nums) {
    done = new HashSet<>();
    graph = new HashMap<>();
    for (int u : nums) {
      graph.putIfAbsent(u, new HashSet<>());
      if (graph.keySet().contains(u - 1)) {
        graph.get(u - 1).add(u);
        graph.get(u).add(u - 1);
      }
      if (graph.keySet().contains(u + 1)) {
        graph.get(u + 1).add(u);
        graph.get(u).add(u + 1);
      }
    }
    int max = 0;
    for (int i : graph.keySet()) {
      if (!done.contains(i)) {
        Stack<Integer> stack = new Stack<>();
        stack.add(i);
        max = Math.max(max, dfs(0, stack));
      }
    }
    return max;
  }

  private int dfs(int count, Stack<Integer> stack) {
    while (!stack.isEmpty()) {
      int top = stack.pop();
      count++;
      done.add(top);

      Set<Integer> children = graph.get(top);
      if (children != null) {
        children.stream().filter(c -> !done.contains(c)).forEach(stack::push);
      }
    }
    return count;
  }
}
