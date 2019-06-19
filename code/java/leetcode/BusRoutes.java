import java.util.*;

public class BusRoutes {

  private class Node {
    int v, dist;

    Node(int v, int dist) {
      this.v = v;
      this.dist = dist;
    }
  }

  private Map<Integer, Set<Integer>> routeGraph = new HashMap<>();
  private Map<Integer, List<Integer>> stationRouteMap = new HashMap<>();
  private BitSet done = new BitSet();
  /**
   * Main method
   *
   * @param args
   */
  public static void main(String[] args) {
    int[][] R = {
      {1, 2, 3, 9}, {9, 3, 4, 5, 8}, {5, 6, 7, 8}, {9, 8, 10, 11}, {12, 13, 14, 6, 1, 2, 3, 5, 7}
    };
    System.out.println(new BusRoutes().numBusesToDestination(R, 1, 14));
  }

  public int numBusesToDestination(int[][] routes, int S, int T) {
    if (S == T) return 0;
    for (int i = 0; i < routes.length; i++) {
      Arrays.sort(routes[i]);
      int[] n = routes[i];
      for (int j : n) {
        if (j == S || j == T) {
          stationRouteMap.putIfAbsent(j, new ArrayList<>());
          stationRouteMap.get(j).add(i);
        }
      }
    }
    for (int i = 0; i < routes.length; i++) {
      int[] A = routes[i];
      for (int j = i + 1; j < routes.length; j++) {
        int[] B = routes[j];
        if (intersect(A, B)) {
          routeGraph.putIfAbsent(i, new HashSet<>());
          routeGraph.putIfAbsent(j, new HashSet<>());
          routeGraph.get(i).add(j);
          routeGraph.get(j).add(i);
        }
      }
    }
    List<Integer> start = stationRouteMap.get(S);
    if (!stationRouteMap.containsKey(T)) return -1;
    Set<Integer> destination = new HashSet<>(stationRouteMap.get(T));
    Queue<Node> queue = new ArrayDeque<>();
    for (int r : start) {
      if (destination.contains(r)) return 1;
      done.set(r);
      queue.offer(new Node(r, 0));
    }
    while (!queue.isEmpty()) {
      Node curr = queue.poll();
      Set<Integer> children = routeGraph.get(curr.v);
      if (children != null) {
        for (int c : children) {
          if (!done.get(c)) {
            done.set(c);
            Node child = new Node(c, curr.dist + 1);
            if (destination.contains(child.v)) {
              return child.dist + 1;
            } else {
              queue.offer(child);
            }
          }
        }
      }
    }
    return -1;
  }

  private boolean intersect(int[] A, int[] B) {
    for (int i = 0, j = 0; i < A.length && j < B.length; ) {
      if (A[i] == B[j]) return true;
      else if (A[i] < B[j]) i++;
      else j++;
    }
    return false;
  }
}
