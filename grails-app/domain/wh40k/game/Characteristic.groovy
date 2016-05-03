package wh40k.game

import wh40k.enums.CharacteristicType;

class Characteristic {

    static constraints = {
    }
	
	static belongsTo = [playingCharacter : PlayingCharacter]
	
	
	
	CharacteristicType type
	
	
	//characteristic value, goes from 0 to 99
	int stat

	//characteristic modifier, can be negative
	int modifier = 0
	
}
