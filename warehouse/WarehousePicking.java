package warehouse;

import java.util.ArrayList;
import java.util.Map;

/**
 * An example WarehousePicking class that is provided by the company.
 */
public class WarehousePicking {

  /**
    * An example of the generic optimize method used by the warehouse.
    * 
    * @param skus
    *            the list of skus in a picking request.
    * @return the list of same skus in an optimized order for the pickers to be
    *         more efficient.
    *
    */
  
  /*
   * This class only contains a single method, so we do not need to create an instance
   * of the class every time to only use the only method optimize. Hence, it makes sense 
   * to make optimize static.
   */
  public static ArrayList<String> optimize(ArrayList<String> skus) {
    ArrayList<String> locationList = new ArrayList<>(); 
    
    Map<String, String> traversalMap = Warehouse.getTraversalMap();
    //System.out.println(traversalMap);
    //key is the sku
    for (String sku: skus) {
      if (traversalMap.containsKey(sku)) {
        locationList.add(traversalMap.get(sku) + "," + sku);
      }
    }
    
    return locationList;
      
  }

}
