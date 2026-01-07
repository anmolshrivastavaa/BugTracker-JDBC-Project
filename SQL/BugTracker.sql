CREATE DATABASE bugtracker;
USE bugtracker;

CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    role VARCHAR(50)
);

CREATE TABLE projects (
    project_id CHAR(1) PRIMARY KEY,
    project_name VARCHAR(100),
    start_date DATE
);

CREATE TABLE bugs (
    bug_id VARCHAR(5) PRIMARY KEY,
    title VARCHAR(200),
    description TEXT,
    project_id CHAR(1),
    created_by INT,
    created_on DATE,
    FOREIGN KEY (project_id) REFERENCES projects(project_id),
    FOREIGN KEY (created_by) REFERENCES employees(emp_id)
);

CREATE TABLE bug_status (
    status_id VARCHAR(5) PRIMARY KEY,
    bug_id VARCHAR(5),
    status VARCHAR(50),
    updated_on DATE,
    FOREIGN KEY (bug_id) REFERENCES bugs(bug_id)
);

INSERT INTO employees VALUES
(1, 'Anmol-QA', 'qa@gmail.com', 'QA Engineer'),
(2, 'Anmol-DEV', 'dev@gmail.com', 'Developer');

INSERT INTO projects VALUES
('A', 'E-Commerce App', CURDATE()),
('B', 'LMS', CURDATE());

INSERT INTO bugs VALUES
('B1', 'Login fails', 'User cannot login with valid credentials', 'A', 1, CURDATE()),
('B2', 'Cart not updating', 'Item count not changing in cart', 'A', 1, CURDATE()),
('B3', 'Payment timeout', 'Payment page freezes on submit', 'A', 1, CURDATE()),
('B4', 'Profile not saving', 'Profile changes are not saved', 'A', 1, CURDATE()),
('B5', 'Search broken', 'Search returns no results', 'A', 1, CURDATE()),
('B6', 'Video not loading', 'Lecture video stuck at loading', 'B', 1, CURDATE()),
('B7', 'Quiz crash', 'Quiz crashes on submit', 'B', 1, CURDATE()),
('B8', 'Certificate error', 'Certificate not generated', 'B', 1, CURDATE()),
('B9', 'Course progress reset', 'Progress resets after logout', 'B', 1, CURDATE()),
('B10','UI misalignment', 'Dashboard layout broken', 'B', 1, CURDATE());

INSERT INTO bug_status VALUES
('S1','B1','Open',CURDATE()),
('S2','B2','Open',CURDATE()),
('S3','B3','In Progress',CURDATE()),
('S4','B4','Open',CURDATE()),
('S5','B5','Closed',CURDATE()),
('S6','B6','Open',CURDATE()),
('S7','B7','In Progress',CURDATE()),
('S8','B8','Open',CURDATE()),
('S9','B9','Closed',CURDATE()),
('S10','B10','Open',CURDATE());

-- FINAL REPORT

SELECT 
    b.bug_id, b.title, p.project_name, e.name AS reported_by, s.status
FROM bugs b
JOIN projects p ON b.project_id = p.project_id
JOIN employees e ON b.created_by = e.emp_id
JOIN bug_status s ON b.bug_id = s.bug_id;

