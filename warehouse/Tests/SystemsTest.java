package warehouse;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

public class SystemsTest {
	
	@Test
	public void testgetWarehouse() {
		Systems s = new Systems();
		s.getWarehouse();
		assertEquals(s.getWarehouse().toString(), s.getWarehouse().getwMap().toString());
	}

	@Test
	public void testaddOrder() {
		Systems s = new Systems();
		Order o1 = new Fascia("Blue", "SES");
		s.addOrder(o1);
		assertTrue(s.getOrderQueue().size() == 1);
		Order o2 = new Fascia("Blue", "SES");
		s.addOrder(o2);
		Order o3 = new Fascia("Blue", "SES");
		s.addOrder(o3);
		Order o4 = new Fascia("Blue", "SES");
		assertTrue(s.getOrderQueue().size() == 3);
		s.addOrder(o4);
		
		assertTrue(s.getPrQueue().size() == 1);
		
	}
	
	@Test
	public void testcreatePickingRequest() {
		Systems s = new Systems();
		Order o1 = new Fascia("Blue", "SES");
		s.addOrder(o1);
		Order o2 = new Fascia("Blue", "SES");
		s.addOrder(o2);
		Order o3 = new Fascia("Blue", "SES");
		s.addOrder(o3);
		int x = s.getOrderQueue().size();
		assertEquals(x, 3);
		Order o4 = new Fascia("Blue", "SES");
		s.addOrder(o4);
		assertTrue(s.getPrQueue().size() == 1);
		
	}
	
	@Test
	public void testgetWorker() {
		Systems s = new Systems();
		Worker map = s.getWorker("Alice", "Picker");
		assertEquals(map, s.getWorkerMap().get("Alice"));
	}
	
	@Test
	public void testgivePickerPr() {
		Systems s = new Systems();
		ArrayList<Order> pr = new ArrayList<>();
		Order o1 = new Fascia("Blue", "SES");
		s.addOrder(o1);
		pr.add(o1);
		Order o2 = new Fascia("Blue", "SES");
		s.addOrder(o2);
		pr.add(o2);
		Order o3 = new Fascia("Blue", "SES");
		s.addOrder(o3);
		pr.add(o3);
		Order o4 = new Fascia("Blue", "SES");
		s.addOrder(o4);
		pr.add(o4);
		Picker p = new Picker("Alice"); 
		Picker p2 = new Picker("Bob");
		s.addPicker(p);
		s.addPicker(p2); 
		assertTrue(s.getPrQueue().size() == 0);
		assertTrue(s.getAvailPickers().size() == 1);
		assertEquals(p.getPr().getOrders(), pr);
		
	}
	
	@Test
	public void testaddPicker() {
		Systems s = new Systems();
		s.getWorker("Alice", "Picker");
		Picker p = new Picker("Alice");
		s.addPicker(p);
		assertTrue(s.getAvailPickers().size() == 1);
		Picker p2 = new Picker("Alex");
		s.addPicker(p2);
		assertTrue(s.getAvailPickers().size() == 2);
		
	}
	@Test
	public void testaddSequencer() {
		Systems s = new Systems();
		s.getWorker("Sue", "Sequencer");
		Sequencer seq = new Sequencer("Sue");
		s.addSequencer(seq);
		assertTrue(s.getAvailSequencers().size() == 1);
		Sequencer p2 = new Sequencer("Sam");
		s.addSequencer(p2);
		assertTrue(s.getAvailSequencers().size() == 2);
		
	}
	
	@Test
	public void testaddLoader() {
		Systems s = new Systems();
		s.getWorker("Bill", "Loader");
		Loader l = new Loader("Bill");
		s.addLoader(l);
		assertTrue(s.getAvailLoaders().size() == 1);
		Loader p2 = new Loader("Bob");
		s.addLoader(p2);
		assertTrue(s.getAvailLoaders().size() == 2);
		
	}

	
	@Test
	public void testpickerPick() {
		Systems s = new Systems();
		Picker p = new Picker("Alice");
		s.pickerPick(p, "37");
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		assertTrue(p.getPr() == null);
		p.changeCurrItem("+");
		p.changeCurrItem("+");
		p.changeCurrItem("+");
		p.changeCurrItem("+");
		p.changeCurrItem("+");
		p.changeCurrItem("+");
		p.changeCurrItem("+");
		p.changeCurrItem("+");
		assertFalse(p.isNextItem());
		Picker p2 = new Picker("Alex");
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
		p2.setPr(pr);
		s.pickerPick(p2, "37");
		assertTrue(p2.getPickedSkus().contains("37"));
		//assertEquals(s.warehouse.getTraversalMap().get("B,1,0,0"), 6);
		//  
		
	}
	
