package com.organization.catpeople;

import com.txmq.aviator.messaging.annotations.TransactionType;
import com.txmq.aviator.messaging.annotations.TransactionTypes;

@TransactionTypes(namespace=CatPeopleTransactionTypes.NAMESPACE, onlyAnnotatedValues=true)
public class CatPeopleTransactionTypes {
	public static final String NAMESPACE = "CatPeopleTransactionTypes";
	
	@TransactionType
	public static final String GET_CATS = "GetCats";
	
	@TransactionType
	public static final String ADD_CAT = "AddCat";
}
