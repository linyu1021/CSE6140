/**
 * HCC.java
 * @author <Linyu Yao>
 * Georgia Institute of Technology, Fall 2018
 *
 * Heist-Closeness Centrality computation implementation
 * 
 * NOTE: You should change this file to add in your implementation.
 * Feel free to create as many local functions as you want.
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HCC {
	public static void compute(Graph g, VertexSet h) {
		// <Implement HCC here>
		int numVertices = g.getNumVertices();
		int [] offsets = g.getRawOffsets();
		int [] edges = g.getRawEdges();
		double [] verticesData =g.getVerticesData();
 
		for (int i = 0; i < numVertices; i++) {
			int sumDistance = 0;
			// ArrayList to store visited vertex
			List<Integer> visited = new ArrayList<Integer>();
			// Queue to store the next visiting vertex
			Queue<Integer> q = new LinkedList<Integer>();
			// HashMap to store the distance of each vertex from the source node
			HashMap<Integer, Integer> distance = new HashMap<Integer, Integer>();

			q.offer(i);
			distance.put(i, 0);
			while (!q.isEmpty()) {
				int curVertex = q.poll();
				if (!visited.contains(curVertex)) {
					visited.add(curVertex);
					for (int j = offsets[curVertex]; j < offsets[curVertex] + g.degree(curVertex); j++) {
						if (!distance.containsKey(edges[j])) {
							distance.put(edges[j], distance.get(curVertex) + 1);
							if (h.searchVertex(edges[j]) && !q.contains(edges[j])) {
								sumDistance += distance.get(edges[j]);
							}
							q.add(edges[j]);	
						}
					}
	
				}
			}
			// System.out.println(sumDistance);
			verticesData[i] = 1.0 / sumDistance;
		}
	}
}