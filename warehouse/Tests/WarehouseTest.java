package warehouse;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class WarehouseTest {

  @Test
  public void testdecreaseAmount() {
    Warehouse warehouse = new Warehouse(2, 2, 3, 4);
    // location loc exists in initial.csv file 
    String loc = "B,1,0,0"; 
    int amount = warehouse.locInFile(loc);
    assertEquals(amount, 7);
    warehouse.decreaseAmount(loc);
    int decreasedAmount = warehouse.getwMap().get(loc);
    assertEquals(decreasedAmount, 6);
    
    String loc2 = "B,1,2,1";
    int amount2 = warehouse.locInFile(loc2);
    assertEquals(amount2, 6);
    warehouse.decreaseAmount(loc2);
    int value = warehouse.getwMap().get(loc2);
    assertEquals(value, 30);
    
  }
   @Test
   public void testincreaseAmount() {
	    Warehouse warehouse = new Warehouse(2, 2, 3, 4);
	    // location loc exists in initial.csv file 
	    String loc = "B,1,0,0"; 
	    int amount = warehouse.locInFile(loc);
	    assertEquals(amount, 7);
	    warehouse.increaseAmount(loc);
	    int decreasedAmount = warehouse.getwMap().get(loc);
	    assertEquals(decreasedAmount, 8);
	    
	    String loc2 = "B,1,2,1";
	    int amount2 = warehouse.locInFile(loc2);
	    assertEquals(amount2, 6);
	    warehouse.increaseAmount(loc2);
	    int value = warehouse.getwMap().get(loc2);
	    assertEquals(value, 7);
   }
   
   @Test
   public void testfinalWarehouseReport() {
	   Warehouse warehouse = new Warehouse(2, 2, 3, 4);
	   warehouse.finalWareHouseReport();
	   File f = new File("./final.csv");
   }
  
   @Test
   public void testtoString() {
	   Warehouse warehouse = new Warehouse(2, 3, 3, 4);
	   for (String loc: warehouse.getwMap().keySet()) {
		    assertTrue(warehouse.toString().contains(loc));
	   }
   } 
   
}
