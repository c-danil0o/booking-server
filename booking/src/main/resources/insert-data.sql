-- Insert data into the address table
INSERT INTO address (city, number, street) VALUES
                                               ('City1', '123', 'Street A'),
                                               ('City2', '456', 'Street B'),
                                               ('City3', '789', 'Street C'),
                                               ('City4', '101', 'Street D'),
                                               ('City5', '202', 'Street E'),
                                               ('City6', '303', 'Street F'),
                                               ('City7', '404', 'Street G'),
                                               ('City8', '505', 'Street H'),
                                               ('City9', '606', 'Street I'),
                                               ('City10', '707', 'Street J');

-- Insert data into the account table for hosts
INSERT INTO account (is_blocked, role, email, password, is_activated)
VALUES
    (false, 'Host', 'host1@example.com', 'password1', true),
    (false, 'Host', 'host2@example.com', 'password2', true),
    (false, 'Host', 'host3@example.com', 'password3', true),
    (false, 'Host', 'host4@example.com', 'password4', true),
    (false, 'Host', 'host5@example.com', 'password5', true);

-- Insert data into the host table
INSERT INTO host (address_id, id, first_name, last_name, phone)
VALUES
    (1, 1, 'Host', 'One', '+1234567890'),
    (2, 2, 'Host', 'Two', '+2345678901'),
    (3, 3, 'Host', 'Three', '+3456789012'),
    (4, 4, 'Host', 'Four', '+4567890123'),
    (5, 5, 'Host', 'Five', '+5678901234');

-- Insert data into the account table for guests
INSERT INTO account (is_blocked, role, email, password, is_activated)
VALUES
    (false, 'Guest', 'guest1@example.com', 'password6', true),
    (false, 'Guest', 'guest2@example.com', 'password7', true),
    (false, 'Guest', 'guest3@example.com', 'password8', true),
    (false, 'Guest', 'guest4@example.com', 'password9', true),
    (false, 'Guest', 'guest5@example.com', 'password10', true);

-- Insert data into the guest table
INSERT INTO guest (times_cancelled, address_id, id, first_name, last_name, phone)
VALUES
    (0, 6, 6, 'Guest', 'One', '+6789012345'),
    (1, 7, 7, 'Guest', 'Two', '+7890123456'),
    (2, 8, 8, 'Guest', 'Three', '+8901234567'),
    (3, 9, 9, 'Guest', 'Four', '+9012345678'),
    (4, 10, 10, 'Guest', 'Five', '+0123456789');

-- Insert data into the accommodation table
INSERT INTO accommodation (average_grade, cancellation_deadline, is_auto_approval, is_price_per_guest, max_guests, min_guests, address_id, id, accommodation_type, description, name, is_approved)
VALUES
    (4.5, 7, true, true, 4, 1, 1, 1, 'Apartment', 'A cozy apartment', 'Cozy Apartment 1', true),
    (4.2, 5, false, false, 2, 1, 2, 2, 'Room', 'Comfortable room with a view', 'Room with a View', true),
    (4.8, 14, true, true, 6, 2, 3, 3, 'Hotel', 'Luxurious hotel with great amenities', 'Luxury Hotel', true),
    (4.0, 10, false, true, 8, 4, 4, 4, 'Apartment', 'Spacious apartment for groups', 'Spacious Group Apartment', true),
    (4.7, 3, true, false, 3, 1, 5, 5, 'Room', 'Charming room for a relaxing stay', 'Charming Room', true);

-- Insert data into the accommodation_amenities table
INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES
    (1, 'WiFi, Kitchen, TV'),
    (2, 'WiFi, Air Conditioning, Breakfast'),
    (3, 'Swimming Pool, Gym, Spa'),
    (4, 'WiFi, Kitchen, Parking'),
    (5, 'Air Conditioning, TV, Coffee Maker');

