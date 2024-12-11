# Scener

## Forslag til ting å gjøre

Scenen [SquareGridDistance.kt](SquareGridDistance.kt) har flotte farger, men savner en labyrint.

Scenen [DrawHex.kt](DrawHex.kt) er litt trist - det er bare et Hex-grid, uten noe labyrint 😢. Følg stegene der inne for
å gi scenen litt liv!

Lag en scene hvor du kan klikke på en celle, så tegnes veien til den fjerneste cellen.

Lag en scene hvor du starter i en celle, og navigerer seg rundt med piltastene.

Lag en scene hvor du starter i en celle, og navigerer seg rundt med musa - men kan ikke gå forbi vegger.

Titt innom de eksisterende scenene, og bli inspirert!

## Å lage en ny Scene

`init()` kalles når scenen blir laget.

`update()` og `draw()` - disse kalles ved hver frame. `update()` kalles først, så `draw()`.
Det er ingen forskjell mellom de to annet enn at `update()` kalles først, men det kan være lurt å putte logikk og
tilstandsoppdatering i `update()`, og tegning i `draw()`.

For å tegne kan du bruke `shapeRenderer`-objektet som er en del av baseklassen.
For geometri relativ til vinduet kan du bruke feltene i det globale `EngineConfig`-objektet.
For eksempel, for å tegne en rød sirkel i midten av vinduet kan du gjøre noe sånt som dette:

```kotlin
override fun draw() {
    shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
        it.circle(EngineConfig.VIEWPORT_CENTER, EngineConfig.VIEWPORT_HEIGHT / 2f)
    }
}
```

## Å kjøre en Scene

Bare lag en `main()`-funksjon, og kall `.play()` på scenen du vil kjøre.

```kotlin
fun main() {
    MyCoolScene().play()
}

class MyCoolScene : Scene() {
    override fun init() {
        // ...
    }

    override fun update() {
        // ...
    }

    override fun draw() {
        // ...
    }
}
```

