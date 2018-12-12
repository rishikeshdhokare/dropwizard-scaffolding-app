package sample.route;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(value = "/ping", description = "Ping route")
@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class IndexRoute {

    @ApiOperation(value = "ping api to check application availability")
    @GET
    public String get() {
        return "pong";
    }
}
