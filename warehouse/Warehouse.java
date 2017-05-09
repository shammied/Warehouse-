package warehouse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A warehouse contains stock for the items that will be ordered. Workers fulfill requests in
 * the warehouse and the stock of items is updated.
 */
public class Warehouse {     

  /** A map of all the locations in the warehouse. The keys are the String location while
   * the stock in that location is the Integer value.
   */
  private Map<String, Integer> wmap = new HashMap<>();
  /**
   * A map created from the contents in the given translation table. The keys are the String
   * color and model(separated by comma), and the values are a list of front and back fascia
   * sku values. This variable is static because it needs to be accessed by Fascia and Pallets
   * without having to create a new instance of warehouse.
   */
  private static Map<ArrayList<String>, ArrayList<String>> translationTable = new HashMap<>();
  
  /**
   * A map created from the contents of the given traversal table. The keys are the skus,
   * and the values are the String location where the corresponding sku object is stored.
   * This variable is static because it needs to be accessed by Systems
   * without having to create a new instance of warehouse.
   */
  private static Map<String, String> traversalTable = new HashMap<>();

  /**
   * Initializes a new WareHouse.
   * 
   * @param zoneNum the zone of warehouse
   * @param aisleNum the aisle in zone of warehouse
   * @param rackNum the rack in aisle in zone of warehouse
   * @param levelNum the level of rack in aisle in zone of warehouse.
   */
  Warehouse(int zoneNum, int aisleNum, int rackNum, int levelNum) {
    for (int i = 0; i < zoneNum; i++) {
      for (int j = 0; j < aisleNum; j++) {
        for (int k = 0; k < rackNum; k++) {
          for (int l = 0; l < levelNum; l++) {
            String location = getCharForNumber(i) + "," + j + "," + k + "," + l;
            int amount = locInFile(location);
            wmap.put(location, amount);
          }
        }
      } 
    }
    //translation table
    String fileName = "./translation.csv";
    translationRead(fileName);
    
    //traversal table
    String file = "./traversal_table.csv";
    traversalRead(file);
  }    
  
  /**
   * Getter for tranlationTable hashmap variable. This method is static because it needs to be 
   * accessed  by Fascia and Pallets without having to create a new instance of warehouse.
   * returns hashMap.
   * 
   * @return the map of orders with their corresponding sku values.
   */
  public static Map<ArrayList<String>, ArrayList<String>> getTranslationMap() {
    return translationTable;
  }
 
  /**
   * Returns the map of all location keys and amount in stock values.
   * 
   * @return the value of the wmap field
   */
  public Map<String, Integer> getwMap() {
    return wmap;
  }
  
  /**
   * Getter for tranlationTable hashmap variable. This method is static because it needs to be 
   * accessed  by Fascia and Pallets without having to create a new instance of warehouse.
   * returns hashMap
   * 
   * @return the value of the traversalTable field
   */
  public static Map<String, String> getTraversalMap() {
    return traversalTable;
  }

  /**
   * Returns the letter that corresponds to index. For example: A = 0, B = 1,...Z = 25
   * 
   * @param index The number which corresponds to a character.
   * @return the character equivalent of a number.
   */
  private String getCharForNumber(int index) {
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    if (index > 25) {
      return null;
    }
    return Character.toString(alphabet[index]);
  }
  
  /**
   * Reads the translation.csv file given and places information into translationTable
   * Map.
   * 
   * @param fileName The name of the translation file.
   * @return void
   */
  private void translationRead(String fileName) {
    try {
      @SuppressWarnings("resource")
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      String line = fileReader.readLine();
      while ((line = fileReader.readLine()) != null) {
        ArrayList<String> lst  = new ArrayList<String>(Arrays.asList(line.split(",")));
        String front = lst.get(2);
        String back = lst.get(3);
        ArrayList<String> skus = new ArrayList<>();
        skus.add(front);
        skus.add(back);
        ArrayList<String> cm = new ArrayList<>();
        cm.add(lst.get(0));
        cm.add(lst.get(1));
        
        translationTable.put(cm, skus);
      }  
    } catch (IOException ioe) {
      ioe.printStackTrace();;
    }
  }
  
  /**
   * Reads the traversal_table.csv file given and places information into traversal
   * Map.
   * 
   * @param fileName The name of the traversal_table file.
   * @return void
   */
  private void traversalRead(String fileName) {
    try {
      @SuppressWarnings("resource")
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      String line = fileReader.readLine();
      while (line != null) {
        List<String> lst  = new ArrayList<String>(Arrays.asList(line.split(",")));
        String loc = lst.get(0) + "," + lst.get(1) + "," + lst.get(2) + "," + lst.get(3);
        
        traversalTable.put(lst.get(4), loc);
        line = fileReader.readLine();
      }

    } catch (IOException ioe) {
      ioe.printStackTrace();;
      
    }
    
  }

  /**
   * Checks the amount of stock in the location in the initial.csv file. This is called
   * when the warehouse is first initialized.
   * 
   * @param loc The location of item stock to be checked.
   * @return The amount of stock in the specified location.
   */
  public int locInFile(String loc) {
    String fileName = "./initial.csv";
    int amount = 30;
    try {
      @SuppressWarnings("resource")
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      String line = fileReader.readLine();
      while ((line = fileReader.readLine()) != null) {
        List<String> table  = new ArrayList<String>(Arrays.asList(line.split(",")));
        int namount = Integer.parseInt(table.get(4));

        String location = table.get(0) + "," + table.get(1) + "," + table.get(2) + "," 
            + table.get(3);
        if (location.equals(loc)) {
          amount = namount;

        }

      }

    } catch (IOException ioe) {
      ioe.printStackTrace();;
      
    }
    return amount;
  }

  /**
   * Decrease the amount of an item by 1 in a specific location in the warehouse.
   * 
   * @param loc The location that should have its stock decreased.
   */
  public void decreaseAmount(String loc) {
    if (this.wmap.containsKey(loc)) {
      this.wmap.put(loc, (int) this.wmap.get(loc) - 1);
      int stock = this.wmap.get(loc);
      // Replenish if there are only 5 items left in the location.
      if (stock == 5) {
        replenish(loc);
      }
    }
  }
  
  /**
   * Increase the amount of an item by 1 in a specific location in the warehouse.
   * 
   * @param loc The location that should have its stock increased.
   */
  public void increaseAmount(String loc) {
    if (this.wmap.containsKey(loc)) {
      this.wmap.put(loc, (int) this.wmap.get(loc) + 1);
    }
  }

  /**
   * Add 25 of an item to the specified location. This is called when there are only 5
   * items remaining in a specific location.
   * 
   * @param loc The location of item that needs replenishing.
   */
  public void replenish(String loc) {
    wmap.put(loc, (int) wmap.get(loc) + 25);
  }

  /**
   * Write a final.csv file about the current state of the warehouse after all the events
   * have occurred. If a file already exists, overwrite it.
   */
  public void finalWareHouseReport() {
    File file = new File("./final.csv");
 
    try {
      FileWriter fw = new FileWriter(file);
      BufferedWriter buffW = new BufferedWriter(fw);
      buffW.write("Zone, Aisle, Rack, Level, Amount");
      buffW.newLine();
      for (Map.Entry<String, Integer> entry : wmap.entrySet()) {
        // write iff value is less than 30
        if (entry.getValue() < 30) {
          buffW.write(entry.getKey().toString() + "," + entry.getValue().toString());
          buffW.newLine();
        }
      }
      buffW.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return wmap.toString();
  }

}
