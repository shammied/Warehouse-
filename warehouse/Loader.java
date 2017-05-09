package warehouse;

import java.util.ArrayList;

/**
 * A Loader is a worker in the warehouse that loads trucks with pallets in the correct order.
 */
public class Loader extends Worker {
  
  /** The ID of the last picking request loaded in a truck. 
   * This variable is left static because we do not want to initialize a new Loader object
   * if we ever need to determine the ID of the last loaded Pallet. */
  private static int lastPRLoaded;
  /** The pallet containing the front fascia. */
  private ArrayList<String> frontPallet;
  /** The pallet containing the back fascia. */
  private ArrayList<String> backPallet;
  /** The correct sku values for the items in the picking request. */
  private ArrayList<String> orderSkus;
  /** The current item on the pallet that the loader is checking. */
  private int palletIndex;

  /**
   * Creates a new Loader.
   * 
   * @param name The name of the loader
   */
  Loader(String name) {
    super(name);
  }
  
  /**
   * Returns the picking id of the last pallets loaded in the truck.
   * 
   * @return the picking id of last pallets loaded.
   */
  
  /*
   * getLastLoaded is used in Systems class, and must be static because it accesses a static
   * variable outside of the Systems class.
   */
  public static int getLastLoaded() {
    return lastPRLoaded;
  }
  
  /**
   * Increases the last picking request field by 1, since picking requests are given integer
   * picking ID's that increase by 1 each time.
   */
  /*
   * setLastLoaded is used in Systems class, and must be static because it accesses a static
   * variable outside of the Systems class.
   */
  public static void setLastLoaded() {
    lastPRLoaded++;
  }
  
  /**
   * Gives the loader a pair of pallets and their associated picking request.
   * 
   * @param pallets the pallets that the loader should load.
   */
  public void setPallets(Pallets pallets) {
    super.setPr(pallets.getPr());
    orderSkus = getPr().getSkus();
    frontPallet = pallets.getFront();
    backPallet = pallets.getBack();
  }

  /**
   * Checks if the items on the pallets are the correct items and if they are sorted in the 
   * correct order.
   * 
   * @return True if the pallets are correct and sorted properly. Otherwise return false.
   */
  @Override
  public boolean scanItem() {
    
    if (super.getCurrItem() % 2 == 0) {
      if (orderSkus.get(super.getCurrItem()).equals(frontPallet.get(palletIndex))) {
        return true;
      }
    } else {
      if (orderSkus.get(super.getCurrItem()).equals(backPallet.get(palletIndex))) {
        // Increase the index after a back fascia is checked.
        palletIndex++;
        return true;
      }
    } 
    return false;
  }
  
  @Override
  public void finishRequest() {
    super.finishRequest();
    frontPallet = null;
    backPallet = null;
    orderSkus = null;
    palletIndex = 0;
  }
  
  @Override
  public void resetCurrItem() {
    super.resetCurrItem();
    palletIndex = 0;
  }
  
  /**
   * Getters used in testing.
   * @return field value specified.
   */
  public ArrayList<String> getOrderSkus() {
	  return this.orderSkus;
  }
  
  public ArrayList<String> getFrontPallet() {
	  return this.frontPallet;
  }
  
  public ArrayList<String> getBackPallet() {
	  return this.backPallet;
  }

  public int getPalletIndex() {
	  return this.palletIndex;
  }
  
}
