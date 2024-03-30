# maids.cc-task
A demo task for maids.cc

# Sales Management System using Spring Boot

The Invoice and Payment Link is a comprehensive web application developed using Spring Boot, aimed at providing users with a feature-rich platform to efficiently manage and organize their invoice and collect payment. This README provides an overview of the system, installation instructions, usage guidelines, API documentation, and details for contributing to the project.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites Requirement](#prerequisites-requirement)
    - [Installation](#installation)
- [API Endpoints](#api-endpoints)


## Features

This Sales Management System comes equipped with robust reporting features can provide valuable insights into sales performance, inventory management, customer behavior, and more:

- **Customer Management**: Store and manage customer information, including contact details, purchase history, preferences, and interactions.
- **Product Management**: Maintain a catalog of products or services offered by the business.
- **Inventory Management**: Monitor and manage inventory levels in real-time.
- **Sales Order Processing**: Create and manage sales orders for products or services.
- **Sales Tracking and Reporting**: Monitor sales performance and track key metrics such as revenue, profit margins, and sales growth.
- **Quoting and Pricing**: Create and manage price quotes for products or services.
- **User Access and Security**: Control user access levels and permissions to ensure data security and confidentiality.

## Technologies Used

The Invoice and Payment Link System leverages modern technologies to deliver a robust and efficient experience:

- **Java**: The core programming language for developing the application logic.
- **Spring Boot**: A powerful framework for building robust and scalable applications.
- **Spring Data JPA**: Provides data access and manipulation capabilities using the Java Persistence API.
- **Spring Web**: Facilitates the creation of web APIs and interfaces.
- **Spring Security**: Ensures secure authentication and authorization within the application, guarding against unauthorized access and protecting sensitive data.
- **JSON Web Token (JWT)**: Enables stateless authentication by securely transmitting user information as JSON objects, facilitating secure communication between the client and server.
- **Auditor Aware**: Automatically tracks and records metadata about the user who created or modified entities, such as creation and modification timestamps, enhancing data auditing and accountability.
- **MSQL**: A popular relational database management system used for storing and retrieving structured data efficiently.
- **Maven**: Manages project dependencies and provides a structured build process.
- **Git**: Version control for collaborative development.

## Getting Started

### Prerequisites Requirement

Before getting started, ensure you have the following components installed:

1. **This project was built using JDK 17, you would need JDK 17 installed on you local machine.**

- [Java Development Kit (JDK 17)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)


## Build and Run the Application:

Execute the following command to build and run the application:

````bash
mvn clean package install
mvn spring-boot:run
````


### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Megapreneur/SalesManagementSystem.git
   cd SalesManagementSystem
   ```

2. **Configure the Database:**

   Modify the `src/main/resources/application.properties` file to include your database connection details:

   ```properties
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=your_database_jdbc_url
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password
   ```

3. **Token Requirements:**

   Modify the `src/main/resources/application.properties` file to include your JWT token:
   
    ```properties
   sales.app.SecretKey=your_JWT_token
    ```

### Invoice and Payment Link

- **Create Invoice:** User passes their information and product details to create an invoice .
- **Link to Invoice:** A link to view the generated invoice is returned to the user.
- **Invoice:** User is routed to this endpoint to view generated invoice. The invoice generated can be viewed on this endpoint.
- **Payment Link:** A unique payment link is generated and returned to the user to complete the transaction using the invoice Id.


## API Endpoints

The Invoice and Payment Link System offers the following API endpoints:

- User's Registration: `POST /api/v1/user/create`
- User's login: `POST /api/v1/auth/login`
- Product creation: `POST /api/v1/product/create`
- Get a product: `GET /api/v1/product/product`
- Update a product: `PUT /api/v1/product/update`
- Get all products: `GET /api/v1/product/allProduct`
- Delete a product: `DELETE /api/v1/product/delete`
- Get product report: `GET /api/v1/product/reports/product`
- Sales creation: `POST /api/v1/sales/create`
- Get a sales: `GET /api/v1/sales/sales`
- Update a sales: `PUT /api/v1/sales/update`
- Get all sales: `GET /api/v1/sales/allTransactions`
- Get sales report: `GET /api/v1/sales/reports/sales`
- Client creation: `POST /api/v1/client/create`
- Get a client: `GET /api/v1/client/client`
- Update a client: `PUT /api/v1/client/update`
- Get all clients: `GET /api/v1/client/allClients`
- Delete a client: `DELETE /api/v1/client/delete`
- Get client report: `GET /api/v1/client/reports/client`
- 
- 
- 
- Generate link to invoice: `POST /api/v1/invoice/generate-invoice-link`
- Route to generated invoice: `GET /api/v1/invoice/view-invoice/{merchantId}/{invoiceId}`
- Generate Payment Link: `POST /api/v1/invoice//payment-link?invoiceId=invoiceId`


### Future Improvements

Given the short time frame for the task, I could not implement downloading of report into a pdf format



.