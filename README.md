# Software-Engineering-II- #
**Team: WeDoIT**

IP: 10.28.2.169

VPN: username with #

**Teammates:**

| name        | email           |
|-------------|-----------------|
|Jonas Ringler| ringler@hm.edu  |
|Bernhard Schaidhammer|schaidha@hm.edu|
|Alexandra Vogel|avogel@hm.edu|
|Adomas Simonenkovas |                 |

- - - -

# Preliminary Specification

## 1.	Functional Requirements

### 1.1	Layout

#### 1.1.1	Overview of all suppliers (done)
One view must give the overview of all suppliers containing the average of them. The suppliers should be ordered to show the best supplier on top of the list. It should also be possible to search for a supplier by name.

#### 1.1.2	Detailed View for each supplier 
If a supplier is clicked, a detailed view of this supplier should appear. In this view a bar chart should show the deliveries of the supplier and if they were in time or not. It should give a nice overview of all deliveries of this single supplier

#### 1.1.3	Comparing some suppliers (maximum of 4)(Done)
An optional feature is to compare multiple suppliers by selecting more than one supplier in the 1.1.2 - view. Then a new view should show something similar to 1.1.3 comparing all checked suppliers. The Label should say the suppliers classification next to it's name.
Also a graph is shown, showing the numbers of orders which are too late, too early, etc. for all choosen suppliers.

### 1.2	Chooseable time interval (done)
A time interval must be chooseable for the whole application, so that the comparing is done for this interval.

### 1.3	Authentication (done)
A dynamic login must be possible. It requires a username and a password. Only after the authentication the application is able to get data from the client-company.

### 1.3.1	User control (done)
There should be 1 admin user, who can create, delete and manage users for the system.

### 1.4	Export reports as PDF 
The Overview (1.1.1), the Detailed-View (1.1.2) and the Comparing-View (1.1.3) must be exportable as a PDF containing all important information and the important charts.

### 1.5	Useable on a desktop and a tablet (done)
The application must be usable on a desktop-pc with Windows 7 as OS and on a tablet.

### 1.6	Database error handling (done)
The application must inform the user if there are any error with the database (fetching data or connecting to database).

### 1.7 Supplier cathegories (done)
There should be a possibility to categorise suppliers by the amount of orders.

### 1.7.1 Category management
The admin should be able to change the category requirements.

### 1.7.2 Category selection (done)
Allow the user to select all the users in a category.

### 1.8 Scoring management
The admin should be able to change the scoring algorithm.


## 2.	Non-Functional Requirements

### 2.1	Database access (done)
The client-company allows read-only access to the MySQL-Database over a remote SSH-Connection. The data is sensitive, therefor encryption is needed to transfer data between the company internal infrastructures to the client side of the application.

### 2.2	Documentation
A documentation of the software is needed and must be handed over to the client-company

### 2.2.1	Code documentation (in progress)
A document explaining the code needs to be done in pdf.

### 2.2.2	User manual
A user manual needs to be done for the customer as a pdf. It should explain how to use the software.

### 2.2.3	Coding standards (done)
The coding standards needs to be decided and written down.

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

### 2.8	Encrypt server info (done)
The login info for the database must be encrypted so it's not in the software as a plank text.

### 2.9 Performance (done)

### 2.9.1 Startuptime (done)
The startup shouldn't take more than 30 seconds after login.

### 2.9.2 selecting suplier (done)
Selecting new supliers shouldn't take more than 1 second.

### 2.9.3 Date selecting (done)
Changing the filtering date shouldn't take more than 5 seconds.


