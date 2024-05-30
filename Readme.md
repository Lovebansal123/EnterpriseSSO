# Enterprise SSO Project

## Overview

This project demonstrates a full-stack application implementing JWT (JSON Web Token) authentication. The backend is developed using Spring Boot, and the frontend is built with React.js. The application supports user registration, login, token generation, user profile retrieval, and password reset functionalities.

## Table of Contents

1. [Backend Endpoints](#backend-endpoints)
2. [Frontend Endpoints](#frontend-endpoints)
3. [Technologies Used](#technologies-used)
4. [Setup Instructions](#setup-instructions)
   - [Backend Setup](#backend-setup)
   - [Frontend Setup](#frontend-setup)
5. [Usage](#usage)

## Backend Endpoints

The backend server is implemented using Spring Boot and exposes the following REST API endpoints:

- **`POST /auth/addNewUser`**: Adds a new user to the MySQL database.
- **`POST /auth/logout`**: Logs out the user and invalidates the token.
- **`GET /auth/user/userProfile`**: Validates if the user is authenticated and returns user details.
- **`POST /auth/generateToken`**: Validates the user and generates a JWT token.
- **`POST /auth/forgot-password`**: Takes the user's email as input, validates the user, and sends an email with a reset password link.
- **`POST /auth/reset-password`**: Validates the reset password link and updates the password in the database.

## Frontend Endpoints

The frontend application is built using React.js and interacts with the backend through the following routes:

- **`/`**: Welcome page with login and register options.
- **`/register`**: User registration page that registers the user in the database and navigates to the login page.
- **`/login`**: User login page that validates the user and generates a JWT token.
- **`/home`**: Home page that validates the token and shows details of the authenticated user.
- **`/forgotPassword`**: Page to request a password reset link by entering the user's email.
- **`/resetPassword`**: Page to reset the password by taking a new password as input and storing it in the database, then navigating to the login page.

## Technologies Used

### Backend
- Spring Boot
- Spring Security
- JWT
- MySQL

### Frontend
- React.js
- Axios (for HTTP requests)
- React Router

## Setup Instructions

### Backend Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd authentication
2. **Configure database**:
Update the application.properties file with your MySQL database credentials.

   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/yourDatabaseName
   spring.datasource.username=yourUsername
   spring.datasource.password=yourPassword
3. **Run the appliction**:
   ```bash
   ./mvnw spring-boot:run
### Frontend Setup

1. **Navigate to the frontend directory:**
   ```bash
   cd jwtFrontend
2. **Install dependencies:**

   ```bash
   npm install
3. **Run the appliction**:
   ```bash
   npm start
## Usage
1. ***Welcome Page:***
Navigate to the / endpoint in the frontend application to access the welcome page with options to login or register.
2. ***Register a new user:***
Navigate to the /register endpoint.
Fill in the registration form and submit. You will be redirected to the login page upon successful registration
(No user can register twice with same email and user should enter strong password as described for successfull registration).
3. ***Login:***
Navigate to the /login endpoint.
Enter your credentials and login. A JWT token will be generated upon successful authentication and get stored in local storage to authorize user.
4. ***Access Home Page:***
After logging in, navigate to the /home endpoint to view the authenticated user details.
5. ***Forgot Password:***
Navigate to the /forgotPassword endpoint.
Enter your email to receive a reset password link.
6. ***Reset Password:***
Use the link sent to your email to navigate to the /resetPassword endpoint.
Enter your new password to reset. You will be redirected to the login page upon successful password reset.



