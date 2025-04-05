DELIMITER $$

-- Loop to create 500 schemas
CREATE PROCEDURE create_tenant_schemas_and_users()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 500 DO
        SET @schema_name = CONCAT('tenant_', i);
        SET @create_schema = CONCAT('CREATE SCHEMA IF NOT EXISTS ', @schema_name);
        PREPARE stmt FROM @create_schema;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        -- Create the users table in the current schema
        SET @create_users_table = CONCAT('
            CREATE TABLE IF NOT EXISTS ', @schema_name, '.users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                tenant_id VARCHAR(255) NOT NULL
            )
        ');
        PREPARE stmt FROM @create_users_table;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

-- Call the procedure to create schemas and users tables
CALL create_tenant_schemas_and_users();

-- Drop the procedure after execution
DROP PROCEDURE create_tenant_schemas_and_users;