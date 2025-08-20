insert into training_types (training_type_name) values ('Strength'), ('Cardio'), ('Yoga'), ('Pilates'), ('CrossFit'), ('Zumba');

insert into users (first_name, last_name, "password", username, isactive ) values ('Pablo', 'Chacon', '$2a$12$kgyjvQz/Gpwt6D15wsJurewyIhzXjQfgE.P8h0HEV.LM6yr6O9ecS', 'Pablo.Chacon', true);
insert into trainers (specialization_training_type_id, user_id) values (4,1);

insert into users (first_name, last_name, "password", username, isactive ) values ('Isabel', 'Miranda', '$2a$12$kgyjvQz/Gpwt6D15wsJurewyIhzXjQfgE.P8h0HEV.LM6yr6O9ecS', 'Isabel.Miranda', true);
insert into trainees (user_id) values (2);