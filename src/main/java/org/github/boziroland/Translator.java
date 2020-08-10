package org.github.boziroland;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public final class Translator {

	private static Translator instance = null;

	private Translate translate;

	private static String[] languageArray;

	public static Translator getInstance() {
		if (instance == null) {
			instance = new Translator();
		}

		return instance;
	}

	@SneakyThrows
	private Translator() {

		String translateLoc = readTranslationAPIKey();

		translate = TranslateOptions
				.newBuilder()
				.setCredentials(
						ServiceAccountCredentials
								.fromStream(new FileInputStream(translateLoc)))
				.build().getService();

		fillLanguageArray();
	}

	@SneakyThrows
	private static String readTranslationAPIKey() {

		FileReader reader = new FileReader("src/main/resources/properties/translateAPILocation.properties");
		Properties p = new Properties();
		p.load(reader);

		return p.getProperty("location");
	}

	private static void fillLanguageArray() {
		languageArray = new String[]{
				"af", "sq", "am", "ar", "hy", "az", "eu", "be", "bn", "bs", "bg", "ca", "ceb", "zh-CN", "zh",
				"zh-TW", "co", "hr", "cs", "da", "nl", "en", "eo", "et", "fi", "fr", "fy", "gl", "ka", "de",
				"el", "gu", "ht", "ha", "haw", "he", "iw", "hi", "hmn", "hu", "is", "ig", "id", "ga", "it", "ja",
				"jw", "kn", "kk", "km", "ko", "ku", "ky", "lo", "la", "lv", "lt", "lb", "mk", "mg", "ms", "ml",
				"mt", "mi", "mr", "mn", "my", "ne", "no", "ny", "ps", "fa", "pl", "pt", "pa", "ro", "ru", "sm",
				"gd", "sr", "st", "sn", "sd", "si", "sk", "sl", "so", "es", "su", "sw", "sv", "tl", "tg", "ta",
				"te", "th", "tr", "uk", "ur", "uz", "vi", "cy", "xh", "yi", "yo", "zu"
		};
	}

	public String translate(String from, String to, String message) {

		String result;

		if (isValidLanguage(from)) {
			if (isValidLanguage(to)) {
				Translation translation = translate.translate(
						message,
						Translate.TranslateOption.sourceLanguage(from.toLowerCase()),
						Translate.TranslateOption.targetLanguage(to.toLowerCase()),
						Translate.TranslateOption.format("text"));

				result = translation.getTranslatedText();
			} else {
				result = "A 2. nyelv nem valid " + "\nTámogatott nyelvek listája: https://cloud.google.com/translate/docs/languages";
			}
		} else {
			result = "Az 1. nyelv nem valid " + "\nTámogatott nyelvek listája: https://cloud.google.com/translate/docs/languages";
		}

		return result;
	}

	private static boolean isValidLanguage(String lang) {

		for (String language : languageArray) {
			if (lang.equalsIgnoreCase(language))
				return true;
		}
		return false;
	}
}