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
		while(true) 
		{
			assignProcessToQueues();
			
			if(realTimeProcesses.Empty() == false || realTimeProcess!=null) {
				if(userProcess != null) {
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
						print(userProcess.getColor(),time,userProcess.getProcessId(),userProcess.getPriority()
	                            ,userProcess.getProcessTime(),userProcess.getProcessSituation(), 
	                            userProcess.getWaitingTime());
					}
				
				}
				
				if(realTimeProcess == null) { // start fcfs algorithm
					realTimeProcess = realTimeProcesses.FCFS();
					
					if(realTimeProcess.getModems() > SystemResource.modemCount || 
							realTimeProcess.getCds() > SystemResource.cdCount
							|| realTimeProcess.getPrinters() > SystemResource.printerCount || 
							realTimeProcess.getScanners() > SystemResource.scannerCount
							|| realTimeProcess.getMemory() > SystemResource.realtimeMemory) {
						
						
						System.out.printf("%d  sn real-time 	process 	demands 	so much 	resource 	and 	DELETED! 	id:%d%n", time ,realTimeProcess.getProcessId());
						realTimeProcess = null;
						time++;
						
					}
					else {
						SystemResource.modemCount -= realTimeProcess.getModems();
				        SystemResource.cdCount -= realTimeProcess.getCds();
				        SystemResource.printerCount -= realTimeProcess.getPrinters();
				        SystemResource.scannerCount -= realTimeProcess.getScanners();
				        SystemResource.realtimeMemory -= realTimeProcess.getMemory();
				        
				        realTimeProcess.setProcessSituation("started");
				        
				        print(realTimeProcess.getColor(),time,realTimeProcess.getProcessId(),realTimeProcess.getPriority()
	                            ,realTimeProcess.getProcessTime(),realTimeProcess.getProcessSituation(), 
	                            realTimeProcess.getWaitingTime());
				        
				        remainTime = realTimeProcess.getProcessTime() - 1;
				        realTimeProcess.setProcessTime(remainTime);
				        
				        
					}
					
					continue;
				}
				else { // continue from real time process
					checkIsProcessExpired(); 
					time++;
					
					if(realTimeProcess.getProcessTime() == 0) { //finish the process
						realTimeProcess.setProcessSituation("finished");
						print(realTimeProcess.getColor(), time, realTimeProcess.getProcessId(),realTimeProcess.getPriority()
                            ,realTimeProcess.getProcessTime(),realTimeProcess.getProcessSituation(), 
                            realTimeProcess.getWaitingTime());
						
						SystemResource.modemCount += realTimeProcess.getModems();
				        SystemResource.cdCount += realTimeProcess.getCds();
				        SystemResource.printerCount += realTimeProcess.getPrinters();
				        SystemResource.scannerCount += realTimeProcess.getScanners();
				        SystemResource.realtimeMemory += realTimeProcess.getMemory();
				        
				        realTimeProcess = null;
				        
						
						
					}else { // keep processing
						realTimeProcess.setProcessSituation("processing");
						print(realTimeProcess.getColor(), time, realTimeProcess.getProcessId(),realTimeProcess.getPriority()
                            ,realTimeProcess.getProcessTime(),realTimeProcess.getProcessSituation(), 
                            realTimeProcess.getWaitingTime());
						remainTime = realTimeProcess.getProcessTime() - 1;
						realTimeProcess.setProcessTime(remainTime);
					}
					
					continue;
				}
			}
			
			else if((!lowPriorityQueue.Empty()) || (!midPriorityQueue.Empty()) || (!highPriorityQueue.Empty()) || userProcess!=null ){
                //calisan gercek zamanli prosesler olmadigi zaman kullanici prosesleri algoritmaya göre calistirilir
                //öncelikle yuksek öncelik kuyruguna bakilir orada elemanlar varsa ilk onlar calistirilir
                if (realTimeProcess==null) {
                    if (!highPriorityQueue.Empty()) {
                        userProcess = highPriorityQueue.getFirstElement();

                    } else if (!midPriorityQueue.Empty()) {
                    	userProcess = midPriorityQueue.getFirstElement();

                    } else if(!lowPriorityQueue.Empty()) {
                    	userProcess = lowPriorityQueue.getFirstElement();
                    }
                    if(userProcess.getProcessSituation()=="in queue"){
                    	if(userProcess.getModems() > SystemResource.modemCount ||       
                    			userProcess.getCds() > SystemResource.cdCount
    							|| userProcess.getPrinters() > SystemResource.printerCount || 
    							userProcess.getScanners() > SystemResource.scannerCount
    							|| userProcess.getMemory() > SystemResource.userMemory) {
                    		//userProcesses.delete(userProcess);
    						System.out.printf("%d  sn user 		process 	demands 	so much 	resource 	and 	DELETED! 	id:%d%n", time, userProcess.getProcessId());
    						userProcess = null;
    						time++;
    						
                    	}
                    	else {
                    		//kuyruktaki prosesler 1sn olacak sekilde calistirilmaya baslanir
	                        checkIsProcessExpired();
	                        userProcess.setProcessSituation("started");
	
	                        print(userProcess.getColor(),time,userProcess.getProcessId(),userProcess.getPriority()
	                                ,userProcess.getProcessTime(),userProcess.getProcessSituation(), 
	                                userProcess.getWaitingTime());
	                        waitOrFinishUserProcess();
	                        //proses 1sn calistirilir isi biterse sonlandirilir bitmezse beklemeye alinip onceligi düsürülür
	                        
                    	}
                        
                    	continue;
                    }
                    else if(userProcess.getProcessSituation()=="waiting"){
                        //proses beklemede ise yürütülür ve tekrar bekletmeye alinir veya sonlandirilir
                        checkIsProcessExpired();
                        userProcess.setWaitingTime(userProcess.getWaitingTime()+1);
                        userProcess.setProcessSituation("processing");

                        print(userProcess.getColor(),time,userProcess.getProcessId(),userProcess.getPriority()
                                ,userProcess.getProcessTime(),userProcess.getProcessSituation(), 
                                userProcess.getWaitingTime());

                        waitOrFinishUserProcess();
                        continue;
                    }
                }
            }
			
			if(processes.Empty()&& userProcesses.Empty()&&realTimeProcesses.Empty()&& lowPriorityQueue.Empty()&&
                    highPriorityQueue.Empty()&& midPriorityQueue.Empty()&& userProcess==null&& realTimeProcess==null) {
				break;
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
	private void print(String color, int time, int processId, int priority, int remainTime,String processSituation,
			int waitingTime) {
		String process = editProcessSituation(processSituation, 18);
		System.out.print(color+time);
		 if(time < 10)
	            System.out.print(" ");
	        System.out.print(" sn "+"\t"+ process +"\t \t" +"\t(id: "+ processId);
	        if(processId < 10)
	            System.out.printf(" ");
	        System.out.printf("   "+"priority: "+priority+"    "+ "remain time: "+ remainTime);
	        if(remainTime < 10)
	            System.out.print(" ");
	        System.out.print(" waiting time: "+ remainTime);
	        if(remainTime < 10)
	            System.out.printf(" ");
	        System.out.println(")");
	}
	
	private String editProcessSituation(String string, int size) {
		if (string.length() >= size)
        {
            return string;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        while (sb.length() < size)
        {
            sb.append(' ');
        }
        return sb.toString();
	}
	
}
