# Nedap Dashboard - Team 23

For now this will serve as an *installation guide* for our project. This should help you to install MySQL, load the data dump to your databse and remove errors that occured while trying to connect to the database server via the JDBC client. 
Most of the dependencies will be defined inside of the *pom.xml* file of this project. 
This version of the README only supports an installation guide for *unix* based OS for now, more particularly,
for the **MAC OS**. This will be extended to *Linux* and *Windows* in the future. 

## Installation 

### Mac Os

First download and install the MySQL Community Server from https://dev.mysql.com/downloads/mysql/.
Download the package *macOS 10.14 (x86, 64-bit), DMG Archive* (as to this date). You will be asked to *sign up* or create a new *account*. Simply ignore this, scroll down and click on "*No thanks, just start my download.*" This system is open source and for free. 
If the package has been downloaded, do the following:
1. Install the MySQL Community Server Application
2. Remember the pasword you used during the installation process
3. Go to *System Preference > MySQL* and click on *Start MySQL Server*

Once you followed the steps open your *Terminal* and do the following.
```sh
$ open -e .bash_profile
```
If you do not have the file then simply create one by:
```sh
$ touch .bash_profile
$ open -e .bash_profile
```
After that copy and paste **export PATH=${PATH}:/usr/local/mysql/bin/** into the *.bash_profile*, save it and open a new window of *Terminal*.
For the next steps make sure that you MySQL Server is running. Now in order to connect to your database you need your password that you used for your database during the installation process. In the following we will also set the server *time_zone* values correctly since JDBC will complain about not recognizing them.
```sh
$ mysql -u root -p
```
Type in your password and type these statements: 
```sh
mysql> SET @@global.time_zone = '+00:00';
mysql> SET @@global.time_zone = '+00:00';
```
To check whether altered successfully: 
```sh
mysql> SELECT @@global.time_zone, @@session.time_zone;
```
After everything has been set up, we can now load the data dump file to the database.
First quit the MySQL Server application by typing in *quit*:
```sh
mysql> quit
```
Now in order to load the dump file to your database, you first go to the directory where the *NedapDump.mysql* file is saved. Then simply run the following command:
```sh
$ mysql -u root -p NedapDump < NedapDump.mysql 
```
In order to check whether it worked:
```sh
mysql> show databases; 
```
It should show you :
![screen](nedapDashboard/src/main/webapp/pictures/readme/readme_screen1.png)
Now  everything should work properly and the project can be run and tested out. 
Information on how to navigate through MySQL with the Terminal visit https://dev.mysql.com/doc/refman/8.0/en/.
