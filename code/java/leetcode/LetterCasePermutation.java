import java.util.ArrayList;
import java.util.List;

public class LetterCasePermutation {

  public static void main(String[] args) throws Exception {
    System.out.println(new LetterCasePermutation().letterCasePermutation("a1b2"));
  }

  public List<String> letterCasePermutation(String S) {
    List<String> result = new ArrayList<>();
    backtrack(S, result, 0, "");
    return result;
  }

  private void backtrack(String s, List<String> result, int i, String r) {
    if (i == s.length()) {
      result.add(r);
    } else {
      if (Character.isAlphabetic(s.charAt(i))) {
        backtrack(s, result, i + 1, r + s.charAt(i));
        if (Character.isLowerCase(s.charAt(i))) {
          backtrack(s, result, i + 1, r + Character.toUpperCase(s.charAt(i)));
        } else {
          backtrack(s, result, i + 1, r + Character.toLowerCase(s.charAt(i)));
        }
      } else {
        backtrack(s, result, i + 1, r + s.charAt(i));
      }
    }
  }
}
