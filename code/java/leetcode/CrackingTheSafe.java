import java.util.HashSet;
import java.util.Set;

public class CrackingTheSafe {


  public static void main(String[] args) {
    System.out.println(new CrackingTheSafe().crackSafe(4, 5));
  }

  public String crackSafe(int n, int k) {
    int states = getStates(n, k);
    int[] N = new int[k];
    for (int i = 0; i < k; i++) {
      N[i] = i;
    }
    return generate(N, n, 0, 0, "", k, states);
  }

  private int getStates(int n, int k) {
    if (n == 0) return 1;
    if (n == 1) return k;
    int result = 1;
    for (int i = 0; i < n; i++) {
      result *= k;
    }
    return result;
  }

  private String generate(int[] N, int n, int i, int count, String num, int k, int states) {
    if (count == n) {
      return dfs(num, new StringBuilder(num), new HashSet<>(), k, states, 1);
    } else {
      for (int j = i; j < N.length; j++) {
        String result = generate(N, n, j, count + 1, num + String.valueOf(N[j]), k, states);
        if (!result.isEmpty()) {
          return result;
        }
      }
    }
    return "";
  }

  private String dfs(
      String num, StringBuilder result, Set<String> done, int k, int states, int count) {
    done.add(num);
    if (states == count) {
      return result.toString();
    } else {
      for (int i = 0; i < k; i++) {
        String newNum = (num + String.valueOf(i));
        String newState = newNum.substring(1);
        if (!done.contains(newState)) {
          String retValue =
              dfs(newState, result.append(String.valueOf(i)), done, k, states, count + 1);
          if (!retValue.isEmpty()) {
            return retValue;
          } else {
            result.deleteCharAt(result.length() - 1);
          }
        }
      }
    }
    done.remove(num);
    return "";
  }
}
