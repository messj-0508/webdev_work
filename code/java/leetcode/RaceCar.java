import java.util.*;

public class RaceCar {

  public static void main(String[] args) {
    System.out.println(new RaceCar().racecar(1000));
  }

  private class Node {
    int v, s, d;

    Node(int v, int s, int d) {
      this.v = v;
      this.s = s;
      this.d = d;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Node)) return false;
      Node node = (Node) o;
      return v == node.v && s == node.s;
    }

    @Override
    public int hashCode() {
      return Objects.hash(v, s);
    }
  }

  public int racecar(int target) {
    if (target == 0) return 0;
    Queue<Node> queue = new ArrayDeque<>();
    Set<Node> done = new HashSet<>();
    Node start = new Node(0, 1, 0);
    done.add(start);
    queue.offer(start);
    while (!queue.isEmpty()) {
      Node curr = queue.poll();
      if (curr.v < (target * 2)) {
        Node c1 = new Node(curr.v + curr.s, curr.s * 2, curr.d + 1);
        if (c1.v >= 0) {
          if (!done.contains(c1)) {
            queue.offer(c1);
            done.add(c1);
            if (target == c1.v) {
              return c1.d;
            }
          }
        }
      }
      Node c2 = new Node(curr.v, curr.s < 0 ? 1 : -1, curr.d + 1);
      if (!done.contains(c2)) {
        done.add(c2);
        queue.offer(c2);
      }
    }
    return -1;
  }
}
