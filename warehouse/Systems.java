package warehouse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * A system for a warehouse. The system interacts with workers and the inventory of the warehouse.
 * It tells workers what to do, tracks orders as they come in, creates and tracks picking requests
 * as they are handled within the warehouse.
 */
public class Systems {

  /** A warehouse with zones, aisles, racks, and levels. */
  private Warehouse warehouse = new Warehouse(2, 2, 3, 4);
  /** A map of all the works in the warehouse. */  
  private Map<String, Worker> workerMap = new HashMap<>();
  /** A queue of pickers that are ready for a picking request. */
  private Queue<Picker> availPickers = new LinkedList<>();
  /** A queue of sequencers that are ready for a picking request. */
  private Queue<Sequencer> availSequencers = new LinkedList<>();
  /** A queue of loaders that are ready for a picking request. */
  private Queue<Loader> availLoaders = new LinkedList<>();
  /** A queue of orders that are waiting to be processed. */
  private Queue<Order> orderQueue = new LinkedList<>();
  /** A queue of picking requests waiting to be picked by a picker. */
  private PriorityQueue<PickingRequest> prQueue = new PriorityQueue<>();
  /** A queue of picking requests waiting to be sequenced. */
  private PriorityQueue<PickingRequest> sequencingQueue = new PriorityQueue<>();
  /** A queue of picking requests waiting to be loaded. */
  private PriorityQueue<Pallets> loadingQueue = new PriorityQueue<>();
  /** An ArrayList of loaded Pallets. */
  private ArrayList<Order> loaded = new ArrayList<>();
  /** Initializes the logger. */
  private static final Logger eventLogger = Logger.getLogger("WarehouseLogger");
  
  /** 
    * Constructs a system with a warehouse that is ready to interact with
    * workers.
    */
  Systems() {
  }

  /**
   * Returns the warehouse that the system is managing.
   * 
   * @return the warehouse for this system.
   */
  public Warehouse getWarehouse() {
    return warehouse;
  }
  
  /**
    * Adds an order to a queue of orders waiting to be processed. Once there
    * are 4 orders, create a picking request.
    * 
    * @param order The order to be added to the queue
    */
  public void addOrder(Order order) {
    orderQueue.add(order);
    eventLogger.info("New order: " + order);
         
    if (orderQueue.size() == 4) {
      createPickingRequest();
    }
  }

  /**
    * Creates an order of the first 4 items in the order queue and removes them
    * from the queue. If there is an available picker, give them the picking
    * request.
    */
  public void createPickingRequest() {
    ArrayList<Order> prOrders = new ArrayList<>();
    prOrders.add(orderQueue.remove());
    prOrders.add(orderQueue.remove());
    prOrders.add(orderQueue.remove());
    prOrders.add(orderQueue.remove());
    PickingRequest pr = new PickingRequest(prOrders);
    prQueue.add(pr);
    // Give the picking request to a picker.
    givePickerPr();
    eventLogger.info("Picking request made");
  } 
  
  /**
   * Returns the worker with the specific name. If there is no such worker, create a new worker
   * with the specified job and return that worker.
   * 
   * @param name the name of the worker
   * @param job the job of the worker
   * @return the worker with the specified name
   */
  public Worker getWorker(String name, String job) {
    if (!workerMap.containsKey(name)) {
      Worker worker;
      if (job.equals("Picker")) {
        worker = new Picker(name);
      } else if (job.equals("Sequencer")) {
        worker = new Sequencer(name);
      } else {
        worker = new Loader(name);
      }
      workerMap.put(name, worker);
    }
    return workerMap.get(name);
  }

  /**
    * Gives a picking request to a picker if there is both a picking request and
    * picker available.
    */
  public void givePickerPr() {
    if (!prQueue.isEmpty() && !availPickers.isEmpty()) {
      PickingRequest pr = prQueue.remove();
      Picker picker = availPickers.remove();
      picker.setPr(pr);
      eventLogger.info(pr + " is with " + picker.getName());
    }
  }
  
  /** 
   * Gives a picking request to a sequencer if there is both a picking request and a sequencer
   * available.
   */
  public void giveSequencerPr() {
    if (!sequencingQueue.isEmpty() && !availSequencers.isEmpty()) {
      PickingRequest pr = sequencingQueue.remove();
      Sequencer sequencer = availSequencers.remove();
      sequencer.setPr(pr);
      eventLogger.info(sequencer.getPr() + " is with " + sequencer.getName());
    }
  }
  
