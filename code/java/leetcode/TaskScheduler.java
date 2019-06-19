import java.util.*;

public class TaskScheduler {

  class Task {
    char t;
    int count;

    Task(char t, int count) {
      this.t = t;
      this.count = count;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }
  }

  public static void main(String[] args) {
    char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
    System.out.println(new TaskScheduler().leastInterval(tasks, 2));
  }

  public int leastInterval(char[] tasks, int n) {
    PriorityQueue<Task> queue =
        new PriorityQueue<>(Comparator.comparing(Task::getCount).reversed());
    List<Task> waiting = new ArrayList<>();
    Map<Character, Integer> map = new HashMap<>();
    for (char c : tasks) {
      if (map.get(c) == null) {
        map.put(c, 1);
      } else {
        int v = map.get(c) + 1;
        map.put(c, v);
      }
    }
    for (char c : map.keySet()) {
      Task task = new Task(c, map.get(c));
      queue.offer(task);
    }
    int count = 0;
    while (!queue.isEmpty()) {
      int i = 0;
      while (i <= n) {
        if (!queue.isEmpty()) {
          Task task = queue.poll();
          task.count--;
          if (task.count > 0) {
            waiting.add(task);
          }
        }
        count++;
        if (queue.isEmpty() && waiting.isEmpty()) break;
        i++;
      }
      queue.addAll(waiting);
      waiting.clear();
    }
    return count;
  }
}
