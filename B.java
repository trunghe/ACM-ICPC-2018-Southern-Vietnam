/**
 * ACM ICPC 2018 Southern Vietnam
 * Problem: B - Landmark
 * Description: Given a cactus graph without any degree-one vertex (a cactus
 *              tree with no leaves), find the longest path between a given pair
 *              of vertices.
 * Classification: Graph
 * Author: Vu Thanh Trung
 * Solution: By Nguyen Anh Viet: Observe that there are exactly 2 separate paths
 *          (2 paths that do not share a common edge) between any pair of
 *          vertices. Therefore, if we find the shortest path (by bfs) and
 *          remove that, performing bfs again will give us the remaining
 *          (longest possible) path.
 * Complexity: In this kind of graphs, if there are V vertices, there are at
 *              most E = 3(V-1)/2 edges. Therefore complexity is
 *              O(E) = O(3(V-1)/2) with V <= 10^5
 */

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br;
    static PrintWriter pw;
    static StringTokenizer st;
    static int n, m, source, target;
    static HashSet<Integer>[] graph; // Use HashSet to optimize the remove function
    static boolean[] visited;
    static LinkedList<Integer> queue;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        System.setOut(new PrintStream("output.txt"));

        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(new BufferedOutputStream(System.out));

        input();
        solve();
    }

    private static void solve() {
        // Assume there are always exactly 2 valid paths between source and target.
        // Remove the shortest path, then output the second one.
        removeShortestPath();
        pw.println(shortestPath());
        pw.close();
    }

    private static void removeShortestPath() {
        init();
        int[] parent = new int[n];
        while (!queue.isEmpty()) {
            int current = queue.pollFirst();
            for (int v : graph[current]) {
                if (!visited[v]) {
                    parent[v] = current;
                    if (v == target) {// Assume always has a valid path to target
                        // Remove the shortest path
                        do {
                            graph[parent[v]].remove(v);
                            graph[v].remove(parent[v]);
                            v = parent[v];
                        } while (v != source);
                        return;
                    }
                    queue.addLast(v);
                    visited[v] = true;
                }
            }
        }
    }

    static int shortestPath() {
        init();
        int[] dist = new int[n];
        while (!queue.isEmpty()) {
            int current = queue.pollFirst();
            for (int v : graph[current]) {
                if (!visited[v]) {
                    dist[v] = dist[current] + 1;
                    if (v == target) {// Assume always has a valid path to target
                        return dist[v];
                    }
                    queue.addLast(v);
                    visited[v] = true;
                }
            }
        }
        return -1;
    }

    private static void init() {
        queue = new LinkedList();
        queue.add(source);
        visited = new boolean[n];
        visited[source] = true;
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        graph = new HashSet[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new HashSet();
        }
        m = Integer.parseInt(st.nextToken());
        source = Integer.parseInt(st.nextToken()) - 1;
        target = Integer.parseInt(st.nextToken()) - 1;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            graph[u].add(v);
            graph[v].add(u);
        }
    }
}
