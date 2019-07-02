package utwente.nedap.team23.nedapDashboard.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utwente.nedap.team23.nedapDashboard.model.Account;
import utwente.nedap.team23.nedapDashboard.model.BatchJobExecution;
import utwente.nedap.team23.nedapDashboard.model.BatchJobExecutionContext;
import utwente.nedap.team23.nedapDashboard.model.BatchJobExecutionParams;
import utwente.nedap.team23.nedapDashboard.model.BatchJobInstance;
import utwente.nedap.team23.nedapDashboard.model.BatchStepExecution;
import utwente.nedap.team23.nedapDashboard.model.BatchStepExecutionContext;
import utwente.nedap.team23.nedapDashboard.model.Customer;
import utwente.nedap.team23.nedapDashboard.model.Organization;
import utwente.nedap.team23.nedapDashboard.model.Staff;
import utwente.nedap.team23.nedapDashboard.model.Account.Role;

public enum ResourceDAO {
	instance;
	
	private Connection dbConnection;

	private ResourceDAO () { dbConnection = DatabaseConnection.getInstance(); }
	

	/**
	 * Returns a list of job instances from a given organization. 
	 * A range of dates can be provided to filter out specific jobs.
	 * 
	 * @param limit		amount of jobs - 50 is default
	 * 
	 * @param start		lowest date (included)
	 * 				
	 * @param end		highest date (excluded)
	 * 
	 * @return			list of <code>BatchJobInstance</code>
	 * 
	 * @throws SQLException
	 */
	public List<BatchJobInstance> getJobInstances(int limit, String orgID, Date start, Date end) 
			throws SQLException {

		Date startDate = (start != null) ? start : new Date(System.currentTimeMillis());
		String range = (end != null) ? "bje.CREATE_TIME between ? AND ? " : "bje.CREATE_TIME >= ? ";
		ArrayList<BatchJobInstance> jobsList = new ArrayList<>();
		String pQuery = "select * from batch_job_instance bji, batch_job_execution bje, "
				+ "batch_job_customer bjc, organization org where bjc.ORGANIZATION_ID = ? "
				+ "and bjc.JOB_INSTANCE_ID = bji.JOB_INSTANCE_ID "
				+ "and bjc.ORGANIZATION_ID = org.o_id "
				+ "and bji.JOB_INSTANCE_ID = bje.JOB_INSTANCE_ID "
				+ "and " + range
				+ "limit ?;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setString(1, orgID);
		pSt.setString(2, startDate.toString());
		if (end != null) { pSt.setString(3, end.toString()); }
		int pos = end != null ? 4 : 3;
		pSt.setInt(pos, limit);
		ResultSet rs = pSt.executeQuery();

		BatchJobInstance bji;
		while (rs.next()) {
			bji = new BatchJobInstance();
			bji.setJobInstanceID(rs.getLong("JOB_INSTANCE_ID"));
			bji.setVersion(rs.getLong("VERSION"));
			bji.setJobName(rs.getString("JOB_NAME"));
			bji.setJobKey(rs.getString("JOB_KEY"));
			bji.setStatus(rs.getString("STATUS"));
			bji.setOrgID(rs.getString("o_id"));
			bji.setOrganizationName(rs.getString("name"));

			jobsList.add(bji);
		}
		pSt.close();
		rs.close(); 

		return jobsList;
	} //    http://domain.com/nedapDashboard/dashboard/employee



