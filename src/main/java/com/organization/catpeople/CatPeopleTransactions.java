package com.organization.catpeople;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.organization.catpeople.model.CatOwner;
import com.organization.catpeople.state.CatPeopleState;
import com.txmq.aviator.messaging.AviatorMessage;
import com.txmq.aviator.pipeline.PlatformEvents;
import com.txmq.aviator.pipeline.metadata.AviatorHandler;

public class CatPeopleTransactions {

	@AviatorHandler(
			namespace=CatPeopleTransactionTypes.NAMESPACE,
			transactionType=CatPeopleTransactionTypes.GET_CATS,
			events= {PlatformEvents.messageReceived}
	)
	public List<CatOwner> getCats(AviatorMessage<?> message, CatPeopleState state) {
		List<CatOwner> copy = 
				state.getCats().stream().map(co -> co.copy()).collect(Collectors.toList());
		
		message.interrupt();
		return copy;
	}
	
	@AviatorHandler(
			namespace=CatPeopleTransactionTypes.NAMESPACE,
			transactionType=CatPeopleTransactionTypes.ADD_CAT,
			events= {PlatformEvents.executeConsensus}
	)
	public void addCat(AviatorMessage<CatOwner> message, CatPeopleState state) {
		//Check to see if the cat name has already been claimed
		Optional<CatOwner> existingRecord = 
				state.getCats().stream().filter(co -> co.cat.equals(message.payload.cat)).findFirst();
		
		if (existingRecord.isPresent()) {
			throw new IllegalStateException(
					existingRecord.get().owner + " already owns a cat named " + existingRecord.get().cat
			);
		} else {
			state.getCats().add(message.payload);
		}
	}	
	
}
