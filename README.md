<h1>Employee Management Application</h1>

<p>This project is a web application and REST API for managing employees and departments. It enables users to perform CRUD operations on employees and departments. The application is developed using Spring Boot and connects to a MySQL database via Docker Compose.</p>

Sonar Cloud: [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=WahidiBilal_employee_management_application&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=WahidiBilal_employee_management_application)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=WahidiBilal_employee_management_application&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=WahidiBilal_employee_management_application)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=WahidiBilal_employee_management_application&metric=coverage)](https://sonarcloud.io/summary/new_code?id=WahidiBilal_employee_management_application)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=WahidiBilal_employee_management_application&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=WahidiBilal_employee_management_application)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=WahidiBilal_employee_management_application&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=WahidiBilal_employee_management_application)

Code coverall: [![Coverage Status](https://coveralls.io/repos/github/WahidiBilal/employee_management_application/badge.svg?branch=main)](https://coveralls.io/github/WahidiBilal/employee_management_application?branch=main)

GithubAction: [![Java CI with Maven](https://github.com/WahidiBilal/employee_management_application/actions/workflows/maven-ci.yml/badge.svg)](https://github.com/WahidiBilal/employee_management_application/actions/workflows/maven-ci.yml)

<h1>Steps to Run</h1>

<ol>
  <li>
    <strong>Clone the Repository</strong>
    <pre><code>git clone https://github.com/WahidiBilal/employee_management_application.git</code></pre>
  </li>
  <li>
    <strong>Start the MySQL Database using Docker Compose</strong>
    <pre><code>docker-compose up -d</code></pre>
  </li>
</ol>

<h2>Prerequisites</h2>
<ul>
  <li>Docker</li>
  <li>Java JDK 17</li>
</ul>

<h2>Web URL</h2>

<p>http://localhost:5333/departments</p>

<h2>REST APIs</h2>

<h3>1. Department APIs</h3>
<ul>
  <li><strong>Get All Departments:</strong> <code>/api/departments/getAll</code></li>
  <li><strong>Get Department by ID:</strong> <code>/api/departments/{did}</code></li>
  <li><strong>Create Department:</strong> <code>/api/departments/create</code></li>
  <li><strong>Update Department:</strong> <code>/api/departments/{did}</code></li>
  <li><strong>Delete Department:</strong> <code>/api/departments/{did}</code></li>
</ul>

<h3>2. Employee APIs</h3>
<ul>
  <li><strong>Get All Employees:</strong> <code>/api/employees/getAll</code></li>
  <li><strong>Get Employee by ID:</strong> <code>/api/employees/{eid}</code></li>
  <li><strong>Create Employee:</strong> <code>/api/employees/create</code></li>
  <li><strong>Delete Employee:</strong> <code>/api/employees/{eid}</code></li>
</ul>


