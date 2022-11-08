CREATE TABLE MessageThreads(
   Id INT IDENTITY,
   Topic VARCHAR(200),

   CONSTRAINT MessageThreadsPK PRIMARY KEY(Id)
);

CREATE TABLE Messages(
    MessageId INT IDENTITY PRIMARY KEY,
    Content VARCHAR(2000),
    SenderId INT,
    MessageThreadId INT,
    SentDate datetime,
    CONSTRAINT MessagesPK PRIMARY KEY(MessageId),
    CONSTRAINT SenderMessageId_FK FOREIGN KEY(SenderId) REFERENCES Users(Id),
    CONSTRAINT MessageThreadMessage_FK FOREIGN KEY(MessageThreadId) REFERENCES MessageThreads(Id)
);