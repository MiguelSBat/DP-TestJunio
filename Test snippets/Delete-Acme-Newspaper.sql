start transaction;
use `acmenewspaper`;
revoke all privileges on `acmenewspaper`.* from 'acme-user'@'%';
revoke all privileges on `acmenewspaper`.* from 'acme-manager'@'%';
drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';
drop database `acmenewspaper`;
commit;