import java.util.*;

public class FreqStack {

  /**
   * Main method
   *
   * @param args
   */
  public static void main(String[] args) {
    FreqStack freqStack = new FreqStack();
    freqStack.push(5);
    freqStack.push(5);
    freqStack.push(5);
    freqStack.push(5);
    freqStack.push(6);
    System.out.println(freqStack.pop());
    freqStack.push(7);
    System.out.println(freqStack.pop());
    System.out.println(freqStack.pop());
    freqStack.push(1);
    freqStack.push(2);
    System.out.println(freqStack.pop());
    System.out.println(freqStack.pop());
    System.out.println(freqStack.pop());
    System.out.println(freqStack.pop());
    System.out.println(freqStack.pop());
  }

  class Node {
    int val, pos;

    Node(int val, int pushCount) {
      this.val = val;
      this.pos = pushCount;
    }
  }

  class FreqNode {
    int freq;
    Stack<Node> stack;

    FreqNode(int freq, Stack<Node> stack) {
      this.freq = freq;
      this.stack = stack;
    }

    public int getFreq() {
      return freq;
    }

    public int getTop() {
      return !stack.isEmpty() ? stack.peek().pos : -1;
    }

    public void push(Node e) {
      freq++;
      stack.push(e);
    }

    public Node pop() {
      freq--;
      return stack.pop();
    }
  }

  private PriorityQueue<FreqNode> priorityQueue;
  private Map<Integer, FreqNode> map;
  private int pushCount;

  public FreqStack() {
    priorityQueue =
        new PriorityQueue<>(
            (o1, o2) -> {
              if (o1.freq == o2.freq) {
                return Integer.compare(o2.getTop(), o1.getTop());
              } else {
                return Integer.compare(o2.freq, o1.freq);
              }
            });
    map = new HashMap<>();
    pushCount = 0;
  }

  public void push(int x) {
    pushCount++;
    Node node = new Node(x, pushCount);
    FreqNode freqNode;
    if (map.containsKey(x)) {
      freqNode = map.get(x);
      priorityQueue.remove(freqNode);
      freqNode.push(node);
    } else {
      Stack<Node> stack = new Stack<>();
      stack.push(node);
      freqNode = new FreqNode(1, stack);
      map.put(x, freqNode);
    }
    priorityQueue.offer(freqNode);
  }

  public int pop() {
    FreqNode freqNode = priorityQueue.poll();
    Node topNode = freqNode.pop();
    if (freqNode.freq == 0) {
      map.remove(topNode.val);
    } else {
      priorityQueue.offer(freqNode);
    }
    return topNode.val;
  }
}
