package com.project.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface that validates given text
 * @author kate
 *
 */

public interface Validator {
	
	public static boolean validateType(String type){
		Pattern p = Pattern.compile("([tT]ask|[Mm]eeting|TASK|MEETING)");
		Matcher m = p.matcher(type);
		return m.matches();
	}
	
	public static boolean validateMarker(String marker){
		Pattern p = Pattern.compile("([Pp]ublic|[Pp]rivate|[Cc]onfidential|PUBLIC|PRIVATE|CONFIDENTIAL)");
		Matcher m = p.matcher(marker);
		return m.matches();
	}
	
	public static boolean validateDate(String date){
		Pattern p = Pattern.compile("(((0[1-9]|1[0-9]|2[0-8])[.](0[1-9]|1[012])[.](20[0-9][0-9]))|((29|30|31)[.](0[13578]|1[02])[.](20[0-9][0-9]))|((29|30)[.](0[469]|11)[.](20[0-9][0-9]))|(29[.]02[.](20(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))))");
		Matcher m = p.matcher(date);
		return m.matches();
	}
	
	public static boolean validateTime(String time){
		Pattern p = Pattern.compile("(((0|1)[0-9]|2[0-3])[:]([0-5][0-9]))");
		Matcher m = p.matcher(time);
		return m.matches();
	}
}
