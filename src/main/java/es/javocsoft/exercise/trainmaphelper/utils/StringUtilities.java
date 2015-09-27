package es.javocsoft.exercise.trainmaphelper.utils;

import java.text.Normalizer;

/**
 * String utilities related auxiliar methods.
 * 
 * @author Javier Gonzalez Serrano
 * @since Dec, 2014
 * @version 1.0.0
 *
 */
public class StringUtilities {

	
	private StringUtilities() { }
	
	
	/**
	 * Normalizes a text:
	 * 
	 * 	- Removing all non character/numbers except the comma
	 * 	- Getting the non accented versions of the letters.
	 * 
	 * @param text
	 * @return	The normalized text.
	 */
	public static String normalize(String text) {
		//This regular expression matches all (Unicode) characters 
		//that are neither letters nor (decimal) digits
		String result = text.replaceAll("[^\\p{L}\\p{Nd}\\,]+", "");
		
		//Now we normalize the text according to NFD.
		//This will do a "Canonical decomposition":
		//	This will separate all of the accent marks from the characters. 
		//	Then, you just need to compare each character against being a 
		//	letter and throw out the ones that aren't :)
		//See
		//	https://docs.oracle.com/javase/tutorial/i18n/text/normalizerapi.html
		result = Normalizer.normalize(result, Normalizer.Form.NFD);
		//	For unicode, \\P{M} matches the base glyph and
		//  \\p{M} (lowercase) matches each accent.
		result = result.replaceAll("\\p{M}", "").toUpperCase();
		
		//Now the line is totally clean :D
		//System.out.println("Normalized text: " + result + "\n");
		
		return result;
	}
	
}
