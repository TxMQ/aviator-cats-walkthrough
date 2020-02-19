package com.organization.catpeople.state;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.organization.catpeople.model.CatOwner;
import com.txmq.aviator.core.AviatorStateBase;

public class CatPeopleState extends AviatorStateBase {
	private List<CatOwner> cats = new ArrayList<>();

	public synchronized List<CatOwner> getCats() {
		return cats;
	}

	@Override
	public synchronized void copyFrom(AviatorStateBase old) {
		super.copyFrom(old);
		
		this.cats = ((CatPeopleState) old).getCats()
				.stream()
				.map(co -> co.copy())
				.collect(Collectors.toList());
	}
	
}
