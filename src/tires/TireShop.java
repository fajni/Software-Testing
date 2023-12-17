package tires;

import java.util.LinkedList;

public class TireShop {

	public LinkedList<CarTire> tires = new LinkedList<CarTire>();

	public void addCarTire(CarTire tire) {
		if (tire == null)
			throw new NullPointerException("Values for tire can't be null");
		if (tires.contains(tire))
			throw new RuntimeException("Tire already exist!");
		tires.add(tire);
	}

	public LinkedList<CarTire> findCarTire(String modelBrand) {
		if (modelBrand == null)
			return null;
		LinkedList<CarTire> newList = new LinkedList<>();
		for (int i = 0; i < tires.size(); i++)
			if (tires.get(i).getModelBrand().equals(modelBrand))
				newList.add(tires.get(i));
		return newList;
	}
}
