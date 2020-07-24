package com.jwordart.model.wordart;

import com.jwordart.model.wordart.mappingType.WordArtMappingType;

public class WordArtMappingTypeAndStyle {
	private WordArtMappingType mappingType;
	private WordArtStyle style;

	public WordArtMappingTypeAndStyle(WordArtMappingType type,
			WordArtStyle style) {
		this.mappingType = type;
		this.style = style;
	}

	public static WordArtMappingTypeAndStyle getWordArtMappingTypeAndStyle(
			int type) {
		int mappingType;
		switch (type) {
		case 1:
			mappingType = 0;
			break;
		case 2:
			mappingType = 36;
			break;
		case 3:
			mappingType = 8;
			break;
		case 4:
			mappingType = 25;
			break;
		case 5:
			mappingType = 19;
			break;
		case 12:
			mappingType = 36;
			break;
		case 15:
			mappingType = 20;
			break;
		case 17:
			mappingType = 2;
			break;
		case 18:
			mappingType = 34;
			break;
		case 19:
			mappingType = 22;
			break;
		case 20:
			mappingType = 16;
			break;
		case 22:
			mappingType = 38;
			break;
		case 23:
			mappingType = 27;
			break;
		case 24:
			mappingType = 38;
			break;
		default:
			mappingType = 0;
			break;
		}

		return new WordArtMappingTypeAndStyle(WordArtMappingType
				.getWordArtMappingType(mappingType), WordArtStyleFactory
				.getWordArtStyle(type));
	}

	public WordArtMappingType getWordArtMappingType() {
		return this.mappingType;
	}

	public WordArtStyle getWordArtStyle() {
		return this.style;
	}
}