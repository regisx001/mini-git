# Architecture Overview

MiniGit is designed as a **layered, modular system** that closely mirrors the conceptual architecture of Git while remaining intentionally simple. Each layer has a well-defined responsibility, ensuring separation of concerns, testability, and long-term maintainability.

The system treats the **local filesystem as the primary persistence layer**, storing all repository data in a hidden directory within the working tree. All versioned data is represented using **immutable, content-addressable objects**, allowing the repository state to be reconstructed at any point in time.

---

## High-Level Architecture

At a high level, MiniGit is composed of five logical layers:

```
Command Line Interface (CLI)
        ↓
Command Orchestration Layer
        ↓
Domain Model (Git Objects)
        ↓
Storage Layer
        ↓
File System
```

Each layer depends only on the layer directly below it, forming a **unidirectional dependency flow**.

---

## Layer Responsibilities

### 1. Command Line Interface (CLI)

The CLI layer is responsible solely for:

* Parsing user input and command-line arguments
* Validating basic syntax
* Dispatching commands to the core command layer

This layer contains **no business logic** and has no knowledge of how repository data is stored or manipulated.

---

### 2. Command Orchestration Layer

The command orchestration layer acts as the **application core**. It coordinates high-level operations such as initializing a repository, staging files, creating commits, and traversing history.

Responsibilities include:

* Enforcing command preconditions (e.g., repository existence)
* Coordinating interactions between domain objects and storage
* Handling command-level errors and reporting them to the user

Each command is implemented as a distinct, self-contained operation.

---

### 3. Domain Model (Version Control Objects)

The domain model represents the **fundamental data structures** of the version control system. These objects are pure and immutable representations of repository state.

Core domain objects include:

* **Blob** – represents file contents
* **Tree** – represents directory structure
* **Commit** – represents a snapshot in history

These objects are independent of how they are stored on disk and contain no filesystem logic.

---

### 4. Storage Layer

The storage layer bridges the gap between the domain model and the filesystem. It is responsible for:

* Serializing and deserializing domain objects
* Computing and managing object hashes
* Persisting objects in a content-addressable layout
* Reading and updating references (HEAD and branches)

This layer ensures that objects, once written, are **never modified**, preserving immutability.

---

### 5. File System Layer

The filesystem layer provides low-level access to the operating system’s file APIs. Its responsibilities are limited to:

* Reading and writing raw files
* Creating directories
* Resolving paths

All higher-level semantics are handled by upper layers, keeping filesystem operations simple and predictable.

---

## Repository Structure

MiniGit stores all repository data in a dedicated hidden directory within the working tree:

```
.minigit/
├── HEAD
├── index
├── objects/
│   ├── ab/
│   └── cd/
└── refs/
    └── heads/
        └── main
```

This structure mirrors Git’s internal layout while remaining minimal and human-readable.

---

## Data Flow Example: Creating a Commit

The following illustrates how data flows through the system when a commit is created:

1. The CLI parses the `commit` command and its arguments.
2. The command layer validates repository state and staging area contents.
3. Domain objects (Tree and Commit) are constructed in memory.
4. The storage layer serializes and hashes the objects.
5. Objects are written to the filesystem.
6. The branch reference is updated to point to the new commit.

At no point is existing repository data mutated.

---

## Design Principles

The architecture of MiniGit adheres to the following principles:

* **Immutability** – All stored objects are write-once
* **Determinism** – Identical content always produces identical results
* **Explicit State** – Repository state is represented by plain files
* **Minimal Abstraction** – Only essential abstractions are introduced

---

## Extensibility

Although MiniGit is intentionally minimal, the architecture is designed to support future extensions such as:

* Branch switching and checkout
* Commit diffs
* Merging and rebasing
* Remote repositories

These features can be added without modifying the core object model.