	@Test 
	public void testworkerScanItem() {
		// case getPr is null
		Systems s = new Systems();
		Worker p = new Picker("A");
		s.workerScanItem(p);
		assertTrue(p.getPr() == null);
		// case isNextItem is false
		Picker p2 = new Picker("Alice");
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
		p2.setPr(pr); 
		p2.changeCurrItem("+");
		p2.changeCurrItem("+");
		p2.changeCurrItem("+");
		p2.changeCurrItem("+");
		p2.changeCurrItem("+");
		p2.changeCurrItem("+");
		p2.changeCurrItem("+");
		p2.changeCurrItem("+");
		s.workerScanItem(p2);
		assertFalse(p2.isNextItem());	
		// case other
		Picker p3 = new Picker("Alice");
		p3.pickItem("37");
		p3.pickItem("38");
		p3.pickItem("35");
		p3.pickItem("36");
		p3.pickItem("33");
		p3.pickItem("34");
		p3.pickItem("45");
		p3.pickItem("46");
		Fascia f8 = new Fascia("Blue", "SES");
		Fascia f9 = new Fascia("Blue", "SE");
		Fascia f10 = new Fascia("Blue", "S");
		Fascia f11 = new Fascia("Black", "SES");
		ArrayList<Order> orders2 =  new ArrayList<>();
		orders.add(f8);
		orders.add(f9);
		orders.add(f10);
		orders.add(f11);
		PickingRequest pr2 = new PickingRequest(orders2);
		p3.setPr(pr2); 
		
		s.workerScanItem(p3);
		assertTrue(p3.isNextItem());
		assertEquals(s.getPrQueue(), pr2);
		
	}
	
	@Test
	public void testpickerPutBack() {
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Systems s = new Systems();
		Picker p = new Picker("Alice");
		p.pickItem("37");
		
		p.pickItem("38");
		p.pickItem("35");
		p.pickItem("36");
		
		s.pickerPutBack(p);
		String skuLoc = Warehouse.getTraversalMap().get("37");
		int amount = warehouse.getwMap().get(skuLoc);
		assertEquals(amount, 7);
		
	}
	
	@Test
	public void testpickerMarshalling() {
		Systems s = new Systems();
		Picker p = new Picker("Alice");
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
		p.setPr(pr);
		s.pickerMarshalling(p);
		assertTrue(s.getSequencingQueue().size() == 1);
		assertEquals(pr.getPickedSkus(), p.getPickedSkus());
	} 
	
	@Test
	public void testgiveSequencerPr() {
		Systems s = new Systems();
		Sequencer sue = new Sequencer("Sue");
		s.addSequencer(sue);
		Picker picker = new Picker("A");
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
		picker.setPr(pr);
		s.pickerMarshalling(picker);
		assertEquals(s.getSequencingQueue().size(), 0 );
		assertEquals(s.getAvailSequencers().size(), 0);
	}
	
	@Test
	public void testgiveLoaderPr() {
		Systems s = new Systems();
		Sequencer sue = new Sequencer("Sue");
		s.addSequencer(sue);
		Loader loader = new Loader("L");
		s.addLoader(loader);
		Picker picker = new Picker("A");
		s.addPicker(picker);
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
		s.getLoadingQueue().add(pallets);
		
		s.giveLoaderPr();
		//System.out.println(Loader.getLastLoaded());
		//System.out.println(pallets.getPr().getPickingId()-1);
		assertEquals(s.getLoadingQueue().size(), 0);
		assertEquals(s.getAvailLoaders().size(), 0);
	}
	 
	@Test
	public void testrescan() {
		Systems s = new Systems();
		Sequencer sue = new Sequencer("Sue");
		s.rescan(sue);
		assertEquals(sue.getCurrItem(), 0);
		Picker picker = new Picker("Sue");
		s.rescan(picker);
		assertEquals(picker.getCurrItem(), 0);
		Loader loader = new Loader("Sue");
		s.rescan(loader);
		assertEquals(loader.getCurrItem(), 0);
	}
	
	@Test
	public void testsequencerToLoading() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Systems s = new Systems();
		Sequencer sue = new Sequencer("Sue");
		s.addSequencer(sue);
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
		ArrayList<String> skus = new ArrayList<>();
		skus.add("37");
		skus.add("38");
		skus.add("35");
		skus.add("36");
		skus.add("33");
		skus.add("34");
		skus.add("45");
		skus.add("46");
		pr.addPickedSkus(skus);
		sue.setPr(pr);
		sue.changeCurrItem("+");
		sue.changeCurrItem("+");
		sue.changeCurrItem("+");
		sue.changeCurrItem("+");
		sue.changeCurrItem("+");
		sue.changeCurrItem("+");
		sue.changeCurrItem("+");
		sue.changeCurrItem("+");
		s.sequencerToLoading(sue);
		assertEquals(s.getLoadingQueue().size(), 1);
		
	} 
	
	@Test
	public void testload() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Systems s = new Systems();
		Loader loader = new Loader("Bob");
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
		loader.setPr(pr);
		loader.changeCurrItem("+");
		loader.changeCurrItem("+");
		loader.changeCurrItem("+");
		loader.changeCurrItem("+");
		loader.changeCurrItem("+");
		loader.changeCurrItem("+");
		loader.changeCurrItem("+");
		loader.changeCurrItem("+");
		Loader.setLastLoaded();
		s.load(loader); 
		assertEquals(s.getLoaded(), orders); 
	}
	
	@Test
	public void testfinalOrdersReport() {
		Systems s = new Systems();
		s.finalOrdersReport();
		File f = new File("./project/orders.csv");
		assertTrue(f.exists() && !f.isDirectory());
		
	}

}
