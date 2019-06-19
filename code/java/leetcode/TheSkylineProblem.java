import java.util.*;

public class TheSkylineProblem {


  public static void main(String[] args) throws Exception {
    int[][] A = {
      {0, 30, 30}, {2, 9, 10}, {3, 7, 15}, {4, 8, 10}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}
    };
    // int[][] A = {{2,9,10}, {3,9,11}, {4,9,12}, {5,9,13}};
    List<int[]> result = new TheSkylineProblem().getSkyline(A);
    result.forEach(
        x -> {
          System.out.println(x[0] + " " + x[1]);
        });
  }

  public List<int[]> getSkyline(int[][] buildings) {
    PriorityQueue<Rectangle> pq =
        new PriorityQueue<>(
            Comparator.comparing(Rectangle::getH)
                .reversed()
                .thenComparing(
                    Rectangle
                        ::getX1)); // order by height, if height is same then, order by left most
                                   // starting edge.
    List<int[]> result = new ArrayList<>();
    Set<Integer> set = new HashSet<>();
    for (int[] p : buildings) {
      set.add(p[0]);
      set.add(p[1]);
    }
    List<Integer> points = new ArrayList<>();
    points.addAll(set);
    points.sort(Integer::compare);

    for (int i = 0, j = 0, l = points.size(); i < l; i++) {
      int curr = points.get(i);

      for (int k = j;
          k < buildings.length;
          k++) { // add all the rectangles that begin at this point
        int[] rectangle = buildings[k];
        if (rectangle[0] == curr) {
          pq.offer(new Rectangle(rectangle[0], rectangle[1], rectangle[2]));
        } else if (rectangle[0] > curr) {
          j = k;
          break;
        }
      }
      int max = Integer.MIN_VALUE;
      while (!pq.isEmpty()) { // remove all the rectangles that end at this point
        if (pq.peek().getX2() == curr) {
          Rectangle top = pq.poll();
          max = Math.max(max, top.getH());
        } else if (pq.peek().getX2() < curr) {
          pq.poll();
        } else {
          break;
        }
      }
      if (pq.isEmpty()) {
        result.add(
            makeNewPoint(
                curr,
                0)); // This is the last rectangle after this there is a gap of at least one unit
      } else {
        if (max > pq.peek().getH()) {
          result.add(
              makeNewPoint(
                  curr,
                  pq.peek()
                      .getH())); // one of the larger rectangle's right edge intersects with a
                                 // smaller one
        } else if (max < pq.peek().getH() && pq.peek().getX1() == curr) {
          result.add(
              makeNewPoint(curr, pq.peek().getH())); // new larger rectangle begins at this point
        }
      }
    }
    return result;
  }

  private int[] makeNewPoint(int x, int y) {
    int[] point = new int[2];
    point[0] = x;
    point[1] = y;
    return point;
  }

  class Rectangle {
    private int x1, x2, h;

    Rectangle(int x1, int x2, int h) {
      this.x1 = x1;
      this.x2 = x2;
      this.h = h;
    }

    public int getH() {
      return h;
    }

    public int getX2() {
      return x2;
    }

    public int getX1() {
      return x1;
    }
  }
}
