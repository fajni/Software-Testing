package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.*;

import tires.CarTire;

public class CarTireTest {

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

	@Before
	public void init() {
		c = new CarTire("Michelin", true, 17, 200, 70);
	}

	@After
	public void destroy() {
		c = null;
	}

	// ============================================================================
	
	@Test
	@Ignore
	public void getWinterTireTest() {
		assertEquals(true, c.getWinterTire());
	}

	@Test
	public void setWinterTireTest() {

		// check method name, (same goes with other methods, so I won't check it)
		try {
			assertEquals("setWinterTireTest", name.getMethodName());
		} catch (Throwable t) {
			ec.addError(t);
		}
		c.setWinterTire(false);
		assertFalse(c.getWinterTire());
	}

	@Test(expected = RuntimeException.class)
	public void setModelBrandTestException1() {
		c.setModelBrand(null);
	}

	@Test(expected = RuntimeException.class)
	public void setModelBrandTestException2() {
		c.setModelBrand("Mi");
	}

	@Test
	public void setModelBrandTest() {
		c.setModelBrand("Hankook");
		assertEquals("Hankook", c.getModelBrand());
	}

	// Annotation for exceptions
	@Test(expected = RuntimeException.class)
	public void setDiameterException1() {
		c.setDiameter(10);
	}

	@Test(expected = RuntimeException.class)
	public void setDiameterException2() {
		c.setDiameter(10);
	}

	@Test
	public void setDiameterTest() {
		c.setDiameter(24);
		assertEquals(24, c.getDiameter());
	}

	// JUnit rule for exception
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void setWidthTestException1() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Width out of range!");
		c.setWidth(100);
	}

	@Test
	public void setWidthTestException2() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Width out of range!");
		c.setWidth(400);
	}

	@Test
	public void setWidthTest() {
		c.setWidth(250);
		assertEquals(250, c.getWidth());
	}

	// Dynamically skipping the exception
	@Test
	public void setHeightTestException1() {
		try {
			c.setHeight(20);
		} catch (Throwable t) {
			Assume.assumeNoException(t);
		}
	}

	@Test
	public void setHeightTestException2() {
		try {
			c.setHeight(100);
		} catch (Throwable t) {
			Assume.assumeNoException(t);
		}
	}

	@Test
	public void setHeightTest() {
		c.setHeight(60);
		assertEquals(60, c.getHeight());
	}

	// ============================================================================

	@Test
	public void calculatePriceTest() {
		assertEquals(260.01, c.calculatePrice(), 0.01);
	}

	@Test
	public void affordableTireTest1() {
		assertTrue(c.affordableTire());
	}

	@Test
	public void affordableTireTest2() {
		c.setDiameter(24);
		c.setWidth(354);
		c.setHeight(94);
		assertFalse(c.affordableTire());
	}

	@Test
	public void toStringTest1() {
		assertEquals("Car Tire [Model Brand: "+c.getModelBrand()+
				", Diameter: "+c.getDiameter()+
				", Width: "+c.getWidth()+
				", Height: "+c.getHeight()+
				", Winter: "+c.getWinterTire()+"]", c.toString());
	}

	@Test
	public void toStringTest2() {
		assertEquals("Car Tire [Model Brand: Michelin, Diameter: 17, Width: 200, Height: 70, Winter: true]", c.toString());
	}
}
