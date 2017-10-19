package com.API.JavaAPI2.resources;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.API.JavaAPI2.api.saying;
import com.API.JavaAPI2.api.sayingArr;
import com.API.JavaAPI2.db.*;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;


//add a company that approves a application
@Path("/addcompany/{app}/{companyname}")
@Produces(MediaType.APPLICATION_JSON)
public class AddCompany {

	

	

	    public AddCompany() {
	      
	    }

	    @GET
	    @Timed
	    public saying AddCompanyfunc(
	    		@PathParam("app") String appName,
	    		@PathParam("companyname") String company)	
	    {
	       
	    	MariaDBConnection DB = new MariaDBConnection();
	    	boolean ret = DB.adCompanyWhoLikeApp(appName, company);
	    	
	    	
	     
	   
	    	
	        return new saying(ret? 1 : 0, ret ? "tillagt!": "inte tillagt");
	    }
	
}
