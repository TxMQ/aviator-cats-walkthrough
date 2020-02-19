package com.organization.catpeople;

import java.io.Serializable;

public class CatOwner implements Serializable {
	
	private static final long serialVersionUID = 1243291208426370661L;
	
	public String cat;
	public String owner;
	
	public CatOwner copy() {
		CatOwner copy = new CatOwner();
		copy.cat = new String(this.cat);
		copy.owner = new String(this.owner);
		
		return copy;
	}
}
