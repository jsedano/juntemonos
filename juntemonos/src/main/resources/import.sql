INSERT INTO community (id, description, "name") VALUES(1, 'the official java user group guadalajara community', 'jug gdl');
INSERT INTO community (id, description, "name") VALUES(2, 'gathers every last thursday to talk about any topic regarding software development', 'j4g');
INSERT INTO community (id, description, "name") VALUES(3, 'the official kotlin user group guadalajara community', 'kotlin gdl');
INSERT INTO community (id, description, "name") VALUES(4, 'everything go programming related', 'golang gdl');
INSERT INTO community (id, description, "name") VALUES(5, 'ruby community, leader is Fer Perales', 'ruby mx');

INSERT INTO technology (id, description, "name") VALUES(1, 'java programming language', 'java');
INSERT INTO technology (id, description, "name") VALUES(2, 'kotlin programming language', 'kotlin');
INSERT INTO technology (id, description, "name") VALUES(3, 'go programming language', 'go');
INSERT INTO technology (id, description, "name") VALUES(4, 'go programming language', 'golang');
INSERT INTO technology (id, description, "name") VALUES(5, 'ruby programming language', 'ruby');
INSERT INTO technology (id, description, "name") VALUES(6, 'one can not imagine java without spring', 'spring');

INSERT INTO community_technologies(communities_id, technologies_id) VALUES(1, 1);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(1, 6);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(2, 1);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(2, 2);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(2, 3);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(2, 4);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(2, 5);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(3, 2);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(4, 3);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(4, 4);
INSERT INTO community_technologies(communities_id, technologies_id) VALUES(5, 5);