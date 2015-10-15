# Software-Engineering-II- #
Team: wedoIT

**Teammates:**

| name        | email           |
|-------------|-----------------|
|Jonas Ringler| ringler@hm.edu  |
|             |                 |
|             |                 |
|             |                 |

- - - -

# Preliminary Specification

## 1.	Functional Requirements

### 1.1	Layout

#### 1.1.1	Overview of all suppliers
One view must give the overview of all suppliers containing the average of them. The suppliers should be ordered to show the best supplier on top of the list. It should also be possible to search for a supplier by name.

#### 1.1.2	Detailed View for each supplier
If a supplier is clicked, a detailed view of this supplier should appear. In this view a bar chart should show the deliveries of the supplier and if they were in time or not. It should give a nice overview of all deliveries of this single supplier

#### 1.1.3	Comparing some suppliers
An optional feature is to compare multiple suppliers by selecting more than one supplier in the 1.1.2 - view. Then a new view should show something similar to 1.1.3 comparing all checked suppliers.

### 1.2	Chooseable time interval
A time interval must be chooseable for the whole application, so that the comparing is done for this interval.

### 1.3	Authentication
A static login must be possible. It requires a username and a password. Only after the authentication the application is able to get data from the client-company.

### 1.4	Export reports as PDF
The Overview (1.1.1), the Detailed-View (1.1.2) and the Comparing-View (1.1.3) must be exportable as a PDF containing all important information and the important charts.

### 1.5	Useable on a desktop and a tablet
The application must be usable on a desktop-pc with Windows 7 as OS and on a tablet.



## 2.	Non-Functional Requirements

### 2.1	Database access
The client-company allows read-only access to the MySQL-Database over a remote SSH-Connection. The data is sensitive, therefor encryption is needed to transfer data between the company internal infrastructures to the client side of the application.

### 2.2	Documentation
A documentation of the software is needed and must be handed over to the client-company

### 2.3	Software-Quality
To guarantee a minimum of software-quality unit-tests must be implemented.

### 2.4	Software-Environments

#### 2.4.1	Server
The server is hosted in the client-company. The server must be able to run on a CentOS. Furthermore it must be easy to install it and it is not allowed to have dependencies to additional software. Java-Version 1.8 is pre-installed on the server and can be used. 

#### 2.4.2	Client
The client of the application must be runnable on Windows 7 and on tablets. JavaScript allowed upon consultation of the client-company. As above an easy installation is wanted which has no dependencies to additional software which isn’t installed on the client yet.

### 2.5	Contact
The client-company is contactable every Thursday from 03:15 pm to 6:30 pm. In exceptional cases it is also possible to write emails to the client-company.

### 2.6	Acceptance-Testing
Acceptance-testing will be done in cooperation with the client-company.

### 2.7	Deadline
The deadline of this project is at the beginning of January 2016.

