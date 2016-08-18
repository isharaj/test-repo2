package com.lk.pearson.input.format.filters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlFilters {
	
	public String removeHyperLinksFromHtmlString(String htmlString) {

		Document doc = Jsoup.parse(htmlString);
		doc.select("a").remove();
		return doc.toString();
	}

}
