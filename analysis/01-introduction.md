# Introduction

Version control systems are a foundational tool in modern software development, enabling teams and individuals to track changes, manage history, and collaborate effectively. Among these systems, Git has emerged as the de facto standard due to its distributed architecture, performance, and robust data model.

Despite its widespread use, Git is often treated as a “black box” by developers. Daily workflows rely on high-level commands (`commit`, `push`, `merge`) while the underlying mechanisms—content-addressable storage, immutable objects, and directed acyclic graphs—remain abstract.

This project aims to demystify Git by building a **minimal, fully functional version control system** from scratch, implemented in **Java** and managed with **Maven**. Rather than replicating Git feature-for-feature, the focus is on understanding and reproducing its **core design principles** in a simplified, educational form.

---

## Project Motivation

The motivation behind MiniGit is threefold:

1. **Deep Understanding**
   By reimplementing Git’s core components—blobs, trees, commits, and references—we gain a practical understanding of how version control systems work internally.

2. **Architectural Learning**
   The project emphasizes clean architecture, immutability, and separation of concerns, making it an excellent case study for system design in Java.

3. **Educational Value**
   MiniGit is designed as a learning tool rather than a production replacement, prioritizing clarity and correctness over performance and advanced features.

---

## Scope and Philosophy

MiniGit is intentionally limited in scope. It supports only local repositories and a small set of essential commands, allowing the internal mechanics to remain transparent and approachable.

The guiding philosophy of the project is:

* **Filesystem as a database**
* **Content-addressable, immutable objects**
* **Explicit state transitions**
* **Simple, deterministic behavior**

These principles mirror the foundational ideas behind Git while keeping the implementation understandable and maintainable.

---

## Technology Choices

The system is implemented using:

* **Java** — for its strong type system, portability, and rich standard library
* **Maven** — for dependency management, project structure, and build reproducibility
* **SHA-based hashing** — to uniquely identify and store content

No external libraries are used for core version control logic, ensuring that all fundamental mechanisms are implemented explicitly.

---

## Intended Audience

This project is intended for:

* Developers who use Git but want to understand how it works internally
* Students studying software architecture or distributed systems
* Engineers seeking a hands-on systems programming exercise in Java

A working knowledge of Java and basic command-line usage is assumed.

---

## Roadmap Overview

The project progresses incrementally, starting with repository initialization and moving toward commit history traversal. Each component is carefully analyzed and designed before implementation to ensure correctness and conceptual clarity.
