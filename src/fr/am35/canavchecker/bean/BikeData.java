package fr.am35.canavchecker.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BikeData {

	private String name;

	private int price;

	private Map<String, List<String>> sizesAvailable = new HashMap<String, List<String>>();

	private Map<String, List<String>> sizesUnavailable = new HashMap<String, List<String>>();

	public String toString() {
		final StringBuilder stb = new StringBuilder("");
		stb.append("Name : " + this.getName() + "\n");
		stb.append("Price : " + this.getPrice() + "\n");
		if (0 < this.getSizesAvailable().size()) {
			stb.append("Available :\n");
			final Iterator<Map.Entry<String, List<String>>> itAv = this.getSizesAvailable().entrySet().iterator();
			while (itAv.hasNext()) {
				Map.Entry<String, List<String>> pair = (Map.Entry<String, List<String>>) itAv.next();
				stb.append("    " + pair.getKey() + " : " + Arrays.toString(pair.getValue().toArray()) + "\n");
			}
		}

		if (0 < this.getSizesUnavailable().size()) {
			stb.append("Unavailable :\n");
			final Iterator<Map.Entry<String, List<String>>> itAv = this.getSizesUnavailable().entrySet().iterator();
			while (itAv.hasNext()) {
				Map.Entry<String, List<String>> pair = (Map.Entry<String, List<String>>) itAv.next();
				stb.append("    " + pair.getKey() + " : " + Arrays.toString(pair.getValue().toArray()) + "\n");
			}
		}

		return stb.toString();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the sizesAvailable
	 */
	public Map<String, List<String>> getSizesAvailable() {
		return sizesAvailable;
	}

	/**
	 * @param sizesAvailable
	 *            the sizesAvailable to set
	 */
	public void setSizesAvailable(Map<String, List<String>> sizesAvailable) {
		this.sizesAvailable = sizesAvailable;
	}

	/**
	 * @return the sizesUnavailable
	 */
	public Map<String, List<String>> getSizesUnavailable() {
		return sizesUnavailable;
	}

	/**
	 * @param sizesUnavailable
	 *            the sizesUnavailable to set
	 */
	public void setSizesUnavailable(Map<String, List<String>> sizesUnavailable) {
		this.sizesUnavailable = sizesUnavailable;
	}

}
