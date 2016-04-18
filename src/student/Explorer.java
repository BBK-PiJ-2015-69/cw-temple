package student;

import game.EscapeState;
import game.ExplorationState;

public class Explorer {

    /**
     * Explore the cavern, trying to find the orb in as few steps as possible.
     * Once you find the orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb,
     * it will count as a failure.
     * <p>
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * <p>
     * At every step, you only know your current tile's ID and the ID of all
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles).
     * <p>
     * To get information about the current state, use functions
     * getCurrentLocation(),
     * getNeighbours(), and
     * getDistanceToTarget()
     * in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * <p>
     * Use function moveTo(long id) in ExplorationState to move to a neighboring
     * tile by its ID. Doing this will change state to reflect your new position.
     * <p>
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     *
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
        /* the National Debt of the UK is high, and George "Gideon" Osbourne's boss
        Cameron is furious. George needs to find the Orb beneath the Palace of 
        Westminster as quickly as possible so as to unlock hidden wealth */
        
        /* create a matrix to hold all nodes unvisited by Gideon
        this Matrix call will create hashmaps for:
        unvisited, visited, distances, and edge costs */
        HashMap matrix = new HashMap();
        
        //add start node and distance to matrix ?

        //when the distance to target is not zero, i.e. we have not reached the orb
        do{

            //currentLocation is the current position of Osbourne figure, the starting point
            long currentLocation = state.getCurrentLocation();

            //set evaluationNodes to clear
            List<Long> evaluationNodes = null;

            /* we get the neighbouring nodes of the starting point, where Gideon currently stands
                and add each one to the matrix as an egde
                and also add the distance */
            for(NodeStatus neighbouringNode : state.getNeighbours() ){
                matrix.addEdge(currentLocation, neighbouringNode.getId());
                matrix.addDistance(neighbouringNode.getId(), neighbouringNode.getDistanceToTarget());
            }
                Set<Long> neighboursList = matrix.getNeighbours(currentLocation);

        //keep this looping until distance is zero, i.e. reach the Orb
        }while(state.getDistanceToTarget() != 0);

        //we call the nearest nodes, neighbours, to the current location, for evaluation
        evaluationNodes = matrix.getEvaluationNode(currentLocation, true);

        //if there are evalution node, we proceed to evaluate which is the best for Gideon to progress to
        if(evaluationNodes != null && evaluationNodes.size() > 0){
                //we check if the evaluationNodes are in neighboursList
               if(neighboursList.contains(evaluationNodes.get(0))){
                   //if they are, we update the matrix to say Gideon has visited the current tile he stands on
                   matrix.addVisit(currentLocation);
                   //we then move Gideon to the evaluationNode
                   state.moveTo(evaluationNodes.get(0));
                }
        }

    }

    /**
     * Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * <p>
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
     * will return a collection of all nodes on the graph.
     * <p>
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * <p>
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed run.
     * <p>
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold.
     *
     * @param state the information available at the current state
     */
    public void escape(EscapeState state) {
         /* George "Gideon" Osbourne, Chancellor of the Exchequer, has found the Orb.
        Now he needs to get lots of gold to clear the National Debt and to cheer up
        his boss Cameron, who is being questioned about offshore holdings */

        //create a matrix to hold unvisited nodes
        Matrix matrix = new Matrix();

    }
}