ALTER TABLE ONLY public.conversation
    ADD CONSTRAINT conversation_unique UNIQUE (advertisement_id, participant_id);
