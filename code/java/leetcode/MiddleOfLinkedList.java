public class MiddleOfLinkedList {

  public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
    }
  }

  public static void main(String[] args) {}

  public ListNode middleNode(ListNode head) {
    int count = 0;
    ListNode temp = head;
    while (temp != null) {
      temp = temp.next;
      count++;
    }
    int mid = count / 2;
    int c = 0;
    while (head != null && c < mid) {
      head = head.next;
      c++;
    }
    return head;
  }
}
