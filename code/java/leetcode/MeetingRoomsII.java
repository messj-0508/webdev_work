import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRoomsII {

  public static class Interval {
    int start;
    int end;

    Interval() {
      start = 0;
      end = 0;
    }

    Interval(int s, int e) {
      start = s;
      end = e;
    }
  }

  public static void main(String[] args) {
    Interval i1 = new Interval(0, 40);
    Interval i2 = new Interval(2, 10);
    Interval i3 = new Interval(10, 40);
    Interval i4 = new Interval(15, 20);
    Interval i5 = new Interval(20, 30);
    Interval i6 = new Interval(20, 40);
    Interval i7 = new Interval(1, 5);
    Interval[] intervals = {i1, i2, i3, i4, i5, i6, i7};
    System.out.println(minMeetingRooms(intervals));
  }

  public static int minMeetingRooms(Interval[] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a.start, b.start));
    PriorityQueue<Interval> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.end, b.end));
    int max = 0;
    for (Interval i : intervals) {
      while (!queue.isEmpty() && queue.peek().end <= i.start) {
        queue.poll();
      }
      queue.offer(i);
      max = Math.max(max, queue.size());
    }
    return max;
  }
}
