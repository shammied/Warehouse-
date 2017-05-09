package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class WarehousePickingTest {

  @Test
  public void optimize() {
	@SuppressWarnings("unused")
	WarehousePicking warehousePicking = new WarehousePicking();
	@SuppressWarnings("unused")
    Warehouse wh = new Warehouse(2, 2, 3, 4);
    ArrayList<String> skus = new ArrayList<>();
    skus.add("12");
    skus.add("1");
    skus.add("5");
    skus.add("8");
    ArrayList<String> loc = (ArrayList<String>) WarehousePicking.optimize(skus);
    //System.out.println(skus.size());
    //System.out.println(loc.size());
    assertEquals(skus.size(), loc.size());
    assertEquals(skus.containsAll(loc) && loc.containsAll(skus), false);
    
  }

}
