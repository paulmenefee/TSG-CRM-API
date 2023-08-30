-- This script will destroy your TSGCRM database and start anew!
DROP DATABASE IF EXISTS TSGCRM;

CREATE DATABASE TSGCRM;

-- Make sure we're in the correct database before we add schema.
USE TSGCRM;

CREATE TABLE Client(
	ClientId int auto_increment primary key,
    ClientName varchar(100) not null,
    ClientAddress varchar(100),
    ClientCity varchar(100),
    ClientState varchar(2),
    ClientZip varchar(10),
    ClientContactFirstName varchar(50),
    ClientContactLastName varchar(50),
    ClientContactEmail varchar(250),
    ClientContactPhone varchar(15)
);

CREATE TABLE Project (
	ProjectId CHAR(50) PRIMARY KEY,
    ProjectName VARCHAR(100) NOT NULL,
    ClientId INT,
    ProjectSummary VARCHAR(2000) NULL,
    ProjectDueDate DATE NULL,
    ProjectIsActive BOOL NOT NULL DEFAULT 1,
    FOREIGN KEY fk_Client_Project (ClientId)
		REFERENCES Client(ClientId)
);

CREATE TABLE Worker (
	WorkerId INT PRIMARY KEY AUTO_INCREMENT,
    WorkerFirstName VARCHAR(50) NOT NULL,
    WorkerLastName VARCHAR(50) NOT NULL,
    WorkerEmail VARCHAR(250) NULL
);

CREATE TABLE ProjectWorker (
	ProjectId CHAR(50) NOT NULL,
    WorkerId INT NOT NULL,
    PRIMARY KEY pk_ProjectWorker (ProjectId, WorkerId),
	FOREIGN KEY fk_ProjectWorker_Project (ProjectId)
		REFERENCES Project(ProjectId),
	FOREIGN KEY fk_ProjectWorker_Worker (WorkerId)
		REFERENCES Worker(WorkerId)
);

CREATE TABLE TaskType (
	TaskTypeId INT PRIMARY KEY AUTO_INCREMENT,
    TaskTypeName VARCHAR(50) NOT NULL
);

CREATE TABLE TaskStatus (
	TaskStatusId INT PRIMARY KEY AUTO_INCREMENT,
    TaskStatusName VARCHAR(50) NOT NULL,
    TaskStatusIsResolved BOOL NOT NULL
);

CREATE TABLE Task (

	TaskId INT PRIMARY KEY AUTO_INCREMENT,
    TaskTitle VARCHAR(100) NOT NULL,
    TaskDetails TEXT NULL,
    TaskDueDate DATE NOT NULL,
    TaskEstimatedHours DECIMAL(5, 2) NULL,
    ProjectId CHAR(50) NOT NULL,
    WorkerId INT NOT NULL,
    TaskTypeId INT NOT NULL,
    TaskStatusId INT NULL,
    ParentTaskId INT NULL,
    
    FOREIGN KEY fk_Task_ProjectWorker (ProjectId, WorkerId)
		REFERENCES ProjectWorker(ProjectId, WorkerId),
	FOREIGN KEY fk_Task_TaskType (TaskTypeId)
		REFERENCES TaskType(TaskTypeId),
	FOREIGN KEY fk_Task_TaskStatus (TaskStatusId)
		REFERENCES TaskStatus(TaskStatusId),
	FOREIGN KEY fk_Task_Task (ParentTaskId)
		REFERENCES Task(TaskId)
);
