package com.organization.catpeople;

import java.util.ArrayList;

import javax.ws.rs.container.AsyncResponse;

import com.txmq.aviator.messaging.AviatorNotification;
import com.txmq.aviator.pipeline.ReportingEvents;
import com.txmq.aviator.pipeline.metadata.AviatorSubscriber;
import com.txmq.aviator.pipeline.subscribers.AviatorSubscriberBase;

import com.organization.catpeople.CatPeopleTransactionTypes;

public class CatPeopleResponders extends AviatorSubscriberBase<AsyncResponse> {
	
	@AviatorSubscriber(
			namespace=CatPeopleTransactionTypes.NAMESPACE,
			transactionType=CatPeopleTransactionTypes.GET_CATS,
			events= {ReportingEvents.transactionComplete})
	public void getCatsTransactionCompleted(AviatorNotification<ArrayList<CatOwner>> notification) {
		AsyncResponse responder = this.getResponder(notification);
		if (responder != null) {
			responder.resume(notification);
		}
	}
	
	@AviatorSubscriber(
			namespace=CatPeopleTransactionTypes.NAMESPACE,
			transactionType=CatPeopleTransactionTypes.ADD_CAT,
			events= {ReportingEvents.transactionComplete})
	public void addCatTransactionCompleted(AviatorNotification<CatOwner> notification) {
		AsyncResponse responder = this.getResponder(notification);
		if (responder != null) {
			responder.resume(notification);
		}
	}
}
