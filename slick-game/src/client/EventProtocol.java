package client;

/**
 * Protocol for events. To be updated!
 * 
 * @author timdolck
 *
 */
public abstract class EventProtocol {
	public static final byte PLAYER_INIT = 0x0;
	public static final byte PLAYER_POS = 0x1;
	public static final byte PLAYER_DIR = 0x2;
	public static final byte PLAYER_HP = 0x3;
	public static final byte PLAYER_DIED = 0x4;
	public static final byte PLAYER_UPGRADED = 0x5;
	
	public static final byte CREEP_HP = 0x10;
	public static final byte CREEP_DIED = 0x11;
	public static final byte CREEP_SENT = 0x12;
	public static final byte CREEP_INIT = 0x13;
	
}
