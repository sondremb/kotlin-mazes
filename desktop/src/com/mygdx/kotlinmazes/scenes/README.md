# Scener

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

Bare lag en `main()`-funksjon, og kall `playScene()` med scenen du vil kjøre som parameter:

```kotlin
void main () {
    playScene(MyCoolScene())
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

⚠️ OBS: Merk at på MacOS kan støte på følgende feilmelding:

```
GLFW may only be used on the main thread and that thread must be the first thread in the process. Please run the JVM with -XstartOnFirstThread. This check may be disabled with Configuration.GLFW_CHECK_THREAD0.
```

Løsningen er som det står i feilmeldingen: kjør JVMen med `-XstartOnFirstThread`-flagget. Dette kan gjøres i IntelliJ
ved å klikke på "kjør"-knappen, og så "Edit Configurations...". Deretter kan du legge til flagget i "VM options"-feltet.