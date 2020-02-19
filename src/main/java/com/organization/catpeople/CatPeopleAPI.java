package com.organization.catpeople;

import java.io.IOException;
import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.txmq.aviator.messaging.AviatorMessage;
import com.txmq.aviator.messaging.AviatorTransactionType;
import com.txmq.aviator.pipeline.ReportingEvents;
import com.txmq.aviator.pipeline.subscribers.AviatorSubscriberManager;

@Path("/catpeople")
public class CatPeopleAPI {

	private AviatorSubscriberManager subscriberManager = new AviatorSubscriberManager();
	
	@GET
	@Path("/cats")
	@Produces(MediaType.APPLICATION_JSON)
	public void getCats(@Suspended final AsyncResponse response) {
		AviatorMessage<Serializable> message = new AviatorMessage<>(
				new AviatorTransactionType(CatPeopleTransactionTypes.NAMESPACE, CatPeopleTransactionTypes.GET_CATS),
				null
		);
		
		this.subscriberManager.registerResponder(message, ReportingEvents.transactionComplete, response);
		
		try {
			message.submit();
		} catch (IOException e) {
			response.resume(Response.serverError().entity(e).build());
		}
	}
	
	@POST
	@Path("/cats")
	@Produces(MediaType.APPLICATION_JSON)
	public void addCat(CatOwner data, @Suspended final AsyncResponse response) {
		AviatorMessage<CatOwner> message = new AviatorMessage<>(
				new AviatorTransactionType(CatPeopleTransactionTypes.NAMESPACE, CatPeopleTransactionTypes.ADD_CAT),
				data
		);
		
		this.subscriberManager.registerResponder(message, ReportingEvents.transactionComplete, response);
		
		try {
			message.submit();
		} catch (IOException e) {
			response.resume(Response.serverError().entity(e).build());
		}
	}
}
