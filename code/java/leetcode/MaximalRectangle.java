package stack;

import java.util.Stack;

public class MaximalRectangle {

  public static void main(String[] args) throws Exception {
    char[][] matrix = {
      {'1', '0', '1', '0', '0'},
      {'1', '0', '1', '1', '1'},
      {'1', '1', '1', '1', '1'},
      {'1', '0', '0', '1', '0'}
    };
    System.out.println(new MaximalRectangle().maximalRectangle(matrix));
  }

  public int maximalRectangle(char[][] matrix) {
    if (matrix.length == 0 || matrix[0].length == 0) return 0;
    int[] A = new int[matrix[0].length];
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == '1') {
          if (i > 0 && matrix[i - 1][j] == '1') {
            A[j] = A[j] + 1;
          } else {
            A[j] = 1;
          }
        } else {
          A[j] = 0;
        }
      }
      // calculate max rectangle for this row
      max = Math.max(max, getMaxRectangle(A));
    }
    return max;
  }

  private int getMaxRectangle(int[] heights) {
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
