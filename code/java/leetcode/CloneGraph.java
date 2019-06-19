import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CloneGraph {

  static class UndirectedGraphNode {
    int label;
    List<UndirectedGraphNode> neighbors;

    UndirectedGraphNode(int x) {
      label = x;
      neighbors = new ArrayList<>();
    }
  }

  private Map<Integer, UndirectedGraphNode> map;

  public static void main(String[] args) throws Exception {
    UndirectedGraphNode node = new UndirectedGraphNode(0);
    UndirectedGraphNode node1 = new UndirectedGraphNode(1);
    UndirectedGraphNode node2 = new UndirectedGraphNode(2);
    node.neighbors.add(node1);
    node.neighbors.add(node2);

    node1.neighbors.add(node);
    node1.neighbors.add(node2);

    node2.neighbors.add(node);
    node2.neighbors.add(node1);
    node2.neighbors.add(node2);
    UndirectedGraphNode result = new CloneGraph().cloneGraph(node);
    // print result
  }

  public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    if (node == null) return null;
    map = new HashMap<>();
    UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
    dfs(node, clone);
    return clone;
  }

  private void dfs(UndirectedGraphNode original, UndirectedGraphNode clone) {
    map.put(clone.label, clone);
    List<UndirectedGraphNode> oChildren = original.neighbors; // original child nodes
    List<UndirectedGraphNode> cChildren = clone.neighbors; // clone child nodes
    for (UndirectedGraphNode oChild : oChildren) {
      if (map.containsKey(oChild.label)) {
        // already visited node
        cChildren.add(map.get(oChild.label));
      } else {
        // a new node
        UndirectedGraphNode newChildClone = new UndirectedGraphNode(oChild.label);
        cChildren.add(newChildClone);
        dfs(oChild, newChildClone);
      }
    }
  }
}
