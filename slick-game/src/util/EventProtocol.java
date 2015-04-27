package util;

/**
 * Protocol for events. To be updated!
 * 
 * @author timdolck
 *
 */
public abstract class EventProtocol {
	public static final byte LOCAL_PLAYER_INIT = 0x0;
	public static final byte PLAYER_POS = 0x1;
	public static final byte PLAYER_DIR = 0x2;
	public static final byte PLAYER_HP = 0x3;
	public static final byte PLAYER_DIED = 0x4;
	public static final byte PLAYER_UPGRADED = 0x5;
	public static final byte PLAYER_ID = 0x6;
	public static final byte OPPONENT_PLAYER_INIT = 0x7;

	public static final byte CREEP_HP = 0x10;
	public static final byte CREEP_DIED = 0x11;
	public static final byte CREEP_SENT = 0x12;
	public static final byte CREEP_INIT = 0x13;

	// Must be set in last function before being sent to server/client meaning
	// "putArrayToServer" client side and "sendMessage" server side.
	public static final byte MESSAGE_LENGTH = 0x20;
}
