insert into training_types (training_type_id, training_type_name) values
  (1,'Strength'),
  (2,'Cardio'),
  (3,'Yoga'),
  (4,'Pilates'),
  (5,'CrossFit'),
  (6,'Zumba')
on conflict (training_type_id) do nothing;