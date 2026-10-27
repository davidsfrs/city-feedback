# Handout

City-Feedback ist ein Online-Abstimmungssystem für Bürgerbefragungen.

## Git

### Inhaltsverzeichnis
* [Was ist Git und warum sollte es verwendet werden?](#was-ist-git-und-warum-sollte-es-verwendet-werden)
* [Grundlegende Git-Befehle](#grundlegende-git-befehle)
* [Branches und ihre Nutzung, Umgang mit Merge-Konflikten](#branches-und-ihre-nutzung-umgang-mit-merge-konflikten)
* [Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository](#git-mit-intellijpycharm-benutzen-local-repository-und-remote-repository)
* [Nützliche Git-Tools und Plattformen](#nützliche-git-tools-und-plattformen)

### Was ist Git und warum sollte es verwendet werden?
Git ist ein Versionskontrollsystem, das jede Änderung an Dateien oder Quellcode nachverfolgt, speichert und somit die Zusammenarbeit an Projekten erleichtert.

### Grundlegende Git-Befehle
- ```git init```: Erstellt ein neues, leeres Git-Repository in deinem aktuellen Ordner.
- ```git add```: Fügt geänderte Dateien zur Staging-Area hinzu, um sie für den nächsten Commit vorzubereiten.
- ```git commit```: Speichert die vorbereiteten Änderungen dauerhaft als neuen Snapshot (mit einer kurzen Beschreibung) in der lokalen Historie
- ```git push```: Lädt deine lokalen Commits hoch auf das entfernte Server-Repository

### Branches und ihre Nutzung, Umgang mit Merge-Konflikten
Bei zwei Branches mit einem
Mergekonflikt kann man 
folgende Stelle manuell ausbessern

1. <<<<HEAD Bereich>>> Branch name

Der markierte Bereich, ist ein merge 
e.g.: von einem Parallelentwickler,
welcher entweder angenommen oder
wegrationalisiert wird und kommt vor
wenn zwei Entwickler an ein
und dem selben Branch arbeiten.

### Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository

IntelliJ IDEA und PyCharm sind Entwicklungsumgebungen von JetBrains, die Git direkt integrieren. Dadurch können Git-Befehle über die Benutzeroberfläche ausgeführt werden, ohne das Terminal verwenden zu müssen.

**Local Repository**

Ein lokales Repository befindet sich auf dem eigenen Computer und speichert die Versionshistorie des Projekts. Änderungen können somit auch ohne Internetverbindung gespeichert werden.

- **Repository erstellen:** Projekt öffnen -> `VCS → Enable Version Control Integration → Git` (entspricht `git init`).
- **Änderungen speichern:** Im Commit-Fenster die gewünschten Dateien auswählen, eine Commit-Nachricht eingeben und auf `Commit` klicken (entspricht `git add` und `git commit`).
- **Versionshistorie anzeigen:** Über `Git → Log` können frühere Commits und Änderungen eingesehen werden.

*Remote Repository**

Ein Remote Repository liegt auf einem externen Server, beispielsweise GitHub oder GitLab. Es ermöglicht mehreren Entwicklern, gemeinsam an einem Projekt zu arbeiten und ihre Änderungen auszutauschen.

- **Repository klonen:** Über `Git → Clone` oder `VCS → Get from Version Control` die URL des Remote Repositorys eingeben und das Projekt herunterladen (entspricht `git clone`).
- **Repository verbinden:** Ein lokales Repository kann über `Git → Manage Remotes` mit einem Remote Repository verbunden werden (entspricht `git remote add origin`).
- **Änderungen hochladen:** Über `Git → Push` werden lokale Commits an das Remote Repository übertragen (entspricht `git push`).
- **Änderungen herunterladen:** Über `Git → Pull` werden Änderungen anderer Entwickler abgerufen und in den lokalen Branch integriert (entspricht `git pull`).

### Nützliche Git-Tools und Plattformen
... *Cindy Bella*
