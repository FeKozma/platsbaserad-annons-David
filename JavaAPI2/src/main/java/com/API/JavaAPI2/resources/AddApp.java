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

//add a application that approves a company
@Path("/addapp/{appname}/{company}")
@Produces(MediaType.APPLICATION_JSON)
public class AddApp {

	

	

	    public AddApp() {
	      
	    }

	    @GET
	    @Timed
	    public saying addAppfunc(
	    		@PathParam("appname") String appName,
	    		@PathParam("company") String company)	
	    {
	       
	    	MariaDBConnection DB = new MariaDBConnection();
	    	boolean ret = DB.adAppWhoLikeCompany(appName, company);
	    	
	    	
	     
	   
	    	
	        return new saying(ret? 1 : 0, ret ? "success!" : "misslyckades");
	    }
	
}
