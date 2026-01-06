# Repository Structure and Data Model

MiniGit represents all version-controlled data using a **well-defined on-disk repository structure** combined with a small set of **immutable domain objects**. Together, these form the persistent state of the system and enable accurate reconstruction of repository history at any point in time.

This section describes the repository layout, the purpose of each component, and the underlying data model that governs how information is stored and referenced.

---

## Repository Root

A directory is considered a MiniGit repository if and only if it contains a hidden `.minigit` directory at its root. All version control metadata is stored exclusively within this directory.

The working directory itself remains unmodified except for user-managed files.

---

## Repository Directory Layout

The complete repository layout for the MVP is as follows:

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

Each file and directory in this structure has a specific, non-overlapping responsibility.

---

## Core Repository Components

### HEAD

The `HEAD` file represents the current position in the repository. Rather than storing a commit hash directly, it contains a reference to a branch:

```
refs/heads/main
```

This indirection allows the current branch to move forward as new commits are created without modifying `HEAD` itself.

---

### References (`refs`)

The `refs` directory stores named pointers to commits.

#### Branches

Branches are represented as plain text files located under:

```
refs/heads/
```

Each branch file contains a single line:

```
<commit-hash>
```

Branches are lightweight, mutable pointers and do not store any additional metadata.

---

### Object Store (`objects`)

The object store is the heart of the MiniGit repository. It contains all immutable data objects—blobs, trees, and commits—stored by their cryptographic hash.

#### Content-Addressable Storage

Each object is stored at a path derived from its hash:

```
objects/<first-two-chars>/<remaining-chars>
```

Example:

```
objects/ab/cdef1234567890
```

This layout prevents directory overload and enables efficient lookup.

---

### Index (Staging Area)

The `index` file represents the staging area. It records which files are scheduled to be included in the next commit.

The index is a plain text file with one entry per line:

```
<file-path> <blob-hash>
```

Example:

```
README.md a1b2c3d4
src/Main.java e5f6g7h8
```

The index is mutable and reflects user intent rather than committed state.

---

## Data Model Overview

MiniGit models repository state using three immutable object types. These objects form a directed acyclic graph (DAG) that represents repository history.

---

## Blob Object

### Purpose

A Blob object represents the contents of a file at a specific point in time.

### Characteristics

* Stores raw file data only
* Does not include file name or path
* Immutable once created

### Logical Format

```
blob <size>\0<file-content>
```

The hash of the blob is computed over this entire structure.

---

## Tree Object

### Purpose

A Tree object represents a directory snapshot, mapping filenames to either blobs or subtrees.

### Tree Entry Format

```
<mode> <name> <object-hash>
```

Example:

```
100644 README.md a1b2c3
040000 src d4e5f6
```

### Characteristics

* Entries are sorted lexicographically
* Trees may reference other trees
* Represents a complete directory state

---

## Commit Object

### Purpose

A Commit object represents a snapshot of the entire repository along with metadata describing the change.

### Logical Format

```
tree <tree-hash>
parent <parent-hash> (optional)
author <name> <timestamp>

<commit-message>
```

### Characteristics

* References exactly one root tree
* May reference one parent commit (MVP)
* Immutable and append-only

---

## Object Relationships

The relationship between objects forms a DAG:

```
Commit
  ↓
Tree
  ↓
Blob
```

* Commits reference trees
* Trees reference blobs and other trees
* Blobs contain raw data

This structure enables efficient history traversal and content reuse.

---

## Immutability and Integrity

Once written, objects are never modified. Any change to file content results in the creation of new objects with new hashes.

This immutability guarantees:

* Historical integrity
* Cheap branching
* Safe rollback

---

## Summary

The repository structure and data model of MiniGit are intentionally simple yet powerful. By combining a minimal filesystem layout with immutable, content-addressable objects, MiniGit reproduces the essential mechanics that make Git reliable and efficient.

