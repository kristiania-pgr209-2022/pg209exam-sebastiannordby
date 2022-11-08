CREATE TABLE MessageThreads(
   Id INT IDENTITY,
   Name VARCHAR(200)
);

CREATE TABLE Messages(
    MessageId INT IDENTITY,
    Content VARCHAR(2000),
    SenderId INT,
    MessageThreadId INT,
    SentDate datetime,
    CONSTRAINT SenderMessageId_FK FOREIGN KEY(SenderId) REFERENCES Users(Id),
    CONSTRAINT MessageThreadMessage_FK FOREIGN KEY(MessageThreadId) REFERENCES MessageThreads(Id)
);