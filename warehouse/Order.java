package warehouse;

import java.util.ArrayList;

/**
 * A new order that the warehouse receives.
 */
public abstract class Order {

  /**
   * Looks up the sku value(s) of the order from a traversal table and returns them in a list.
   * 
   * @return A list of sku value(s) that match the order.
   */
  public abstract ArrayList<String> skuLookup();

}
