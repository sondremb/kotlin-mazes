# Labyrint-genererende funksjoner

Det er mange forskjellige måter å generere labyrinter på.

Prøv å implementere algoritmen slik at den kan håndtere de abstrakte `Grid` og `Cell`-klassene, slik at du kan prøve ut
algoritmen på forskjellige typer grids!
Det kan hende du må utvide `Grid`-klassen med noen metoder for å få det til å fungere.


> 💡 **Tips! Holde styr på "unvisited" celler**
>
> Ofte står det i pseudokoden at man skal markere celler som unvisited eller visited.
> Hvis celler som markeres som visited, også alltid linkes til en annen celle, kan man bruke hvorvidt cellen har en
> lenke for å sjekke om den er visited.
> Altså ved å sjekke `cell.links.isEmpty()` eller `cell.links.isNotEmpty()`

## Aldous-Broder

```raw
choose a random cell
set remaining count to number of cells - 1
while remaining count > 0
    choose a random neighbor of current cell
    if neighbor has no links
        link current cell and neighbor
        remaining count -= 1
        
    move to the neighbor
```

## Wilson's

```raw
mark all cells as unvisited
choose a random cell and mark it as visited

while there are unvisited cells
    current = random unvisited cell
    path = [current]
    while current is unvisited
        current = random neighbor of current
        if current is in path
            remove all cells in path after current
        else
            append current to path
    
    for each cell in path
        link cell to the next cell in path
        mark cell as visited
```

## Hunt and kill

```raw
mark all cells as unvisited
current = random cell
mark current as visited
while there are unvisited cells
    candidates = neighbors of current that are unvisited
    if candidates is not empty
        next = random candidate
        link current and next
        mark next as visited
        current = next
    else
        current = unvisited cell with at least one visited neighbor
        neighbor = random visited neighbor of current
        link current and neighbor
        mark current as visited
```

## Recursive backtracker

```raw
mark all cells as unvisited
first = random cell (or start at a specific cell)
mark first as visited
stack = [first]
while stack is not empty
    current = stack.pop()
    candidates = neighbors of current that are unvisited
    if candidates is not empty
        next = random candidate
        link current and next
        mark next as visited
        stack.push(next)
    else
        stack.pop()
```

## Andre

- Binary tree
- Sidewinder

- Eller's
- Kruskal's
- Growing tree
- Prim's
- Recursive division