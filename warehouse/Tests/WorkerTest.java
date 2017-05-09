package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class WorkerTest {
	
	@Test
	public void testgetName() {
		Worker w = new Picker("Alice");
		assertEquals(w.getName(), "Alice");
	}
	
	@Test
	public void testchangeCurrItem() {
		Worker w = new Picker("Alice");
		w.changeCurrItem("+");
		assertEquals(w.getCurrItem(), 1);
		w.changeCurrItem("-");
		assertEquals(w.getCurrItem(), 0);
	}
	 
	@Test
	public void testisNextItem() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Worker w = new Picker("Alice");
		Fascia f1 = new Fascia("Blue", "SES");
		Fascia f2 = new Fascia("Blue", "SE");
		Fascia f3 = new Fascia("Blue", "S");
		Fascia f4 = new Fascia("Black", "SES");
		ArrayList<Order> orders =  new ArrayList<>();
		orders.add(f1);
		orders.add(f2);
		orders.add(f3);
		orders.add(f4);
		PickingRequest pr = new PickingRequest(orders);
		w.setPr(pr);
		assertTrue(w.getCurrItem() == 0);
		assertTrue(w.isNextItem());
		w.changeCurrItem("+");
		w.changeCurrItem("+");
		w.changeCurrItem("+");
		w.changeCurrItem("+");
		w.changeCurrItem("+");
		w.changeCurrItem("+");
		w.changeCurrItem("+");
		w.changeCurrItem("+");
		assertFalse(w.isNextItem());
		
	}
	
}
