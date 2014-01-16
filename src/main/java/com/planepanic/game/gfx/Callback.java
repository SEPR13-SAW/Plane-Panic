package com.planepanic.game.gfx;

/**
 * Can be given to another class to be called on an event
 * 
 * @author Thomas
 *
 * @param <T> Type returned by the callback, ie a boolean to indicate success.
 */
public interface Callback<T> {

	public T call();

}
