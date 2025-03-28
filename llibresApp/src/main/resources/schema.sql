CREATE TABLE IF NOT EXISTS books (
    id_Llibre BIGINT AUTO_INCREMENT PRIMARY KEY,
    titol VARCHAR(150) UNIQUE NOT NULL,
    autor VARCHAR(150) NOT NULL,
    editorial VARCHAR(150),
    datapublicacio DATE,
    tematica VARCHAR(150),
    isbn VARCHAR (13) UNIQUE NOT NULL
);
