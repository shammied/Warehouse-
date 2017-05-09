package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class SequencerTest {

	@Test
	public void testsetPr() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Sequencer seq = new Sequencer("Sue");
		Picker p = new Picker("A");
		p.pickItem("37");
		p.pickItem("38");
		p.pickItem("35");
		p.pickItem("36");
		
        Fascia f1 = new Fascia("Blue", "SES");
		Fascia f2 = new Fascia("Red", "SE");
		Fascia f3 = new Fascia("Red", "S");
		ArrayList<Order> o = new ArrayList<>();
		o.add(f1);
		o.add(f2);
		o.add(f3);
		PickingRequest pr = new PickingRequest(o);
		pr.addPickedSkus(p.getPickedSkus());
		seq.setPr(pr); 
		assertEquals(seq.getPicked(), pr.getPickedSkus());
		assertEquals(seq.getOrderSkus(), pr.getSkus()); 
	}
	
	@Test
	public void testscanItem() {
		@SuppressWarnings("unused")
		Warehouse warehouse = new Warehouse(2, 3, 3, 4);
		Sequencer seq = new Sequencer("Sue");
		Picker p = new Picker("A");
		p.pickItem("37");
		p.pickItem("38");
		p.pickItem("35");
		p.pickItem("36");
		
        Fascia f1 = new Fascia("Blue", "SES");
		Fascia f2 = new Fascia("Blue", "SE");
		Fascia f3 = new Fascia("Red", "SES");
		ArrayList<Order> o = new ArrayList<>();
		o.add(f1);
		o.add(f2);
		
		PickingRequest pr = new PickingRequest(o);
		pr.addPickedSkus(p.getPickedSkus());
		seq.setPr(pr);
		assertTrue(seq.scanItem()); 
		o.add(f3);
		PickingRequest pr2 = new PickingRequest(o);
		pr2.addPickedSkus(p.getPickedSkus());
		seq.setPr(pr2);
		
		assertFalse(seq.scanItem()); 
	}

	

	@Test
	public void testfinishRequest() {
		Sequencer seq = new Sequencer("Sue");
		seq.finishRequest();
		System.out.println(seq.getOrderSkus());
		assertTrue(seq.getOrderSkus() == null);
		assertTrue(seq.getPicked() == null);
	}
 

}
