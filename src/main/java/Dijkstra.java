import java.util.Arrays;

public class Dijkstra {
	private int[] prev;
	private boolean[] finished;
	private Heap heap;
	private Graph graph;

	/**
	 * Constructor for the class Dijkstra
	 */
	public Dijkstra(Graph graph) {
		this.graph = graph;
		heap = new Heap(graph.getNodeCount()); // create priority queue
		prev = new int[graph.getNodeCount()];
		finished = new boolean[graph.getNodeCount()];

	}

	/**
	 * One-to-all Dijkstra implementation
	 */
	@SuppressWarnings("Duplicates")
	public void dijkstra(int start) {
		if (!heap.isEmpty())
			heap.flush();
		heap.setEmpty(false);
		Arrays.fill(finished, false);
		this.heap.insert(start, 0); // insert first node

		while (heap.getSize() > 0) {
			int finishedNode = heap.deleteMin();
			int finishedValue = heap.getValueOf(finishedNode);
			finished[finishedNode] = true;
			int offset = graph.getOffsetOf(finishedNode);
			if (offset == -1)
				continue;
			while (offset < graph.getEdgeCount() && graph.getSourceOf(offset) == finishedNode) {
				int destination = graph.getDestinationOf(offset);
				int weight = graph.getWeight(offset);
				offset++;

				if (finished[destination])
					continue;
				if (heap.getValueOf(destination) == -1) {
					heap.insert(destination, finishedValue + weight);
				} else {
					if (heap.getValueOf(finishedNode) + weight < heap.getValueOf(destination)) {
						heap.decreaseKey(destination, finishedValue + weight);
					}
				}

			}

		}
	}

	/**
	 * One-to-one Dijkstra implementation
	 */
	@SuppressWarnings("Duplicates")
	public void dijkstra(int start, int destination) {
		if (!heap.isEmpty())
			heap.flush();
		heap.setEmpty(false);
		Arrays.fill(finished, false);
		Arrays.fill(prev, -1); // Initialize distances with "infinite"
		this.heap.insert(start, 0); // insert first node

		while (heap.getSize() > 0) {
			int finishedNode = heap.deleteMin(); // get node with shortest path
			if (finishedNode == destination)
				return;
			int finishedValue = heap.getValueOf(finishedNode);
			finished[finishedNode] = true;
			int offset = graph.getOffsetOf(finishedNode);
			if (offset == -1)
				continue;
			while (offset < graph.getEdgeCount() && graph.getSourceOf(offset) == finishedNode) {
				int dest = graph.getDestinationOf(offset);
				int weight = graph.getWeight(offset);
				offset++;

				if (finished[dest])
					continue;
				if (heap.getValueOf(dest) == -1) {
					heap.insert(dest, finishedValue + weight);
					prev[dest] = finishedNode;
				} else {
					if (heap.getValueOf(finishedNode) + weight < heap.getValueOf(dest)) {
						heap.decreaseKey(dest, finishedValue + weight);
						prev[dest] = finishedNode;
					}
				}

			}

		}
	}

	/**
	 * This method returns the distance from the used start to the destination given
	 * in the parameter
	 * 
	 * @param destination
	 * @return int
	 */
	public int getDistanceTo(int destination) {
		return heap.getValueOf(destination);
	}
}