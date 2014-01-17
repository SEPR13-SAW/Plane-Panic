package com.planepanic.game.gfx;

/**
 * Can be given to another class to be called on an event
 * 
 * @author Thomas
 * 
 * @param <T> Type returned by the callback, ie a boolean to indicate success.
 * @param <R> Type accepted as a parameter of the callback
 */
public abstract class CallbackP<T, R> implements Callback<T> {

	@Override
	public T call() {
		return this.call(null);
	}

	public abstract T call(R param);

}
