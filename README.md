# Nedap Tracker
The Nedap Tracker is an intuitive web tool that can be used to view, query and visualise data about the jobs that Nedap accepts.

## What we are proud of 
As a group, we are proud of multiple things. A lot of work went towards both the front end and backend with all of us learning a lot about web applications and how they function with databases. Security was an optional requirement but was done with a great deal of enthusiasm as we attempted to replicate what major web applications provided to customers in terms of looking after their confidential information.Basic web authentication was implemented along with the JSON web token. Ultimately, It was important to be rest compliant an adhere to conventions.  Furthermore, the whole experience gave us something to try in our free time as the skills we learnt are applicable in the real world as we access websites on a daily basis but did not fully realize the amount of effort to produce what can be described as a work of art. All in all, we hope the work done in the project is only the beginning of what's to follow in our career as web developers.

## Login
The login page can be accessed via localhost:8080/dashboard. The credentials needed to access the specific pages are given below:

|     Role   |   Username   |  Password |
|:----------:|:------------:|:----------|
| Technician |    118824    | password1 |
|  Customer  |    101010    | password5 |
|  Customer  |    302178    | password7 |
|  Support   |    419284    | password2 |


Attempting to use an invalid username or password will deny access to either of those pages and display an error message, showing that the wrong credentials have been entered.

## Usage
The sidebar on the lefthand side of the page can be used to switch between the tabs described below. When the page is loaded, the Home Tab is loaded by default. The sidebar can also be used to log out. When the log out button is clicked, the page is redirected to the login page and the session storage of the browser is cleared. When the contact button is clicked, a new tab is opened and is directed to the official Nedap contact page.

### Home Tab
The Home Tab is displayed on default when the page reloads. This page is occupied primarily by the data table. The data table consists of all the jobs Nedap accepts. When a particular job in the table is clicked, the information tab is displayed with the information about the selected job. The table has functions to search, filter and sort through data. These functions are described below:

* Searching
  
  The search bar located above the table can be used to query the jobs in the table. The search bar can search through all the columns in the table.

* Filtering

  To the right of the search bar is the dropdown menu that can be used to filter the data to show all jobs, completed jobs or failed jobs.

* Sorting
 
  The data in the table can also be sorted by clicking the column headers which cause the data to be sorted (by ascending or descending order of the column selected).

The above functions can be used in tandem to query very accurately the required results.

### Information Tab
The information tab contains information about the chosen job. Along the top of the section are the executions of a job. When a particular execution is selected, a table in the bottom left is populated to display the step executions as well as a table on the bottom right displaying information about the parameters used. These step executions can also be clicked to display further information about a particular step.

### Infographics Tab
The infographics tab visualises the data from the table.

Along the top of the page are counts which show all jobs, successful jobs for the previous month, failed jobs for the previous month as well as ongoing jobs.

At the bottom left is a line graph which visualises the number of jobs by month in the current year which fall into different categories. 

The bottom right contains a pie chart which shows the percentages of successful and failed jobs.

Disclaimer: Currently a customer does not see any infographics but will be included in the final version during the final presentation.

For an employee of Nedap who may have the role of technician or customer support, this section displays data from all companies. For a customer of Nedap, this section displays only data from the jobs they have with Nedap.

### FAQ Tab
The Frequently Asked Questions tab contains a set of questions the users of the dashboard may have. When a question is clicked, a dropdown answer is displayed pertaining to the question clicked.

## Miscellaneous
Database transactions were not used in this project as in the scope of the project, our service only contains read operations meaning no such errors such as dirty read, phantom etc can occur. However, If the system is in use, write operations will be used which means that the dirty read error, repeatable read and phantom can occur. In order to solve it, the system can be made serializable.