  /**
   * Gives a picking request to a loader if there is both a picking request and a loader available.
   */
  public void giveLoaderPr() { 
    Pallets pallets = loadingQueue.peek();
    /* Check if there is an available loader and picking request, and if the last picking request
     * has been loaded. */
    if (!loadingQueue.isEmpty() && !availLoaders.isEmpty() 
        && Loader.getLastLoaded() == pallets.getPr().getPickingId() - 1) {
      pallets = loadingQueue.remove();
      Loader loader = availLoaders.remove();
      loader.setPallets(pallets);
      eventLogger.info(pallets.getPr() + " is with " + loader.getName());
    }
  }
  
  /**
    * Adds a picker to the queue of available pickers.
    * 
    * @param worker The picker to be added to the queue
    */
  public void addPicker(Picker worker) {
    // check if the picker is already waiting in the queue
    if (availPickers.contains(worker)) {
      eventLogger.info(worker.getName() + " is already waiting for a picking request!");
   
    } else if (worker.getPr() != null) {
      // check if the picker is already working on a picking request
      String msg = worker.getName() + " is still picking request " + worker.getPr();
      eventLogger.info(msg);
      
    } else {
      availPickers.add(worker);
      eventLogger.info(worker.getName() + " ready for new picking request");
      givePickerPr();
    }
  }
  
  /**
   * Adds a sequencer to the queue of available sequencers.
   * 
   * @param worker The sequencer to be added to the queue.
   */
  public void addSequencer(Sequencer worker) {
    if (availSequencers.contains(worker)) {
      eventLogger.info(worker.getName() + " is already waiting for a picking request!");
    } else if (worker.getPr() != null) {
      String msg = worker.getName() + " is still sequencing request" + worker.getPr();
      eventLogger.info(msg);
    } else {
      availSequencers.add(worker);
      eventLogger.info(worker.getName() + " ready to sequence a new picking request");
      giveSequencerPr();
    }
  }
    
  /**
   * Adds a loader to the queue of available loaders.
   * 
   * @param worker The loader to be added to the queue.
   */
  public void addLoader(Loader worker) {
    if (availLoaders.contains(worker)) {
      eventLogger.info(worker.getName() + " is already waiting for a picking request!");
    } else if (worker.getPr() != null) {
      String msg = worker.getName() + " is still loading request" + worker.getPr();
      eventLogger.info(msg);
    } else {
      availLoaders.add(worker);
      eventLogger.info(worker.getName() + " ready to load a new picking request");
      giveLoaderPr();
    }
  }

  /**
    * Tells the picker to pick the item with the specified SKU number.
    * 
    * @param picker The picker that will pick the item.
    * @param sku The SKU number of the item to be picked. The picker may pick up an incorrect
    *            item if this number is incorrect.   
    */
  public void pickerPick(Picker picker, String sku) {
    if (picker.getPr() == null) {
      eventLogger.info(picker.getName() + " does not have a picking request!");
      
    } else if (!picker.isNextItem()) {
      eventLogger.info(picker.getName() 
          + " has already retrieved all items for the picking request!");
    } else {
      ArrayList<String> location = picker.getNextItem();
      // The last item of the list is the sku of the item that was to be picked.
      // String correctSku = location.remove(location.size() - 1);
      eventLogger.info(picker.getName() + " go to " + location);  
      picker.pickItem(sku);
      eventLogger.info(picker.getName() + " picked sku: " + sku);
      boolean correctItem = picker.scanItem();
      picker.changeCurrItem("+");
      // The location that the picker actually went to
      String pickedLocation = Warehouse.getTraversalMap().get(sku);
      warehouse.decreaseAmount(pickedLocation);
      if (!correctItem) {
        eventLogger.info("Incorrect item. Put item back.");
      }
    }
  }
  
  /**
   * This method is meant for loaders and sequencers. Loaders and sequencers scan the next
   * item in the picking request if there is one available. If the item is valid, increase
   * the worker's current item. If the item is invalid, the worker sends the picking request
   * back to be repicked. Do nothing if there is no item available to scan.
   * 
   * @param worker The worker who is scanning the item.
   */
  public void workerScanItem(Worker worker) {
    if (worker.getPr() == null) {
      eventLogger.info(worker.getName() + " does not have a picking request!");
    } else if (!worker.isNextItem()) {
      eventLogger.info(worker.getName() 
          + " has already scanned all items for the picking request!");
    } else {
      boolean valid = worker.scanItem();
      // If there is an incorrect item, send the picking request to be re-picked
      if (!valid) {
        eventLogger.info(worker.getName() + " has scanned an invalid item.");
        prQueue.add(worker.getPr());
        eventLogger.info(worker.getPr() + " has been sent back to be repicked.");
        worker.finishRequest();
      } else {
        worker.changeCurrItem("+");
      }
    } 
  }
  
