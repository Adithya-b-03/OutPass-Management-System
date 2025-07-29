# OutPass Management System 🚪🎓

A full-stack desktop application to manage student outpass requests efficiently.  
Built using **Java (Swing GUI)** and **MySQL**, this system streamlines the request, review, and approval process for students and administrators.

## 🛠️ Features

- Student outpass request submission
- Admin panel for viewing & approving requests
- Role-based access (Student / Admin)
- MySQL database integration
- Java Swing GUI frontend (Client + Server)
- Real-time request status display

## 📁 Project Structure

-OutPass-Management-System/
- OutpassClient.java # GUI for students
- OutpassServer.java # Admin-side request handling
- DatabaseConnection.java # MySQL DB utility
- README.md # This file
- ... # Other supporting files

markdown
Copy
Edit

## 🧪 Technologies Used

- Java (Swing)
- MySQL
- JDBC (Java Database Connectivity)
- Socket Programming

## 🚀 Getting Started

### Prerequisites

- Java JDK 17+
- MySQL Server & Workbench

### Setup Instructions

1. **Clone the repository**
   git clone https://github.com/Adithya-b-03/OutPass-Management-System.git
   cd OutPass-Management-System
## Create MySQL database

CREATE DATABASE outpass_system;
USE outpass_system;

- CREATE TABLE OutpassRequests (
     id INT AUTO_INCREMENT PRIMARY KEY,
     student_name VARCHAR(100),
     request_message TEXT,
     status VARCHAR(20) DEFAULT 'Pending'
 );

## Update database credentials in DatabaseConnection.java

- String url = "jdbc:mysql://localhost:3306/outpass_system";
- String user = "your_mysql_username";
- String password = "your_mysql_password";
Compile and run

## 👨‍💻 Author
Adithya B
GitHub

## 📄 License
This project is licensed under the MIT License.

---

✅ You can now paste this directly into your `README.md` file. Let me know if you want to add screenshots or documentation later!








Ask ChatGPT
