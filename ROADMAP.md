# MiniGit Roadmap

This roadmap outlines the progress of the MiniGit project, detailing what has been achieved so far, what needs to be done next, and the requirements for reaching a stable `v1.0.0` release.

##  Vision
To build a minimal, fully functional version control system from scratch in Java that demystifies Git's core internals (content-addressable storage, immutable objects, and directed acyclic graphs) for educational purposes.

---

##  Phase 1: Foundation & MVP (Completed)

We have successfully implemented the core architecture and the Minimum Viable Product (MVP) features.

### Architecture & Infrastructure
- [x] **Layered Architecture**: Implemented CLI, Command Orchestration, Domain Model, Storage, and FileSystem layers.
- [x] **Domain Model**: Created immutable representations of Git objects (`Blob`, `Tree`, `TreeEntry`, `Commit`).
- [x] **Storage Layer**: Implemented content-addressable storage (`ObjectStore`), staging area management (`Index`), and reference management (`RefStore`).
- [x] **FileSystem Abstraction**: Created `FileSystemService` to handle all low-level OS interactions safely.
- [x] **Hashing Utility**: Implemented SHA-1 hashing for object identification.

### Core Commands
- [x] `init`: Initializes a new `.minigit` repository with the correct directory structure (`objects/`, `refs/heads/`, `HEAD`, `index`).
- [x] `add`: Hashes file content, stores it as a Blob, and updates the staging area (`index`).
- [x] `commit`: Creates Trees from the index, creates a Commit object linking to the Tree and parent commit, and updates the branch reference.
- [x] `log`: Traverses the commit history DAG backwards from `HEAD` and prints commit messages.
- [x] `status`: Compares the working directory, index, and `HEAD` commit to show staged and untracked files.

### CI/CD & Testing
- [x] **Unit Testing**: Comprehensive JUnit 5 test suite covering domain objects, storage, filesystem, and CLI parsing.
- [x] **GitHub Actions (CI)**: Automated Maven build and test execution on push/PR.
- [x] **GraalVM Native Binaries**: Automated release workflow that builds and publishes native executables for Linux, macOS, and Windows.

---

##  Phase 2: Robustness & Edge Cases (Current Focus)

Before we can call this `v1.0.0`, we need to handle edge cases, improve error handling, and support nested directories.

### Directory & Subtree Support
- [ ] **Recursive `add`**: Allow `add <directory>` to recursively stage all files within a folder.
- [ ] **Nested Trees**: Update the `commit` command to properly generate nested `Tree` objects for subdirectories (currently, it flattens everything or only handles top-level files).

### Error Handling & Validation
- [ ] **Graceful Failures**: Improve error messages when running commands outside a repository (e.g., running `add` before `init`).
- [ ] **File Deletion**: Handle files that are deleted from the working directory (update `status` and `add` to record deletions).
- [ ] **Empty Commits**: Prevent creating a commit if the index exactly matches the `HEAD` tree.

### CLI Improvements
- [ ] **Help Command**: Implement a `help` command or `-h`/`--help` flags to display usage instructions.
- [ ] **Author Configuration**: Read author name/email from a config file or environment variable instead of hardcoding "You" in `CommitCommand`.

---

## Phase 3: Advanced Features (Post v1.0.0)

Once the core is stable, we can introduce more advanced Git concepts.

### Branching & Checkout
- [ ] `branch <name>`: Create a new branch reference pointing to the current commit.
- [ ] `checkout <branch>`: Update `HEAD`, read the target commit's tree, and update the working directory to match.
- [ ] `checkout -b <name>`: Create and switch to a new branch.

### History & Inspection
- [ ] `cat-file -p <hash>`: Inspect the raw contents of a stored object (Blob, Tree, or Commit).
- [ ] `diff`: Show the differences between the working directory and the index, or between commits.

### Remote Operations (Stretch Goal)
- [ ] `clone`: Copy a repository from a remote URL.
- [ ] `push` / `fetch`: Implement a basic protocol to transfer objects and update references over HTTP/SSH.

---

##  Requirements for v1.0.0 Release

To officially release `v1.0.0`, the following checklist must be completed:

- [ ] Full support for nested directories (Subtrees).
- [ ] Ability to stage file deletions.
- [ ] Comprehensive error handling for invalid user inputs.
- [ ] A `--help` command for CLI discoverability.
- [ ] 100% passing test suite with coverage for the new edge cases.
- [ ] Updated documentation reflecting the final v1.0.0 feature set.
