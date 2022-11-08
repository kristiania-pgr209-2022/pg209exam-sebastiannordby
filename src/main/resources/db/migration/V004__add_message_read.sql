CREATE TABLE MessageRead(
    Id INT IDENTITY(1, 1),
    UserId int not null ,
    MessageId int not null ,
    ReadAt datetime,
    CONSTRAINT UserId_FK FOREIGN KEY(UserId) REFERENCES Users(Id),
    CONSTRAINT MessageId_FK FOREIGN KEY(MessageId) REFERENCES Messages(Id)
);