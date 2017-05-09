package warehouse;

/*
 * A worker in the the warehouse. Each worker has a different job.
 */
public abstract class Worker {

  /** The name of the worker. */
  private String name;
  /** A picking request that the worker is working on. */
  private PickingRequest pr;
  /** The number of the item that the worker is working on in regards to how many items are in the
   * the picking request. */
  private int currItem;
  /** The number of items in the picking request. 1 sku value equals 1 item. */
  private int maxItems;

  /**
   * Creates a new worker in the warehouse.
   * 
   * @param name The name of the worker.
   */
  Worker(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the worker.
   * 
   * @return The name of the worker.
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Returns the picking request that the worker is working on.
   * 
   * @return the picking request that the worker is working on.
   */
  public PickingRequest getPr() {
    return pr;
  }
  
  /**
   * Gives the worker a picking request.
   * 
   * @param pr the picking request that the worker is to work on.
   */
  public void setPr(PickingRequest pr) {
    this.pr = pr;
    maxItems = pr.getSkus().size();
  }
  
  /**
   * Changes the currItem field of the worker by 1. Increases if the instruction is "+" and
   * decreases if the instruction is "-".
   * 
   * @param instruction "+" if currItem should increase or "-" if it should decrease.
   */
  public void changeCurrItem(String instruction) {
    if (instruction.equals("+")) {
      currItem++;
    } else if (instruction.equals("-")) {
      currItem--;
    }
  }
  
  /**
   * Returns the current item the worker is worker is working on with regards to how many items
   * are in the picking request.
   * 
   * @return the current item that the worker is working on
   */
  public int getCurrItem() {
    return currItem;
  }
  
  /**
   * Resets which item the worker is working on. currItem is set to 0.
   */
  public void resetCurrItem() {
    currItem = 0;
  }
  
  /**
   * Returns true if there is a next item for the worker to work on. False otherwise.
   * 
   * @return true if there is another item for the worker to work on or false otherwise.
   */
  public boolean isNextItem() {
    return currItem < maxItems;
  }
  
  /**
   * This is called once the worker is finished working on a picking request. It resets the
   * worker's current item and clears the picking request field, as well as all other fields
   * other than the worker's name.
   */
  public void finishRequest() {
    resetCurrItem();
    pr = null;
    maxItems = 0;
  }
  
  /**
   * This is called when a worker scans an item with a barcode reader. It returns whether the sku
   * that was scanned is a valid sku for the picking request.
   * 
   * @return true if the worker scanned a valid item or false otherwise.
   */
  public abstract boolean scanItem();
  
}
