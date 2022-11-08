CREATE TABLE MessageThreadMemberships(
    Id INT IDENTITY PRIMARY KEY,
    MessageThreadId INT NOT NULL,
    UserId INT NOT NULL,
    CONSTRAINT MessgageMembershipThreadFK FOREIGN KEY(MESSAGETHREADID) REFERENCES MessageThreads(Id),
    CONSTRAINT UserGroupMembershipFK FOREIGN KEY(UserId) REFERENCES Users(Id)
);


