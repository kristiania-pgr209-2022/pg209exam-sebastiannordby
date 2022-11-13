CREATE TABLE MessageRead(
    UserId INT NOT NULL,
    MessageId INT NOT NULL,
    ReadAt DATETIME NOT NULL,
    PRIMARY KEY(MessageId, UserId),
    CONSTRAINT UserId_FK FOREIGN KEY(UserId) REFERENCES Users(Id),
    CONSTRAINT MessageId_FK FOREIGN KEY(MessageId) REFERENCES Messages(Id)
);