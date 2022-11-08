CREATE TABLE GroupMemberships(
    Id INT IDENTITY,
    GroupId INT NOT NULL,
    UserId INT NOT NULL,
    CONSTRAINT GroupMembershipGroupFK FOREIGN KEY(GroupId) REFERENCES Groups(GroupId),
    CONSTRAINT UserGroupMembershipFK FOREIGN KEY(UserId) REFERENCES Users(Id)
);


