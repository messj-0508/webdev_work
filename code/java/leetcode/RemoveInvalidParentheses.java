import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveInvalidParentheses {

  private Set<String> done;
  private int maxLen = Integer.MIN_VALUE;
  private int minDiff = Integer.MAX_VALUE;
  /**
   * Main method
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    List<String> result = new RemoveInvalidParentheses().removeInvalidParentheses("())())");
    result.forEach(System.out::println);
  }

  public List<String> removeInvalidParentheses(String s) {
    done = new HashSet<>();
    List<String> result = new ArrayList<>();
    backTrack(s, 0, 0, result, "", 0, 0);
    return result;
  }

  private void backTrack(
      String s, int i, int count, List<String> result, String state, int selected, int total) {
    if (i >= s.length()) {
      if (count == 0) {
        if (selected >= maxLen) {
          result.add(state);
          maxLen = selected;
          minDiff = total - selected;
        }
      }
    } else {
      done.add(state);
      char c = s.charAt(i);
      if (c == '(') {
        if (!done.contains(state + "(")) {
          backTrack(s, i + 1, count + 1, result, state + "(", selected + 1, total + 1);
        }
        if ((total - selected + 1) <= minDiff) {
          backTrack(s, i + 1, count, result, state, selected, total + 1);
        }
      } else if (c == ')') {
        if (count - 1 < 0) {
          if ((total - selected + 1) <= minDiff) {
            backTrack(s, i + 1, count, result, state, selected, total + 1);
          }
        } else {
          if (!done.contains(state + ")")) {
            backTrack(s, i + 1, count - 1, result, state + ")", selected + 1, total + 1);
          }
          if ((total - selected + 1) <= minDiff) {
            backTrack(s, i + 1, count, result, state, selected, total + 1);
          }
        }
      } else {
        backTrack(s, i + 1, count, result, state + c, selected + 1, total + 1);
      }
    }
  }
}
