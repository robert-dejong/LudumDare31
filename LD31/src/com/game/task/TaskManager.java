package com.game.task;

import java.util.ArrayList;

public class TaskManager {
	
	public ArrayList<Task> tasks = new ArrayList<Task>();
	
	public void add(final Task task) {
		tasks.add(task);
	}
	
	public void tick() {
		if(tasks.size() == 0)
			return;
		
		long time = System.currentTimeMillis();
		
		for(int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			
			if(task == null)
				continue;
			
			if(time - task.getLast() > task.getTime()) {
				task.setLast(time);
				task.execute();
				
				if(!task.isRepeat()) {
					task.stop();
					tasks.remove(task);
				}
			}
		}
	}

}
