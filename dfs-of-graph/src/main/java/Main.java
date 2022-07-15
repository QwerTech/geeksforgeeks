// { Driver Code Starts

import java.util.*;
import java.io.*;
import java.lang.*;

class DriverClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            ArrayList<ArrayList<Integer>> list = new ArrayList<>();
            int V = sc.nextInt();
            int E = sc.nextInt();
            for (int i = 0; i < V + 1; i++)
                list.add(i, new ArrayList<Integer>());
            for (int i = 0; i < E; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                list.get(u).add(v);
            }
            if (new Solution().isCyclic(V, list) == true)
                System.out.println("1");
            else
                System.out.println("0");
        }
    }
}// } Driver Code Ends


/*Complete the function below*/

class Solution {
    // Function to detect cycle in a directed graph.
    private boolean[] visited;
    private boolean[] recursionStack;
    private ArrayList<ArrayList<Integer>> graph;

    public boolean canFinish(int numCourses, int[][] prerequisites) {

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            ArrayList<Integer> children = graph.get(prerequisite[0]);
            children.add(prerequisite[1]);
        }
        return !isCyclic(numCourses, graph);

    }

    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> children) {
        // code here
        visited = new boolean[V];
        recursionStack = new boolean[V];
        for (int i = 0; i < V; i++) {
            visited[i] = false;
            recursionStack[i] = false;
        }
        graph = children;
        for (int i = 0; i < V; i++) {
            if (isCyclicInternal(i)) {
                return true;
            }
        }
        return false;

    }

    private boolean isCyclicInternal(int item) {
        if (recursionStack[item]) {
            return true;
        }
        if (visited[item]) {
            return false;
        }
        visited[item] = true;
        recursionStack[item] = true;

        for (Integer child : graph.get(item)) {
            if (isCyclicInternal(child)) {
                return true;
            }
        }

        recursionStack[item] = false;
        return false;

    }
}