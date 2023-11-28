-- Drop tables in reverse order to avoid foreign key constraints
-- Disable foreign key checks temporarily

-- Drop tables
DROP TABLE IF EXISTS guest_favorites;
DROP TABLE IF EXISTS host_host_reviews;
DROP TABLE IF EXISTS host_properties;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS report;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS accommodation_photos;
DROP TABLE IF EXISTS accommodation_availability;
DROP TABLE IF EXISTS accommodation_amenities;
DROP TABLE IF EXISTS accommodation;
DROP TABLE IF EXISTS time_slot;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS host;
DROP TABLE IF EXISTS guest;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS address;
