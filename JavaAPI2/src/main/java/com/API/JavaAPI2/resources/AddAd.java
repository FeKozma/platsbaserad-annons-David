package com.API.JavaAPI2.resources;

/*

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.API.JavaAPI2.api.saying;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/hi")
@Produces(MediaType.APPLICATION_JSON)
public class AddAd {

	private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public AddAd(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new saying(counter.incrementAndGet(), value);
    }
}

*/

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.API.JavaAPI2.api.saying;
import com.API.JavaAPI2.db.*;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/addad/{longitude}/{latitude}/{radius}/{company}/{summary}/{days}/{nrViews}/query")
@Produces(MediaType.APPLICATION_JSON)
public class AddAd {

	

	

	    public AddAd() {
	      
	    }

	    @GET
	    @Timed
	    public saying addAdfunk(@PathParam("longitude") double lng, 
	    		@PathParam("latitude") double lat,
	    		@PathParam("radius") double radius,
	    		@PathParam("company") String company,
	    		@PathParam("summary") String summary,
	    		@PathParam("days") int days,
	    		@PathParam("nrViews") int views,
	    		@QueryParam("url") String url)
	    {
	       
	    	//TODO: check if string to long
	    	System.out.println("------url-------\n" + (int)radius + "\n");
	    	
	    	databaseHandler DB = new databaseHandler();
	    	boolean ret = DB.addAd(lng, lat, (int)radius, company, summary, days, views, url);
	        
	        //RedisConnection DB2 = new RedisConnection();
	        //DB2.getClosest(0, 0, 0);
	    	
	    	
	        return new saying(ret ? 1 : 0, ret ? "reklamen är uppe!" : "servern mislyckades lägga till reklamen");
	    }
	
}