	/**
	 * Returns a list of job instances. A range of dates can
	 * be provided to filter out specific jobs.
	 * 
	 * @param limit		amount of jobs - 50 is default
	 * 
	 * @param start		lowest date (included)
	 * 				
	 * @param end		highest date (excluded)
	 * 
	 * @return			list of <code>BatchJobInstance</code>
	 * 
	 * @throws SQLException
	 */
	public List<BatchJobInstance> getJobInstances(int limit, Date start, Date end) 
			throws SQLException {

		Date startDate = (start != null) ? start : new Date(System.currentTimeMillis());
		String range = (end != null) ? "bje.CREATE_TIME between ? AND ? " : "bje.CREATE_TIME >= ? ";
		ArrayList<BatchJobInstance> jobsList = new ArrayList<>();
		String pQuery = "select * from batch_job_instance bji, batch_job_execution bje, "
				+ "batch_job_customer bjc, organization org "
				+ "where bji.JOB_INSTANCE_ID = bjc.JOB_INSTANCE_ID "
				+ "and bje.JOB_INSTANCE_ID = bjc.JOB_INSTANCE_ID "
				+ "and bjc.ORGANIZATION_ID = org.o_id "
				+ "and " + range
				+ "limit ?;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setString(1, startDate.toString());
		if (end != null) { pSt.setString(2, end.toString()); }
		int pos = end != null ? 3 : 2;
		pSt.setInt(pos, limit);
		System.out.println(pSt);
		ResultSet rs = pSt.executeQuery();

		BatchJobInstance bji;
		while (rs.next()) {
			bji = new BatchJobInstance();
			bji.setJobInstanceID(rs.getLong("JOB_INSTANCE_ID"));
			bji.setVersion(rs.getLong("VERSION"));
			bji.setJobName(rs.getString("JOB_NAME"));
			bji.setJobKey(rs.getString("JOB_KEY"));
			bji.setStatus(rs.getString("STATUS"));
			bji.setOrgID(rs.getString("o_id"));
			bji.setOrganizationName(rs.getString("name"));

			jobsList.add(bji);
		}
		pSt.close();
		rs.close(); 

		return jobsList;
	}



	/**
	 * Returns a specific job from a given organization.
	 * 
	 * @param iID		id of the job
	 * 
	 * @param orgID		id of the organization the job is to be obtained
	 * 
	 * @return			a <code>BatchJobInstance</code>
	 * 
	 * @throws SQLException
	 */
	public BatchJobInstance getJobInstance(long iID, String orgID) throws SQLException {			// iID = Instance ID

		String pQuery = "select * from batch_job_instance bji, batch_job_execution bje, "
				+ "batch_job_customer bjc where bjc.ORGANIZATION_ID = ? "
				+ "and bjc.JOB_INSTANCE_ID = ? "
				+ "and bjc.JOB_INSTANCE_ID = bji.JOB_INSTANCE_ID;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setString(1, orgID);
		pSt.setLong(2, iID);
		ResultSet rs = pSt.executeQuery();

		BatchJobInstance bji = null;
		while (rs.next()) {
			bji = new BatchJobInstance();
			bji.setJobInstanceID(rs.getLong("JOB_INSTANCE_ID"));
			bji.setVersion(rs.getLong("VERSION"));
			bji.setJobName(rs.getString("JOB_NAME"));
			bji.setJobKey(rs.getString("JOB_KEY"));
		}
		pSt.close();
		rs.close(); 


		return bji;
	}


	/**
	 * Returns a list of batch job executions with a specified status.
	 * 
	 * @param orgID				id of the organization
	 * 
	 * @param jobInstanceID		id of the job
	 * 
	 * @param status			desired status of the execution
	 * 
	 * @return					a list of <code>BatchJobExecution</code>
	 * 
	 * @throws SQLException
	 */
	public List<BatchJobExecution> getExecutionsWithStatus(String orgID, long jobInstanceID, String status)
			throws SQLException {

		List<BatchJobExecution> executions = new ArrayList<>();
		String pQuery = "select * from batch_job_instance bji, batch_job_execution bje, "
				+ "batch_job_customer bjc where bjc.ORGANIZATION_ID = ? "
				+ "and bjc.JOB_INSTANCE_ID = ? "
				+ "and bjc.JOB_INSTANCE_ID = bji.JOB_INSTANCE_ID "
				+ "and bji.JOB_INSTANCE_ID = bje.JOB_INSTANCE_ID "
				+ "and bje.status = ?;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setString(1, orgID);
		pSt.setLong(2, jobInstanceID);
		pSt.setString(3, status.toUpperCase());
		ResultSet rs = pSt.executeQuery();

		BatchJobExecution bje;
		while (rs.next()) {
			bje = new BatchJobExecution();
			bje.setJobExecutionID(rs.getLong("JOB_EXECUTION_ID"));
			bje.setVersion(rs.getLong("VERSION"));
			bje.setJobInstanceID(rs.getLong("JOB_INSTANCE_ID"));
			bje.setCreateTime(rs.getString("CREATE_TIME"));
			bje.setStartTime(rs.getString("START_TIME"));
			bje.setEndTime(rs.getString("END_TIME"));
			bje.setStatus(rs.getString("STATUS"));
			bje.setExitCode(rs.getString("EXIT_CODE"));
			bje.setExitMessage(rs.getString("EXIT_MESSAGE"));
			bje.setLastUpdated("LAST_UPDATED");

			executions.add(bje);
		}
		pSt.close();
		rs.close();

		return executions;
	}



