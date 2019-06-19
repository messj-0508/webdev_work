import java.util.ArrayList;
import java.util.List;

public class ExpressionAddOperators {

  public static void main(String[] args) throws Exception {
    List<String> result = new ExpressionAddOperators().addOperators("202010201", 201);
    result.stream().forEach(System.out::println);
  }

  public List<String> addOperators(String num, int target) {
    List<String> result = new ArrayList<>();
    backTrack("", result, 0, num, target, 0L, 0L);
    return result;
  }

  private void backTrack(
      String exp, List<String> list, int curr, String num, int target, long total, long prod) {
    if (curr == num.length()) {
      if (total == target) {
        list.add(exp);
      }
    } else {
      for (int i = curr, l = num.length(); i < l; i++) {
        String newNum = num.substring(curr, i + 1);
        if (newNum.length() > 1 && newNum.startsWith("0")) {
          break;
        }
        long newNumL = Long.parseLong(newNum);
        if (curr == 0) {
          backTrack(newNum, list, i + 1, num, target, newNumL, newNumL);
        } else {
          backTrack(exp + "+" + newNum, list, i + 1, num, target, total + newNumL, newNumL);

          backTrack(exp + "-" + newNum, list, i + 1, num, target, total - newNumL, newNumL * -1L);

          backTrack(
              exp + "*" + newNum,
              list,
              i + 1,
              num,
              target,
              (total - prod + (prod * newNumL)),
              prod * newNumL);
        }
      }
    }
  }
}
