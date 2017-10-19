package com.API.JavaAPI2.db;

import java.util.List;
import java.util.Set;
//import redis.clients.jedis.params.geo.GeoRadiusParam;


import com.lambdaworks.redis.*;
import redis.clients.jedis.*;

import redis.clients.jedis.GeoRadiusResponse;


public class RedisConnection  {

	private final String key = "geo";
	
	public RedisConnection () {};
	
	public int[] getClosest(double longitude, double latitude, int meterRadius)
	{
		
		
		List<GeoRadiusResponse> nearest = null;
		int[] nearestId = null;
		try {
			   
			    
			    //jedis.geoadd(key,13.191007, 55.704660, "3");
		nearest = jedisVar.georadius(key, longitude, latitude, meterRadius, GeoUnit.M);
		
		System.out.println("nearest.size(): " + Integer.toString(nearest.size()));
		nearestId = new int[nearest.size()];
		
		//System.out.println(nearest.get(0).getMemberByString());
		for (int i = 0; i < nearest.size(); i++)
		{
			nearestId[i] = Integer.parseInt(nearest.get(i).getMemberByString());
		}
		
		
		
		} catch ( Exception  ex) {
	    	System.out.println("error: redisconnection failed in function getClosest");
	    	ex.printStackTrace();
	    }
		
		
		return  nearestId;
	}
	
	public boolean adLocation(double longitude, double latitude, int id) {

		boolean executed = false;
			    try {
				   
				    jedisVar.geoadd(key,  longitude, latitude, Integer.toString(id));
				    executed = true; 
				    //System.out.println(jedis.geodist("pos1", "Karlskrona", "Lund", GeoUnit.KM)); //160km
				    
				    //List<GeoRadiusResponse> nearest = jedis.georadius("pos1", 13.183658, 55.705889, 1, GeoUnit.KM);
				    
				    
				    //System.out.println(nearest.get(0));
				    
			    } catch ( Exception  ex) {
			    	System.out.println("error: redisconnection failed in function adLocation");
			    	ex.printStackTrace();
			    }
				return executed;
				
				
				/*
				RedisClient redisClient = new RedisClient(

				RedisURI.create("redis://redis-17231.c12.us-east-1-4.ec2.cloud.redislabs.com:17231"));
			    RedisConnection<String, String> connection = redisClient.connect();
			    
			    //GeoOperations<String, String> geoOperations;
			    
			    //String key = "my-geo-set";
			    //connection.geoadd(key, 8.6638775, 49.5282537, "Weinheim", 8.3796281, 48.9978127, "Office tower", 8.665351, 49.553302,
	            //    "Train station");

			    //connection.set("string", "hello");
			    
			    String fromRedis = connection.get("string");
			    System.out.println("Connected to Redis\nfrom redis: " + fromRedis + "\n");
			    
			    connection.close();
			    redisClient.shutdown();*/
			    
		/*
		 RedisClient redisClient =  RedisClient.create(RedisURI.Builder.redis("http://redis-17231.c12.us-east-1-4.ec2.cloud.redislabs.com", 17231).build());
	        RedisConnection<String, String> redis = (RedisConnection<String, String>) redisClient.connect();
	        String key = "my-geo-set";

	        redis.geoadd(key, 8.6638775, 49.5282537, "Weinheim", 8.3796281, 48.9978127, "Office tower", 8.665351, 49.553302,
	                "Train station");

	        Set<String> georadius = redis.georadius(key, 8.6582861, 49.5285695, 5, GeoArgs.Unit.km);
	        System.out.println("Geo Radius: " + georadius);

	        // georadius contains "Weinheim" and "Train station"

	        Double distance = redis.geodist(key, "Weinheim", "Train station", GeoArgs.Unit.km);
	        System.out.println("Distance: " + distance + " km");

	        // distance ≈ 2.78km

	        GeoArgs geoArgs = new GeoArgs().withHash().withCoordinates().withDistance().withCount(2).asc();

	        List<GeoWithin<String>> georadiusWithArgs = redis.georadius(key, 8.665351, 49.5285695, 5, GeoArgs.Unit.km, geoArgs);

	        // georadiusWithArgs contains "Weinheim" and "Train station"
	        // ordered descending by distance and containing distance/coordinates
	        GeoWithin<String> weinheim = georadiusWithArgs.get(0);

	        System.out.println("Member: " + weinheim.getMember());
	        System.out.println("Geo hash: " + weinheim.getGeohash());
	        System.out.println("Distance: " + weinheim.getDistance());
	        System.out.println("Coordinates: " + weinheim.getCoordinates().getX() + "/" + weinheim.getCoordinates().getY());

	        List<GeoCoordinates> geopos = redis.geopos(key, "Weinheim", "Train station");
	        GeoCoordinates weinheimGeopos = geopos.get(0);
	        System.out.println("Coordinates: " + weinheimGeopos.getX() + "/" + weinheimGeopos.getY());

	        redis.close();
	        redisClient.shutdown();
		/*
		 @SuppressWarnings("deprecation")
		RedisClient redisClient = new RedisClient(
	      RedisURI.create("redis://redis-17231.c12.us-east-1-4.ec2.cloud.redislabs.com:17231\r\n"));
	    //@SuppressWarnings("deprecation")
	    RedisConnection<String, String> connection = redisClient.connect();
	   System.out.println("Connected to Redis");
	   connection.close();
	   redisClient.shutdown();*/
		  
	}
private Jedis jedisVar;

	public void connect()
	{
		try {
		jedisVar = new Jedis("redis-17231.c12.us-east-1-4.ec2.cloud.redislabs.com", 17231);
		
		} catch ( Exception  ex) {
	    	System.out.println("error: redisconnection failed");
	    	ex.printStackTrace();
	    }
		
		
	}

	public int getDistance(int id, double longitude, double latitude)
	{
		double distance = 0;
		 try {
			    
			    
			    jedisVar.geoadd(key, longitude, latitude, "-1");
			    distance = jedisVar.geodist(key, "-1", Integer.toString(id), GeoUnit.M);
			     
			    
			    Long test = jedisVar.zrem(key, "-1");
			    if (test == 0)
			    {
			    	System.out.println("------------"
			    			+ "\nuser information may have been stored"
			    			+ "\n------------");
			    }
			    
		    } catch ( Exception  ex) {
		    	System.out.println("error: redisconnection failed in function adLocation");
		    	ex.printStackTrace();
		    }
			return (int) distance;
			
	}
}
//redis://password@host:port"
