package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;
import java.util.HashMap;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utwente.nedap.team23.nedapDashboard.database.StatisticsDAO;
import utwente.nedap.team23.nedapDashboard.model.JobStatistics;
import utwente.nedap.team23.nedapDashboard.model.TotalJobsCount;

@Path("statistics")
@RolesAllowed({"TECHNICIAN", "SUPPORT" })
public class StatisticsResource {
	
	
	
	@GET
	@Path("all-jobs/count")
	@Produces({MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
	public Response getCountOfAllJobs() {
		
		try {
			StatisticsDAO statsService = StatisticsDAO.instance;
			TotalJobsCount count = statsService.getCount();
			
			return Response.ok().entity(count).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					   .entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
	
	
	@GET
	@Path("all-jobs/years/{year}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getStatsOfYear(@PathParam("year") int year) {
		
		try {
			StatisticsDAO statsService = StatisticsDAO.instance;
			HashMap<Integer, JobStatistics> stats = statsService.getJobStatsFrom(year);
			
			return Response.ok().entity(stats).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
	
	
	@GET
	@Path("all-jobs/years/{year}/months/{month}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getStatsOfMonth(@PathParam("year") int year, 
			@PathParam("month") int month) {
		
		try {
			StatisticsDAO statsService = StatisticsDAO.instance;
			HashMap<Integer, JobStatistics> stats = statsService.getJobStatsFrom(year, month);
			
			return Response.ok().entity(stats).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
}

