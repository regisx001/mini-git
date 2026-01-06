# UML Analysis

This section presents a Unified Modeling Language (UML) analysis of the MiniGit system. The goal is to formally describe the **static structure** and **dynamic behavior** of the MVP using standard UML views. These diagrams serve as a blueprint for implementation and ensure clarity in system responsibilities and interactions.

The UML analysis is divided into:

1. Use Case Diagram
2. Package Diagram
3. Class Diagram
4. Sequence Diagrams (Key Commands)

---

## 1. Use Case Diagram

### Actors

* **User** – interacts with MiniGit via the command line

### Use Cases (MVP Scope)

* Initialize repository
* Stage files
* Create commit
* View commit history
* View repository status

### Textual UML Representation

```
Actor: User

User --> Init Repository
User --> Add Files
User --> Commit Changes
User --> View Log
User --> Check Status
```

### Notes

* All interactions are local
* No external systems or remote actors exist in the MVP

---

## 2. Package Diagram

The package diagram illustrates the **high-level modular structure** and dependency directions.

### Packages

```
+-------------------+
| cli               |
+-------------------+
          |
          v
+-------------------+
| core              |
+-------------------+
          |
          v
+-------------------+
| domain            |
+-------------------+
          |
          v
+-------------------+
| storage           |
+-------------------+
          |
          v
+-------------------+
| filesystem        |
+-------------------+
```

### Dependency Rules

* Dependencies flow **downward only**
* No cyclic dependencies
* Upper layers do not access filesystem directly

---

## 3. Class Diagram (Core MVP)

This diagram describes the **static structure** of the system.

---

### 3.1 CLI Layer

```
+------------------+
| Main             |
+------------------+
| + main(args[])   |
+------------------+
        |
        v
+------------------+
| CommandParser    |
+------------------+
| + parse(args[])  |
+------------------+
```

**Responsibilities**

* Argument parsing
* Command dispatch

---

### 3.2 Core Command Layer

```
+----------------------+
| Command (interface)  |
+----------------------+
| + execute()          |
+----------------------+
          ^
          |
----------------------------------------------
|        |        |        |        |
v        v        v        v        v
InitCmd  AddCmd  CommitCmd LogCmd  StatusCmd
```

Each command:

* Validates preconditions
* Orchestrates domain + storage interactions

---

### 3.3 Domain Model

```
+------------------+
| Blob             |
+------------------+
| - content: byte[]|
| + serialize()    |
+------------------+

+------------------+
| Tree             |
+------------------+
| - entries: List  |
| + serialize()    |
+------------------+

+------------------+
| TreeEntry        |
+------------------+
| - mode: String   |
| - name: String   |
| - hash: String   |
+------------------+

+------------------+
| Commit           |
+------------------+
| - treeHash       |
| - parentHash     |
| - author         |
| - timestamp      |
| - message        |
| + serialize()    |
+------------------+
```

### Notes

* Domain objects are immutable
* No filesystem knowledge exists here

---

### 3.4 Storage Layer

```
+----------------------+
| ObjectStore          |
+----------------------+
| + writeObject()      |
| + readObject()       |
| + exists(hash)       |
+----------------------+

+----------------------+
| RefStore             |
+----------------------+
| + readHEAD()         |
| + updateBranch()     |
| + readBranch()       |
+----------------------+

+----------------------+
| Index                |
+----------------------+
| + addEntry()         |
| + readEntries()      |
| + clear()            |
+----------------------+
```

---

### 3.5 Filesystem Layer

```
+----------------------+
| FileSystemService    |
+----------------------+
| + readFile()         |
| + writeFile()        |
| + createDir()        |
+----------------------+
```

---

## 4. Sequence Diagrams (Key Workflows)

---

## 4.1 `init` Command Sequence

```
User
 ↓
CLI
 ↓
InitCommand
 ↓
FileSystemService
```

### Flow

1. User runs `minigit init`
2. CLI parses command
3. InitCommand creates `.minigit`
4. HEAD, refs, index created
5. Control returns to user

---

## 4.2 `add` Command Sequence

```
User
 ↓
CLI
 ↓
AddCommand
 ↓
Blob
 ↓
ObjectStore
 ↓
Index
```

### Flow

1. Read file from filesystem
2. Create Blob
3. Hash and store Blob
4. Update index entry

---

## 4.3 `commit` Command Sequence

```
User
 ↓
CLI
 ↓
CommitCommand
 ↓
Index
 ↓
Tree
 ↓
ObjectStore
 ↓
Commit
 ↓
RefStore
```

### Flow

1. Validate index not empty
2. Create Tree from index
3. Store Tree
4. Create Commit
5. Store Commit
6. Update branch reference
7. Clear index

---

## 5. Design Constraints Captured by UML

* Objects are immutable
* Commands are stateless
* Storage is append-only
* Branches are lightweight references

---

## 6. Benefits of This UML Design

* Clear separation of concerns
* Easy testability
* Predictable behavior
* Straightforward extensibility

---

## Summary

This UML analysis defines the **structural and behavioral blueprint** of MiniGit’s MVP. The diagrams ensure that responsibilities are well-scoped, dependencies are controlled, and system behavior is deterministic.
