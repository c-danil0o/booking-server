-- Delete all data from the tables

-- Delete data from the guest_favorites table
DELETE FROM guest_favorites;

-- Delete data from the accommodation_photos table
DELETE FROM accommodation_photos;

-- Delete data from the accommodation_availability table
DELETE FROM accommodation_availability;

-- Delete data from the accommodation_amenities table
DELETE FROM accommodation_amenities;

-- Delete data from the reservation table
DELETE FROM reservation;

-- Delete data from the report table
DELETE FROM report;

-- Delete data from the notification table
DELETE FROM notification;

-- Delete data from the review table
DELETE FROM review;

-- Delete data from the host_properties table
DELETE FROM host_properties;

-- Delete data from the host_host_reviews table
-- (Note: I'm assuming "host_host_reviews" is a mistake and should be "review" since the table name is defined as "review" in your script.)
DELETE FROM review;

-- Delete data from the accommodation table
DELETE FROM accommodation;

-- Delete data from the guest table
DELETE FROM guest;

-- Delete data from the host table
DELETE FROM host;

-- Delete data from the account table
-- Here, I'm assuming that you're deleting all accounts, but if you have specific conditions, add a WHERE clause.
DELETE FROM account;

-- Delete data from the address table
DELETE FROM address;

DELETE FROM time_slot;

-- Optionally reset any auto-increment fields if needed, like for the id columns in your tables
-- ALTER TABLE table_name AUTO_INCREMENT = 1;  -- This resets auto-increment values

