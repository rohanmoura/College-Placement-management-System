CREATE DATABASE IF NOT EXISTS cpms_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cpms_db;

-- Admin reset file
-- Login after running this file:
-- Username: admin
-- Password: admin123

INSERT INTO admin (username, password, role, email, contact)
VALUES ('admin', 'admin123', 'Admin', 'admin@cpms.local', '9999999999')
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    role = VALUES(role),
    email = VALUES(email),
    contact = VALUES(contact);
