package warehouse;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A picking request holds a group of orders. Warehouse workers will interact with the picking
 * request to make sure that it is fulfilled properly.
 */

public class PickingRequest implements Comparable<PickingRequest> {

  /** An ArrayList of the orders contained in this picking request. */
  private ArrayList<Order> orders = new ArrayList<>();  
  /** The number of picking requests created by the warehouse. It increases each time a new picking
   * request is made. It must be static since every time an instance of PickingRequest is
   * made, the variable will keep the same value of currRequest and increment when needed. */
  private static int currRequest;
  /** The unique id for this picking request. */
  private int pickingId;
  /** The corresponding sku values for the orders. */
  private ArrayList<String> skus;
  /** An ArrayList of locations along with the sku at that location. */
  private ArrayList<String> locations = new ArrayList<>();
  /** An ArrayList of sku values that the picker picked. The sku values are not guaranteed to
   * be the correct sku values for this picking request. They may also be incomplete. */
  private ArrayList<String> pickedSkus;

  /**
    * Creates a new PickingRequest. The currRequest field increases by one and
    * pickingID is set.
    * 
    * @param orders The orders which belong to this picking request.
    */
  PickingRequest(ArrayList<Order> orders) {
    currRequest++;
    pickingId = currRequest;
    this.orders = orders;
    skuLookup();
    locations = (ArrayList<String>) WarehousePicking.optimize(skus);
  } 

  /**
    * Looks up the sku value(s) for each order and adds them to picking request's
    * list of sky values.
    */
  public void skuLookup() {
    ArrayList<String> skus = new ArrayList<>();
    for (Order order : orders) {
      ArrayList<String> sku = order.skuLookup();
      skus.addAll(sku);
    }
    this.skus = skus;
  }

  /**
    * Returns the sku values of the orders in this picking request.
    * 
    * @return skus The ArrayList of SKU numbers from the orders in this picking request.
    */
  public ArrayList<String> getSkus() {
    return skus;
  }

  /**
    * Returns the unique id of this picking request.
    * 
    * @return pickingID the unique id of this picking request.
    */
  public int getPickingId() {
    return pickingId;
  }

  /**
    * Returns a list of the orders in this picking request.
    * 
    * @return The ArrayList of orders of the picking request.
    */
  public ArrayList<Order> getOrders() {
    return orders;
  }

  /**
   * Sets pickedSkus to the ArrayList of sku values that a picker picked while
   * completing this picking request.
   * 
   * @param skus The ArrayList of sku values that a picker picked for this picking request.
   * @see Picker
   */
  public void addPickedSkus(ArrayList<String> skus) {
    pickedSkus = skus;
  }

  /**
   * Returns a list of the SLU numbers that a picker picked for this picking request.
   * 
   * @return The ArrayList of SKU numbers that a picker picked for this picking request.
   */
  public ArrayList<String> getPickedSkus() {
    return pickedSkus;
  }

  /**
    * Gets and returns the location of the item at itemNum relative to the
    * ArrayList of locations.
    * 
    * @param itemNum The index of the location in the ArrayList of locations.
    * @return an ArrayList of size 4 containing the location in the format:
    *         {zone, aisle, rack, level}
    */
  public ArrayList<String> getLocation(int itemNum) {
    String item = locations.get(itemNum);
    ArrayList<String> location = new ArrayList<String>(Arrays.asList(item.split(",")));
    // Remove the sku value from the ArrayList, so just the location is returned
    location.remove(location.size() - 1);
    return location;
  }
  
  /**
   * Gets and returns the sku value of the item at itemNum relative to the ArrayList
   * of locations.
   * 
   * @param itemNum The index of the sku in the ArrayList of locations.
   * @return the sku value as a string.
   */
  public String getNextSku(int itemNum) {
    String item = locations.get(itemNum);
    ArrayList<String> sku = new ArrayList<>(Arrays.asList(item.split(",")));
    // The last value of the list is the sku value.
    return sku.get(sku.size() - 1);
  }

  @Override
  public String toString() {
    return "Picking ID: " + pickingId;
  }

  public int compareTo(PickingRequest other) {
    return Integer.compare(this.pickingId, other.pickingId);
  }
  
}
