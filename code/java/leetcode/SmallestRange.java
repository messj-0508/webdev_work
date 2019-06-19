import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class SmallestRange {

  class MinIndex {
    int i, j;

    MinIndex(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }

  public static void main(String[] args) throws Exception {
    List<List<Integer>> list = new ArrayList<>();
    List<Integer> row1 = Arrays.asList(4, 10, 15, 24, 26);
    List<Integer> row2 = Arrays.asList(0, 9, 12, 20);
    List<Integer> row3 = Arrays.asList(5, 18, 22, 30);
    list.add(row1);
    list.add(row2);
    list.add(row3);
    int[] R = new SmallestRange().smallestRange(list);
    System.out.println(R[0] + " " + R[1]);
  }

  public int[] smallestRange(List<List<Integer>> nums) {
    PriorityQueue<MinIndex> pq =
        new PriorityQueue<>(
            (o1, o2) -> Integer.compare(nums.get(o1.i).get(o1.j), nums.get(o2.i).get(o2.j)));
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    for (int i = 0, l = nums.size(); i < l; i++) {
      min = Math.min(min, nums.get(i).get(0));
      max = Math.max(max, nums.get(i).get(0));
      pq.offer(new MinIndex(i, 0));
    }
    if (min == max) return new int[] {min, max};
    int ansMin = min, ansMax = max;
    while (true) {
      MinIndex minIndex = pq.poll();
      if (minIndex.j + 1 >= nums.get(minIndex.i).size()) {
        return new int[] {ansMin, ansMax};
      }
      int next = nums.get(minIndex.i).get(minIndex.j + 1);
      max = Math.max(max, next); // update max if any
      pq.offer(new MinIndex(minIndex.i, minIndex.j + 1));
      min = nums.get(pq.peek().i).get(pq.peek().j); // new minimum
      if ((max - min) < (ansMax - ansMin)) {
        ansMax = max;
        ansMin = min;
      }
    }
  }
}
