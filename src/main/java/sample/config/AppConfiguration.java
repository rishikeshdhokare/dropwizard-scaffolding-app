package sample.config;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AppConfiguration extends Configuration {

    public String appName;
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
