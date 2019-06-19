import java.util.Arrays;

public class NonOverlappingIntervals {

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

  public static void main(String[] args) throws Exception {
    Interval i1 = new Interval(1, 4);
    Interval i2 = new Interval(5, 9);
    Interval i3 = new Interval(3, 12);
    // Interval i4 = new Interval(1, 3);
    Interval[] intervals = {i1, i2, i3};
    System.out.println(new NonOverlappingIntervals().eraseOverlapIntervals(intervals));
  }

  public int eraseOverlapIntervals(Interval[] intervals) {
    if (intervals.length == 0) return 0;
    Arrays.sort(intervals, ((o1, o2) -> o1.end - o2.end));
    int count = 0;
    Interval prev = intervals[0];
    for (int i = 1; i < intervals.length; i++) {
      if (intervals[i].start < prev.end) {
        count++;
      } else {
        prev = intervals[i];
      }
    }
    return count;
  }
}
