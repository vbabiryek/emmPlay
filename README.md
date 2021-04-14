# emmPlay

EMM solution for Android device management. This project is solely for the exploration of an <br>
Enterprise Mobility Management test console via GAPI's Android Management API.<br>

Technologies/Languages/Frameworks used:

JSP
<br>
Javascript
<br>
JQuery
<br>
HTML5/CSS
<br>
Java
<br>
local storage
<br>
Ajax
<br>
SpringBoot 
<br>
JUnit
<br>
DB - MySQL Workbench version 8.0.22 Build 107600 CE (Community)

SQL queries:

CREATE DATABASE MYDB;
CREATE DATABASE MYDB2;
CREATE DATABASE POLICY_DATABASE;
CREATE DATABASE DEVICE_POLICY;
CREATE DATABASE DATABASE_THREE;
CREATE DATABASE DATABASE_FOUR;
DROP DATABASE POLICY_DATABASE;
DROP DATABASE DATABASE_THREE;
USE MYDB2;
USE POLICY_DATABASE;
USE DATABASE_THREE;
USE DATABASE_FOUR;
DESC Application_policy;
USE DEVICE_POLICY;
SELECT * FROM POLICY;
SELECT * FROM Password_Requirements;
SELECT * FROM Permission_Grants;
SELECT * FROM Freeze_Periods;
SELECT * FROM System_Update_Requirements;
SELECT * FROM Policy_Enforcement_Rules;
SELECT * FROM Advanced_Security_Overrides;
SELECT * FROM App_Auto_Update_Policy;
SELECT * FROM Auto_Update;
SELECT * FROM App_Track_Info;
ALTER TABLE Auto_Update DROP COLUMN app_auto_update;
SELECT * FROM Application_Policy;
DESC application_policy;
SELECT * FROM template_policy;
SELECT * FROM configuration_variables;
DELETE from Application_Policy WHERE id > 4;
select * from managedConfigurationTemplate;
UPDATE Application_Policy SET managed_configuration_template = '{"firstKey":"firstString","secondKey":"secondVal"}';
UPDATE Application_Policy SET disabled = false WHERE id = 1;
DROP TABLE Application_Policy;
DROP TABLE managedConfigurationTemplate;
DROP TABLE template_policy;
DROP TABLE configuration_variables;
DROP TABLE App_Auto_Update_Policy;
SELECT concat('ALTER TABLE ', TABLE_NAME, ' DROP FOREIGN KEY ', CONSTRAINT_NAME, ';') 
FROM information_schema.key_column_usage 
WHERE CONSTRAINT_SCHEMA = 'mydb2' 
AND referenced_table_name IS NOT NULL;
SET foreign_key_checks = 0;
SET foreign_key_checks = 1;
alter table application_policy drop foreign key FKdypcgctngm7typbkljnwb54ll;
alter table application_policy drop foreign key FK1wx1txqty2t7gqxjo582ok04o;
alter table application_policy drop foreign key FK9dplx1k1x8u3qjb7xbk8oy2o8;
alter table template_policy drop foreign key configuration_variable_ibfk_1;
SELECT * FROM applications_policye_delegated_scopes;
SELECT * FROM applications_policye_accessible_track_ids;
ALTER TABLE Application_Policy DROP COLUMN permission_grants;
ALTER TABLE Application_Policy DROP COLUMN permission_grants_state;
ALTER TABLE Application_Policy DROP COLUMN policy_id;
DELETE FROM Application_Policy WHERE id = 2;
ALTER TABLE Application_Policy DROP COLUMN app_track_info;
DESC System_Update_Requirements;
DESC Permissions_Requirements;
DESC Policy_Enforcement_Rules;
DESC Application_Policy;
DESC applications_policye_delegated_scopes;
DESC App_Auto_Update_Policy;

-- DDL (DATA DEFINITION LANGUAGE how the schema exists)
-- This will create the template policy table that is connected to the application policy table by the template ids
USE POLICY_DATABASE;
USE DATABASE_THREE;
USE DATABASE_FOUR;
-- Table One 
CREATE TABLE managedConfigurationTemplate (
    row_id INT NOT NULL AUTO_INCREMENT,
    application_policy_id BIGINT NOT NULL, 
    template_id VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (row_id),
    FOREIGN KEY (application_policy_id) REFERENCES application_policy(id)
);
-- int row_id, String templateId(must be unique it must come from outside the table i.e outside of table two)
-- Table Two represents the hashmap of the configuration variables needed for table one to be created a dependecy
-- int row_id, String templateId(foreign_key), String configuration_key(doesn't have to be unique composite uniqueness because its the config key combined that makes it unique from the templateId), String configuration_val
CREATE TABLE configuration_variables (
    row_id INT NOT NULL AUTO_INCREMENT,
    template_id VARCHAR(255) NOT NULL,
    configuration_key VARCHAR(255) NOT NULL,
    configuration_val VARCHAR(255) NOT NULL,
    PRIMARY KEY (row_id),
    FOREIGN KEY (template_id) REFERENCES managedConfigurationTemplate(template_id)
);
-- TODO: DML queries (DATA MANIPULATION QUERIES)
insert into managedConfigurationTemplate (application_policy_id,template_id) values(?,?);
insert into configuration_variables (template_id, configuration_key, configuration_val) values(?,?,?);
delete from managedConfigurationTemplate where template_id = '05804983173632807841';
delete from configuration_variables where template_id = '05804983173632807841';
-- find all templateIds
select * from managedConfigurationTemplate where  template_id = '05804983173632807841';
-- find this template id by this template id
select * from managedConfigurationTemplate where application_policy_id = ? and template_id = ?;
-- find all configuration variables
select * from configuration_variables cv where cv.template_id = (select tp.template_id from managedConfigurationTemplate tp where tp.template_id = ?);
-- find this configuration variable by this template id
select * from configuration_variables cv where cv.template_id = (select tp.template_id from managedConfigurationTemplate tp where tp.template_id = '09208293921768970497');
select * from configuration_variables;

