CREATE TABLE MessageThreads(
   Id INT IDENTITY(1, 1),
   Topic VARCHAR(200),
   CONSTRAINT MessageThreadsPK PRIMARY KEY(Id)
);

CREATE TABLE Messages(
    Id INT IDENTITY(1, 1),
    Content VARCHAR(2000),
    SenderId INT,
    MessageThreadId INT,
    SentDate datetime,
    CONSTRAINT MessagesPK PRIMARY KEY(Id),
    CONSTRAINT SenderMessageId_FK FOREIGN KEY(SenderId) REFERENCES Users(Id),
    CONSTRAINT MessageThreadMessage_FK FOREIGN KEY(MessageThreadId) REFERENCES MessageThreads(Id)
);