	/**
	 * Returns a list of batch job executions where no regards to
	 * the organization is made.
	 * 
	 * @param jobInstanceID		id of the job
	 * 
	 * @return					a list of <code>BatchJobExecution</code>
	 * 
	 * @throws SQLException
	 */
	public List<BatchJobExecution> getExecutions(long jobInstanceID)
			throws SQLException {

		List<BatchJobExecution> executions = new ArrayList<>();
		String pQuery = "select * from batch_job_instance bji, batch_job_execution bje "
				+ "where bji.JOB_INSTANCE_ID = ? "
				+ "and bji.JOB_INSTANCE_ID = bje.JOB_INSTANCE_ID "
				+ "limit 20;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, jobInstanceID);
		ResultSet rs = pSt.executeQuery();

		BatchJobExecution bje;
		while (rs.next()) {
			bje = new BatchJobExecution();
			bje.setJobExecutionID(rs.getLong("JOB_EXECUTION_ID"));
			bje.setVersion(rs.getLong("VERSION"));
			bje.setJobInstanceID(rs.getLong("JOB_INSTANCE_ID"));
			bje.setCreateTime(rs.getString("CREATE_TIME"));
			bje.setStartTime(rs.getString("START_TIME"));
			bje.setEndTime(rs.getString("END_TIME"));
			bje.setStatus(rs.getString("STATUS"));
			bje.setExitCode(rs.getString("EXIT_CODE"));
			bje.setExitMessage(rs.getString("EXIT_MESSAGE"));
			bje.setLastUpdated("LAST_UPDATED");

			executions.add(bje);
		}
		pSt.close();
		rs.close();

		return executions;
	}



	/**
	 * Returns the batch job execution of a given job, specified by
	 * its execution id.
	 * 
	 * @param jobInstanceID		id of the job
	 * 
	 * @param exeID				id of the execution
	 * 
	 * @return					a <code>BatchJobExecution</code>
	 * 
	 * @throws SQLException
	 */
	public BatchJobExecution getExecution(long jobInstanceID, long exeID) 
			throws SQLException {

		String pQuery = "select * from batch_job_instance bji, batch_job_execution bje "
				+ "where bji.JOB_INSTANCE_ID = ? "
				+ "and bji.JOB_INSTANCE_ID = bje.JOB_INSTANCE_ID "
				+ "and bje.JOB_EXECUTION_ID = ?";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, jobInstanceID);
		pSt.setLong(2, exeID);
		ResultSet rs = pSt.executeQuery();

		BatchJobExecution bje = null;
		while (rs.next()) {
			bje = new BatchJobExecution();
			bje.setJobExecutionID(rs.getLong("JOB_EXECUTION_ID"));
			bje.setVersion(rs.getLong("VERSION"));
			bje.setJobInstanceID(rs.getLong("JOB_INSTANCE_ID"));
			bje.setCreateTime(rs.getString("CREATE_TIME"));
			bje.setStartTime(rs.getString("START_TIME"));
			bje.setEndTime(rs.getString("END_TIME"));
			bje.setStatus(rs.getString("STATUS"));
			bje.setExitCode(rs.getString("EXIT_CODE"));
			bje.setExitMessage(rs.getString("EXIT_MESSAGE"));
			bje.setLastUpdated("LAST_UPDATED");
		}
		pSt.close();
		rs.close();

