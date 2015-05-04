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
	
	public static void logByteArray(byte[] bytes){
		if(log){
			for(byte b : bytes)
			System.out.print(" " + b);
		}
		System.out.println();
	}
}
