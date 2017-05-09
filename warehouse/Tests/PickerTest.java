package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class PickerTest {
	
	@Test 
	public void getNextItem() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Picker p = new Picker("Bill");
        Fascia f1 = new Fascia("Blue", "SES"); 
		Fascia f2 = new Fascia("Red", "SE");
		Fascia f3 = new Fascia("Red", "S");
		ArrayList<Order> o = new ArrayList<>();
		o.add(f1);
		o.add(f2);
		o.add(f3);
		PickingRequest pr = new PickingRequest(o);
		p.setPr(pr);
		ArrayList<String> location = new ArrayList<String>(Arrays.asList(Warehouse.getTraversalMap().get("37").split(",")));
		assertEquals(p.getNextItem(), location);
		
	}
	  
	@Test
	public void testpickItem() {
		Picker p2 = new Picker("Joe");
		p2.pickItem("37");
		assertTrue(p2.getPickedSkus().contains("37"));
		
	}
	
	@Test
	public void testunloadForklift() {
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Picker p = new Picker("Bob");
		ArrayList<Order> orders = new ArrayList<>();
		Fascia f = new Fascia("Blue", "SES");
		Fascia f2 = new Fascia("Blue", "SE");
		orders.add(f);
		orders.add(f2);
		PickingRequest pr = new PickingRequest(orders);
		p.setPr(pr);
		p.pickItem("37");
		p.pickItem("38");
		ArrayList<String> picked2 = p.getPickedSkus(); 
		p.unloadForklift();
		assertTrue(p.getPickedSkus().isEmpty());
		assertEquals(p.getPr().getPickedSkus(), picked2);
	}
	
	@Test
	public void testscanItem() {
		Picker p = new Picker("Bob");
		ArrayList<Order> orders = new ArrayList<>();
		Fascia f = new Fascia("Blue", "SES");
		Fascia f2 = new Fascia("Blue", "SE");
		orders.add(f);
		orders.add(f2);
		PickingRequest pr = new PickingRequest(orders);
		p.setPr(pr);
		p.pickItem("37");
		assertTrue(p.scanItem());
		p.pickItem("38");
		p.pickItem("35");
		p.pickItem("36");
		
		Picker p2 = new Picker("Bob");
		ArrayList<Order> orders2 = new ArrayList<>();
		Fascia f3 = new Fascia("Blue", "SES");
		orders2.add(f3);
		PickingRequest pr2 = new PickingRequest(orders2);
		p2.setPr(pr2);
		p2.pickItem("37");
		p2.pickItem("38");
		assertFalse(p2.scanItem());
	}

}
