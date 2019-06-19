import java.util.*;

public class Candy {

  public static void main(String[] args) throws Exception {
    int[] ratings = {29, 51, 87, 87, 72, 12};
    System.out.println(new Candy().candy(ratings));
  }

  public int candy(int[] ratings) {
    if (ratings.length == 1) return 1;
    PriorityQueue<Integer> pq =
        new PriorityQueue<>((o1, o2) -> Integer.compare(ratings[o1], ratings[o2]));
    for (int i = 0; i < ratings.length; i++) {
      pq.offer(i);
    }
    int[] count = new int[ratings.length];
    while (!pq.isEmpty()) {
      int index = pq.poll();
      if (index - 1 < 0) {
        if (ratings[index + 1] == ratings[index]) {
          count[index] = 1;
        } else {
          count[index] = count[index + 1] + 1;
        }
      } else if (index + 1 >= ratings.length) {
        if (ratings[index - 1] == ratings[index]) {
          count[index] = 1;
        } else {
          count[index] = count[index - 1] + 1;
        }
      } else {
        if ((ratings[index - 1] == ratings[index]) && (ratings[index + 1] == ratings[index])) {
          count[index] = 1;
        } else {
          if (((ratings[index - 1] == ratings[index]) && (ratings[index + 1] > ratings[index]))
              || ((ratings[index + 1] == ratings[index])
                  && (ratings[index - 1] > ratings[index]))) {
            count[index] = 1;
          } else if (((ratings[index - 1] == ratings[index])
              && (ratings[index + 1] < ratings[index]))) {
            count[index] = count[index + 1] + 1;
          } else if (((ratings[index + 1] == ratings[index])
              && (ratings[index - 1] < ratings[index]))) {
            count[index] = count[index - 1] + 1;
          } else {
            if (count[index - 1] > count[index + 1]) {
              count[index] = count[index - 1] + 1;
            } else {
              count[index] = count[index + 1] + 1;
            }
          }
        }
      }
    }
    int result = 0;
    for (int c : count) {
      result += c;
    }
    return result;
  }
}
