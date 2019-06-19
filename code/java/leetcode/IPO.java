import java.util.*;

public class IPO {

  public static void main(String[] args) {
    int[] P = {1, 2, 3};
    int[] C = {1, 1, 2};
    System.out.println(new IPO().findMaximizedCapital(1, 0, P, C));
  }

  class Pair {
    int p, c;

    Pair(int p, int c) {
      this.p = p;
      this.c = c;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Pair)) return false;
      Pair pair = (Pair) o;
      return p == pair.p && c == pair.c;
    }

    public int getP() {
      return p;
    }

    public int getC() {
      return c;
    }

    @Override
    public int hashCode() {
      return Objects.hash(p, c);
    }
  }

  public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
    PriorityQueue<Pair> profits =
        new PriorityQueue<>(Comparator.comparing(Pair::getP).reversed().thenComparing(Pair::getC));
    PriorityQueue<Pair> capitals = new PriorityQueue<>(Comparator.comparing(Pair::getC));
    for (int i = 0; i < Profits.length; i++) {
      capitals.offer(new Pair(Profits[i], Capital[i]));
    }
    while (true) {
      while (!capitals.isEmpty() && capitals.peek().getC() <= W) {
        profits.offer(capitals.poll());
      }
      if (!profits.isEmpty() && profits.peek().getC() <= W && k > 0) {
        W += profits.poll().getP();
        k--;
      }
      if (capitals.isEmpty() || capitals.peek().getC() > W || k == 0) break;
    }
    while (k > 0 && !profits.isEmpty()) {
      W += profits.poll().getP();
      k--;
    }
    return W;
  }
}
