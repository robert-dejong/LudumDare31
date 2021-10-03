package com.game.task;

public abstract class Task {
	
	private int time;
	private boolean repeat;
	private long last;
	
	public Task(final int time, final boolean repeat) {
		this.last = System.currentTimeMillis();
		this.time = time;
		this.repeat = repeat;
	}
	
	public abstract void execute();
	
	public abstract void stop();

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public long getLast() {
		return last;
	}

	public void setLast(long last) {
		this.last = last;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
