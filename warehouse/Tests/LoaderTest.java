package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class LoaderTest {
	
	@Test
	public void testsetPallets() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Loader loader = new Loader("Bill");
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
		Pallets pallets = new Pallets(pr);
		loader.setPallets(pallets);
		assertEquals(loader.getPr(), pallets.getPr());
		assertEquals(loader.getOrderSkus(), loader.getPr().getSkus());
	}
	
	@Test
	public void testscanItem() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Loader loader = new Loader("Bill");
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
		Pallets pallets = new Pallets(pr);
		loader.setPallets(pallets);
		assertTrue(loader.scanItem());
		loader.changeCurrItem("+");
		assertTrue(loader.scanItem());  
		assertTrue(loader.getPalletIndex() == 1);
	}  
	
	@Test
	public void testfinishRequest() {
		Loader loader = new Loader("Bill");
		loader.finishRequest();
		assertTrue(loader.getFrontPallet() == null);
		assertTrue(loader.getBackPallet() == null);
		assertTrue(loader.getOrderSkus() == null);
		assertTrue(loader.getPalletIndex() == 0);
		
	}
	
	@Test
	public void testresetCurrItem() {
		Loader loader = new Loader("Bill");
		loader.resetCurrItem();
		assertTrue(loader.getPalletIndex() == 0);
	}

}
