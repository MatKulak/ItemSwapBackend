-- CREATE TABLE public.users (
--                        id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
--                        username VARCHAR(255),
--                        email VARCHAR(255),
--                        name VARCHAR(255),
--                        surname VARCHAR(255),
--                        password VARCHAR(255),
--                        phoneNumber VARCHAR(255)
-- );

-- CREATE TABLE users (
--                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  -- UUID for the unique identifier
--                        name VARCHAR(255) NOT NULL,                      -- Name of the user
--                        surname VARCHAR(255) NOT NULL,                   -- Surname of the user
--                        username VARCHAR(255) NOT NULL UNIQUE,           -- Unique username
--                        email VARCHAR(255) NOT NULL UNIQUE,              -- Unique email
--                        phone_number VARCHAR(15) NOT NULL UNIQUE,        -- Unique phone number, adjust length if needed
--                        password BYTEA NOT NULL                          -- Storing the encoded password as BYTEA
-- );