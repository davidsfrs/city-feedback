# Handout

City-Feedback ist ein Online-Abstimmungssystem für Bürgerbefragungen.

---

## Inhaltsverzeichnis

- [1. Git](#1-git)
  - [1.1 Was ist Git und warum sollte es verwendet werden?](#11-was-ist-git-und-warum-sollte-es-verwendet-werden)
  - [1.2 Grundlegende Git-Befehle](#12-grundlegende-git-befehle)
  - [1.3 Branches und Merge-Konflikte](#13-branches-und-merge-konflikte)
  - [1.4 Git mit IntelliJ IDEA und PyCharm](#14-git-mit-intellij-idea-und-pycharm)
    - [Local Repository](#local-repository)
    - [Remote Repository](#remote-repository)
  - [1.5 Nützliche Git-Tools & Plattformen](#15-nützliche-git-tools--plattformen)
  - [1.6 Dokumentation der Zusammenarbeit](#16-dokumentation-der-zusammenarbeit)
- [2. CI/CD](#2-cicd)

---

# 1. Git

Dieses Handout bietet eine Einführung in Git, erklärt die wichtigsten Git-Befehle und zeigt, wie Git mit IntelliJ IDEA und PyCharm verwendet werden kann.

## 1.1 Was ist Git und warum sollte es verwendet werden?

Git ist ein Versionskontrollsystem, das Änderungen an Dateien und Quellcode nachverfolgt und speichert. Dadurch können verschiedene Versionen eines Projekts verwaltet und Änderungen mehrerer Entwickler zusammengeführt werden.

**Vorteile von Git:**
- **Versionskontrolle:** Frühere Versionen und Änderungen können nachvollzogen werden.
- **Zusammenarbeit:** Mehrere Entwickler können parallel am selben Projekt arbeiten.
- **Sicherheit:** Änderungen werden in Commits dokumentiert.
- **Flexibilität:** Branches ermöglichen die Entwicklung neuer Funktionen unabhängig vom Hauptentwicklungszweig.

## 1.2 Grundlegende Git-Befehle

Die folgenden Befehle gehören zu den wichtigsten Grundlagen für die tägliche Arbeit mit Git.

| Befehl | Beschreibung |
|---|---|
| `git init` | Erstellt ein neues lokales Git-Repository. |
| `git add <datei>` | Fügt eine Datei zur Staging-Area hinzu. |
| `git add .` | Fügt alle Änderungen im aktuellen Verzeichnis zur Staging-Area hinzu. |
| `git commit -m "Nachricht"` | Speichert die vorbereiteten Änderungen mit einer Beschreibung in der lokalen Versionshistorie. |
| `git push` | Überträgt lokale Commits in ein Remote Repository. |
| `git pull` | Ruft Änderungen aus einem Remote Repository ab und integriert sie in den aktuellen Branch. |
| `git status` | Zeigt den aktuellen Zustand des Repositorys an. |
| `git log` | Zeigt die bisherige Commit-Historie an. |
| `git clone <url>` | Lädt ein bestehendes Remote Repository herunter. |
| `git branch` | Zeigt vorhandene Branches an. |
| `git switch <branch>` | Wechselt zu einem anderen Branch. |
| `git merge <branch>` | Führt Änderungen eines anderen Branches mit dem aktuellen Branch zusammen. |

**Typischer Ablauf beim Speichern und Hochladen von Änderungen:**

```bash
git add .
git commit -m "Beschreibung der Änderungen"
git push
```

Dabei werden die Änderungen zunächst vorbereitet, anschließend lokal gespeichert und zuletzt auf den Remote-Server übertragen.

## 1.3 Branches und Merge-Konflikte

### Branches

Ein Branch ist ein eigenständiger Entwicklungszweig innerhalb eines Git-Repositorys. Er ermöglicht es, neue Funktionen oder Fehlerbehebungen unabhängig vom Hauptzweig zu entwickeln.

**Beispiel:**

```bash
git switch -c new-feature
```

Dieser Befehl erstellt den Branch `new-feature` und wechselt direkt zu ihm.

### Merge-Konflikte
Bei zwei Branches mit einem Mergekonflikt kann man folgende Stelle manuell ausbessern:

```
<<<<<<< HEAD
Änderung im aktuellen Branch
=======
Änderung aus dem anderen Branch
>>>>>>> anderer-branch
```

Der markierte Bereich, ist ein merge e.g.: von einem Parallelentwickler,
welcher entweder angenommen oderwegrationalisiert wird und kommt vor
wenn zwei Entwickler an ein und dem selben Branch arbeiten.

## 1.4 Git mit IntelliJ IDEA und PyCharm

IntelliJ IDEA und PyCharm sind Entwicklungsumgebungen von JetBrains, die Git direkt integrieren. Dadurch können Git-Befehle über die Benutzeroberfläche ausgeführt werden, ohne das Terminal verwenden zu müssen.

### Local Repository

Ein lokales Repository befindet sich auf dem eigenen Computer und speichert die Versionshistorie des Projekts. Änderungen können somit auch ohne Internetverbindung gespeichert werden.

- **Repository erstellen:** Projekt öffnen -> `VCS → Enable Version Control Integration → Git` (entspricht `git init`).
- **Änderungen speichern:** Im Commit-Fenster die gewünschten Dateien auswählen, eine Commit-Nachricht eingeben und auf `Commit` klicken (entspricht `git add` und `git commit`).
- **Versionshistorie anzeigen:** Über `Git → Log` können frühere Commits und Änderungen eingesehen werden.

### Remote Repository

Ein Remote Repository liegt auf einem externen Server, beispielsweise GitHub oder GitLab. Es ermöglicht mehreren Entwicklern, gemeinsam an einem Projekt zu arbeiten und ihre Änderungen auszutauschen.

- **Repository klonen:** Über `Git → Clone` oder `VCS → Get from Version Control` die URL des Remote Repositorys eingeben und das Projekt herunterladen (entspricht `git clone`).
- **Repository verbinden:** Ein lokales Repository kann über `Git → Manage Remotes` mit einem Remote Repository verbunden werden (entspricht `git remote add origin`).
- **Änderungen hochladen:** Über `Git → Push` werden lokale Commits an das Remote Repository übertragen (entspricht `git push`).
- **Änderungen herunterladen:** Über `Git → Pull` werden Änderungen anderer Entwickler abgerufen und in den lokalen Branch integriert (entspricht `git pull`).

## 1.5 Nützliche Git-Tools & Plattformen

### Plattformen

* **GitHub:** Die weltweit populärste Plattform für Open-Source- und Teamprojekte. Bietet Funktionen wie Pull Requests, Issue-Tracking und GitHub Actions (CI/CD).
* **GitLab:** Eine webbasierte Plattform, die besonders stark auf integrierte DevOps-Pipelines (CI/CD) und Selbsthosting setzt.
* **Bitbucket:** Wird häufig im Unternehmensumfeld eingesetzt und lässt sich nahtlos mit Atlassian-Tools wie Jira oder Confluence verbinden.

### Grafische Git-Tools
* **GitKraken:** Sehr visuelles und übersichtliches Tool für Windows, Mac und Linux. Bietet eine intuitive Darstellung von Branch-Bäumen und vereinfacht das Lösen von Merge-Konflikten.
* **Sourcetree:** Ein kostenloses GUI-Tool von Atlassian, das eine einfache Verwaltung lokaler und remote Repositories ermöglicht.
* **GitHub Desktop:** Ein Einsteiger-freundliches Tool, das sich auf die wesentlichen Git-Funktionen (Commit, Push, Pull, Branching) konzentriert.

## 1.6 Dokumentation der Zusammenarbeit

Die Erstellung und Überarbeitung dieses Handouts wurde von folgenden Personen übernommen:

| Person | Verantwortungsbereich |
|---|---|
| David Scheifers | Einführung in Git, Grundlegende Git-Befehle, Strukturierung des Handouts |
| Michael Kress | Branches, ihre Verwendung und der Umgang mit Merge-Konflikten |
| Torben Jost | Verwendung von Git mit IntelliJ IDEA und PyCharm, Local und Remote Repository |
| Cindy Bella | Nützliche Git-Tools und Plattformen sowie Überarbeitung der Dokumentation |

---

# 2. CI/CD

CityFeedback verwendet GitHub Actions zur Continuous Integration.
Bei jedem Push und bei Pull Requests nach `main` werden Backend und Frontend automatisch geprüft.

### Warum GitHub Actions?
Das Repository liegt bereits auf GitHub. GitHub Actions ist deshalb eine naheliegende Wahl: Die Workflow-Dateien liegen direkt im Repository, Commits und Pull Requests können automatisch geprüft werden, und die Ergebnisse sind auf GitHub sichtbar. Man benötigst für den Einstieg weder einen eigenen Jenkins-Server noch eine separate CI-Plattform.

### Pipeline
- Backend: Java 21, Maven, automatisierte Tests und Build
- Frontend: Node.js, npm und Produktions-Build
- Fehlerhafte Prüfungen führen zu einem fehlgeschlagenen Workflow.

### Deployment
Ein automatisches Deployment ist für eine spätere Projektphase
vorgesehen. Die aktuelle Pipeline führt noch kein Deployment aus.