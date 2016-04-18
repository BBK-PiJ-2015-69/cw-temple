package student;

public class MatrixNode implements Comparable<MatrixNode> {

    long node;
    Integer distance;
    
    public MatrixNode(long node, int distance){
        this.node = node;
        this.distance = distance;
    } 

}