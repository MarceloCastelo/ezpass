CREATE TABLE sites (
    name TEXT PRIMARY KEY UNIQUE NOT NULL,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    url TEXT NOT NULL,
    note TEXT
)