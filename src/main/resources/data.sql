insert into users(username,email,password) values('admin','admin@test.nl','$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i'),('user','user@test.nl','$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i');
insert into user_roles(user_id,roles) values(1, 'ADMIN'),(2,'USER'),(1,'USER');