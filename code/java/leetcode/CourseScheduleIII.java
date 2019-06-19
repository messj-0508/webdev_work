import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class CourseScheduleIII {
  public static void main(String[] args) throws Exception {
    int[][] course = {{5, 5}, {2, 6}, {4, 6}};
    System.out.println(new CourseScheduleIII().scheduleCourse(course));
  }

  public int scheduleCourse(int[][] courses) {
    Arrays.sort(courses, (a, b) -> a[1] - b[1]);
    Queue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
    int time = 0;
    for (int[] course : courses) {
      time += course[0];
      pq.add(course[0]);
      if (time > course[1]) time -= pq.poll();
    }
    return pq.size();
  }
}
