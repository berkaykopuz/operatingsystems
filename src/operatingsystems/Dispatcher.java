package operatingsystems;

import java.util.LinkedList;
import java.util.Queue;

public class Dispatcher {
	private Queues processes;
	private Queues realTimeProcesses;
	private Queues userProcesses;
    private Queues highPriorityQueue;
    private Queues midPriorityQueue;
    private Queues lowPriorityQueue;
    
    private int time;
    private int remainTime;
    
    private Process realTimeProcess;
    private Process userProcess;
	
	private int q;
	
	public Dispatcher(Queues processes) {
		 this.realTimeProcesses = new Queues();
		 this.userProcesses = new Queues();
		 this.highPriorityQueue = new Queues();
		 this.midPriorityQueue = new Queues();
		 this.lowPriorityQueue = new Queues();
		 this.processes = processes;
		
		this.realTimeProcess = null;
		this.userProcess = null;
		this.time = 0;
		this.remainTime = 0;
		
		this.q = 1; //quantization 1 second
	}
	
	
	public void simulate() {
		while(true) {
			assignProcessToQueues();
			
			if(!realTimeProcesses.isEmpty() || realTimeProcess!=null) {
				if(userProcess.getProcessSituation() == "started") { // if there is a real time process make user p. wait
					userProcess.setProcessSituation("waiting");
					if(userProcess.getPriorityLevel() == 1) {
						userProcess.setPriorityLevel(2);
						highPriorityQueue.delete(userProcess);
						midPriorityQueue.add(userProcess);
					}
					else if(userProcess.getPriorityLevel() == 2) {
						userProcess.setPriorityLevel(3);
						midPriorityQueue.delete(userProcess);
						lowPriorityQueue.add(userProcess);
					}
					else {
						lowPriorityQueue.delete(userProcess);
						lowPriorityQueue.add(userProcess);
					}
					//print()
				}
			}
			if(realTimeProcess == null) { // start fcfs process
				realTimeProcess = realTimeProcesses.FCFS();
				
				
			}
		}
	}
	
	private void assignProcessToQueues() {
		if(!processes.isEmpty()) {
			for(var process : processes.getAll().stream().toList()) {
				if(process.getArrivalTime() == time) {
					if(process.getPriorityLevel() == 0) { //real-time
						realTimeProcesses.add(process);
					}
					else {
						userProcesses.add(process);
					}
					processes.delete(process); //clean garbage
				}
			}
		}
		if(!userProcesses.isEmpty()) {
			for(var userProcess : userProcesses.getAll().stream().toList()) {
				userProcess.setProcessSituation("in queue");
				
				if(userProcess.getPriorityLevel() == 1) {
					highPriorityQueue.add(userProcess);
				}
				else if(userProcess.getPriorityLevel() == 2) {
					midPriorityQueue.add(userProcess);
				}
				else {
					lowPriorityQueue.add(userProcess);
				}
				userProcesses.delete(userProcess); //clean garbage
			}	
		}
	}
	
	private void waitOrFinishTheProcess() {
		time++;
		
		userProcess.setWaitingTime(0);
		
		remainTime = userProcess.getProcessTime() - 1;
		userProcess.setProcessTime(time);
		
		if(userProcess.getProcessTime() != 0) { //if process didnt finish, then assign to lower queues
			
			if(userProcess.getPriorityLevel() == 1) {
				userProcess.setPriorityLevel(2);
				midPriorityQueue.add(userProcess);
			}
			else if(userProcess.getPriorityLevel() == 2) {
				userProcess.setPriorityLevel(3);
				lowPriorityQueue.add(userProcess);
			}
			else if(userProcess.getPriorityLevel() == 3) {
				lowPriorityQueue.add(userProcess);
			}
			
			userProcess.setProcessSituation("waiting");
			//print();
		}
		else {
			userProcess.setProcessSituation("finished");
			//print();
			
			if(userProcess.getPriorityLevel() == 3) {
				lowPriorityQueue.delete(userProcess);
			}
			else if(userProcess.getPriorityLevel() == 2) {
				midPriorityQueue.delete(userProcess);
			}
			else {
				highPriorityQueue.delete(userProcess);
			}
			
			userProcess = null;
		}
		
	}
	
	private void checkIsProcessExpired() {
		if(!highPriorityQueue.isEmpty()) {
			for(var process : highPriorityQueue.getAll().stream().toList()) {
				if(process.getProcessSituation() == "waiting" || process.getProcessSituation() == "in queue") {
					if(process.getWaitingTime() == 30) {
						process.setProcessSituation("expired");
						//print();
						
						highPriorityQueue.delete(process);
					}
					else {
						process.setWaitingTime(process.getWaitingTime() + 1);
					}
				}
			}
		}
		
		if(!midPriorityQueue.isEmpty()) {
			for(var process : midPriorityQueue.getAll().stream().toList()) {
				if(process.getProcessSituation() == "waiting" || process.getProcessSituation() == "in queue") {
					if(process.getWaitingTime() == 30) {
						process.setProcessSituation("expired");
						//print();
						
						midPriorityQueue.delete(process);
					}
					else {
						process.setWaitingTime(process.getWaitingTime() + 1);
					}
				}
			}
		}
		
		if(!lowPriorityQueue.isEmpty()) {
			for(var process : lowPriorityQueue.getAll().stream().toList()) {
				if(process.getProcessSituation() == "waiting" || process.getProcessSituation() == "in queue") {
					if(process.getWaitingTime() == 30) {
						process.setProcessSituation("expired");
						//print();
						
						lowPriorityQueue.delete(process);
					}
					else {
						process.setWaitingTime(process.getWaitingTime() + 1);
					}
				}
			}
		}
		
		
		
			
	}
	
	
}