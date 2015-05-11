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
	public static final byte PLAYER_LOST_CONNECTION = 0x8;
	public static final byte REMOVE_LOCAL = 0x9;
	public static final byte PLAYER_SCORE = 0xA;

	public static final byte CREEP_HP = 0x10;
	public static final byte CREEP_DIED = 0x11;
	public static final byte CREEP_SENT = 0x12;
	public static final byte CREEP_INIT = 0x13;
	public static final byte CREEP_ID = 0x14;
	public static final byte CREEP_POS = 0x15;
	
	public static final byte BULLET_INIT = 0x30;
	public static final byte BULLET_ID = 0x31;
	public static final byte BULLET_POS = 0x32;
	public static final byte BULLET_DIED = 0x33;
}
