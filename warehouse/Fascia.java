package warehouse;

import java.util.ArrayList;

/**
 * An order for fascia for one minivan with a specific colour and model.
 */
public class Fascia extends Order {
  
  /** The colour of the car for this fascia order. */
  private String colour;
  /** The model of the car for this fascia order. */
  private String model;

  /**
   * Creates a new order of Fascia.
   * 
   *  @param colour The colour of minivan.
   *  @param model The model of the minivan.
   */
  Fascia(String colour, String model) {
    this.colour = colour;
    this.model = model;
  }
  
  @Override
  public String toString() {
    return this.colour + " " + this.model;
  }

  @Override
  public ArrayList<String> skuLookup() {
    ArrayList<String> key = new ArrayList<>();
    key.add(colour);
    key.add(model); 
    ArrayList<String> sku = Warehouse.getTranslationMap().get(key);    
    return sku;
    
  }

}
