# MiniGit

A minimal, educational implementation of a Git-like version control system built from scratch in Java. This project demystifies Git's core internals by reproducing its fundamental design principles in a simplified, transparent form.

## Motivation

Git is often treated as a "black box" in daily development workflows. While we use high-level commands like `commit`, `push`, and `merge`, the underlying mechanisms—content-addressable storage, immutable objects, and directed acyclic graphs—remain abstract.

MiniGit aims to change this by providing a **fully functional, minimal version control system** that makes Git's internals accessible and understandable. Rather than replicating Git feature-for-feature, the focus is on clarity, correctness, and educational value.

## Features

MiniGit supports essential version control operations:

- **Repository Initialization** - Create new repositories with proper structure
- **File Staging** - Add files to the staging area (index)
- **Committing** - Create immutable snapshots of repository state
- **History Viewing** - Browse commit history and changes
- **Status Checking** - View repository status and staged changes

## Architecture

MiniGit follows a clean, layered architecture that mirrors Git's conceptual design:

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

### Key Design Principles

- **Filesystem as Database** - All data stored as plain files
- **Content-Addressable Storage** - Objects stored by SHA hash
- **Immutable Objects** - Once written, objects never change
- **Explicit State Transitions** - Clear, deterministic behavior

### Repository Structure

```
.minigit/
├── HEAD                    # Current branch reference
├── index                   # Staging area
├── objects/                # Content-addressable object store
│   ├── ab/
│   └── cd/
└── refs/                   # Branch references
    └── heads/
        └── main
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/regisx001/mini-git.git
cd mini-git
```

2. Build the project:
```bash
mvn clean compile
```

3. Create a runnable JAR:
```bash
mvn package
```

### Usage

Initialize a new repository:
```bash
java -jar target/minigit-0.1.0.jar init
```

Add files to staging:
```bash
java -jar target/minigit-0.1.0.jar add <filename>
```

Create a commit:
```bash
java -jar target/minigit-0.1.0.jar commit -m "Your commit message"
```

View commit history:
```bash
java -jar target/minigit-0.1.0.jar log
```

Check repository status:
```bash
java -jar target/minigit-0.1.0.jar status
```

## Project Structure

```
src/
├── main/java/com/regisx001/minigit/
│   ├── App.java                    # Main entry point
│   ├── cli/
│   │   └── CommandParser.java      # CLI argument parsing
│   ├── core/
│   │   ├── Command.java            # Command interface
│   │   └── commands/               # Command implementations
│   ├── domain/                     # Git object model
│   │   ├── Blob.java              # File content objects
│   │   ├── Commit.java            # Commit snapshots
│   │   ├── Tree.java              # Directory structure
│   │   └── TreeEntry.java         # Tree entries
│   ├── filesystem/
│   │   └── FileSystemService.java # Low-level file operations
│   └── storage/                   # Persistence layer
│       ├── Index.java             # Staging area management
│       ├── ObjectStore.java       # Object storage/retrieval
│       └── RefStore.java          # Reference management
└── test/java/com/regisx001/minigit/
    └── AppTest.java               # Unit tests

analysis/                          # Design documentation
├── 01-introduction.md
├── 02-architecture-overview.md
├── 03-repository-structure-data-model.md
└── 04-uml-analysis.md
```

## Understanding Git Internals

MiniGit helps you understand Git's core concepts:

- **Blobs**: Immutable file content storage
- **Trees**: Directory structure representation
- **Commits**: Repository state snapshots
- **References**: Branch and HEAD pointers
- **Index**: Staging area for next commit

Each concept is implemented with minimal abstraction, making the code easy to follow and modify.

## Contributing

This is an educational project designed for learning. Contributions that enhance clarity, add documentation, or improve the codebase are welcome!

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Further Reading

For a deep dive into the design and implementation:

- [Introduction](analysis/01-introduction.md)
- [Architecture Overview](analysis/02-architecture-overview.md)
- [Repository Structure & Data Model](analysis/03-repository-structure-data-model.md)
- [UML Analysis](analysis/04-uml-analysis.md)

---

*Built for educational purposes*