# cw-temple

Basically, we need to implement an algorithm to find an Euler cycle for a given starting vertex in an undirected graph. An actual game would like use the A* approach, which is a variation of Djikstra's which accounts for varying distances "costs" of edges. The instructions state to use depth first, but a combination of breadth and depth first, such as Depth Limited Search, using recursiveness and a limit to the initial depth searched may be faster, and get the large bonus multiplier.

Depth First Search by Tremaux is an algorithm for searching graphs and and trees, and it can be useful for traversing quickly into deeper search domains. One starts at the root node (the staircase tile) and explores as far as possible along each branch before backtracking. We need to keep track of visited squares and avoid them unless we have to backtrack.

Breadth First Search would mean expand this root node, by looking at each of its neighbouring tiles. Then we expand their successors and so on, recursively searching every level of the binary tree going deeper and deeper until we find the shortest possible path. Due to the fact that this strategy for graph traversal has no additional information about states beyond that provided in the problem definition, Breadth First Search is classed as an uninformed or blind search.

DFS and BFS use a queue data structure which is a 'First in, First Out' or FIFO data structure. This queue stores all the nodes that we have to explore and each time a node is explored it is added to our set of visited nodes.

The question implies trying other methods. I believe we should use Dijkstra's algorithm to find the shortest path. Dijkstra's being more or less, BFS with a Priority Queue. When Dijkstra algorithm is run on unweighted graph, at any time, the Priority Queue contains at most two distinct (distance) values. Therefore, a FIFO queue of BFS suffices.