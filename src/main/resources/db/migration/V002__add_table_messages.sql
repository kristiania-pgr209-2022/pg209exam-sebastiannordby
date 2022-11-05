CREATE TABLE Messages(
    MessageId INT IDENTITY,
    Content VARCHAR(2000),
    SenderId INT,
    ReceiverId INT,
    SentDate datetime,

    CONSTRAINT SenderId_fk FOREIGN KEY(SenderId) REFERENCES Users(Id),
    CONSTRAINT ReceiverId_fk FOREIGN KEY(ReceiverId) REFERENCES Users(Id)

);