package user;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import DTO.NotFoundException;

@Provider

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException exception) {
		// TODO Auto-generated method stub
		return Response.status(Status.NOT_FOUND).entity("{msg:"+exception.getMessage()+"}").build();
	}
	//if NotFoundException called then return 404 http error code
	
}
