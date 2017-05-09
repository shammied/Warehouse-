package warehouse;

import java.util.ArrayList;

/**
 * A Picker is a worker in the warehouse that picks items from the warehouse.
 */
public class Picker extends Worker {
  
  private ArrayList<String> picked = new ArrayList<>();
    
  /**
    * Creates a new Picker.
    * 
    * @param name the name of the picker.         
    */
  Picker(String name) {
    super(name);
  }

  /**
    * Prints the location of the next location for the picker to go and adds the sku value 
    * of the item that the picker picked to ordersToSeq. It is possible for a picker to pick 
    * an incorrect item, so the sku value of the item they picked may not match the sku of
    * the location. 
    * 
    * @param sku The sku value of the item that the picker picked.      
    * @return A list of the location of where the picker should go in the format:
    *         [zone, aisle, rack, level]
    * @see Location
    */
  public ArrayList<String> getNextItem() {
    ArrayList<String> location = super.getPr().getLocation(getCurrItem());
    
    return location;
  } 
  
  /**
   * The picker adds the item with the given sku value to their forklift.
   * 
   * @param sku the sku value of the item that the picker adds to their forklift.
   */
  public void pickItem(String sku) {
    picked.add(sku);
  }
  
  public ArrayList<String> getPickedSkus() {
    return picked;
  }
  
  /**
   * The picker unloads their forklift and the sku values of the items that they picked are
   * transferred to the picking request.
   */
  public void unloadForklift() {
    super.getPr().addPickedSkus(picked);
    picked = new ArrayList<>();
  }
  
  @Override
  public boolean scanItem() {
    // The sku of the last item that was supposed to be picked
    String correctSku = getPr().getNextSku(getCurrItem());
    // The sku of the last item picked
    String lastPicked = picked.get(picked.size() - 1);
    return lastPicked.equals(correctSku);
  }

}