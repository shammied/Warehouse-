package warehouse;

import java.util.ArrayList;

/**
 * A sequencer is a worker in the warehouse that checks if a picker picked the correct items for
 * a picking request.
 */
public class Sequencer extends Worker {
  
  /** The sku values of the items that were picked by the picker for the picking request. */
  private ArrayList<String> picked;
  /** The correct sku values for the items in the picking request. */
  private ArrayList<String> orderSkus;

  /**
    * Creates a new Sequencer.
    * 
    * @param name The name of the sequencer.
    */
  Sequencer(String name) {
    super(name);
  }
    
  @Override
  public void setPr(PickingRequest pr) {
    super.setPr(pr);
    picked = new ArrayList<>(pr.getPickedSkus());
    orderSkus = new ArrayList<>(pr.getSkus());
  }
  
  @Override
  public boolean scanItem() {
    if (picked.size() == orderSkus.size()) {
      String currSku = picked.get(0);
      if (orderSkus.contains(currSku)) {
        picked.remove(currSku);
        orderSkus.remove(currSku);
        return true;
      } 
    }  
    
    return false;
  }
  
  @Override
  public void finishRequest() {
    super.finishRequest();
    picked = null;
    orderSkus = null;
  }
  
  /**
   * Returns the sku values that the picker picked for the sequencer's current picking request.
   * 
   * @return the list of picked sku values
   */
  public ArrayList<String> getPicked() {
	  return this.picked;
  }
  
  /**
   * Returns a list of the correct sku values for the sequencer's current picking request.
   * 
   * @return the list of correct sku values
   */
  public ArrayList<String> getOrderSkus() {
	  return this.orderSkus;
  }
  
  @Override
  public void resetCurrItem() {
    super.resetCurrItem();
    picked = new ArrayList<>(super.getPr().getPickedSkus());
    orderSkus = new ArrayList<>(super.getPr().getSkus());
  }

}