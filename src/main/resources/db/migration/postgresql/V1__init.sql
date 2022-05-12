CREATE SEQUENCE customer_entity_id_seq START 1 INCREMENT 1;

CREATE SEQUENCE document_id_seq START 1 INCREMENT 1;

CREATE SEQUENCE facility_id_seq START 1 INCREMENT 1;

CREATE SEQUENCE identifier_mapping_id_seq START 1 INCREMENT 1;

CREATE TABLE customer_entity
(
    id             INT8         NOT NULL,
    city           VARCHAR(255) NOT NULL,
    contact_name   VARCHAR(255) NOT NULL,
    mnemonic       VARCHAR(255) NOT NULL,
    name           VARCHAR(255) NOT NULL,
    phone_number   VARCHAR(255) NOT NULL,
    state          VARCHAR(255) NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    zip_code       VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE document (
  document_id       INT8         NOT NULL,
  document_filename VARCHAR(255) NOT NULL,
  document_format   VARCHAR(255) NOT NULL,
  document_location VARCHAR(255) NOT NULL,
  document_subtitle VARCHAR(255),
  document_title    VARCHAR(255),
  document_type     VARCHAR(255) NOT NULL,
  person_id         VARCHAR(255) NOT NULL,
  number_of_pages   INT8,
  service_date      DATE,
  facility_id       INT8         NOT NULL,
  PRIMARY KEY (document_id)
);

CREATE TABLE facility (
  facility_id             INT8         NOT NULL,
  facility_city           VARCHAR(255) NOT NULL,
  facility_name           VARCHAR(255) NOT NULL,
  facility_state          VARCHAR(255) NOT NULL,
  facility_street_address VARCHAR(255) NOT NULL,
  facility_zip_code       VARCHAR(255) NOT NULL,
  customer_entity_id      INT8         NOT NULL,
  PRIMARY KEY (facility_id)
);

CREATE TABLE identifier_mapping (
  identifier_mapping_id   INT8 NOT NULL,
  source_identifier_type  INT4,
  source_identifier_value INT8,
  source_system_type      INT4,
  target_identifier_type  INT4,
  target_identifier_value INT8,
  target_system_type      INT4,
  facility_id             INT8 NOT NULL,
  PRIMARY KEY (identifier_mapping_id)
);

ALTER TABLE customer_entity
  ADD CONSTRAINT UK_n2in7fwgill4rtu12paxjweea UNIQUE (mnemonic);

ALTER TABLE identifier_mapping
  ADD CONSTRAINT UK6bl84rpkxgdsb3waiti9eumr3 UNIQUE (facility_id, source_system_type, target_system_type, source_identifier_type, target_identifier_type, source_identifier_value, target_identifier_value);

ALTER TABLE document
  ADD CONSTRAINT FKt0u15777s8a0al23bb9yxxk0 FOREIGN KEY (facility_id) REFERENCES facility;

ALTER TABLE facility
  ADD CONSTRAINT FKt0qjfl2td8h5p1g2reathay0o FOREIGN KEY (customer_entity_id) REFERENCES customer_entity;

ALTER TABLE identifier_mapping
  ADD CONSTRAINT FKdxwr8lnsilaqotk34p49c2gux FOREIGN KEY (facility_id) REFERENCES facility;
