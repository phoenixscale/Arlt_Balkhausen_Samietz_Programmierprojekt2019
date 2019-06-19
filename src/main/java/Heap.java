
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Heap {
	private int[] value;
	private int[] place;
	private int[] heap;
	private int size;
	private boolean empty = true;

	/**
	 * Constructor for Heap class
	 * 
	 * @param maxEntries
	 */
	public Heap(int maxEntries) {
		size = 0;
		value = new int[maxEntries];
		Arrays.fill(value, -1);
		place = new int[maxEntries];
		heap = new int[maxEntries];
	}

	/**
	 * Deletes heap's root
	 * 
	 * @return heap's root's value
	 * @throws NoSuchElementException
	 */
	public int deleteMin() throws NoSuchElementException {
		if (size > 0) {
			int tmp = heap[0];
			swap(0, size - 1);
			size--;
			if (size > 1) {
				siftdown(0);
			}
			return tmp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Deacrese key
	 * 
	 * @param node
	 * @param value
	 */
	public void decreaseKey(int node, int value) {
		if (this.value[node] > value) {
			this.value[node] = value;
			siftup(place[node]);
		}
	}

	/**
	 * Aids in sorting the heap
	 * 
	 * @param i
	 */
	public void siftup(int i) {
		int index = i;
		int node = heap[i];
		while (index > 0) {
			int node2 = heap[(parent(index))];
			if (value[node] >= value[node2])
				break;
			heap[index] = node2;
			place[node2] = index;
			index = parent(index);
		}
		heap[index] = node;
		place[node] = index;
	}

	/**
	 * Aids in sorting the heap
	 * 
	 * @param i
	 */
	public void siftdown(int i) {
		int node = heap[i];
		int index = i;
		double end = getLeafCount();
		while (index < end) {
			if (hasRight(index)) {
				if (value[heap[left(index)]] < value[heap[right(index)]]) {
					place[heap[left(index)]] = index;
					heap[index] = heap[left(index)];
					index = left(index);
				} else {
					place[heap[right(index)]] = index;
					heap[index] = heap[right(index)];
					index = right(index);
				}

			} else {
				place[heap[left(index)]] = index;
				heap[index] = heap[left(index)];
				index = left(index);

			}
		}
		place[node] = index;
		heap[index] = node;
		siftup(index);
	}

	/**
	 * swaps two vertices within heap
	 * 
	 * @param x
	 * @param y
	 */
	public void swap(int x, int y) {
		int tmp1 = heap[x];
		heap[x] = heap[y];
		heap[y] = tmp1;
		int tmp2 = place[heap[x]];
		place[heap[x]] = place[heap[y]];
		place[heap[y]] = tmp2;

	}

	/**
	 * inserts a vertex into the heap
	 * 
	 * @param vertex
	 * @param distance
	 */
	public void insert(int vertex, int distance) {
		heap[size] = vertex;
		value[vertex] = distance;
		place[vertex] = size;
		size++;
		siftup(size - 1);
	}

	/**
	 * Returns the size of the heap
	 * 
	 * @return heap-size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * returns leaf-count
	 * 
	 * @return leaf-count
	 */
	public double getLeafCount() {
		return Math.floor(size / 2.0);
	}

	/**
	 * Returns True if node has right child
	 * 
	 * @param index
	 * @return boolean
	 */
	public boolean hasRight(int index) {
		return right(index) < size;
	}

	/**
	 * Returns value of node
	 * 
	 * @param node
	 * @return boolean
	 */
	public int getValueOf(int node) {
		return value[node];
	}

	/**
	 * resets heap
	 */
	public void flush() {
		Arrays.fill(value, -1);
		this.size = 0;
		this.empty = true;
	}

	/**
	 * returns empty
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * sets empty
	 * 
	 * @param empty
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/**
	 * returns parent node
	 * 
	 * @param i
	 * @return
	 */
	static int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * returns left child
	 * 
	 * @param i
	 * @return
	 */
	static int left(int i) {
		return (2 * i + 1);
	}

	/**
	 * returns right child
	 * 
	 * @param i
	 * @return
	 */
	static int right(int i) {
		return (2 * i + 2);
	}

}