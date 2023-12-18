package tires;

public class CarTire {

	private String modelBrand = null;
	private boolean winterTire = false;
	private int diameter = 0;
	private int width = 0;
	private int height = 0;

	/*
	 * public CarTire() {
	 * 
	 * }
	 */

	public CarTire(String modelBrand, boolean winterTire, int diameter, int width, int height) {
		super();
		this.modelBrand = modelBrand;
		this.winterTire = winterTire;
		this.diameter = diameter;
		this.width = width;
		this.height = height;
	}

	public boolean getWinterTire() {
		return winterTire;
	}

	public void setWinterTire(boolean winter) {
		this.winterTire = winter;
	}

	public String getModelBrand() {
		return modelBrand;
	}

	public void setModelBrand(String modelBrand) {
		if (modelBrand == null || modelBrand.length() < 3)
			throw new RuntimeException("You must enter Model and Brand for tire!");
		this.modelBrand = modelBrand;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		if (diameter < 13 || diameter > 25)
			throw new RuntimeException("Diameter out of range!");
		this.diameter = diameter;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width < 135 || width > 355)
			throw new RuntimeException("Width out of range!");
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height < 25 || height > 95)
			throw new RuntimeException("Height out of range!");
		this.height = height;
	}

	public double calculatePrice() {
		return (this.diameter * 3 + this.width + this.height) * 0.81;
	}

	public boolean affordableTire() {
		if (this.calculatePrice() <= 310)
			return true;
		// else
		return false;
	}

	@Override
	public String toString() {
		return "Car Tire [Model Brand: " + modelBrand + 
				", Diameter: " + diameter + 
				", Width: " + width + 
				", Height: "+ height + 
				", Winter: "+winterTire+"]";
	}
}
