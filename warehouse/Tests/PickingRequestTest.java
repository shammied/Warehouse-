package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import java.util.ArrayList;

public class PickingRequestTest {
	
	@Test
	public void testgetSkus() {
		ArrayList<Order> orders = new ArrayList<>();
		PickingRequest pr = new PickingRequest(orders);
		pr.getSkus().add("3"); 
		pr.getSkus().add("5");
		pr.getSkus().add("89");
		ArrayList<String> check = new ArrayList<>();
		check.add("3");
		check.add("5");
		check.add("89");
		assertEquals(pr.getSkus(), check);
		assertNotNull(pr.getSkus());
		
	}
	
	@Test
	public void skuLookup() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		ArrayList<Order> ord = new ArrayList<>();
		PickingRequest pr = new PickingRequest(ord);
		Fascia f1 = new Fascia("Blue", "SES");
		Fascia f2 = new Fascia("Blue", "SE");
		ord.add(f1);
		ord.add(f2);
		pr.skuLookup();
		ArrayList<String> check = new ArrayList<>();
		check.add("37");
		check.add("38");
		check.add("35");
		check.add("36");
		assertEquals(pr.getSkus(), check);
		
	}
	
	@Test 
	public void testaddPickedSkus() {
		ArrayList<Order> orders = new ArrayList<>();
		PickingRequest preq = new PickingRequest(orders);
		ArrayList<String> sku = new ArrayList<>();
		sku.add("37");
		sku.add("abc");
		sku.add("1234");
		preq.addPickedSkus(sku);
		assertEquals(preq.getPickedSkus(), sku);
	}
	
	@Test 
	public void getItem() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Fascia f1 = new Fascia("Blue", "SES");
		Fascia f2 = new Fascia("Blue", "SE");
		Fascia f3 = new Fascia("Blue", "S");
		Fascia f4 = new Fascia("Red", "SES");
		ArrayList<Order> ord = new ArrayList<>();
		ord.add(f1);
		ord.add(f2);
		ord.add(f3);
		ord.add(f4);
		PickingRequest pr = new PickingRequest(ord);
		ArrayList<String> str = new ArrayList<String>(Arrays.asList(Warehouse.getTraversalMap().get("37").split(",")));
		assertEquals(pr.getLocation(0), str);
		
	} 
	
	@Test
	public void testgetOrders() {
		ArrayList<Order> orders = new ArrayList<>();
		PickingRequest pr1 = new PickingRequest(orders);
		assertEquals(pr1.getOrders(), orders);
	}
	
	@Test
	public void testtoString() {
		ArrayList<Order> orders = new ArrayList<>();
		PickingRequest pr1 = new PickingRequest(orders);
		assertEquals(pr1.toString(), "Picking ID: " + pr1.getPickingId());
	}
	@Test 
	public void compareTo() {
		ArrayList<Order> orders = new ArrayList<>();
		PickingRequest pr1 = new PickingRequest(orders);
		PickingRequest pr2 = new PickingRequest(orders);
		assertEquals(-1, pr1.compareTo(pr2));
	}
}
