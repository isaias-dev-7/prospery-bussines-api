# 🚀 Business Platform – User Management Backend (Spring Boot + PostgreSQL + pgAdmin) (Dockerized Starter Setup)

This project provides a dockerized backend foundation for managing users in a business platform. Built with Spring Boot, PostgreSQL, and pgAdmin, it offers a clean and scalable setup for future API development, database persistence, and administrative tooling.

---

## 📦 Tech Stack

- **Spring Boot** – Java backend framework
- **PostgreSQL** – Relational database
- **pgAdmin 4** – Web-based database management
- **Docker Compose** – Service orchestration
- **Devtools (optional)** – Hot reload for development

---

## 🎯 Project Purpose

The goal of this backend is to manage user data for a business-oriented platform. While no endpoints are implemented yet, the architecture is designed to support:

- User registration and authentication
- Role-based access control
- Profile and account management

---

## 🛠️ Prerequisites

Make sure you have the following installed:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Git](https://git-scm.com/)
- Java 17+ (only required if building outside Docker)

---

## ⚙️ Setup Instructions (Development)

---

1. **Clone the repository**

- Run:
``` bash
docker-compose up --build
```
This will spin up:
- Spring Boot backend on http://localhost:8080
- PostgreSQL database on port 5432
- pgAdmin on http://localhost:5050

- Stop the services:
``` bash
docker-compose down
```