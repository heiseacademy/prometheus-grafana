# Heise Academy
## Prometheus und Grafana Programmbeispiele

Dies Repository stellt die einzelnen Programmstände des _Monitoring mit Prometheus und Grafana_ Videokurses bei Heise Academy bereit.

Damit der Zugriff möglichst einfach ist, gibt es für jeden Stand einen einzelnen Ordner.
So kannst Du auch zwei Stände einfach miteinander vergleichen.
Die einzelnen Stände bauen dabei aufeinander auf. 
Der `stage-1`-Ordner enthält etwa das Basis-Beispiel, während in `stage-9` der Endstand _nach_ der letzten Prometheus-Übung zu finden ist.

Die Beispiele, die nur Prometheus behandeln sind in den `stage-`-Ordnern enthalten.  
Die Beispiele, in denen zusätzlich Grafana genutzt wird, sind in den `stage-grafana-`-Ordnern enthalten.

## Setup (Linux)

Für Linux-Verwender ist es aufgrund des Berechtigungssystems erforderlich, die Inhalte des `content`-Ordners für alle Nutzer schreibbar zu machen.
Ansonsten können die Docker-Umgebungen nicht fehlerfrei hochgefahren werden.
Dazu den folgenden Befehl auf der Hauptebene eingeben:

```
chmod -R a+w content
```

Falls bereits versucht worden ist, das Setup zu starten, muss der Befehl ggf. mit `sudo` wiederholt werden:

```
sudo chmod -R a+w content
```
