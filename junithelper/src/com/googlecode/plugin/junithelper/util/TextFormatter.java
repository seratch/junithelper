package com.googlecode.plugin.junithelper.util;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.plugin.junithelper.IConstants;

public class TextFormatter
{

	Map<String, String> specialCharsMap = new HashMap<String, String>();

	public TextFormatter()
	{
		init();
	}

	public void init()
	{

		// http://e-words.jp/p/r-htmlentity.html
		specialCharsMap.put("&lt;", "<");
		specialCharsMap.put("&gt;", ">");
		specialCharsMap.put("&quot;", "\"");
		specialCharsMap.put("&nbsp;", " ");
		specialCharsMap.put("&amp;", "&");
		specialCharsMap.put("&euro;", "€");
		specialCharsMap.put("&permil;", "‰");
		specialCharsMap.put("&trade;", "™");
		specialCharsMap.put("&iexcl;", "¡");
		specialCharsMap.put("&cent;", "¢");
		specialCharsMap.put("&pound;", "£");
		specialCharsMap.put("&curren;", "¤");
		specialCharsMap.put("&yen;", "¥");
		specialCharsMap.put("&brvbar;", "¦");
		specialCharsMap.put("&sect;", "§");
		specialCharsMap.put("&uml;", "¨");
		specialCharsMap.put("&copy;", "©");
		specialCharsMap.put("&ordf;", "ª");
		specialCharsMap.put("&reg;", "®");
		specialCharsMap.put("&micro;", "µ");
		specialCharsMap.put("&para;", "¶");
		specialCharsMap.put("&frac14;", "¼");
		specialCharsMap.put("&frac12;", "½");
		specialCharsMap.put("&frac34;", "¾");
		specialCharsMap.put("&iquest;", "¿");
		specialCharsMap.put("&laquo;", "«");
		specialCharsMap.put("&raquo;", "»");
		specialCharsMap.put("&spades;", "♠");
		specialCharsMap.put("&clubs;", "♣");
		specialCharsMap.put("&hearts;", "♥");
		specialCharsMap.put("&diams;", "♦");

	}

	public String replaceSpecialChars(String argStr)
	{
		for (String key : specialCharsMap.keySet())
		{
			argStr = argStr.replaceAll(key, specialCharsMap.get(key));
		}
		return argStr;
	}

	public String replaceAllTags(String argStr)
	{
		return argStr.replaceAll("<.+?>", IConstants.EMPTY_STIRNG);
	}

}
