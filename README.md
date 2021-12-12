# AStarSearch
When you compile a program that uses the JavaFX library and the source files I provided.
You can press run. If everything works well you get a 30x50 grid where you can put walls, goals and a starting point.
If you click on solve it will find the shortest path and indicate which fields where visited with a red and display the path it found.

Note: here I used the assumption that when a field is already visited, that was always through the shortest path. I am not 100% certain if that is also the case.
So far I could not think of a counter example. If this is not true, I would not only keep (visited) but a pair: (visited, shortest distance). In that case I would make the criteria:
(!visited || newState.length < length). And then also update the pair at the node.  

Update: a node is only visited when there is no shorter way to that node. This is because the estimated minimum cost from that node to the goal is constant, so then the choice is purely based on the actual lenght of the road froms start that node. 
