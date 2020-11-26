delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$PaCMdgtDzrzGV3hoqR/QvelWb.uyrM2uf0km9ZyTnmsSJuHFQnHYa', 'admin'),
(2, true, '$2a$08$t99vlr/AkBu8y88Dr8OdEeRBNF/c3rXb3hNX7wyUhjRL5aSOwWvvC', 'user');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');
