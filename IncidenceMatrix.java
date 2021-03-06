import java.io.*;
import java.util.*;

/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class IncidenceMatrix extends AbstractAssocGraph {

	private int[][] incidenceMatrix;
	private List<String> vertexCollection = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	private List<Edge> edgeCollection = new ArrayList<Edge>();
	private List<MyPair> myPair = new ArrayList<MyPair>();
	int numVertex = 0;
	int numEdge = 0;
	int weight = 0;

	public IncidenceMatrix() {
		incidenceMatrix = new int[0][0];

	} // end of IncidentMatrix()

	public void addVertex(String vertLabel) {
		for (String vertex : vertexCollection) {
			if (vertex.equals(vertLabel))
				return;
		}
		vertexCollection.add(vertLabel);
		numVertex = numVertex + 1;
		createMatrix();

	} // end of addVertex()

	@SuppressWarnings("rawtypes")
	public void createMatrix() {
		incidenceMatrix = new int[numVertex][numEdge];

		for (int row = 0; row < numVertex; row++) {
			for (int col = 0; col < numEdge; col++) {
				Edge edge = (Edge) edgeCollection.get(col);
				String r = vertexCollection.get(row);
				String vertex_1 = edge.getVertex1();
				String vertex_2 = edge.getVertex2();

				if (r.equals(vertex_1) || r.equals(vertex_2))
					incidenceMatrix[row][col] = 1;
				else
					incidenceMatrix[row][col] = 0;

			}
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addEdge(String srcLabel, String tarLabel, int weight) {

		Edge edge = new Edge(srcLabel, tarLabel, weight);
		checkEdge(srcLabel, tarLabel);

		// check that if there are duplicates
		for (Edge e : edgeCollection) {
			String vertex_1 = e.getVertex1();
			String vertex_2 = e.getVertex2();

			if (vertex_1.equals(srcLabel) && vertex_2.equals(tarLabel)
					|| vertex_1.equals(tarLabel) && vertex_2.equals(srcLabel)) {
				System.err.println("Edge Already Exists!");
				return;
			}
		}

		edgeCollection.add(edge);
		numEdge++;
		createMatrix();
	} // end of addEdge()

	@SuppressWarnings("unused")
	private void checkEdge(String srcLabel, String tarLabel) {

		for (Edge e : edgeCollection) {
			if (!(vertexCollection.contains(srcLabel)) || !(vertexCollection.contains(tarLabel))) {
				System.err.println("Vertex not found.");
			} else if (srcLabel.equals(tarLabel)) {
				System.err.println("Invalid edge.");
			}
		}
		return;
	}

	public boolean edgeExistence(String srcLabel, String tarLabel) {
		checkEdge(srcLabel, tarLabel);
		if ((vertexCollection.contains(srcLabel)) && (vertexCollection.contains(tarLabel))) {
			return true;
		} else
			return false;
	}

	public int getEdgeWeight(String srcLabel, String tarLabel) {

		checkEdge(srcLabel, tarLabel);

		for (Edge e : edgeCollection) {
			if (edgeExistence(tarLabel, srcLabel) == false) {
				return EDGE_NOT_EXIST;
			} else
				weight = e.getWeight();
		}
		return weight;
	}// end of existEdge()

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
		checkEdge(srcLabel, tarLabel);

		for (Edge edge : edgeCollection) {
			String vertex_1 = edge.getVertex1();
			String vertex_2 = edge.getVertex2();

			if (vertex_1.equals(srcLabel) && vertex_2.equals(tarLabel)
					|| vertex_1.equals(tarLabel) && vertex_2.equals(srcLabel)) {
				edge.setWeight(weight);
			}
		}
	} // end of updateWeightEdge()

	public void removeVertex(String vertLabel) {
		List<Edge> newEdgeList = new ArrayList<Edge>();

		if (!vertexCollection.contains(vertLabel)) {
			System.err.println("vertLabel not found");
			return;
		}

		vertexCollection.remove(vertLabel);

		for (Edge e : edgeCollection) {
			if (!(e.getVertex1().equals(vertLabel)) && !(e.getVertex2().equals(vertLabel))) {
				newEdgeList.add(e);
			}

		}
		edgeCollection = newEdgeList;

		numVertex--;
		numEdge = edgeCollection.size();

		createMatrix();
	} // end of removeVertex()

	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
		List<MyPair> inNeighbours = new ArrayList<MyPair>();

		for (Edge e : edgeCollection) {
			int w1 = e.getWeight();
			
			String vertex_2 = e.getVertex2();

			for (MyPair p : myPair) {
				int w2 = p.getValue();
				String label = p.getKey();
				if (w1 == w2 && (vertex_2 == label)) {
					inNeighbours.add(w1, (MyPair) myPair);
				}

			}
		}
		return inNeighbours;
	}
	// end of inNearestNeighbours()
  //NOT FINISHED YET
	public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
	List<MyPair> outNeighbours = new ArrayList<MyPair>();
	
	
	for (Edge e : edgeCollection) {
		int w1 = e.getWeight();
		
	
		String vertex_1 = e.getVertex1();
		for (MyPair p : myPair) {
			int w2 = p.getValue();
			String label = p.getKey();
			if (w1 == w2 && (vertex_1 == label)) {
				outNeighbours.add(w1, (MyPair) myPair);
			}

		}
	}
	return outNeighbours;

	} // end of outNearestNeighbours()
	  //NOT FINISHED YET
	public void printVertices(PrintWriter os) {
		for (String v : vertexCollection) {
			os.print(v + " ");
		}
		os.println(" ");

		os.flush();
	} // end of printVertices()

	public void printEdges(PrintWriter os) {
		for (Edge e : edgeCollection) {
			String v1 = e.getVertex1();
			String v2 = e.getVertex2();

			os.printf("%s %s\n%s %s\n", v1, v2, v2, v1);
		}
		os.flush();
	} // end of printEdges()

} // end of class IncidenceMatrix
