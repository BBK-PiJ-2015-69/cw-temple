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

            /* we just added neighbours to the matrix
            and now we make a call to the matrix of details of those neighbours */
            Set<Long> neighboursList = matrix.getNeighbours(currentLocation);

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
            /* however, if there no evaluationNodes to consider, 
            Gideon will have to backtrack and try another branch */
            }else{ 
            
                /* we make a call to the matrix data, 
                but as there are no nodes to evaluate, we have the second parameter set to false. 
                we are requesting the keySet, the content of the maze
                this being unvisited nodes, which are sorted into distance order
                and therefore, we are provided with the nearest unvisited node
                */
                evaluationNodes = matrix.getEvaluationNode(currentLocation, false);

                //counter set to zero
                int n=0;
                //add to gideonsMove first of evaluationNodes - index 0
                long gideonsMove = evaluationNodes.get(n);
                //while gideonsMove features evaluation node with shared egde with current location
                // and not visited this evaluation node more than twice  
                
                while( (gideonsMove == currentLocation) && matrix.getVisits(gideonsMove)<=2){
                    //add to gideonsMove rest of evaluationNodes - incrementing by 1 from index 0
                    gideonsMove = evaluationNodes.get(n);
                    //increment
                    n++;
                }

                //use dijkstras algorithim to get the shortest path between the current location and gideonsMove
                List<Long> dijkstrasPath = matrix.getDijkstrasPath(currentLocation, gideonsMove);
                long dijkstrasPathPlus;

                //scan possible paths
                for(int i=0; i <= (dijkstrasPath.size()-2); i++){
                    //if possible path
                    if(dijkstrasPath.get(i) != null){
                        //attempt plus one from current location
                        dijkstrasPathPlus = dijkstrasPath.get(i+1);
                        //move Gideon to dijkstrasPath plus one
                        state.moveTo(dijkstrasPathPlus);
                        //update the matrix to note that Gideon visited this tile
                        matrix.addVisit(currentLocation);
                        //update current location to the new position of Gideon, e.g. dijkstrasPath.get(i+1)
                        currentLocation = state.getCurrentLocation();
                    }
                }
                
            }

        //keep this looping until distance is zero, i.e. reach the Orb
        }while(state.getDistanceToTarget() != 0);

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

        //get the Vertices
        Collection<Node> vertices = state.getVertices(); 
        
        //unvisitedEscapeNodes lists the unvisited nodes
        Map unvisitedEscapeNodes = new HashMap(999);

        //scan the vertices
        for(Node v : vertices){

            //scan for vertices/edges relating to current position of Gideon
            for(Edge e: v.getExits()){
                matrix.addEdgeCost(e.length(), e.getSource().getId(), e.getDest().getId());
            }

            //add to unvisitedEscapeNodes the vertex id and details
            unvisitedEscapeNodes.put(v.getId(), v);
            
            //Gideon needs to grab some gold to wipe the National Debt
            matrix.addGold(v.getId(), v.getTile().getGold());

        }

        //set currentLocation to current state
        long currentLocation = state.getCurrentNode().getId();
        //get the exit from this dungeon
        long dungeonExit = state.getExit().getId();

        //use dijkstra's algorithm to find the shortest path between the currentLocation and the exit
        List<Long> dijkstrasPath = matrix.getDijkstrasPath(currentLocation, dungeonExit);
        
        //consider the options returned for Gideon by dijkstras algorithm
        for(Long gideonsOption : dijkstrasPath){

            //if there is an option for Gideon, and it is not where he currently stands
            if(gideonsOption != null && !gideonsOption.equals((Long) currentLocation)){

                //then move Gideon to that option
                state.moveTo( (Node) unvisitedEscapeNodes.get(gideonsOption) );
                
                //if the tile has gold, Gideon must pick it up; the UK National Debt must be cleared
                if(matrix.queryGold(gideonsOption)){
                    state.pickUpGold();
                }
                
                //note to the matrix as visited the new tile that Gideon has progressed to
                matrix.addVisit(currentLocation);
                //update the record of currentLocation to reflect Gideon's new position
                currentLocation = state.getCurrentNode().getId();

            }

        }

        //get Gideon out the room before the roof falls in
        state.moveTo(state.getExit());

    }
}