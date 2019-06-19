import java.util.Stack;

public class LargestRectangleInHistogram {
  public static void main(String[] args) throws Exception {
    int[] A = {2, 3};
    System.out.println(new LargestRectangleInHistogram().largestRectangleArea(A));
  }

  public int largestRectangleArea(int[] heights) {
    if (heights.length == 0) return 0;
    int maxArea = Integer.MIN_VALUE;
    Stack<Integer> stack = new Stack<>();
    int i = 0;
    for (; i < heights.length; i++) {
      while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
        int top = stack.pop();
        int base = stack.isEmpty() ? i : i - stack.peek() - 1;
        maxArea = Math.max(maxArea, base * heights[top]);
      }
      stack.push(i);
    }
    while (!stack.isEmpty()) {
      int top = stack.pop();
      int base = stack.isEmpty() ? i : i - stack.peek() - 1;
      maxArea = Math.max(maxArea, base * heights[top]);
    }
    return maxArea;
  }
}
