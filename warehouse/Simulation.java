package warehouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Simulation {

  /**
   * Creates a simulation and runs it.
   * 
   * @param args any input that needs to be received.
   */    
  public static void main(String[] args) {
    Simulation sim = new Simulation();
    sim.readEvents(args);
  }
  
  /**
   * Reads events from any fascia warehouse events file with a specific format.
   * 
   * @param args any input that needs to be received by the simulation.
   */
  public void readEvents(String[] args) { 
    // Initialize the logger
    Logger eventLogger = Logger.getLogger("WarehouseLogger");
    Handler consoleHandler = new ConsoleHandler();
    LogManager.getLogManager().reset();
    eventLogger.setLevel(Level.ALL);
    consoleHandler.setLevel(Level.ALL);
    consoleHandler.setLevel(Level.ALL);
    eventLogger.addHandler(consoleHandler); 
    
    try {
      FileHandler fhandler = new FileHandler("./log.txt");
      fhandler.setLevel(Level.ALL);
      fhandler.setFormatter(new SimpleFormatter());
      eventLogger.addHandler(fhandler);
    } catch (IOException io) {
      eventLogger.info("Cannot create a file handler");
    }
    
    String fileName = args[0];
    File file1 = new File(fileName);
    Systems system = new Systems();
  
    try {
      Scanner input = new Scanner(file1);
      while (input.hasNextLine()) {
        String data = input.nextLine(); // line 1
        eventLogger.info("Input: " + data);
        ArrayList<String> line = new ArrayList<String>(Arrays.asList(data.split(" ")));
        
        String event = line.get(0);
        if (event.equals("Order")) {
          Fascia fascia = new Fascia(line.get(2), line.get(1));
          // add fascia to main queue, creating new PickingRequest
          // when there are 4, adds new PR to PriorityQueue
          system.addOrder(fascia);

        } else if (event.equals("Picker")) {
          Picker picker = (Picker) system.getWorker(line.get(1), event);

          if (line.get(2).equals("ready")) {
            // add picker to availPickers queue, set status to
            // ready, give picking request to picker
            system.addPicker(picker);

          }

          if (line.get(2).equals("to") && line.get(3).equals("marshalling")) {
            // calls finishRequest
            system.pickerMarshalling(picker);

          }
            
          if (line.get(2).equals("pick")) {
            system.pickerPick(picker, line.get(3));

          }
          
          if (line.get(2).equals("put") && line.get(3).equals("back")) {
            system.pickerPutBack(picker);
          }

        } else if (event.equals("Sequencer")) {
          Sequencer seq = (Sequencer) system.getWorker(line.get(1), event);
          
          if (line.get(2).equals("ready")) {
            // add picker to availPickers queue, set status to
            // ready, give picking request to picker
            system.addSequencer(seq);
          }
          if (line.get(2).equals("sequence")) {
            system.workerScanItem(seq);
          }
          if (line.get(2).equals("rescan")) {
            system.rescan(seq);
          }
          if (line.get(2).equals("to") && line.get(3).equals("loading")) {
            system.sequencerToLoading(seq);
          }
 
        } else if (event.equals("Loader")) {
          Loader loader = (Loader) system.getWorker(line.get(1), event);
          if (line.get(2).equals("ready")) {
            system.addLoader(loader);
          }
          if (line.get(2).equals("scan") && line.get(3).equals("pallets")) {
            system.workerScanItem(loader);
          }
          if (line.get(2).equals("rescan")) {
            system.rescan(loader);
          }
          if (line.get(2).equals("loads")) {
            system.load(loader);
          }

        }

      }
      input.close();
    } catch (FileNotFoundException notFound) {
      System.out.println("File not found!");

    }
    system.getWarehouse().finalWareHouseReport();
    system.finalOrdersReport();

  }

}

