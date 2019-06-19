public class BinaryTreeLongestConsecutiveSequenceII {

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  private class Node {
    int i, d;
    int val;

    Node(int i, int d, int val) {
      this.i = i;
      this.d = d;
      this.val = val;
    }
  }

  private int max = Integer.MIN_VALUE;

  public static void main(String[] args) throws Exception {
    TreeNode root = new TreeNode(2);
    root.left = new TreeNode(1);
    // root.left.left = new TreeNode(4);
    root.right = new TreeNode(3);
    System.out.println(new BinaryTreeLongestConsecutiveSequenceII().longestConsecutive(root));
  }

  public int longestConsecutive(TreeNode root) {
    Node n = preorder(root);
    if (n != null) {
      max = Math.max(max, n.d);
      max = Math.max(max, n.i);
      if (n.i > 1 && n.d > 1) {
        max = Math.max(max, n.d + n.i - 1);
      }
    }
    if (max == Integer.MIN_VALUE) return 0;
    return max;
  }

  private Node preorder(TreeNode node) {
    if (node == null) return null;
    Node left = preorder(node.left);
    Node curr = new Node(1, 1, node.val);
    if (left != null) {
      max = Math.max(max, left.d);
      max = Math.max(max, left.i);
      if (left.val == node.val + 1) {
        curr.d = left.d + 1;
        curr.i = 1;
      } else if (left.val == node.val - 1) {
        curr.i = left.i + 1;
        curr.d = 1;
      }
    }
    Node right = preorder(node.right);
    if (right != null) {
      max = Math.max(max, right.d);
      max = Math.max(max, right.i);
      if (right.val == node.val + 1) {
        if (right.d + 1 > curr.d) {
          curr.d = right.d + 1;
        }
      } else if (right.val == node.val - 1) {
        if (right.i + 1 > curr.i) {
          curr.i = right.i + 1;
        }
      }
    }
    if (curr.i > 1 && curr.d > 1) {
      max = Math.max(max, curr.d + curr.i - 1);
    }
    return curr;
  }
}
