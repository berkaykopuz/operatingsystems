package operatingsystems;

import java.util.List;
import java.util.Scanner;


public class main {

    public static void main(String[] args) {
    	Reader reader = new Reader();
        Queues queues = reader.read();
        Dispatcher dispatcher = new Dispatcher(queues);
        dispatcher.simulate();
    }

    
}
