package sample;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import sample.config.AppConfiguration;
import sample.route.IndexRoute;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.EnumSet;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class Main extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public final void initialize(Bootstrap<AppConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new Java8Bundle());

        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor()));

        bootstrap.addBundle(new SwaggerBundle<AppConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public final void run(AppConfiguration configuration, Environment environment) {
        environment.jersey().register(new IndexRoute());
        addCrossOriginFilter(environment);
    }

    private void addCrossOriginFilter(Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*");

        cors.setInitParameter(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter(ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER, "true");
        cors.setInitParameter(ACCESS_CONTROL_EXPOSE_HEADERS_HEADER, "Authorization");
        cors.setInitParameter(ALLOWED_HEADERS_PARAM, "*");
        cors.setInitParameter(ALLOWED_METHODS_PARAM, "GET, POST, HEAD, PUT, DELETE");
    }
}
