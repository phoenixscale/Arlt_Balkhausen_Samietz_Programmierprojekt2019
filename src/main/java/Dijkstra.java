public class Dijkstra {
	
	private static int pivotSource;
	
	private static int[] edgeOffset, startNodeID, endNodeID, edgeValue;
	
	private static int amountOfEdges, amountOfNodes;
	
	private static boolean[] inPriorityQueue, minimalDistanceFound;
	private static int priorityQueueLength;	//	repräsentiert die Lände der priorityQueue
	private static int[] priorityQueue;	//	enthält Verweise auf distanceArray
	private static int[] distanceArray;	//	enthält Abstände von sourceNode zu entsprechender Node

	/**
	 * calculates the distance from the source to the target
	 * 
	 * @param source
	 * @param target
	 * @return distance from source to target
	 */
	public static int setSourceAndTarget(int source, int target) {
		
		edgeOffset = Data.getOffsetArray();
		startNodeID = Data.getStartNodeIDArray();
		endNodeID = Data.getEndNodeIDArray();
		edgeValue = Data.getEdgeValueArray();
		
		amountOfNodes = edgeOffset.length;
		amountOfEdges = endNodeID.length;
		
		priorityQueueLength = 1;

		inPriorityQueue = new boolean[amountOfNodes];
		minimalDistanceFound = new boolean[amountOfNodes];
		priorityQueue = new int[amountOfNodes];
		distanceArray = new int[amountOfNodes];
		
		for (int ctr = 0; ctr < distanceArray.length; ctr++)
			distanceArray[ctr] = Integer.MAX_VALUE;
		
		priorityQueue[0] = source;
		distanceArray[source] = 0;
		

		while (true) {
			
			int closestNode = priorityQueue[0];
			int tempDist = distanceArray[closestNode];
			int closestNodeTarget = -1;
			int offset = edgeOffset[closestNode];
			
			minimalDistanceFound[closestNode] = true;
			
			if (offset != -1)
				while (offset != amountOfEdges && closestNode == startNodeID[offset]) {
					closestNodeTarget = endNodeID[offset];
					
					// verhindert Schleifen
					if (!minimalDistanceFound[closestNodeTarget])
						if (distanceArray[closestNodeTarget] > tempDist + edgeValue[offset]) {
							distanceArray[closestNodeTarget] = tempDist + edgeValue[offset];
							
							// falls noch nicht in priorityQueue, Aufnahme in diese
							if (!inPriorityQueue[closestNodeTarget]) {
								priorityQueue[priorityQueueLength] = closestNodeTarget;
								priorityQueueLength++;
								inPriorityQueue[closestNodeTarget] = true;
							}
						}
					offset++;
				}
			
			if (priorityQueueLength == 0)
				return -1;
			
			priorityQueueLength--;
			priorityQueue[0] = priorityQueue[priorityQueueLength];
			inPriorityQueue[closestNode] = false;
			
			minHeapSort();
			
			if (distanceArray[target] < distanceArray[priorityQueue[0]])
				return distanceArray[target];
		}
	}

	/**
	 * Sets a pivot source for other methods
	 * 
	 * @param sourceToSet
	 */
	public static void setPivotSource(int sourceToSet) {
		pivotSource = sourceToSet;
	}
	
	/**
	 * calculates the distance from the pivot source to the given target
	 * 
	 * @param target
	 * @return distance between pivotSource to target
	 */
	public static int fromPivotSourceToTarget(int target) {
		return setSourceAndTarget(pivotSource, target);
	}

	
	/**
	 * calculates the distance from the pivot source to multiple targets
	 * 
	 * @param targets
	 * @return distance between pivotSource to multiple targets
	 */
	public static int[] fromPivotSourceToTargets(int[] targets) {
		
		boolean finished = false;
		
		edgeOffset = Data.getOffsetArray();
		startNodeID = Data.getStartNodeIDArray();
		endNodeID = Data.getEndNodeIDArray();
		edgeValue = Data.getEdgeValueArray();
		
		amountOfNodes = edgeOffset.length;
		amountOfEdges = endNodeID.length;
		
		priorityQueueLength = 1;

		inPriorityQueue = new boolean[amountOfNodes];
		minimalDistanceFound = new boolean[amountOfNodes];
		priorityQueue = new int[amountOfNodes];
		distanceArray = new int[amountOfEdges];
		
		for (int ctr = 0; ctr < distanceArray.length; ctr++)
			distanceArray[ctr] = Integer.MAX_VALUE;
		
		priorityQueue[0] = pivotSource;
		distanceArray[pivotSource] = 0;
		
		
		while (true) {
			
			int closestNode = priorityQueue[0];
			int tempDist = distanceArray[closestNode];
			int closestNodeTarget = -1;
			int offset = edgeOffset[closestNode];
			
			minimalDistanceFound[closestNode] = true;
			
			if (offset != -1)
				while (offset != amountOfEdges && closestNode == startNodeID[offset]) {
					closestNodeTarget = endNodeID[offset];
					
					// verhindert Schleifen
					if (!minimalDistanceFound[closestNodeTarget])
						if (distanceArray[closestNodeTarget] > tempDist + edgeValue[offset]) {
							distanceArray[closestNodeTarget] = tempDist + edgeValue[offset];
							
							// falls noch nicht in priorityQueue, Aufnahme in diese
							if (!inPriorityQueue[closestNodeTarget]) {
								priorityQueue[priorityQueueLength] = closestNodeTarget;
								priorityQueueLength++;
								inPriorityQueue[closestNodeTarget] = true;
							}
						}
					offset++;
				}
			
			if (priorityQueueLength == 0) {
				for (int ctr = 0; ctr < targets.length; ctr++)
					if (distanceArray[targets[ctr]] == Integer.MAX_VALUE) {
						distanceArray[targets[ctr]] = -1;
						minimalDistanceFound[targets[ctr]] = true;
					}
			} else {
				priorityQueueLength--;
				priorityQueue[0] = priorityQueue[priorityQueueLength];
				inPriorityQueue[closestNode] = false;
				
				minHeapSort();
				
				finished = true;
				
				// falls für nur 1 target kein ergebniss gefunden wurde, ist die Suche NICHT beendet
				for (int target : targets) {
					if (!minimalDistanceFound[target]) {
						finished = false;
						break;
					}
				}
			}
			if (finished) {
				for (int ctr = 0; ctr < targets.length; ctr++)
					targets[ctr] = distanceArray[targets[ctr]];
				return targets;
			}
		}
	}
		
	/**
	 * minHeap sortierer
	 * 
	 * sortiert die ersten n = nodesToVisit nodes der PrioQueue anhand der Länge
	 * x = distanceArray[prioQueue[i]] von der Node i
	 * 
	 */
	private static void minHeapSort() {
		
		if (priorityQueueLength == 0)
			return;
		
		int firstParentNode = (priorityQueueLength - 2) / 2;
		int childNode = firstParentNode * 2 + 2;
		
		int parentPointer = priorityQueue[firstParentNode];

		// bei erstem durchlauf ArrayOutOfBounds möglich, deswegen seperater durchlauf mit extra if-Abfrage
		if (childNode < priorityQueueLength &&
				distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
			priorityQueue[firstParentNode] = priorityQueue[childNode];
			priorityQueue[childNode] = parentPointer;
			
			parentPointer = priorityQueue[firstParentNode];
		}
		
		childNode--;

		if (childNode < priorityQueueLength &&
				distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
			priorityQueue[firstParentNode] = priorityQueue[childNode];
			priorityQueue[childNode] = parentPointer;
		}
		
		
		
		for (int parentNode = firstParentNode - 1; parentNode != -1; parentNode--) {
			childNode--;
			parentPointer = priorityQueue[parentNode];

			if (distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
				priorityQueue[parentNode] = priorityQueue[childNode];
				priorityQueue[childNode] = parentPointer;
				
				parentPointer = priorityQueue[parentNode];
			}
			
			childNode--;

			if (distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
				priorityQueue[parentNode] = priorityQueue[childNode];
				priorityQueue[childNode] = parentPointer;
			}
		}
	}
	
	public static void nodesConnectedTo(int source) {
		Utility.startTimer();
		
		edgeOffset = Data.getOffsetArray();
		endNodeID = Data.getEndNodeIDArray();
		
		amountOfNodes = edgeOffset.length;
		amountOfEdges = endNodeID.length;
		
		priorityQueue = new int[amountOfNodes];
		priorityQueue[0] = source;
		priorityQueueLength = 1;
		
		int reachableNodes = 0;
		boolean[] alreadyVisited = new boolean[amountOfNodes];
		
		alreadyVisited[0] = true;

		while (true) {
			int currNode = priorityQueue[0];
			int currNodeEdgesFrom = edgeOffset[currNode];
			int currNodeEdgesTo;
			
			if (currNodeEdgesFrom != -1) {
				if (currNode + 1 == amountOfNodes)
					currNodeEdgesTo = amountOfEdges;
				else
					currNodeEdgesTo = edgeOffset[currNode + 1];

				for (int ctr = currNodeEdgesFrom; ctr < currNodeEdgesTo; ctr++) {

					if (!alreadyVisited[endNodeID[ctr]]) {
						reachableNodes++;
						alreadyVisited[endNodeID[ctr]] = true;
						priorityQueue[priorityQueueLength] = endNodeID[ctr];
						priorityQueueLength++;
					}
				}
			}
			
			if (priorityQueueLength == 0) {
				System.out.println(reachableNodes + "/" + amountOfNodes + " edges are reachable from " + source);
				System.out.println(Utility.endTimer());
				
				for (int i = 0; i < alreadyVisited.length; i++)
					if (!alreadyVisited[i] && i == 200)
						System.out.println(i);
				return;
			}
			
			priorityQueueLength--;
			priorityQueue[0] = priorityQueue[priorityQueueLength];
		}
	}

}