-- Insert data into the accommodation_availability table
INSERT INTO time_slot (is_occupied, price, start_date, end_date)
VALUES (false, 100.0, '2023-12-01 12:00:00', '2023-12-13 12:00:00'),
       (false, 110.0, '2024-01-01 12:00:00', '2024-01-17 12:00:00'),
       (false, 150.0, '2023-12-20 12:00:00', '2023-12-30 12:00:00'),
       (false, 120.0, '2024-01-15 12:00:00', '2024-01-27 12:00:00'),
       (false, 90.0, '2023-12-08 12:00:00', '2023-12-16 12:00:00'),
       (false, 95.0, '2024-01-01 12:00:00', '2024-01-15 12:00:00'),
       (false, 210.0, '2023-12-12 12:00:00', '2023-12-22 12:00:00'),
       (false, 200.0, '2024-01-18 12:00:00', '2024-01-28 12:00:00'),
       (false, 70.0, '2023-12-20 12:00:00', '2023-12-25 12:00:00'),
       (false, 75.0, '2024-01-06 12:00:00', '2024-01-10 12:00:00');

INSERT INTO accommodation_availability (accommodation_id, availability_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 9),
       (5, 10);

-- Insert data into the accommodation_photos table
INSERT INTO accommodation_photos (accommodation_id, photos)
VALUES
    (1, 'apartment1_1.jpg, apartment1_2.jpg'),
    (2, 'room1_1.jpg, room1_2.jpg'),
    (3, 'hotel1_1.jpg, hotel1_2.jpg'),
    (4, 'apartment2_1.jpg, apartment2_2.jpg'),
    (5, 'room2_1.jpg, room2_2.jpg');

-- Insert data into the guest_favorites table
INSERT INTO guest_favorites (favorites_id, favorite_to_id)
VALUES (1, 6),
       (2, 7),
       (3, 8),
       (4, 9),
       (5, 10);


/*INSERT INTO accommodation_favorite_to (favorites_id,favorite_to_id)
VALUES (1, 6),
       (2, 7),
       (3, 8),
       (4, 9),
       (5, 10);*/

-- Insert data into the host_host_reviews table
INSERT INTO review (grade, is_approved, author_id, comment)
VALUES
    (4, true, 6, 'Great host!'),
    (5, true, 7, 'Excellent hospitality'),
    (4, true, 8, 'Wonderful stay, highly recommend'),
    (3, true, 9, 'Good experience overall'),
    (5, true, 10, 'Perfect host, would come again');

-- Insert data into the host_host_reviews table
INSERT INTO host_host_reviews (host_id, host_reviews_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Insert data into the host_properties table
INSERT INTO host_properties (host_id, properties_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Insert data into the reservation table
INSERT INTO reservation (days, price, accommodation_id, guest_id, host_id, start_date, reservation_status)
VALUES
    (2, 200.0, 1, 6, 1, '2023-01-01 12:00:00', 'Approved'),
    (7, 560.0, 2, 7, 2, '2023-02-01 12:00:00', 'Pending'),
    (10, 1500.0, 3, 8, 3, '2023-03-01 12:00:00', 'Active'),
    (14, 1680.0, 4, 9, 4, '2023-04-01 12:00:00', 'Pending'),
    (5, 450.0, 5, 10, 5, '2023-05-01 12:00:00', 'Approved');

-- Insert data into the report table
INSERT INTO report (author_id, date, reported_user_id, reason)
VALUES
    (6, '2023-01-01 12:00:00', 2, 'Inappropriate behavior'),
    (7, '2023-01-02 12:00:00', 4, 'Violating community guidelines'),
    (2, '2023-01-03 12:00:00', 6, 'Suspicious activity'),
    (1, '2023-01-04 12:00:00', 8, 'Harassment'),
    (2, '2023-01-05 12:00:00', 9, 'Fraudulent behavior');

-- Insert data into the notification table
INSERT INTO notification (date, receiver_id, message)
VALUES
    ('2023-01-01 12:00:00', 2, 'New report received'),
    ('2023-01-02 12:00:00', 4, 'Community guidelines update'),
    ('2023-01-03 12:00:00', 6, 'Account verification required'),
    ('2023-01-04 12:00:00', 8, 'Reminder: Review your recent stay'),
    ('2023-01-06 12:00:00', 1, 'System maintenance scheduled'),
    ('2023-01-07 12:00:00', 3, 'Important security update'),
    ('2023-01-08 12:00:00', 5, 'Feedback requested for your recent experience'),
    ('2023-01-09 12:00:00', 7, 'Upcoming event: Host meetup'),
    ('2023-01-10 12:00:00', 9, 'Your account status has been updated');