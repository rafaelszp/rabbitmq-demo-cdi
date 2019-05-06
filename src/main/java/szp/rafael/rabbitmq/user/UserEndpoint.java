package szp.rafael.rabbitmq.user;


import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(value= MediaType.APPLICATION_JSON)
@Produces(value= MediaType.APPLICATION_JSON)
public class UserEndpoint {

  @Inject
  UserService userService;

  @GET
  public Response get(){
    return Response.ok("URRUL").build();
  }

  @POST
  public Response insertUser(@Valid User user){
    userService.sendEvent(user);
    return Response.created(null).build();
  }


}
