package operatingsystems;

import java.util.LinkedList;
import java.util.Queue;

public class Queues {
	
	private Queue<Process> processes;

	public Queues() {
		processes = new LinkedList<>();
	}
	
	public Process getFirstElement() {
		return processes.poll();
	}
	
	public void add(Process process) {
		processes.add(process);
	}
	
	public void delete(Process process) {
		processes.remove(process);
	}
	
	public boolean Empty() {
		return processes.isEmpty();
	}
	
	public Queue<Process> getAll(){
		return processes;
	}
	
	public Process peek()
    {
        return processes.peek();
    }
	
	public Process getHighestPriority() {
		Process highestPriorityProcess = processes.peek();
		
		for(var process : getAll().stream().toList()) {
			if(process.getPriorityLevel() < highestPriorityProcess.getPriorityLevel()) {
				highestPriorityProcess = process;
			}
		}
		
		return highestPriorityProcess;
	}
	
	public Process FCFS() {
		Process firstArrived = getFirstElement();
		
		for(var process : getAll().stream().toList()) {
			if(process.getArrivalTime() < firstArrived.getArrivalTime() ) {
				firstArrived = process;
			}
		}
		
		return firstArrived;
	}
	
	public boolean isThereProcess(Process process) {
		return getAll().stream().anyMatch(x -> x.getProcessId() == process.getProcessId());
	}
}
