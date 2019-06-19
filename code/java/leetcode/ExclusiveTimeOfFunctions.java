import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class ExclusiveTimeOfFunctions {

  class Log {
    int funId, time;
    String fun;

    Log(int funId, String fun, int time) {
      this.funId = funId;
      this.fun = fun;
      this.time = time;
    }
  }


  public static void main(String[] args) throws Exception {
    int[] N =
        new ExclusiveTimeOfFunctions()
            .exclusiveTime(2, Arrays.asList("0:start:0", "1:start:2", "1:end:5", "0:end:6"));
    Arrays.stream(N).forEach(System.out::println);
  }

  public int[] exclusiveTime(int n, List<String> logs) {
    int[] N = new int[n];
    List<Log> functions = new ArrayList<>();
    for (String s : logs) {
      String[] parts = s.split(":");
      functions.add(new Log(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2])));
    }
    Stack<Log> stack = new Stack<>();
    stack.push(functions.get(0));
    for (int i = 1, l = functions.size(); i < l; i++) {
      Log next = functions.get(i);
      if (stack.isEmpty()) {
        stack.push(next);
        continue;
      }
      Log curr = stack.peek();
      if (next.fun.equals("end")) {
        N[curr.funId] += (next.time - curr.time + 1);
        stack.pop(); // since the end has reached, remove from stack
        if (!stack.isEmpty()) {
          stack.peek().time =
              next.time + 1; // IMPORTANT: update the time of the old function to a new start
          // time
        }
      } else {
        stack.push(next);
        N[curr.funId] += (next.time - curr.time);
      }
    }
    return N;
  }
}
