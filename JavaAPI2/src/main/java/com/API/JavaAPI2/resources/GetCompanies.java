package com.API.JavaAPI2.resources;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.API.JavaAPI2.api.sayingArr;
import com.API.JavaAPI2.db.*;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/getcompanies")
@Produces(MediaType.APPLICATION_JSON)
public class GetCompanies {

	

	

	    public GetCompanies() {
	      
	    }

	    @GET
	    @Timed
	    public sayingArr getCompaniesfunc()
	    		
	    {
	       
	
	    	MariaDBConnection DB = new MariaDBConnection();
	    	
	    		   
	    	
	        return  new sayingArr(DB.getCompanies());
	    }
	
}
