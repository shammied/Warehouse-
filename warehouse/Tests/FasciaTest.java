package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class FasciaTest {

	@Test
	public void skuLookuptest() {
	    @SuppressWarnings("unused")
        Warehouse warehouse = new Warehouse(2, 2, 3, 4);
		Fascia f1 = new Fascia("Blue", "SES");
		ArrayList<String> list1 = new ArrayList<>();
		list1.add(Integer.toString(37));
		list1.add(Integer.toString(38));
		Fascia f2 = new Fascia("Red", "SE");
		ArrayList<String> list2 = new ArrayList<>();
		list2.add(Integer.toString(19));
		list2.add(Integer.toString(20));
		Fascia f3 = new Fascia("Orange", "S");
		ArrayList<String> list3 = new ArrayList<>();
		list3.add(Integer.toString(73));
		list3.add(Integer.toString(74));
		
		assertEquals(f1.skuLookup(), list1);
		assertEquals(f2.skuLookup(), list2);
		assertNull(f3.skuLookup());
		
	}
	
	@Test 
	public void testtoString() {
		@SuppressWarnings("unused")
        Warehouse warehouse = new Warehouse(2, 2, 3, 4);
		Fascia f1 = new Fascia("Blue", "SES");
		assertEquals(f1.toString(), "Blue SES");
	}

}
