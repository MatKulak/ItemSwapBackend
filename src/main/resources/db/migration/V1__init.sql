create table public._user
(
    id           uuid NOT NULL,
    first_name   character varying(255),
    last_name    character varying(255),
    username     character varying(255),
    email        character varying(255),
    phone_number character varying(255),
    password     character varying(255)
);

ALTER TABLE ONLY public._user
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public._user
    ADD CONSTRAINT user_unique_username UNIQUE (username);

ALTER TABLE ONLY public._user
    ADD CONSTRAINT user_unique_email UNIQUE (email);

ALTER TABLE ONLY public._user
    ADD CONSTRAINT user_unique_phone_number UNIQUE (phone_number);

create table public.token
(
    id         uuid NOT NULL,
    logged_out boolean,
    token      character varying(255),
    user_id    uuid NOT NULL
);

create table public.role
(
    id   uuid NOT NULL,
    name character varying(255)
);

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);

create table public.user_roles
(
    user_id   uuid NOT NULL,
    role_id   uuid NOT NULL
);

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_fkey_user FOREIGN KEY (user_id) REFERENCES public._user (id);

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_fkey_role FOREIGN KEY (role_id) REFERENCES public.role (id);

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_fkey_user FOREIGN KEY (user_id) REFERENCES public._user (id);

create table public.main_category
(
    id          uuid NOT NULL,
    name        character varying(255)
);

ALTER TABLE ONLY public.main_category
    ADD CONSTRAINT main_category_pkey PRIMARY KEY (id);

create table public.sub_category
(
    id          uuid NOT NULL,
    name        character varying(255)
);

ALTER TABLE ONLY public.sub_category
    ADD CONSTRAINT sub_category_pkey PRIMARY KEY (id);

create table public.main_category_sub_categories
(
    main_category_id uuid NOT NULL,
    sub_category_id  uuid NOT NULL
);

ALTER TABLE ONLY public.main_category_sub_categories
    ADD CONSTRAINT main_category_sub_categories_pkey PRIMARY KEY (main_category_id, sub_category_id);

ALTER TABLE ONLY public.main_category_sub_categories
    ADD CONSTRAINT main_category_sub_categories_fkey_main_category FOREIGN KEY (main_category_id) REFERENCES public.main_category (id);

ALTER TABLE ONLY public.main_category_sub_categories
    ADD CONSTRAINT main_category_sub_categories_fkey_sub_category FOREIGN KEY (sub_category_id) REFERENCES public.sub_category (id);

create table public.localization
(
    id uuid NOT NULL,
    country character varying(255),
    city character varying(255),
    postal_code character varying(255),
    street character varying(255)
);

ALTER TABLE ONLY public.localization
    ADD CONSTRAINT localization_pkey PRIMARY KEY (id);

create table public.advertisement (
                                      id uuid NOT NULL,
                                      title      character varying(255),
                                      description  character varying(1023),
                                      phone_number  character varying(255),
                                      add_date timestamp(6) without time zone NOT NULL,
                                      localization_id uuid NOT NULL,
                                      main_category_id uuid NOT NULL,
                                      user_id uuid NOT NULL
);

ALTER TABLE ONLY public.advertisement
    ADD CONSTRAINT advertisement_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.advertisement
    ADD CONSTRAINT advertisement_fkey_localization FOREIGN KEY (localization_id) REFERENCES public.localization (id);

ALTER TABLE ONLY public.advertisement
    ADD CONSTRAINT advertisement_fkey_main_category FOREIGN KEY (main_category_id) REFERENCES public.main_category (id);

ALTER TABLE ONLY public.advertisement
    ADD CONSTRAINT advertisement_fkey_user FOREIGN KEY (user_id) REFERENCES public._user (id);

create table public.advertisement_followers
(
    user_id          uuid NOT NULL,
    advertisement_id uuid NOT NULL
);

ALTER TABLE ONLY public.advertisement_followers
    ADD CONSTRAINT advertisement_followers_pkey PRIMARY KEY (user_id, advertisement_id);

ALTER TABLE ONLY public.advertisement_followers
    ADD CONSTRAINT advertisement_followers_fkey_user FOREIGN KEY (user_id) REFERENCES public._user (id);

ALTER TABLE ONLY public.advertisement_followers
    ADD CONSTRAINT advertisement_followers_fkey_advertisement FOREIGN KEY (advertisement_id) REFERENCES public.advertisement (id);

create table public.file
(
    id            uuid NOT NULL,
    original_name character varying(255),
    type          character varying(255),
    path          character varying(255),
    mime_type     character varying(255),
    size          bigint,
    deleted       boolean
);

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (id);

create table public.advertisement_files
(
    advertisement_id uuid NOT NULL,
    file_id          uuid NOT NULL
);

ALTER TABLE ONLY public.advertisement_files
    ADD CONSTRAINT advertisement_files_pkey PRIMARY KEY (advertisement_id, file_id);

ALTER TABLE ONLY public.advertisement_files
    ADD CONSTRAINT advertisement_files_fkey_advertisement FOREIGN KEY (advertisement_id) REFERENCES public.advertisement (id);

ALTER TABLE ONLY public.advertisement_files
    ADD CONSTRAINT advertisement_files_fkey_file FOREIGN KEY (file_id) REFERENCES public.file (id);

create table public.conversation
(
    id uuid NOT NULL,
    advertisement_id uuid NOT NULL,
    participant_id uuid NOT NULL
);

ALTER TABLE ONLY public.conversation
    ADD CONSTRAINT conversation_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.conversation
    ADD CONSTRAINT conversation_fkey_advertisement FOREIGN KEY (advertisement_id) REFERENCES public.advertisement (id);

ALTER TABLE ONLY public.conversation
    ADD CONSTRAINT conversation_fkey_participant FOREIGN KEY (participant_id) REFERENCES public._user (id);

create table public.message
(
    id        uuid                           NOT NULL,
    content   character varying(1023),
    send_date timestamp(6) without time zone NOT NULL,
    sender_id uuid                           NOT NULL
);

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_fkey_sender FOREIGN KEY (sender_id) REFERENCES public._user (id);

create table public.conversation_messages
(
    conversation_id uuid NOT NULL,
    message_id      uuid NOT NULL
);

ALTER TABLE ONLY public.conversation_messages
    ADD CONSTRAINT conversation_messages_pkey PRIMARY KEY (conversation_id, message_id);

ALTER TABLE ONLY public.conversation_messages
    ADD CONSTRAINT conversation_messages_fkey_conversation FOREIGN KEY (conversation_id) REFERENCES public.conversation (id);

ALTER TABLE ONLY public.conversation_messages
    ADD CONSTRAINT conversation_messages_fkey_message FOREIGN KEY (message_id) REFERENCES public.message (id);