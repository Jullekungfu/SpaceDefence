package util;

import org.newdawn.slick.Color;

public class ColorSwitch {
	
	public static Color getColorFromId(byte id){
		switch(id){
			case 0x1: 
				return Color.red;
			case 0x2:
				return Color.green;
			case 0x3:
				return Color.cyan;
			case 0x4:
				return Color.yellow;
			default:
				return Color.white;
		}
	}

}
