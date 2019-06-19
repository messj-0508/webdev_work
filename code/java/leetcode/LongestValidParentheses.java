import java.util.*;

public class LongestValidParentheses {

  private class Node {
    char c;
    int i;

    Node(char c, int i) {
      this.c = c;
      this.i = i;
    }
  }

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    System.out.println(new LongestValidParentheses().longestValidParentheses("((()()(((())))))"));
  }

  public int longestValidParentheses(String s) {
    Stack<Node> stack = new Stack<>();
    int max = 0;
    for (int i = 0, l = s.length(); i < l; i++) {
      char c = s.charAt(i);
      switch (c) {
        case '(':
          stack.push(new Node(c, i));
          break;

        case ')':
          if (!stack.isEmpty()) {
            if (stack.peek().c == '(') {
              stack.pop();
              if (stack.isEmpty()) {
                max = Math.max(max, i + 1);
              } else {
                max = Math.max(max, i - stack.peek().i);
              }
            } else {
              stack.push(new Node(c, i));
            }
          } else {
            stack.push(new Node(c, i));
          }
      }
    }
    return max;
  }
}
