package com.he.model;

import java.util.Collection;

public interface Cond {
	public boolean cond(Collection<String> keys, Object obj);
}
