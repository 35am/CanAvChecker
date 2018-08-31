package fr.am35.canavchecker.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.am35.canavchecker.bean.BikeData;

/**
 * 
 * Check the availability of a Canyon bike given his URL, color and size. <br />
 * 
 * <b>Params :</b> URL Color (replace spaces by _) Size <br />
 * <b>Example :</b> java CanAvCheck https://www.canyon.com/fr/triathlon/speedmax/2018/speedmax-cf-slx-9-0-ltd.html comet_blue XS
 */
public class CanAvCheckApp {

	public static void main(final String[] args) {
		final String url = args[0];
		final String color = args[1].replaceAll("_", " ");
		final String size = args[2];
		final BikeData data = getBikeData(url);

		if (null != data) {
			if (data.getSizesAvailable().containsKey(color) && data.getSizesAvailable().get(color).contains(size)) {
				System.out.println("Sweet, the " + data.getName() + " is available in " + color + " size " + size + " !");
			} else {
				System.out.println("Sorry, the " + data.getName() + " is not available in " + color + " size " + size + "...");
			}
			System.out.println("--------------------");
			System.out.println(data.toString());
		} else {
			System.out.println("No data found, sorry !");
		}

	}

	private static BikeData getBikeData(final String url) {
		BikeData result = null;
		try {
			// Get doc
			final Document doc = Jsoup.connect(url).get();

			// Select modal with availability from the page
			final Element modalAv = doc.select("#availability-layer").get(0);

			// Can init result
			result = new BikeData();

			// Select html with bike name
			final String nameHtml = modalAv.select("#bike-name").get(0).html();
			// TODO : Regex
			result.setName(nameHtml.substring(0, nameHtml.indexOf("<")).trim());

			// Select html with price
			final String price = modalAv.select(".bike-price").get(0).html();
			// TODO : Regex
			result.setPrice(Integer.parseInt(price.substring(0, price.indexOf("&")).replace(".", "")));

			// Select rows of bike availability
			final Elements bikeColors = modalAv.select(".bike-row");

			// For each bike color...
			for (final Element bikeColor : bikeColors) {

				// Select html with color name
				final String colorName = bikeColor.select(".hidden-xs").get(0).html();

				// Select html of available sizes
				final Elements availableHtml = bikeColor.select("a");
				final List<String> availables = availableHtml.size() > 0 ? new ArrayList<String>() : null;
				// Select sizes name
				for (int idAv = 0; idAv < availableHtml.size(); idAv++) {
					availables.add(availableHtml.get(idAv).html().substring(0, availableHtml.get(idAv).html().indexOf(" ")));
				}
				// Put it in the result only if we found available sizes
				if (null != availables) {
					result.getSizesAvailable().put(colorName, availables);
				}

				// Select html of unavailable sizes
				final Elements unavailableHtml = bikeColor.select("span.button");
				final List<String> unavailables = unavailableHtml.size() > 0 ? new ArrayList<String>() : null;
				// Select sizes List
				for (int idUnav = 0; idUnav < unavailableHtml.size(); idUnav++) {
					unavailables.add(unavailableHtml.get(idUnav).html().substring(0, unavailableHtml.get(idUnav).html().indexOf(" ")));
				}
				// Put it in the result only if we found unavailable sizes
				if (null != unavailables) {
					result.getSizesUnavailable().put(colorName, unavailables);
				}

			}
		} catch (final IOException ioe) {
			System.out.println("Error while getting page : " + ioe.getMessage());
		}

		return result;

	}
}
