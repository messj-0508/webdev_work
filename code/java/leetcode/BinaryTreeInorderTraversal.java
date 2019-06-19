import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class BinaryTreeInorderTraversal {
  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  public static void main(String[] args) throws Exception {
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(4);
    root.left.left = new TreeNode(5);
    root.left.right = new TreeNode(6);
    root.left.left.left = new TreeNode(9);
    root.left.left.right = new TreeNode(10);
    root.right = new TreeNode(2);
    root.right.left = new TreeNode(7);
    root.right.right = new TreeNode(8);
    List<Integer> result = new BinaryTreeInorderTraversal().inorderTraversal(root);
    System.out.println(result);
  }

  public List<Integer> inorderTraversal(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    List<Integer> result = new ArrayList<>();
    while (curr != null || !stack.isEmpty()) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
      curr = stack.pop();
      result.add(curr.val);
      curr = curr.right;
    }
    return result;
  }
}
