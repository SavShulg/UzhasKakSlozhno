-- liquibase formatted sql

-- changeset jrembo:1
CREATE INDEX st_name_idx ON student (name);

-- changest jrembo:2

CREATE INDEX fc_color_name_idx on faculty (name, color);
