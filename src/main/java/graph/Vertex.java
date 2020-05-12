package graph;

public class Vertex {

    public static int VERTEX_INDEX = 0;

    private final int index;
    private String value;

    public Vertex() {
        this.index = Vertex.VERTEX_INDEX++;
        this.value = "V_" + index;
    }

    public Vertex(int index) {
        this.index = index;
        if (VERTEX_INDEX <= index) {
            VERTEX_INDEX = index + 1;
        }
        this.value = "V_" + index;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return this.index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Vertex other = (Vertex) obj;
        return other.getIndex() == this.getIndex();
    }

    public void print() {
        System.out.println(this);
    }
}
