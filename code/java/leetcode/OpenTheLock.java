import java.util.*;

public class OpenTheLock {

  class State {
    String state;
    int dist;

    State(String state, int dist) {
      this.state = state;
      this.dist = dist;
    }
  }

  private Set<String> done;

  public static void main(String[] args) throws Exception {
    String[] A = {"0201", "0101", "0102", "1212", "2002"};
    System.out.println(new OpenTheLock().openLock(A, "0202"));
  }

  public int openLock(String[] deadends, String target) {
    done = new HashSet<>();
    Arrays.stream(deadends).forEach(e -> done.add(e));
    if (done.contains("0000")) return -1;
    if (target.equals("0000")) return 0;
    Queue<State> queue = new ArrayDeque<>();
    queue.offer(new State("0000", 0));
    done.add("0000");
    while (!queue.isEmpty()) {
      State state = queue.poll();
      if (state.state.equals(target)) return state.dist;
      String currState = state.state;
      for (int i = 0; i < 4; i++) {
        char c = currState.charAt(i);
        int cV = Integer.parseInt(String.valueOf(c));
        String prefix = currState.substring(0, i);
        String postFix = currState.substring(i + 1, 4);
        String newChild1 = prefix + (((cV + 1) > 9) ? 0 : (cV + 1)) + postFix;
        if (!done.contains(newChild1)) {
          queue.offer(new State(newChild1, state.dist + 1));
          done.add(newChild1);
        }
        String newChild2 = prefix + (((cV - 1) < 0) ? 9 : (cV - 1)) + postFix;
        if (!done.contains(newChild2)) {
          queue.offer(new State(newChild2, state.dist + 1));
          done.add(newChild2);
        }
      }
    }
    return -1;
  }
}
