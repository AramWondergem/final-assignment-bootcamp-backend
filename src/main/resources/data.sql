insert into users(username,email,password) values('admin','admin@test.nl','$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i'),('user','user@test.nl','$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i');
insert into user_roles(user_email,roles) values('admin@test.nl', 'ADMIN'),('user@test.nl','USER'),('admin@test.nl','USER');