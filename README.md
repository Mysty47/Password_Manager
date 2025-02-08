# Password Manager

## Overview
A Java-based desktop application for securely storing and managing passwords. It uses **Swing** for the user interface and **MySQL** as the database for storing login credentials.

## Features
- Add, view, and delete stored passwords
- Copy passwords to clipboard
- Search functionality
- MySQL database integration
- Simple GUI built with Java Swing

## Technologies Used
- **Java (Swing)** - For the graphical user interface
- **MySQL** - Database to store password entries
- **JDBC** - Database connectivity

## Installation
### Prerequisites:
- Java 8 or later installed
- MySQL installed and running

### Setup:
1. Clone the repository:
   ```sh
   git clone https://github.com/Mysty47/Password_Manager.git
   ```
2. Configure MySQL:
   - Create a database named `login_info`
   - Create a table `USERS` with columns: `id`, `place`, `password`
3. Update `Frame.java` and `Passwords.java` with your database credentials.
4. Compile and run the project:
   ```sh
   javac *.java
   java ControlPanel
   ```

## Future Improvements
- Convert the desktop app into a web application using **Spring Boot** and **React**
- Add user authentication
- Encrypt stored passwords

## License
This project is open-source and available under the MIT License.