		return bje;	
	}



	/**
	 * Returns a list of batch job execution parameters, from a given job execution. 
	 * 
	 * @param exeID		id of the batch job execution
	 * 
	 * @return			list of <BatchJobExecutionParams</code			
	 * 
	 * @throws SQLException
	 */
	public List<BatchJobExecutionParams> getParametersFor(long exeID) throws SQLException {

		List<BatchJobExecutionParams> params = new ArrayList<>();
		String pQuery = "select * from batch_job_execution_params bjep "
				+ "where bjep.JOB_EXECUTION_ID = ?;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, exeID);
		ResultSet rs = pSt.executeQuery();

		while (rs.next()) {
			BatchJobExecutionParams bjep = new BatchJobExecutionParams();
			bjep.setJobExecutionID(rs.getLong("JOB_EXECUTION_ID"));
			bjep.setTypeCD(rs.getString("TYPE_CD"));
			bjep.setKeyName(rs.getString("KEY_NAME"));
			bjep.setStringVal(rs.getString("STRING_VAL"));
			bjep.setDateVal(rs.getString("DATE_VAL"));
			bjep.setLongVal(rs.getLong("LONG_VAL"));
			bjep.setDoubleVal(rs.getDouble("DOUBLE_VAL"));
			bjep.setIdentifying(rs.getString("IDENTIFYING"));

			params.add(bjep);
		}
		pSt.close();
		rs.close();

		return params;
	}


	/**
	 * Returns a list of contexts for a given batch job execution.
	 * 
	 * @param exeID		id of the batch job execution
	 * 
	 * @return			list of <BatchJobExecutionContext</code>
	 * 
	 * @throws SQLException
	 */
	public List<BatchJobExecutionContext> getContextsFor(long exeID) throws SQLException {

		List<BatchJobExecutionContext> contexts = new ArrayList<>();
		String pQuery = "select * from batch_job_execution_context bjec "
				+ "where bjec.JOB_EXECUTION_ID = ?;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, exeID);
		ResultSet rs = pSt.executeQuery();


		while (rs.next()) {
			BatchJobExecutionContext bjec = new BatchJobExecutionContext();
			bjec.setJobExecutionID(rs.getLong("JOB_EXECUTION_ID"));
			bjec.setShortContext(rs.getString("SHORT_CONTEXT"));
			bjec.setSerializedContext(rs.getString("SERIALIZED_CONTEXT"));

			contexts.add(bjec);
		}
		pSt.close();
		rs.close();

		return contexts;
	}



	/**
	 * Returns a list of steps from given batch job execution.
	 * One can look for steps from a batch job execution with a specific 
	 * status. If no status is provided, steps from both executions with status
	 * "FAILED" and "COMPLETED" are returned.
	 * 
	 * @param status		status of the batch job execution
	 * 
	 * @param exeID			id of the batch job execution
	 * 
	 * @return				a list of <BatchStepExecution</code>
	 * 
	 * @throws SQLException
	 */
	public List<BatchStepExecution> getStepExecutionFor(String status, long exeID) 
			throws SQLException {

		List<BatchStepExecution> steps = new ArrayList<>();
		String pQuery = "select * from batch_step_execution bse "
				+ "where bse.JOB_EXECUTION_ID = ?";

		if (status != null) {
			pQuery += " and bse.STATUS = ?;";
		} else  { pQuery += ";"; }

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, exeID);
		if (status != null) { pSt.setString(2, status); }
		ResultSet rs = pSt.executeQuery();


		while (rs.next()) {
			BatchStepExecution bse = new BatchStepExecution();
			bse.setStepExecutionID(rs.getLong("STEP_EXECUTION_ID"));
			bse.setVersion(rs.getLong("STEP_EXECUTION_ID"));
			bse.setStepName(rs.getString("STEP_NAME"));
			bse.setJobExecutionID(rs.getLong("JOB_EXECUTION_ID"));
			bse.setStartTime(rs.getString("START_TIME"));
			bse.setEndTime(rs.getString("END_TIME"));
			bse.setStatus(rs.getString("STATUS"));
			bse.setCommitCount(rs.getLong("COMMIT_COUNT"));
			bse.setReadCount(rs.getLong("READ_COUNT"));
			bse.setFilterCount(rs.getLong("FILTER_COUNT"));
			bse.setWriteCount(rs.getLong("WRITE_COUNT"));
			bse.setReadSkipCount(rs.getLong("READ_SKIP_COUNT"));
			bse.setWriteSkipCount(rs.getLong("WRITE_SKIP_COUNT"));
			bse.setProcessSkipCount(rs.getLong("PROCESS_SKIP_COUNT"));
			bse.setRollBackCount(rs.getLong("ROLLBACK_COUNT"));
			bse.setExitCode(rs.getString("EXIT_CODE"));
			bse.setExitMessage(rs.getString("EXIT_MESSAGE"));
			bse.setLastUpdated(rs.getString("LAST_UPDATED"));
			bse.setContext(getStepContextFor(bse.getStepExecutionID()));

			steps.add(bse);
		}
		pSt.close();
		rs.close();

		return steps;
	}



	/**
	 * Returns the context of a given batch step execution.
	 * 
	 * @param stepID		id of the batch step execution
	 * 
	 * @return				a <code>BatchStepExecutionContext</code>
	 * 
	 * @throws SQLException
	 */
	public BatchStepExecutionContext getStepContextFor(long stepID) throws SQLException {

		String pQuery = "select * from batch_step_execution_context bsec "
				+ "where bsec.STEP_EXECUTION_ID = ?;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, stepID);
		ResultSet rs = pSt.executeQuery();

		BatchStepExecutionContext bsec = null;
		while (rs.next()) {
			bsec = new BatchStepExecutionContext();
			bsec.setStepExecutionID(rs.getLong("STEP_EXECUTION_ID"));
			bsec.setShortContext(rs.getString("SHORT_CONTEXT"));
			bsec.setSerializedContext(rs.getString("SERIALIZED_CONTEXT"));	
		}
		pSt.close();
		rs.close();

		return bsec;
	}



	/**
	 * Returns a list of organizations. If desired, a limit
	 * of the amount of organizations to be returned can be 
	 * specified.
	 * 
	 * @param limit		limit of organizations to be returned - 20 is default
	 * 
	 * @return			list of <code>Organization</code>
	 * 
	 * @throws SQLException
	 */
	public List<Organization> getAllOrganizations(int limit) throws SQLException {

		String pQuery = "select * from organization "
				+ "limit ?;";	

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setInt(1, limit);
		ResultSet rs = pSt.executeQuery();

		List<Organization> organizations = new ArrayList<>();
		while(rs.next()) {
			Organization org = new Organization();
			org.setOrganizationID(rs.getString("o_id"));
			org.setName(rs.getString("name"));
			org.setEmail(rs.getString("email_address"));
			org.setContact_no(rs.getString("contact_no"));

			organizations.add(org);
		}
		pSt.close();
		rs.close();

		return organizations;		
	}



	/**
	 * Returns an organization with the given id.
	 * 
	 * @param orgID			id of the organization
	 * 
	 * @return				an <code>Organization</code>
	 * 
	 * @throws SQLException
	 */
	public Organization getOrganization(String orgID) throws SQLException {

		String pQuery = "select * from organization org "
				+ "where org.o_id = ?;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setString(1, orgID);
		ResultSet rs = pSt.executeQuery();

		Organization org = null;
		while (rs.next()) {
			org = new Organization();
			org.setOrganizationID(rs.getString("o_id"));
			org.setName(rs.getString("name"));
			org.setEmail(rs.getString("email_address"));
			org.setContact_no(rs.getString("contact_no"));	
		}
		pSt.close();
		rs.close();

		return org;
	}



	/**
	 * Returns a list of accounts from a given organization.
	 * Each returned account contains information about
	 * its owner. If desired, a limit of accounts to be returned can 
	 * be provided.
	 * 
	 * @param limit			limit of accounts - default is 20
	 * 	
	 * @param orgID			id of the organization
	 * 
	 * @return				a list of <code>Account</code>
	 * 
	 * @throws SQLException
	 */
	public List<Account> getAccounts(int limit, String orgID) throws SQLException {

		List<Account> accounts = new ArrayList<>();
		String pQuery = "select * from account acc, customer c "
				+ "where c.employed_by = ? "
				+ "and acc.owner = c.c_id "
				+ "limit ?;";		// since organizations/customers are not narrowed down
		// to location

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setString(1, orgID);
		pSt.setInt(2, limit);
		ResultSet rs = pSt.executeQuery();


		while (rs.next()) {
			Account acc = new Account();
			acc.setAccount_id(rs.getLong("a_id"));
			acc.setCreated(rs.getString("created"));
			String role = rs.getString("acces_level");
			acc.setRole(role);

			Account.Role person = Account.Role.valueOf(role.toUpperCase());
			if (person == Role.TECHNICIAN || person == Role.SUPPORT) {
				acc.setOwner(getStaffMember(rs.getLong("owner")));

			} else { acc.setOwner(getCustomer(rs.getLong("owner")));}

			accounts.add(acc);
		}
		pSt.close();
		rs.close();

		return accounts;
	}



	/**
	 * Returns an account with the given id, from a specified
	 * organization. The returned account contains 
	 * information about its owner.
	 * 
	 * @param accID			id of the account
	 * 
	 * @param orgID			id of the organization
	 * @return
	 * @throws SQLException
	 */
	public Account getAccountOf(long accID, String orgID) throws SQLException {

		String pQuery = "select * from account acc, customer c "
				+ "where acc.a_id = ? "
				+ "and c.employed_by = ? "
				+ "and acc.owner = c.c_id;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, accID);
		pSt.setString(2, orgID);
		ResultSet rs = pSt.executeQuery();

		Account acc = null;
		while (rs.next()) {
			acc = new Account();
			acc.setAccount_id(rs.getLong("a_id"));
			acc.setCreated(rs.getString("created"));
			String role = rs.getString("acces_level");
			acc.setRole(role);

			Account.Role person = Account.Role.valueOf(role.toUpperCase());
			if (person == Role.TECHNICIAN || person == Role.SUPPORT) {
				acc.setOwner(getStaffMember(rs.getLong("owner")));

			} else { acc.setOwner(getCustomer(rs.getLong("owner")));}
		}
		pSt.close();
		rs.close();

		return acc;
	}



	/**
	 * Returns the account from a given person.
	 * The account contains information about
	 * its owner.
	 *
	 * @param perID			id of the person
	 * 
	 * @return				an <code>Account</code>
	 * 
	 * @throws SQLException
	 */
	public Account getAccountOf(long perID) throws SQLException {

		String pQuery = "select * from account acc, person per "
				+ "where per.p_key = ? "
				+ "and acc.owner = per.p_key;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, perID);
		ResultSet rs = pSt.executeQuery();

		Account acc = null;
		while (rs.next()) {
			acc = new Account();
			acc.setAccount_id(rs.getLong("a_id"));
			acc.setCreated(rs.getString("created"));
			String role = rs.getString("acces_level");
			acc.setRole(role);

			Account.Role person = Account.Role.valueOf(role.toUpperCase());
			if (person == Role.TECHNICIAN || person == Role.SUPPORT) {
				acc.setOwner(getStaffMember(rs.getLong("owner")));

			} else { acc.setOwner(getCustomer(rs.getLong("owner")));}
		}
		pSt.close();
		rs.close();

		return acc;
	}


	/**
	 * Returns information about a staff member, i.e an 
	 * employee from Nedap.
	 * 
	 * @param employeeID		id of the employee
	 * 
	 * @return					an <code>Staff</code>
	 * @throws SQLException
	 */
	public Staff getStaffMember(long employeeID) throws SQLException {

		String pQuery = "select * from employee emp, person per "
				+ "where per.p_key = ? "
				+ "and emp.e_id = per.p_key;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, employeeID);
		ResultSet rs = pSt.executeQuery();

		Staff staff = null;
		while (rs.next()) {
			staff = new Staff();
			staff.setPersonID(rs.getLong("e_id"));
			staff.setEmail(rs.getString("email_address"));
			staff.setBirthDate(rs.getString("birth_date"));
			staff.setName(rs.getString("first_name"));
			staff.setSurname(rs.getString("last_name"));
		}
		pSt.close();
		rs.close();

		return staff;
	}


	/**
	 * Returns information about a customer's employee.
	 * 
	 * @param customerID		id of the person
	 * 
	 * @return					a <code>Customer</code>
	 * 
	 * @throws SQLException
	 */
	public Customer getCustomer(long customerID) throws SQLException {

		String pQuery = "select * from customer cust, person per "
				+ "where per.p_key = ? "
				+ "and cust.c_id = per.p_key;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, customerID);
		ResultSet rs = pSt.executeQuery();

		Customer customer = null;
		while (rs.next()) {
			customer = new Customer();
			customer.setName(rs.getString("first_name"));
			customer.setSurname(rs.getString("last_name"));
			customer.setPersonID(rs.getLong("c_id"));
			customer.setOrganizationID(rs.getString("employed_by"));
		}
		pSt.close();
		rs.close();

		return customer;
	}



	/**
	 * Returns the password of a given (user's) id.
	 * An empty string is return in case the user
	 * does not exist.
	 * 
	 * @param userID		id of the user
	 * 
	 * @return				password 
	 * 
	 * @throws SQLException
	 */
	public String getPasswordOf(long userID) throws SQLException {

		String pQuery = "select acc.password from person p, account acc "
				+ "where p.p_key = ? "
				+ "and acc.owner = p.p_key;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setLong(1, userID);
		ResultSet rs = pSt.executeQuery();

		return rs.next() != false ? rs.getString("password") : "";
	}


}
