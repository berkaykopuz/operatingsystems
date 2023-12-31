package operatingsystems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Process {
    private int arrivalTime;
    private int priority;
    private int processTime;
    private int memory;
    private int printers;
    private int scanners;
    private int modems;
    private int cds;
    
    private int processId;
    private String color;
    private int waitingTime;
    private String processSituation;
    
    public Process(int arrivalTime, int priority, int processTime, int memory, int printers, int scanners, int modems,
			int cds, String color, String processSituation) {
		
		this.arrivalTime = arrivalTime;
		this.priority = priority;
		this.processTime = processTime;
		this.memory = memory;
		this.printers = printers;
		this.scanners = scanners;
		this.modems = modems;
		this.cds = cds;
		this.color = color;
		this.processSituation = processSituation;
		
	}
    
    public Process(int arrivalTime, int priority, int processTime, int memory, int printers, int scanners, int modems,
			int cds) {
		this.arrivalTime = arrivalTime;
		this.priority = priority;
		this.processTime = processTime;
		this.memory = memory;
		this.printers = printers;
		this.scanners = scanners;
		this.modems = modems;
		this.cds = cds;
	}

	public int getArrivalTime() {
        return arrivalTime;
    }

    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
		this.color = color;
	}

	public int getProcessId() {
        return processId;
    }
    
    
    public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getProcessSituation() {
        return processSituation;
    }

    public void setProcessSituation(String processSituation) {
        this.processSituation = processSituation;
    }

    public int getPriorityLevel() {
        return priority;
    }

    public void setPriorityLevel(int priority) {
        this.priority = priority;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getPrinters() {
		return printers;
	}



	public void setPrinters(int printers) {
		this.printers = printers;
	}



	public int getScanners() {
		return scanners;
	}



	public void setScanners(int scanners) {
		this.scanners = scanners;
	}



	public int getModems() {
		return modems;
	}



	public void setModems(int modems) {
		this.modems = modems;
	}



	public int getCds() {
		return cds;
	}



	public void setCds(int cds) {
		this.cds = cds;
	}



	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
    
    
}

