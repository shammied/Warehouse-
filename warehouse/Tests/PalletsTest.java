package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PalletsTest {

	@Test
	public void testgetFront() {
		@SuppressWarnings("unused")
		Warehouse w = new Warehouse(2, 3, 3, 4);
		Fascia f1 = new Fascia("Blue","SES");
		Fascia f2 = new Fascia("White","SES");
		Fascia f3 = new Fascia("White","SE");
		Fascia f4 = new Fascia("White","S");
		ArrayList<Order> orders = new ArrayList<>();
		orders.add(f1);
		orders.add(f2);
		orders.add(f3);
		orders.add(f4);
		PickingRequest pr = new PickingRequest(orders);
		Pallets p = new Pallets(pr);
		ArrayList<String> front = new ArrayList<>();
		front.add("37");
		front.add("5");
		front.add("3");
		front.add("1");
		assertEquals(p.getFront(), front);
	}
	
	@Test
	public void testgetBack() {
		@SuppressWarnings("unused")
		Warehouse w = new Warehouse(2, 3, 3, 4);
		Fascia f1 = new Fascia("Blue","SES");
		Fascia f2 = new Fascia("White","SES");
		Fascia f3 = new Fascia("White","SE");
		Fascia f4 = new Fascia("White","S");
		ArrayList<Order> orders = new ArrayList<>();
		orders.add(f1);
		orders.add(f2);
		orders.add(f3);
		orders.add(f4);
		PickingRequest pr = new PickingRequest(orders);
		Pallets p = new Pallets(pr);
		ArrayList<String> back = new ArrayList<>();
		back.add("38");
		back.add("6");
		back.add("4");
		back.add("2");
		assertEquals(p.getBack(), back);
	}
	
	@Test
	public void testPr() {
		@SuppressWarnings("unused")
		Warehouse w = new Warehouse(2, 3, 3, 4);
		Fascia f1 = new Fascia("Blue","SES");
		Fascia f2 = new Fascia("White","SES");
		Fascia f3 = new Fascia("White","SE");
		Fascia f4 = new Fascia("White","S");
		ArrayList<Order> orders = new ArrayList<>();
		orders.add(f1);
		orders.add(f2);
		orders.add(f3);
		orders.add(f4);
		PickingRequest pr = new PickingRequest(orders);
		Pallets p = new Pallets(pr);
		assertEquals(p.getPr(), pr);
	}
	
	@Test
	public void testcompareTo() {
		@SuppressWarnings("unused")
		Warehouse w = new Warehouse(2, 3, 3, 4);
		Fascia f1 = new Fascia("Blue","SES");
		Fascia f2 = new Fascia("White","SES");
		Fascia f3 = new Fascia("White","SE");
		Fascia f4 = new Fascia("White","S");
		ArrayList<Order> orders = new ArrayList<>();
		orders.add(f1);
		orders.add(f2);
		orders.add(f3);
		orders.add(f4);
		PickingRequest pr = new PickingRequest(orders);
		Pallets p = new Pallets(pr);
		Fascia f5 = new Fascia("Blue","SES");
		Fascia f6 = new Fascia("White","SES");
		Fascia f7 = new Fascia("White","SE");
		Fascia f8 = new Fascia("White","S");
		ArrayList<Order> orders2 = new ArrayList<>();
		orders2.add(f5);
		orders2.add(f6);
		orders2.add(f7);
		orders2.add(f8);
		PickingRequest pr2 = new PickingRequest(orders);
		Pallets p2 = new Pallets(pr2);
		assertEquals(p.compareTo(p2), -1);
	}

}
