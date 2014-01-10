package com.planepanic.game.gfx;

public abstract class CallbackP<T, R> extends Callback<T> {

	@Override
	public T call() {
		return this.call(null);
	}

	public abstract T call(R param);

}
