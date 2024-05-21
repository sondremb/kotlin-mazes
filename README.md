# Labyrint-lab i Kotlin

<p align="center">
<img alt="masked.png" src="assets/masked.png" width="80%"/>
</p>

*Basert på boken "Mazes for Programmers" av Jamis Buck*

## Hva kan jeg gjøre?

- Implementer flere labyrint-algoritmer. Sjekk ut README-fila i `mazegeneration`-pakken!
- Implementer flere typer grids. Sjekk ut README-fila i `grids`-pakken!
- Lag en ny scene! For eksmepl:
    - Ta inspirasjon fra `scenes/SquareGridDistanceClick.kt` og lag en scene hvor du klikker på to celler for å tegne
      raskeste vei mellom de to
    - Lag en scene hvor du lager labyrinter ved å klikke på celler for å linke de til hverandre
    - Maskering - "drep" celler ved å fjerne koblingen til naboenene, for å lage "maskerte" labyrinter med unike
      former (som bildet over)

## Feilmelding ved kjøring på MacOS

⚠️ OBS: Merk at på MacOS kan støte på følgende feilmelding:

```
GLFW may only be used on the main thread and that thread must be the first thread in the process. Please run the JVM with -XstartOnFirstThread. This check may be disabled with Configuration.GLFW_CHECK_THREAD0.
```

Løsningen er som det står i feilmeldingen: kjør JVMen med `-XstartOnFirstThread`-flagget. Dette kan gjøres i IntelliJ
ved å klikke på "kjør"-knappen, og så "Edit Configurations...". Deretter kan du legge til flagget i "VM options"-feltet.