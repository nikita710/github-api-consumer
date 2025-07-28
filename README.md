# GitHub API Consumer

A Spring Boot application for consuming the GitHub REST API. It fetches user repositories and their branches, excluding forked repositories.

## Features

- Fetch public repositories for a given GitHub user
- Exclude forked repositories from results
- Retrieve branches for each repository
- Exception handling for non-existent users
- RESTful API design

## Technologies Used

- Java 21+
- Spring Boot 3.5+
- Maven 3.9+
- RestClient (Spring)
- Lombok 
- JUnit 

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- Internet connection (to access GitHub API)

### Setup

1. **Clone the repository:**
   
   git clone https://github.com/nikita710/github-api-consumer.git
   cd github-api-consumer
   1.1 Build the project:
      mvn clean install
   1.2 Run the application:
      mvn spring-boot:run

2. **Configuration:**
   -Replace the property value of github token with your token
   github:
    baseUrl: "https://api.github.com"
    token: "your_token"

**Usage:**
The main endpoint:
GET /users/{username}/repos
Returns a list of non-forked repositories for the specified user, including their branches.
Get request
http://localhost:8080/users/octocat/repos

**Error Handling:**
  -Returns 404 Not Found if the GitHub user does not exist.
  -Returns 500 Internal Server Error for other client/server errors.

**Project Structure:**  
  controller/ — REST controllers
  service/ — Business logic and GitHub API integration
  model/ — Data models (RepoInfo, Branch, etc.)
  exception/ — Custom exceptions
  client/ RestClientConfig
  


