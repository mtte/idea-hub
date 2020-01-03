-- SCRIPT TO FILL INITIAL DATA INTO THE DB

-- USER admin
INSERT INTO ideahub."user" (name, password, role) VALUES ('admin', '$2a$14$sQ8zPYv.5zfzE4G6m.MlYu444jp5ULZp8sG1iQIOGwAExu7H3wvne', 'ADMIN');

-- USER author
INSERT INTO ideahub."user" (name, password, role) VALUES ('author', '$2a$14$sQ8zPYv.5zfzE4G6m.MlYu444jp5ULZp8sG1iQIOGwAExu7H3wvne', 'AUTHOR');

-- USER test
INSERT INTO ideahub."user" (name, password, role) VALUES ('test', '$2a$14$sQ8zPYv.5zfzE4G6m.MlYu444jp5ULZp8sG1iQIOGwAExu7H3wvne', 'USER');