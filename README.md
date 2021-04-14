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
<br>
CREATE DATABASE MYDB2;
<br>
CREATE DATABASE POLICY_DATABASE;
<br>
CREATE DATABASE DEVICE_POLICY;
<br>
CREATE DATABASE DATABASE_THREE;
<br>
CREATE DATABASE DATABASE_FOUR;
<br>
DROP DATABASE POLICY_DATABASE;
<br>
DROP DATABASE DATABASE_THREE;
<br>
USE MYDB2;
<br>
USE POLICY_DATABASE;
<br>
USE DATABASE_THREE;
<br>
USE DATABASE_FOUR;
<br>
DESC Application_policy;
<br>
USE DEVICE_POLICY;
<br>
SELECT * FROM POLICY;
<br>
SELECT * FROM Password_Requirements;
<br>
SELECT * FROM Permission_Grants;
<br>
SELECT * FROM Freeze_Periods;
<br>
SELECT * FROM System_Update_Requirements;
<br>
SELECT * FROM Policy_Enforcement_Rules;
<br>
SELECT * FROM Advanced_Security_Overrides;
<br>
SELECT * FROM App_Auto_Update_Policy;
<br>
SELECT * FROM Auto_Update;
<br>
SELECT * FROM App_Track_Info;
<br>
ALTER TABLE Auto_Update DROP COLUMN app_auto_update;
<br>
SELECT * FROM Application_Policy;
<br>
DESC application_policy;
<br>
SELECT * FROM template_policy;
<br>
SELECT * FROM configuration_variables;
<br>
DELETE from Application_Policy WHERE id > 4;
<br>
select * from managedConfigurationTemplate;
<br>
UPDATE Application_Policy SET managed_configuration_template = '{"firstKey":"firstString","secondKey":"secondVal"}';
<br>
UPDATE Application_Policy SET disabled = false WHERE id = 1;
<br>
DROP TABLE Application_Policy;
<br>
DROP TABLE managedConfigurationTemplate;
<br>
DROP TABLE template_policy;
<br>
DROP TABLE configuration_variables;
<br>
DROP TABLE App_Auto_Update_Policy;
<br>
SELECT concat('ALTER TABLE ', TABLE_NAME, ' DROP FOREIGN KEY ', CONSTRAINT_NAME, ';') 
FROM information_schema.key_column_usage 
WHERE CONSTRAINT_SCHEMA = 'mydb2' 
AND referenced_table_name IS NOT NULL;
<br>
SET foreign_key_checks = 0;
<br>
SET foreign_key_checks = 1;
<br>
alter table application_policy drop foreign key FKdypcgctngm7typbkljnwb54ll;
<br>
alter table application_policy drop foreign key FK1wx1txqty2t7gqxjo582ok04o;
<br>
alter table application_policy drop foreign key FK9dplx1k1x8u3qjb7xbk8oy2o8;
<br>
alter table template_policy drop foreign key configuration_variable_ibfk_1;
<br>
SELECT * FROM applications_policye_delegated_scopes;
<br>
SELECT * FROM applications_policye_accessible_track_ids;
<br>
ALTER TABLE Application_Policy DROP COLUMN permission_grants;
<br>
ALTER TABLE Application_Policy DROP COLUMN permission_grants_state;
<br>
ALTER TABLE Application_Policy DROP COLUMN policy_id;
<br>
DELETE FROM Application_Policy WHERE id = 2;
<br>
ALTER TABLE Application_Policy DROP COLUMN app_track_info;
<br>
DESC System_Update_Requirements;
<br>
DESC Permissions_Requirements;
<br>
DESC Policy_Enforcement_Rules;
<br>
DESC Application_Policy;
<br>
DESC applications_policye_delegated_scopes;
<br>
DESC App_Auto_Update_Policy;
<br>
-- DDL (DATA DEFINITION LANGUAGE how the schema exists)
<br>
-- This will create the template policy table that is connected to the application policy table by the template ids
<br>
USE POLICY_DATABASE;
<br>
USE DATABASE_THREE;
<br>
USE DATABASE_FOUR;
<br>
-- Table One 
<br>
CREATE TABLE managedConfigurationTemplate (
<br>
    row_id INT NOT NULL AUTO_INCREMENT,
    <br>
    application_policy_id BIGINT NOT NULL, 
    <br>
    template_id VARCHAR(255) UNIQUE NOT NULL,
    <br>
    PRIMARY KEY (row_id),
    <br>
    FOREIGN KEY (application_policy_id) REFERENCES application_policy(id)
    <br>
);
-- int row_id, String templateId(must be unique it must come from outside the table i.e outside of table two)
<br>
-- Table Two represents the hashmap of the configuration variables needed for table one to be created a dependecy
<br>
-- int row_id, String templateId(foreign_key), String configuration_key(doesn't have to be unique composite uniqueness because its the config key combined that
<br>
makes it unique from the templateId), String configuration_val
<br>
CREATE TABLE configuration_variables (
<br>
    row_id INT NOT NULL AUTO_INCREMENT,
    <br>
    template_id VARCHAR(255) NOT NULL,
    <br>
    configuration_key VARCHAR(255) NOT NULL,
    <br>
    configuration_val VARCHAR(255) NOT NULL,
    <br>
    PRIMARY KEY (row_id),
    <br>
    FOREIGN KEY (template_id) REFERENCES managedConfigurationTemplate(template_id)
    <br>
);
<br>
-- TODO: DML queries (DATA MANIPULATION QUERIES)
<br>
insert into managedConfigurationTemplate (application_policy_id,template_id) values(?,?);
<br>
insert into configuration_variables (template_id, configuration_key, configuration_val) values(?,?,?);
<br>
delete from managedConfigurationTemplate where template_id = '05804983173632807841';
<br>
delete from configuration_variables where template_id = '05804983173632807841';
<br>
-- find all templateIds
<br>
select * from managedConfigurationTemplate where  template_id = '05804983173632807841';
<br>
-- find this template id by this template id
<br>
select * from managedConfigurationTemplate where application_policy_id = ? and template_id = ?;
<br>
-- find all configuration variables
<br>
select * from configuration_variables cv where cv.template_id = (select tp.template_id from managedConfigurationTemplate tp where tp.template_id = ?);
<br>
-- find this configuration variable by this template id
<br>
select * from configuration_variables cv where cv.template_id = (select tp.template_id from managedConfigurationTemplate tp where tp.template_id = '09208293921768970497');
<br>
select * from configuration_variables;

