CREATE TABLE Users(
  Id INT IDENTITY PRIMARY KEY,
  Name VARCHAR(200),
  EmailAddress VARCHAR(255),
  Nickname VARCHAR(20),
  Bio VARCHAR(250),
);

INSERT INTO Users(Name, EmailAddress, Nickname, Bio) VALUES(
    'Kristoffer', 'kristoffer@hotmail.com', 'Kris', 'Glad i programmering'
);

INSERT INTO Users(Name, EmailAddress, Nickname, Bio) VALUES(
   'Mats', 'mats@gmail.com', 'CityBoy', 'Finner meg på byen'
);

INSERT INTO Users(Name, EmailAddress, Nickname, Bio) VALUES(
   'Muhammed', 'muhammed@outlook.com', 'Muham', 'Love life'
);

INSERT INTO Users(Name, EmailAddress, Nickname, Bio) VALUES(
   'Marius', 'marius@outlook.com', 'Mar1us', 'I love kebab'
);

INSERT INTO Users(Name, EmailAddress, Nickname, Bio) VALUES(
   'Lars', 'lars@larsen.com', 'VolvoBoi98_240', 'Finner meg i værkste'
);


