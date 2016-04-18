package student;

public class MatrixNode implements Comparable<MatrixNode> {

    long node;
    Integer distance;
    
    public MatrixNode(long node, int distance){
        this.node = node;
        this.distance = distance;
    } 

    public long getNode(){
        return node;
    }

    public int getDistance(){
        return distance;
    }

    @Override
    public int compareTo(MatrixNode other) {
        return Integer.compare(distance, other.distance);
    }

}