# Løsing av labyrinter

Okei, you got me. Her har jeg åpenbart ikke lagt inn like mye innsats.

## Animert DFS

Depth-first search er allerede implementert - men du kan gjøre den animert!  
Ta inspirasjon fra algoritmegenereringsverdenen - [mazegeneration/BinaryTree.kt](../mazegeneration/BinaryTree.kt)
for hvordan man lager selve algoritmen, og
[scenes/BinaryTreeAnimated.kt](../scenes/BinaryTreeAnimated.kt) for hvordan man lager en animasjon.

For at det skal se ålreit ut, er det lurt om man klarer å se hvilke celler som er besøkt (`visited`), og hvilke som skal
besøkes (`stack`).
Hvis du gjør den animerte versjonen til en klasse, og lar `visited` og `stack` være ikke private, kan du tegne disse med
egne farger i scenen for å se hva som skjer underveis.

Bytt ut `depthFirstSearch` med en animert versjon i [scenes/DfsClick.kt](../scenes/DfsClick.kt)

## Andre algoritmer

Du kan også implementere flere algoritmer for å løse labyrinter, for eksempel:

* Breadth-first search
* Dijkstra's
* A*
* "Hold til høyre"