  /**
   * A picker puts back the last item that they picked onto the racks of the warehouse. This
   * removes it from their forklift and increases the stock of that item in the racks by 1.
   * 
   * @param picker The picker that is going to put back their previously picked item.
   */
  public void pickerPutBack(Picker picker) {
    ArrayList<String> forklift = picker.getPickedSkus();
    // This method is only valid if a picker has an item to put back.
    if (!forklift.isEmpty()) {
      String putBack = forklift.remove(forklift.size() - 1);
      String location = Warehouse.getTraversalMap().get(putBack);
      warehouse.increaseAmount(location);
      picker.changeCurrItem("-");
      eventLogger.info(picker.getName() + " has returned item " + putBack);
    } else {
      eventLogger.info(picker.getName() + " has no item to put back.");
    }
  }

  /**
   * Tells the picker to go to marshalling. The picking request that the picker fulfilled is
   * added to the queue of picking requests waiting to be sequenced.
   * 
   * @param picker The picker who is finished with their picking request.
   */
  public void pickerMarshalling(Picker picker) {
    eventLogger.info(picker.getName() + " go to marshalling.");
    // Picker finishes working on the request
    if (picker.getPr() != null) {
      picker.unloadForklift();
      PickingRequest picked = picker.getPr();
      picker.finishRequest();
      sequencingQueue.add(picked);
      giveSequencerPr();
    }
  } 
   
  /**
   * Tells the sequencer to send the pallets that they filled to the queue of pallets waiting
   * to be loaded. If the sequencer is not finished sequencing, do nothing.
   * 
   * @param sequencer The sequencer that should send the pallets to the loading queue.
   */
  public void sequencerToLoading(Sequencer sequencer) {
    if (!sequencer.isNextItem() && sequencer.getPr() != null) {
      Pallets pallets = new Pallets(sequencer.getPr());
      loadingQueue.add(pallets);
      eventLogger.info(sequencer.getName() + " created pallets for " + sequencer.getPr());
      sequencer.finishRequest();
      // If there is a loader available, give them the pallets
      giveLoaderPr();
    } else {
      eventLogger.info(sequencer.getName() + " is not finished sequencing!");
    }
  }
  
  /**
   * Tells the loader to load the pallets on the truck. This happens once the loader has finished
   * double checking that the items on the pallets are all valid items.
   * 
   * @param loader The loader to load pallets on the truck.
   */
  public void load(Loader loader) {
    // Only load if the previous picking request was loaded.
    if (loader.getPr() != null && !loader.isNextItem() 
        && Loader.getLastLoaded() == loader.getPr().getPickingId() - 1) {
      loaded.addAll(loader.getPr().getOrders());
      eventLogger.info(loader.getName() + " has loaded " + loader.getPr());
      Loader.setLastLoaded();
      loader.finishRequest();
    } else { 
      eventLogger.info(loader.getName() + " cannot load.");
    }
  }    
    
  /**
   * This is called when a sequencer or a loader restarts the scanning process for a picking
   * request.
   * 
   * @param worker The worker that will restart the scanning process.
   */
  public void rescan(Worker worker) {
    worker.resetCurrItem();
    eventLogger.info(worker.getName() + " restarted scanning");
  }

  /**
   * Writes a final.csv file with the list of orders that were processed and loaded onto a truck.
   * If a file already exists, overwrite that file.
   */
  public void finalOrdersReport() {
    File file = new File("./orders.csv");
    
    try {
      FileWriter fw = new FileWriter(file);
      BufferedWriter buffW = new BufferedWriter(fw);
      buffW.write("Orders Loaded:");
      buffW.newLine();
      if (!loaded.isEmpty()) {
        // Writes each order on a new line of the csv file.
        for (Order o: loaded) {
          buffW.write(o.toString());
          buffW.newLine();
        }
      }
      buffW.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  /**
   * getters used in testing.
   * @return variable value specified in method.
   */
  
  public Map<String, Worker> getWorkerMap() {
    return this.workerMap;
  }
  
  public Queue<Picker> getAvailPickers() {
    return this.availPickers;
  }
  
  public Queue<Sequencer> getAvailSequencers() {
    return this.availSequencers;
  }
  
  public Queue<Loader> getAvailLoaders() {
    return this.availLoaders;
  }
  
  public Queue<Order> getOrderQueue() {
    return this.orderQueue;
  }
 
  public PriorityQueue<PickingRequest> getPrQueue() {
    return this.prQueue;
  }
  
  public PriorityQueue<PickingRequest> getSequencingQueue() {
    return this.sequencingQueue;
  }
  
  public PriorityQueue<Pallets> getLoadingQueue() {
    return this.loadingQueue;
  }
  
  public ArrayList<Order> getLoaded() {
    return this.loaded;
  }

}