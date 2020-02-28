// { Driver Code Starts
//Initial Template for Java

import java.util.*;
import java.lang.*;


class Driverclass {

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int t = sc.nextInt();
    while (t-- > 0) {
      ArrayList<ArrayList<Integer>> list = new ArrayList<>();
      int nov = sc.nextInt();
      int edg = sc.nextInt();

      for (int i = 0; i < nov; i++) {
        list.add(i, new ArrayList<Integer>());
      }

      for (int i = 1; i <= edg; i++) {
        int u = sc.nextInt();
        int v = sc.nextInt();
        list.get(u).add(v);
      }
      boolean vis[] = new boolean[nov];
      for (int i = 0; i < nov; i++) {
        vis[i] = false;
      }
      new Traversal().bfs(0, list, vis, nov);
      System.out.println();
    }
  }
}

// } Driver Code Ends
//User function Template for Java

/*
*ArrayList<ArrayList<Integer>> list: represent graph containing vertices
                                    and edges between them
*vis[]: boolean array to represent visited vertex
*s: starting vertex
*/
class Traversal {

  public static class Graph {

    public Graph(int size) {
      this.allNodes = new Node[size];
    }

    private Node[] allNodes;

    class Node {

      public Node(Integer index) {
        this.index = index;
        allNodes[index] = this;
      }

      private boolean visited = false;
      private Integer index;
      private List<Node> to = new ArrayList<>();
    }

    private Node getOrAddNode(Integer ci) {
      Node node = allNodes[ci];
      if (node == null) {
        node = allNodes[ci] = new Node(ci);
      }
      return node;
    }
  }

  static void bfs(int s, ArrayList<ArrayList<Integer>> list, boolean vis[], int nov) {
    Graph graph = new Graph(nov);
    for (int i = 0; i < list.size(); i++) {
      Graph.Node node = graph.getOrAddNode(i);
      ArrayList<Integer> childIndexes = list.get(i);
      childIndexes.forEach(ci -> {
        node.to.add(graph.getOrAddNode(ci));
      });
    }
    Graph.Node root = graph.getOrAddNode(s);
    List<Graph.Node> children = Collections.singletonList(root);
    while (!children.isEmpty()) {
      children.forEach(Traversal::print);
      children = children.stream().flatMap(n -> n.to.stream()).filter(n -> !n.visited)
          .collect(java.util.stream.Collectors.toList());
    }
  }

  private static void print(Graph.Node node) {
    if (node.visited) {
      return;
    }
    node.visited = true;
    if (node.index == 0) {
      System.out.print(node.index);
    } else {
      System.out.print(" " + node.index);
    }
  }
}
