CREATE DATABASE PM_Java;
USE PM_Java;

CREATE TABLE Visibility (
    visibility_id INT PRIMARY KEY,
    visibility VARCHAR(50) NOT NULL
);

CREATE TABLE Status (
    status_id INT PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE Role (
    role_id INT PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE Admin (
    admin_id INT PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(255),
    email VARCHAR(100),
    visibility_id INT,
    FOREIGN KEY (visibility_id) REFERENCES Visibility(visibility_id)
);

CREATE TABLE Leader (
    leader_id INT PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(255),
    email VARCHAR(100),
    visibility_id INT,
    FOREIGN KEY (visibility_id) REFERENCES Visibility(visibility_id)
);

CREATE TABLE TeamMember (
    team_member_id INT PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(255),
    email VARCHAR(100),
    visibility_id INT,
    role_id INT,
    FOREIGN KEY (visibility_id) REFERENCES Visibility(visibility_id),
    FOREIGN KEY (role_id) REFERENCES Role(role_id)
);

CREATE TABLE Project (
    project_id INT PRIMARY KEY,
    name VARCHAR(100),
    creation_date DATE,
    deadline DATE,
    admin_id INT,
    leader_id INT,
    status_id INT,
    visibility_id INT,
    FOREIGN KEY (admin_id) REFERENCES Admin(admin_id),
    FOREIGN KEY (leader_id) REFERENCES Leader(leader_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id),
    FOREIGN KEY (visibility_id) REFERENCES Visibility(visibility_id)
);

CREATE TABLE Task (
    task_id INT PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    creation_date DATE,
    deadline DATE,
    project_id INT,
    responsible_id INT,
    status_id INT,
    visibility_id INT,
    FOREIGN KEY (project_id) REFERENCES Project(project_id),
    FOREIGN KEY (responsible_id) REFERENCES TeamMember(team_member_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id),
    FOREIGN KEY (visibility_id) REFERENCES Visibility(visibility_id)
);

CREATE TABLE Team (
    project_id INT,
    team_member_id INT,
    PRIMARY KEY (project_id, team_member_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id),
    FOREIGN KEY (team_member_id) REFERENCES TeamMember(team_member_id)
);

CREATE TABLE Leader_x_TeamMember (
    leader_id INT,
    team_member_id INT,
    PRIMARY KEY (leader_id, team_member_id),
    FOREIGN KEY (leader_id) REFERENCES Leader(leader_id),
    FOREIGN KEY (team_member_id) REFERENCES TeamMember(team_member_id)
);

CREATE TABLE Admin_x_Leader (
    admin_id INT,
    leader_id INT,
    PRIMARY KEY (admin_id, leader_id),
    FOREIGN KEY (admin_id) REFERENCES Admin(admin_id),
    FOREIGN KEY (leader_id) REFERENCES Leader(leader_id)
);
