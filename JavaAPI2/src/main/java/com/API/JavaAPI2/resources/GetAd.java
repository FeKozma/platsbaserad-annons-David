package com.API.JavaAPI2.resources;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.API.JavaAPI2.api.sayingAd;
import com.API.JavaAPI2.db.*;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/getad/{longitude}/{latitude}/{id}/{app}")
@Produces(MediaType.APPLICATION_JSON)
public class GetAd {

	

	

	    public GetAd() {
	      
	    }

	    @GET
	    @Timed
	    public sayingAd addAdfunk(@PathParam("longitude") Double lng, 
	    		@PathParam("latitude") Double lat,
	    		@PathParam("id") int id,
	    		@PathParam("app") String app)
	    		
	    {
	       
	    	
	    	sayingAd ret = null;
	    	databaseHandler DBH = new databaseHandler();
	        String[] retArr = DBH.getAd(lng, lat, id, app);
	        if (retArr != null)
	        {
	        	ret = new sayingAd(retArr[0], retArr[1], retArr[2], retArr[3]);
	        }
	        else
	        {
	        	ret = new sayingAd("null", "null", "null", "null");
	        }
	    	
	        return  ret;
	    }
	
}
