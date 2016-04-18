package student;
import java.util.*;
import java.lang.IllegalArgumentException;

public class Matrix{

    private Map unvisited;
    private Map visited;

    public Matrix(){

        unvisited = new HashMap(999);
        visited = new HashMap(999);

    }

     public void addEdge(long alpha, long beta){
        
        if(!unvisited.containsKey(alpha)){
            unvisited.put(alpha, new HashSet<Long>());
        }
        Set alphaEdges = (Set) unvisited.get(alpha);
        alphaEdges.add(beta);

        if(!unvisited.containsKey(beta)){
            unvisited.put(beta, new HashSet<Long>());
        }
        Set betaEdges = (Set) unvisited.get(beta);
        betaEdges.add(alpha);
	
	}	

    public void addDistance(long id, int targets){
        
        if(!targets.containsKey(id)){
            targets.put(id, targets);
        }

    }

 	public List<Long> getEvaluationNode(long id, boolean neighboursExist){
        
        //store here the nodes
        Set<Long> evaluationNodes;

        //if now neighbouring nodes
        if(!neighboursExist){
            //add all unvisited nodes
            evalutionNodes = unvisited.keySet();
        }else{
            //add neighbouring nodes
            evaluationNodes = getNeighbours(id);
        }
        
        return evaluationNodes;

    }   

}