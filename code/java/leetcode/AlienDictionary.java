import java.util.*;


public class AlienDictionary {

  private Map<Character, List<Character>> graph;
  private Set<Character> done;
  private Set<Character> visited;
  private Stack<Character> toposort;

  /**
   * Main method
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    String[] words = {"z", "x", "z"};
    System.out.println(new AlienDictionary().alienOrder(words));
  }

  public String alienOrder(String[] words) {
    graph = new HashMap<>();
    done = new HashSet<>();
    visited = new HashSet<>();
    toposort = new Stack<>();
    boolean[] A = new boolean[26];
    for (int i = 0; i < words.length - 1; i++) {
      for (int j = 0, l = Math.min(words[i].length(), words[i + 1].length()); j < l; j++) {
        if (words[i].charAt(j) != words[i + 1].charAt(j)) {
          graph.putIfAbsent(words[i].charAt(j), new ArrayList<>());
          graph.get(words[i].charAt(j)).add(words[i + 1].charAt(j));
          break;
        }
      }
    }

    for (String w : words) {
      for (int i = 0, l = w.length(); i < l; i++) {
        A[w.charAt(i) - 'a'] = true;
      }
    }

    for (char c : graph.keySet()) {
      if (!done.contains(c)) {
        if (!dfs(c)) return "";
      }
    }

    StringBuilder sb = new StringBuilder();
    while (!toposort.isEmpty()) {
      sb.append(toposort.pop());
    }

    // Add remaining elements. This can come in any order.
    String result = sb.toString();
    StringBuilder remaining = new StringBuilder();
    for (int i = 0; i < 26; i++) {
      if (A[i]) {
        char c = (char) (i + 'a');
        boolean found = false;
        for (char r : result.toCharArray()) {
          if (r == c) {
            found = true;
            break;
          }
        }
        if (!found) {
          remaining.append(c);
        }
      }
    }
    return result.concat(remaining.toString().trim());
  }

  /**
   * Dfs to toposort
   *
   * @param u
   * @return
   */
  private boolean dfs(char u) {
    done.add(u);
    visited.add(u);
    List<Character> children = graph.get(u);
    if (children != null) {
      for (char c : children) {
        if (visited.contains(c)) return false; // check cycle
        if (!done.contains(c)) {
          boolean status = dfs(c);
          if (!status) return false;
        }
      }
    }
    toposort.push(u);
    visited.remove(u);
    return true;
  }
}
