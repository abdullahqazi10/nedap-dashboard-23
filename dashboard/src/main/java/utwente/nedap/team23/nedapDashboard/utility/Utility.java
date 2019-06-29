package utwente.nedap.team23.nedapDashboard.utility;

import java.time.Month;

public class Utility {


	/**
	 * Determines whether the given year is a leap year.
	 * 
	 * @param year		year to be checked
	 * 
	 * @return			true if leap year, otherwise false
	 */
	public static boolean isLeapYear(int year) {

		if (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the number of days of a given monthing, 
	 * considering whether it is a leap year or not.
	 * 
	 * @param year		given year
	 * 
	 * @param month		given month
	 * 
	 * @return			number of days
	 */
	public static int getDaysOfMonth(int year, int month) { return Month.of(month).length(isLeapYear(year)); }
	
	
		
}
