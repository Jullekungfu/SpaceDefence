/**
 * 
 */
package util;

/**
 * @author nille
 *
 */
public abstract class Logger {
	
	private static boolean log = true;
	
	public static void log(String msg){
		if(log){
			System.out.println(msg);
		}
	}

}
