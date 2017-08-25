package com.ipeirotis.readability.engine;

import java.math.BigDecimal;

import com.ipeirotis.readability.enums.MetricType;

/**
 * Implements various readability indexes
 * 
 * @author Panos Ipeirotis
 * 
 */
public class Readability {

	ISentenceExtractor se;

	Integer sentences;

	Integer complex;

	Integer words;

	Integer syllables;

	Integer characters;

	public double getMetric(MetricType t) throws IllegalArgumentException {

		switch (t) {
		case SMOG:
			return getSMOG();
		case FLESCH_READING:
			return getFleschReadingEase();
		case FLESCH_KINCAID:
			return getFleschKincaidGradeLevel();
		case ARI:
			return getARI();
		case GUNNING_FOG:
			return getGunningFog();
		case COLEMAN_LIAU:
			return getColemanLiau();
		case SMOG_INDEX:
			return getSMOGIndex();
		case CHARACTERS:
			return getCharacters();
		case SYLLABLES:
			return getSyllables();
		case WORDS:
			return getWords();
		case COMPLEXWORDS:
			return getComplex();
		case SENTENCES:
			return getSentences();

		default:
			throw new IllegalArgumentException("Readability Metric '"
					+ t.toString() + "' not supported");
		}
	}

	private int getCharacters() {
		return characters;
	}

	private int getComplex() {
		return complex;
	}

	private int getSentences() {
		return sentences;
	}

	private int getSyllables() {
		return syllables;
	}

	private int getWords() {
		return words;
	}

	public Readability(String text) {
		se = new SentenceExtractor();
		// We add the "." for the sentence extractor to pick the last sentence,
		// if it does not end with a punctuation mark.
		this.sentences = getNumberOfSentences(text + ".");
		this.complex = getNumberOfComplexWords(text);
		this.words = getNumberOfWords(text);
		this.syllables = getNumberOfSyllables(text);
		this.characters = getNumberOfCharacters(text);
	}

	public Readability(String text, ISentenceExtractor sentenceExtractor) {
		se = sentenceExtractor;
		// We add the "." for the sentence extractor to pick the last sentence,
		// if it does not end with a punctuation mark.
		this.sentences = getNumberOfSentences(text + ".");
		this.complex = getNumberOfComplexWords(text);
		this.words = getNumberOfWords(text);
		this.syllables = getNumberOfSyllables(text);
		this.characters = getNumberOfCharacters(text);
	}

	/**
	 * Returns true is the word contains 3 or more syllables
	 *
	 * @param w
	 * @return
	 */
	boolean isComplex(String w) {
		int syllables = Syllabify.syllable(w);
		return (syllables > 2);
	}

	/**
	 * Returns the number of letter characters in the text
	 *
	 * @return
	 */
	int getNumberOfCharacters(String text) {
		String cleanText = cleanLine(text);
		String[] word = cleanText.split(" ");

		int characters = 0;
		for (String w : word) {
			characters += w.length();
		}
		return characters;
	}

	/**
	 * Returns the number of words with 3 or more syllables
	 *
	 * @param text
	 * @return the number of words in the text with 3 or more syllables
	 */
	int getNumberOfComplexWords(String text) {
		String cleanText = cleanLine(text);
		String[] words = cleanText.split(" ");
		int complex = 0;
		for (String w : words) {
			if (isComplex(w))
				complex++;
		}
		return complex;
	}

	int getNumberOfWords(String text) {
		String cleanText = cleanLine(text);
		String[] word = cleanText.split(" ");
		int words = 0;
		for (String w : word) {
			if (w.length() > 0)
				words++;
		}
		return words;
	}

	/**
	 * Returns the total number of syllables in the words of the text
	 *
	 * @param text
	 * @return the total number of syllables in the words of the text
	 */
	int getNumberOfSyllables(String text) {
		String cleanText = cleanLine(text);
		String[] word = cleanText.split(" ");
		int syllables = 0;
		for (String w : word) {
			if (w.length() > 0) {
				syllables += Syllabify.syllable(w);
			}
		}
		return syllables;
	}

	String cleanLine(String line) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c < 128 && Character.isLetter(c)) {
				buffer.append(c);
			} else {
				buffer.append(' ');
			}
		}
		return buffer.toString().toLowerCase();
	}

	int getNumberOfSentences(String text) {
		int l = se.getSentences(text).length;
		if (l > 0)
			return l;
		else if (text.length() > 0)
			return 1;
		else
			return 0;
	}

	/**
	 *
	 * http://en.wikipedia.org/wiki/SMOG_Index
	 *
	 * @return The SMOG index of the text
	 */
	double getSMOGIndex() {
		double score = Math.sqrt(complex * (30.0 / sentences)) + 3;
		return round(score, 3);
	}

	/**
	 *
	 * http://en.wikipedia.org/wiki/SMOG
	 *
	 * @return Retugns the SMOG value for the text
	 */
	double getSMOG() {
		double score = 1.043 * Math.sqrt(complex * (30.0 / sentences)) + 3.1291;
		return round(score, 3);
	}

	/**
	 *
	 * http://en.wikipedia.org/wiki/Flesch-Kincaid_Readability_Test
	 *

	 * @return Returns the Flesch_Reading Ease value for the text
	 */
	double getFleschReadingEase() {

		double score = 206.835 - (1.015 * words / sentences) - (84.6 * syllables / words);

		return round(score, 3);
	}

	/**
	 *
	 * http://en.wikipedia.org/wiki/Flesch-Kincaid_Readability_Test
	 *

	 * @return Returns the Flesch-Kincaid_Readability_Test value for the text
	 */
	double getFleschKincaidGradeLevel() {
		double score = (0.39 * words / sentences) + (11.8 * syllables / words)
				- 15.59;
		return round(score, 3);
	}

	/**
	 *
	 * http://en.wikipedia.org/wiki/Automated_Readability_Index
	 *

	 * @return the Automated Readability Index for text
	 */
	double getARI() {
		double score = 4.71 * characters / words + 0.5 * words / sentences
				- 21.43;
		return round(score, 3);
	}

	/**
	 *
	 * http://en.wikipedia.org/wiki/Gunning-Fog_Index
	 *

	 * @return the Gunning-Fog Index for text
	 */
	double getGunningFog() {
		double score = 0.4 * ((words / sentences) + (100 * complex / words));
		return round(score, 3);
	}

	/**
	 * 
	 * http://en.wikipedia.org/wiki/Coleman-Liau_Index
	 * 
	 * @return The Coleman-Liau_Index value for the text
	 */
	double getColemanLiau() {
		double score = (5.89 * characters / words) - (30 * sentences / words)
				- 15.8;
		return round(score, 3);
	}

	private static Double round(double d, int decimalPlace) {
		// see the Javadoc about why we use a String in the constructor
		// http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
} 