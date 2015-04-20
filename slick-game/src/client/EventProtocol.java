package client;

/**
 * Protocol for events. To be updated!
 * 
 * @author timdolck
 *
 */
public abstract class EventProtocol {
	public static final byte died = 0x0;
	public static final byte hurtCreep = 0x1;
	public static final byte killedCreep = 0x2;
	public static final byte sentCreeps = 0x3;
	public static final byte upgraded = 0x4;
}
