package com.API.JavaAPI2.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.API.JavaAPI2.api.sayingArr;
import com.API.JavaAPI2.db.MariaDBConnection;
import com.codahale.metrics.annotation.Timed;

@Path("/getcurnrviews/{company}")
@Produces(MediaType.APPLICATION_JSON)
public class GetCurNrViews {
	 
		
	public GetCurNrViews() {
	      
	}

	@GET
	@Timed
	public sayingArr GetCurNrViewsfunc(
			@PathParam("company") String company)
	   		
	{
	       
	
	 	MariaDBConnection DB = new MariaDBConnection();
	    	
	    		   
	    	
	    return  new sayingArr(DB.getCurNrViews(company));
	}
}
