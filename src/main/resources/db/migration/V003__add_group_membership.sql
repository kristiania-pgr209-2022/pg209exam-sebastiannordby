CREATE TABLE MessageThreadMemberships(
    Id INT IDENTITY(1, 1),
    MessageThreadId INT NOT NULL,
    UserId INT NOT NULL,
    CONSTRAINT MessageThreadMembershipsPK PRIMARY KEY(Id),
    CONSTRAINT MessgageMembershipThreadFK FOREIGN KEY(MESSAGETHREADID) REFERENCES MessageThreads(Id),
    CONSTRAINT UserGroupMembershipFK FOREIGN KEY(UserId) REFERENCES Users(Id)
);


