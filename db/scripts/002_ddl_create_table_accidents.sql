CREATE table if not exists accident (
                           id serial primary key,
                           name varchar(255),
                           text varchar(255),
                           address varchar(255),
                           type_id INT,
                          FOREIGN KEY (type_id) REFERENCES type(id)
);