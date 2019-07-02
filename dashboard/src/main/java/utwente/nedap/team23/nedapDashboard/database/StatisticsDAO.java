package utwente.nedap.team23.nedapDashboard.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utwente.nedap.team23.nedapDashboard.model.*;
import utwente.nedap.team23.nedapDashboard.model.Account.Role;
import utwente.nedap.team23.nedapDashboard.utility.Utility;

public enum StatisticsDAO {
	
	instance;

	private Connection dbConnection;

	private StatisticsDAO() { dbConnection = DatabaseConnection.getInstance(); }

	/**
	 * Returns the total count of jobs.
	 * 
	 * @return		count of jobs
	 * 
	 * @throws SQLException
	 */
	public TotalJobsCount getCount() throws SQLException {
		
		String pQuery = "select count(*) as totalCount from batch_job_instance;";
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(pQuery);
		TotalJobsCount count = new TotalJobsCount();
		if (rs.next()) {
			count.setCount(rs.getLong("totalCount"));
		}
		
		return count;
	}
	
	
	/**
	 * Returns statistics from every month of a given year.
	 * Leap years are taken into account.
	 * 
	 * @param year		desired year
	 * 
	 * @return			stats
	 * 
	 * @throws SQLException 
	 */
	public HashMap<Integer, JobStatistics> getJobStatsFrom(int year) throws SQLException {

		String pQuery = "select foo.status, count(*) as count "
				+ "from ( select bje.status from batch_job_instance bji, batch_job_execution bje "
				+ "where bji.JOB_INSTANCE_ID = bje.JOB_INSTANCE_ID and bje.CREATE_TIME >= ? "
				+ "and bje.CREATE_TIME <= ? ) as foo "   
				+ "group by foo.status;";

		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		HashMap<Integer, JobStatistics> stats = new HashMap<>();			// the key represents the month
		for (int i=1; i <= 12; i++) {
			String start = year + "-" + i + "-01";
			String end = year + "-" + i + "-" + Utility.getDaysOfMonth(year, i);

			pSt.setString(1, start);
			pSt.setString(2, end);
			ResultSet rs = pSt.executeQuery();

			JobStatistics stat = new JobStatistics();
			while (rs.next()) {

				String status = rs.getString("STATUS");

				if (status.equals("COMPLETED")) {
					stat.setCompleted(rs.getLong("count"));
				} else if (status.equals("FAILED")) {
					stat.setFailed(rs.getLong("count"));
				} else if (status.equals("STARTED")) {
					stat.setOngoing(rs.getLong("count"));
				} else if (status.equals("STOPPED")) {
					stat.setStopped(rs.getLong("count"));
				} else {
					stat.setUnknown(rs.getLong("count"));
				}			
			}

			long total = stat.getCompleted() + stat.getFailed() + stat.getOngoing()
			+ stat.getStopped() + stat.getUnknown();
			stat.setTotal(total);
			stat.setStart(year);
			stat.setEnd(year + 1);
			stats.put(i, stat);
			rs.close();
		}
		pSt.close();

		return stats;
	}
	
	
	/**
	 * Returns statistics from a month of a given year.
	 * Leap years are taken into account.
	 * 
	 * @param year		year of the month
	 * 
	 * @param month		desired month
	 * 
	 * @return			stats
	 * 
	 * @throws SQLException 
	 */
	public HashMap<Integer, JobStatistics> getJobStatsFrom(int year, int month) throws SQLException {
		
		String pQuery = "select foo.status, count(*) as count "
				+ "from ( select bje.status from batch_job_instance bji, batch_job_execution bje "
				+ "where bji.JOB_INSTANCE_ID = bje.JOB_INSTANCE_ID and bje.CREATE_TIME >= ? "
				+ "and bje.CREATE_TIME <= ? ) as foo "   
				+ "group by foo.status;";
		
		String start = year + "-" + month + "-01";
		String end = year + "-" + month + "-" + Utility.getDaysOfMonth(year, month);
		HashMap<Integer, JobStatistics> stats = new HashMap<>();		// the key represents the month
		PreparedStatement pSt = dbConnection.prepareStatement(pQuery);
		pSt.setString(1, start);
		pSt.setString(2, end);
		ResultSet rs = pSt.executeQuery();
		
		JobStatistics stat = new JobStatistics();
		while (rs.next()) {

			String status = rs.getString("STATUS");

			if (status.equals("COMPLETED")) {
				stat.setCompleted(rs.getLong("count"));
			} else if (status.equals("FAILED")) {
				stat.setFailed(rs.getLong("count"));
			} else if (status.equals("STARTED")) {
				stat.setOngoing(rs.getLong("count"));
			} else if (status.equals("STOPPED")) {
				stat.setStopped(rs.getLong("count"));
			} else {
				stat.setUnknown(rs.getLong("count"));
			}			
		}

		long total = stat.getCompleted() + stat.getFailed() + stat.getOngoing()
		+ stat.getStopped() + stat.getUnknown();
		stat.setTotal(total);
		stat.setStart(year);
		stat.setEnd(year);
		stats.put(month, stat);
		rs.close();
		
		return stats;
	}
}
