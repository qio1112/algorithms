package graph;

public class Edge {

    private final Vertex from;
    private final Vertex to;
    private final int weight;

    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(Vertex from, Vertex to) {
        this(from, to, 1);
    }


    public Integer getWeight() {
        return weight;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    @Override
    public int hashCode() {
        return 47 * from.getIndex() + 71 * to.getIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Edge other = (Edge) obj;
        return this.from.equals(other.getFrom()) && this.to.equals(other.getTo()) && this.weight == other.getWeight();
    }

    @Override
    public String toString() {
        return "FROM " + from + " TO " + to;
    }
}
