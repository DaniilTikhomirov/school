CREATE TABLE human(
    id BIGINT PRIMARY KEY,
    car_id BIGINT NOT NULL UNIQUE REFERENCES car (id),
    name text NOT NULL,
    age INTEGER NOT NULL CHECK ( age > 0 ),
    can_ride BOOLEAN NOT NULL
);

CREATE TABLE car(
    id BIGINT PRIMARY KEY,
    human_id BIGINT REFERENCES human (id),
    mark varchar(255) NOT NULL,
    model varchar(255) NOT NULL,
    price INTEGER NOT NULL
);

