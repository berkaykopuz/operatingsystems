package operatingsystems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public static List<Process> read() {
    	
        List<Process> processList = new ArrayList<>();

        // Dosya adını değiştirin veya tam yolu belirtin
        String fileName = "giris.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(", ");
                Process newProcess = new Process(
                        Integer.parseInt(tokens[0].trim()),
                        Integer.parseInt(tokens[1].trim()),
                        Integer.parseInt(tokens[2].trim()),
                        Integer.parseInt(tokens[3].trim()),
                        Integer.parseInt(tokens[4].trim()),
                        Integer.parseInt(tokens[5].trim()),
                        Integer.parseInt(tokens[6].trim()),
                        Integer.parseInt(tokens[7].trim())
                );
                processList.add(newProcess);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return processList;
    }
}

