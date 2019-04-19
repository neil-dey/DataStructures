package datastructs;
public class AdjacencyList<E> {
    public List<Vertex> vertices;
    public int numEdges;

    public AdjacencyList() {
        this.vertices = new ArrayList<Vertex>();
        this.numEdges = 0;
    }

    public class Vertex {
        private E data;
        private List<Edge> edges;

        public Vertex(E data) {
            this.data = data;
            this.edges = new LinkedList<Edge>();
        }

        public void addEdge(Vertex v) {
            this.edges.add(new Edge(this, v));
        }
    }

    public class Edge {
        private Vertex v1;
        private Vertex v2;
        private int weight;

        public Edge(Vertex v1, Vertex v2, int weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        public Edge(Vertex v1, Vertex v2) {
            this(v1, v2, 0);
        }
    }

}
