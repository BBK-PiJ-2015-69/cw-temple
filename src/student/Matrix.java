package student;
import java.util.*;
import java.lang.IllegalArgumentException;

public class Matrix{

	private final int MATRIX_HASHES_INIT = 999;
    private Map unvisited;
    private Map visited;
    private Map targets;
    private Map edgeCosts;

    public Matrix(){

        unvisited = new HashMap(MATRIX_HASHES_INIT);
        visited = new HashMap(MATRIX_HASHES_INIT);
        targets = new HashMap(MATRIX_HASHES_INIT);
        edgeCosts = new HashMap(MATRIX_HASHES_INIT);
        //gold??

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

    public void addEdgeCost(int distance, long alpha, long beta){

       List<Long> alphaBetaCost = new ArrayList(); 
       
       edgeCosts.put(alphaBetaCost, distance);
       
       addEdge(alpha, beta);
    
    }

    public void addDistance(long id, int targets){
        
        if(!targets.containsKey(id)){
            targets.put(id, targets);
        }

    }

 	public List<Long> getEvaluationNode(long id, boolean neighboursExist){
        
        //store here the nodes
        Set<Long> nodes;

        //if now neighbouring nodes
        if(!neighboursExist){
            //add all unvisited nodes
            nodes = unvisited.keySet();
        }else{
            //add neighbouring nodes
            nodes = getNeighbours(id);
        }
        
        //store here possible needs to visit
        List<MatrixNode> availableNodes = new ArrayList(); 

        //scan nodes
        for(long n : nodes){

            //if unvisited
            if(!visited.containsKey(n)){

                //get distance
                int distance = (Integer) targets.get(n);
                //add to availableNodes
                availableNodes.add(new MatrixNode(n, distance));

            }

        }

        //store here nodes to evaluate
        List<Long> evaluationNodes = new ArrayList();
        //sort the avialable unvisited nodes
        Collections.sort(availableNodes);
        
        //scan available nodes and add to evaluationNodes, now sorted in order
        for (MatrixNode available : availableNodes){
            evaluationNodes.add(available.getNode());
        }
        
        return evaluationNodes;

    }

    public void addVisit(long id){

        int i = 1;
        
        if(visited.containsKey(id)){
            i += (int) visited.get(id); 
        } 
        
        visited.put(id, i);

    }   

}