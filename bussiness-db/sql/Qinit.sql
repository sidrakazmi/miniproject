CREATE USER 'test'@'%' IDENTIFIED BY 'mypassword';
GRANT ALL on *.* TO 'test'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON `northwind` . * TO 'test'@'%';
GRANT ALL PRIVILEGES ON `recommendationdb` . * TO 'test'@'%';
GRANT ALL PRIVILEGES ON `supportdb` . * TO 'test'@'%';