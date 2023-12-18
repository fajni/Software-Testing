package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import tires.CarTire;
import tires.TireShop;

@RunWith(Parameterized.class)
public class TireShopTest {

	@BeforeClass
	public static void checkOS() {
		Assume.assumeTrue(System.getProperty("os.name").contains("Windows"));
	}

	@Rule
	public final TestName name = new TestName();

	@Rule
	public final TestRule timeout = Timeout.seconds(5);

	@Rule
	public final ErrorCollector ec = new ErrorCollector();

	// ============================================================================

	CarTire c;
	TireShop s;

	@Before
	public void init() {
		c = new CarTire("Michelin", true, 17, 200, 70);
		s = new TireShop();
	}

	@After
	public void destroy() {
		c = null;
		s = null;
	}
	
	public TireShopTest(CarTire c) {
		super();
		this.c=c;
	}
	
	@Parameters
	public static Collection<Object[]> tires(){
		return Arrays.asList(new Object[][] {
			{new CarTire("Michelin", true, 17, 200, 70)},
			{new CarTire("Michelin", true, 17, 200, 70)},
			{new CarTire("Hankook", false, 20, 310, 81)},
			{new CarTire("Pirelli", false, 15, 210, 51)}
		});
	}
	
	// ============================================================================
	
	@Test(expected = NullPointerException.class)
	public void addCarTireTestException1() {
		s.addCarTire(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void addCarTireTestException2() {
		s.addCarTire(c);
		s.addCarTire(c);
	}
	
	@Test
	public void findCarTireTest1() {
		assertNull(s.findCarTire(null));
	}
	
	@Test
	public void findCarTireTest2() {
		assertFalse(s.tires.contains(c));
		LinkedList<CarTire> list = new LinkedList<>();
		list.add(c);
		s.tires.add(c);
		assertNotEquals(list, s.findCarTire("Non existing tire!"));
	}
	
	@Test
	public void findCarTireTest3() {
		assertFalse(s.tires.contains(c));
		LinkedList<CarTire> list = new LinkedList<>();
		list.add(c);
		s.tires.add(c);
		assertEquals(list, s.findCarTire(c.getModelBrand()));
	}

}
