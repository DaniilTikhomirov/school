-- liquibase formatted sql

-- changeset danil:1
CREATE INDEX name_index ON student (name);

-- changeset danil:3
CREATE INDEX color_index ON faculty (name, color);