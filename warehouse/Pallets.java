package warehouse;

import java.util.ArrayList;

/**
 * A pair of pallets.
 */
public class Pallets implements Comparable<Pallets> {
  
  /** A list of the 4 sku values for the front pallet. */
  private ArrayList<String> front = new ArrayList<>();  
  /** A list of the 4 sku values for the back pallet. */
  private ArrayList<String> back = new ArrayList<>();
  /** The picking request that this pair of pallets fulfills. */
  private PickingRequest pr;

  /**
    * Creates a new pair of Pallets. A pallet is simply a Picking Request divided into 2
    * ArrayLists (one containing all the 4 front fascias and the other
    * containing all the 4 back fascia) Pallet is specifically designed for 4
    * orders, 8 skus with fascia front and back typess
    * 
    * @param preq The picking request that these pallets satisfy.
    *            
    */
  Pallets(PickingRequest preq) {
    pr = preq;
    
    ArrayList<String> totalSku = preq.getSkus();
    for (String string : totalSku) {
    	for (ArrayList<String> value : Warehouse.getTranslationMap().values()) {
    	    if (string.equals(value.get(0))) {
    	    	this.front.add(string);
    	    }
    	    else if (string.equals(value.get(1))) {
    	    	this.back.add(string);
    	    }
    	} 
    }
  }

  /**
   * Returns the list of SKU numbers in the pallet containing the front fascia.
   * 
   * @return The list of SKU numbers of the front fascia in this pair of pallets.
   */
  public ArrayList<String> getFront() {
    return front;
  }

  /**
   * Returns the list of SKU numbers in the pallet containing the back fascia.
   * 
   * @return The list of SKU numbers of the back fascia in this pair of pallets.
   */
  public ArrayList<String> getBack() {
    return back;
  }

  /**
   * Returns the picking request that this pair of pallets satisfies.
   * @return The picking request of this pair of pallets.
   */
  public PickingRequest getPr() {
    return pr;
  }

  public int compareTo(Pallets other) {
    return Integer.compare(pr.getPickingId(), other.pr.getPickingId());
  }

}
