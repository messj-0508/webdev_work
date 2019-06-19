
import java.util.*;

public class GroupsOfSpecialEquivalentStrings {

  public static void main(String[] args) {}

  public int numSpecialEquivGroups(String[] A) {
    Set<String> set = new HashSet<>();
    for (String s : A) {
      StringBuilder temp1 = new StringBuilder();
      for (int i = 0, l = s.length(); i < l; i += 2) {
        char c = s.charAt(i);
        temp1.append(c);
      }
      StringBuilder temp2 = new StringBuilder();
      if (s.length() > 1) {
        for (int i = 1, l = s.length(); i < l; i += 2) {
          char c = s.charAt(i);
          temp2.append(c);
        }
      }
      char[] temp1Chars = temp1.toString().toCharArray();
      char[] temp2Chars = temp2.toString().toCharArray();
      Arrays.sort(temp1Chars);
      Arrays.sort(temp2Chars);
      set.add(String.valueOf(temp1Chars) + "+" + String.valueOf(temp2Chars));
    }
    return set.size();
  }
}
