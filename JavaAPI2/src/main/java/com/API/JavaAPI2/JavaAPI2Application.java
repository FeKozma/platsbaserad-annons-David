package com.API.JavaAPI2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.API.JavaAPI2.health.templateHealthCheck;
import com.API.JavaAPI2.resources.*;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class JavaAPI2Application extends Application<JavaAPI2Configuration>{

	
    public static void main(String[] args) throws Exception {
        new JavaAPI2Application().run(args);
        System.out.print("\n\n\ntest\n\n\n");
        
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<JavaAPI2Configuration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(JavaAPI2Configuration configuration,
                    Environment environment) {
        final APIResource resource = new APIResource(
        		configuration.getTemplate(),
        		configuration.getDefaultName()
        		);
        final AddAd aAd = new AddAd(
        		);
        final GetAd getad = new GetAd();
        final GetApps getApps = new GetApps();
        final AddApp addApp = new AddApp();
        final AddCompany addCompany = new AddCompany();
        final GetCompanies getCompanies = new GetCompanies();
        final GetCurNrViews getCurNrViews = new GetCurNrViews();
        
        final templateHealthCheck healthCheck =
                new templateHealthCheck(configuration.getTemplate());
            environment.healthChecks().register("template", healthCheck);
            environment.jersey().register(resource);
            environment.jersey().register(aAd);
            environment.jersey().register(getad);
            environment.jersey().register(getApps);
            environment.jersey().register(addApp);
            environment.jersey().register(addCompany);
            environment.jersey().register(getCompanies);
            environment.jersey().register(getCurNrViews);
          /*  
        environment.jersey().register(resource);
        environment.jersey().register(aAd);
        environment.jersey().register(getad);
        environment.jersey().register(getApps);
        environment.jersey().register(addApp);
        environment.jersey().register(addCompany);*/



    
        enableCorsHeaders(environment);
    }
    
    private void enableCorsHeaders (Environment env) {
    	 final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

         // Configure CORS parameters
    	 cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "http://localhost:4200");
         cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://localhost:4200");
         cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
         cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");

         // Add URL mapping
         cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}
