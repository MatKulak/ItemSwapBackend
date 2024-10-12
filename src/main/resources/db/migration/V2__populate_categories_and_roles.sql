CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO public.role (id, name) VALUES
                                       (uuid_generate_v4(), 'USER'),
                                       (uuid_generate_v4(), 'ADMIN');

INSERT INTO public.main_category (id, name) VALUES
                                                (uuid_generate_v4(), 'ELECTRONICS'),
                                                (uuid_generate_v4(), 'FASHION'),
                                                (uuid_generate_v4(), 'AUTOMOTIVE'),
                                                (uuid_generate_v4(), 'HOME_AND_GARDEN'),
                                                (uuid_generate_v4(), 'SPORTS_AND_OUTDOORS');

WITH main_category_ids AS (
    SELECT id FROM public.main_category WHERE name IN ('ELECTRONICS', 'FASHION', 'AUTOMOTIVE', 'HOME_AND_GARDEN', 'SPORTS_AND_OUTDOORS')
)
SELECT * FROM main_category_ids;

INSERT INTO public.sub_category (id, name) VALUES
                                               (uuid_generate_v4(), 'MOBILE_PHONE'),
                                               (uuid_generate_v4(), 'COMPUTERS'),
                                               (uuid_generate_v4(), 'CAMERAS'),
                                               (uuid_generate_v4(), 'TVS');

INSERT INTO public.sub_category (id, name) VALUES
                                               (uuid_generate_v4(), 'MENS_CLOTHING'),
                                               (uuid_generate_v4(), 'WOMENS_CLOTHING'),
                                               (uuid_generate_v4(), 'ACCESSORIES'),
                                               (uuid_generate_v4(), 'SHOES');

INSERT INTO public.sub_category (id, name) VALUES
                                               (uuid_generate_v4(), 'CAR_PARTS'),
                                               (uuid_generate_v4(), 'MOTORCYCLES'),
                                               (uuid_generate_v4(), 'CAR_ACCESSORIES');

INSERT INTO public.sub_category (id, name) VALUES
                                               (uuid_generate_v4(), 'FURNITURE'),
                                               (uuid_generate_v4(), 'KITCHEN_APPLIANCES'),
                                               (uuid_generate_v4(), 'GARDEN_TOOLS');

INSERT INTO public.sub_category (id, name) VALUES
                                               (uuid_generate_v4(), 'SPORTS_EQUIPMENT'),
                                               (uuid_generate_v4(), 'OUTDOOR_GEAR'),
                                               (uuid_generate_v4(), 'FITNESS_ACCESSORIES');

INSERT INTO public.main_category_sub_categories (main_category_id, sub_category_id)
SELECT m.id, s.id FROM public.main_category m, public.sub_category s
WHERE m.name = 'ELECTRONICS' AND s.name IN ('MOBILE_PHONE', 'COMPUTERS', 'CAMERAS', 'TVS');

INSERT INTO public.main_category_sub_categories (main_category_id, sub_category_id)
SELECT m.id, s.id FROM public.main_category m, public.sub_category s
WHERE m.name = 'FASHION' AND s.name IN ('MENS_CLOTHING', 'WOMENS_CLOTHING', 'ACCESSORIES', 'SHOES');

INSERT INTO public.main_category_sub_categories (main_category_id, sub_category_id)
SELECT m.id, s.id FROM public.main_category m, public.sub_category s
WHERE m.name = 'AUTOMOTIVE' AND s.name IN ('CAR_PARTS', 'MOTORCYCLES', 'CAR_ACCESSORIES');

INSERT INTO public.main_category_sub_categories (main_category_id, sub_category_id)
SELECT m.id, s.id FROM public.main_category m, public.sub_category s
WHERE m.name = 'HOME_AND_GARDEN' AND s.name IN ('FURNITURE', 'KITCHEN_APPLIANCES', 'GARDEN_TOOLS');

INSERT INTO public.main_category_sub_categories (main_category_id, sub_category_id)
SELECT m.id, s.id FROM public.main_category m, public.sub_category s
WHERE m.name = 'SPORTS_AND_OUTDOORS' AND s.name IN ('SPORTS_EQUIPMENT', 'OUTDOOR_GEAR', 'FITNESS_ACCESSORIES');
