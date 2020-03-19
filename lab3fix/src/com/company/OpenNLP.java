package com.company;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OpenNLP {

    public static String LANG_DETECT_MODEL = "models/langdetect-183.bin";
    public static String TOKENIZER_MODEL = "models/en-token.bin";
	public static String TOKENIZER_MODEL_GERMAN = "models/de-token.bin";
    public static String SENTENCE_MODEL = "models/en-sent.bin";
    public static String POS_MODEL = "models/en-pos-maxent.bin";
    public static String CHUNKER_MODEL = "models/en-chunker.bin";
    public static String LEMMATIZER_DICT = "models/en-lemmatizer.dict";
    public static String NAME_MODEL = "models/en-ner-person.bin";
    public static String ENTITY_XYZ_MODEL = "models/en-ner-xyz.bin";

	public static void main(String[] args) throws IOException
    {
		OpenNLP openNLP = new OpenNLP();
		openNLP.run();
	}

	public void run() throws IOException
    {

		//languageDetection();
		// tokenization();
		// sentenceDetection();
		// posTagging();
		// lemmatization();
		// stemming();
		// chunking();
		nameFinding();
	}

	private void languageDetection() throws IOException
    {
		File modelFile = new File(LANG_DETECT_MODEL);
		LanguageDetectorModel model = new LanguageDetectorModel(modelFile);
		LanguageDetectorME languageDetectorME = new LanguageDetectorME(model);
		String text = "";
		text = "cats";
		Language language;
		language = languageDetectorME.predictLanguage(text);
		System.out.println(language.getLang());

		text = "cats like milk";
		language = languageDetectorME.predictLanguage(text);
		System.out.println(language.getLang());

		 text = "Many cats like milk because in some ways it reminds them of their mother's milk.";
		language = languageDetectorME.predictLanguage(text);
		System.out.println(language.getLang());

		 text = "The two things are not really related. Many cats like milk because in some ways it reminds them of their mother's milk.";
		language = languageDetectorME.predictLanguage(text);
		System.out.println(language.getLang());

		text = "The two things are not really related. Many cats like milk because in some ways it reminds them of their mother's milk. "
				+ "It is rich in fat and protein. They like the taste. They like the consistency . "
				+ "The issue as far as it being bad for them is the fact that cats often have difficulty digesting milk and so it may give them "
				+ "digestive upset like diarrhea, bloating and gas. After all, cow's milk is meant for baby calves, not cats. "
				+ "It is a fortunate quirk of nature that human digestive systems can also digest cow's milk. But humans and cats are not cows.";
		language = languageDetectorME.predictLanguage(text);
		System.out.println(language.getLang());

		 text = "Many cats like milk because in some ways it reminds them of their mother's milk. Le lait n'est pas forc�ment mauvais pour les chats";
		language = languageDetectorME.predictLanguage(text);
		System.out.println(language.getLang());

		 text = "Many cats like milk because in some ways it reminds them of their mother's milk. Le lait n'est pas forc�ment mauvais pour les chats. "
		 + "Der Normalfall ist allerdings der, dass Salonl�wen Milch weder brauchen noch gut verdauen k�nnen.";
		language = languageDetectorME.predictLanguage(text);
		System.out.println(language.getLang());
	}

	private void tokenization() throws IOException
    {
		String text = "";
		File modelFile = new File(TOKENIZER_MODEL_GERMAN);
		TokenizerModel model = new TokenizerModel(modelFile);
		TokenizerME tokenizerME = new TokenizerME(model);
		File modelFileEn = new File(TOKENIZER_MODEL);
		TokenizerModel modelEn = new TokenizerModel(modelFileEn);
		TokenizerME tokenizerMEEn = new TokenizerME(modelEn);

		text = "Since cats were venerated in ancient Egypt, they were commonly believed to have been domesticated there, "
				+ "but there may have been instances of domestication as early as the Neolithic from around 9500 years ago (7500 BC).";
		//tokenizerME.tokenize(text);
		String[] strings = tokenizerME.tokenize(text);
		for (String tex:
		strings) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println("EN");
		String[] strings4 = tokenizerMEEn.tokenize(text);
		for (String tex:
				strings4) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println("");



		text = "Since cats were venerated in ancient Egypt, they were commonly believed to have been domesticated there, "
				+ "but there may have been instances of domestication as early as the Neolithic from around 9,500 years ago (7,500 BC).";

		String[] strings2 = tokenizerME.tokenize(text);
		for (String tex:
				strings2) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println("EN");
		String[] strings6 = tokenizerMEEn.tokenize(text);
		for (String tex:
				strings6) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println(	);

		text = "Since cats were venerated in ancient Egypt, they were commonly believed to have been domesticated there, "
		 + "but there may have been instances of domestication as early as the Neolithic from around 9 500 years ago ( 7 500 BC).";


		String[] strings3 = tokenizerME.tokenize(text);
		for (String tex:
				strings3) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println("EN");
		String[] strings7 = tokenizerMEEn.tokenize(text);
		for (String tex:
				strings7) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println(	);

		ArrayList<Double> probs = new ArrayList<>();
		for (Double d:
				tokenizerME.getTokenProbabilities()){
			probs.add(d);
		}
		for (int i = 0; i < strings7.length; i++) {
			System.out.println(strings7[i] + " , " + probs.get(i));
		}

	}

	private void sentenceDetection() throws IOException
    {
		File modelFile = new File(SENTENCE_MODEL);
		SentenceModel model = new SentenceModel(modelFile);
		SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(model);

    	String text = "Hi";
		String[] strings;
		strings = sentenceDetectorME.sentDetect(text);
		for (String tex:
			 strings) {
			System.out.println(tex);

		}
		text = "Hi. How are you? Welcome to OpenNLP. "
				+ "We provide multiple built-in methods for Natural Language Processing.";
		strings = sentenceDetectorME.sentDetect(text);
		for (String tex:
				strings) {
			System.out.println(tex);

		}
		System.out.println(	);
		text = "Hi. How are you?! Welcome to OpenNLP? "
				+ "We provide multiple built-in methods for Natural Language Processing.";
		strings = sentenceDetectorME.sentDetect(text);
		for (String tex:
				strings) {
			System.out.println(tex);

		}
		System.out.println(	);
		text = "Hi. How are you? Welcome to OpenNLP.?? "
				+ "We provide multiple . built-in methods for Natural Language Processing.";
		strings = sentenceDetectorME.sentDetect(text);
		for (String tex:
				strings) {
			System.out.println(tex);

		}
		System.out.println(	);

		text = "The interrobang, also known as the interabang (often represented by ?! or !?), "
				+ "is a nonstandard punctuation mark used in various ??  written languages. "
				+ "It is intended to combine the functions of the question mark (?), or interrogative point, "
				+ "and the exclamation mark (!), or exclamation point?? , known in the jargon of printers and programmers as a \"bang\". ";
		strings = sentenceDetectorME.sentDetect(text);
		for (String tex:
				strings) {
			System.out.println(tex);

		}
		System.out.println(	);


	}

	private void posTagging() throws IOException {
		File modelFile = new File(POS_MODEL);
		POSModel model = new POSModel(modelFile);
		POSTaggerME posTaggerME = new POSTaggerME(model);

		String[] strings;
		String[] sentence = new String[0];
		sentence = new String[] { "Cats", "like", "milk" };
		strings = posTaggerME.tag(sentence);
		for (String tex:
				strings) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println(	);
		sentence = new String[]{"Cat", "is", "white", "like", "milk"};
		strings = posTaggerME.tag(sentence);
		for (String tex:
				strings) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println(	);
		sentence = new String[] { "Hi", "How", "are", "you", "Welcome", "to", "OpenNLP", "We", "provide", "multiple",
				"built-in", "methods", "for", "Natural", "Language", "Processing" };
		strings = posTaggerME.tag(sentence);
		for (String tex:
				strings) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println(	);
		sentence = new String[] { "She", "put", "the", "big", "knives", "on", "the", "table" };
		strings = posTaggerME.tag(sentence);
		for (String tex:
				strings) {
			System.out.print("'" + tex + "' ");
		}
		System.out.println(	);
	}

	private void lemmatization() throws IOException
    {

		String[] text = new String[0];

		File modelFile = new File(LEMMATIZER_DICT);
		DictionaryLemmatizer dictionaryLemmatizer = new DictionaryLemmatizer(modelFile);

		text = new String[] { "Hi", "How", "are", "you", "Welcome", "to", "OpenNLP", "We", "provide", "multiple",
				"built-in", "methods", "for", "Natural", "Language", "Processing" };
		String[] tags = new String[0];
		tags = new String[] { "NNP", "WRBw", "VBP", "PRP", "VB", "TO", "VB", "PRP", "VB", "JJ", "JJ", "NNS", "IN", "JJ",
				"NN", "VBG" };

		for(String tex: dictionaryLemmatizer.lemmatize(text, tags)){
			System.out.println(tex);
		}

	}

	private void stemming()
    {
		String[] sentence = new String[0];
		sentence = new String[] { "Hi", "How", "are", "you", "Welcome", "to", "OpenNLP", "We", "provide", "multiple",
				"built-in", "methods", "for", "Natural", "Language", "Processing" };

		PorterStemmer porterStemmer = new PorterStemmer();

		for(String tex: sentence) {
			System.out.println(porterStemmer.stem(tex));
		}
	}
	
	private void chunking() throws IOException
    {
		String[] sentence = new String[0];
		sentence = new String[] { "She", "put", "the", "big", "knives", "on", "the", "table" };

		String[] tags = new String[0];
		tags = new String[] { "PRP", "VBD", "DT", "JJ", "NNS", "IN", "DT", "NN" };

		File modelFile = new File(CHUNKER_MODEL);
		ChunkerModel chunkerModel = new ChunkerModel(modelFile);
		ChunkerME chunkerME = new ChunkerME(chunkerModel);

		for(String tex:
				chunkerME.chunk(sentence, tags)){
			System.out.println(tex);
		}


	}

	private void nameFinding() throws IOException
    {
		String text = "he idea of using computers to search for relevant pieces of information was popularized in the article "
				+ "As We May Think by Vannevar Bush in 1945. It would appear that Bush was inspired by patents "
				+ "for a 'statistical machine' - filed by Emanuel Goldberg in the 1920s and '30s - that searched for documents stored on film. "
				+ "The first description of a computer searching for information was described by Holmstrom in 1948, "
				+ "detailing an early mention of the Univac computer. Automated information retrieval systems were introduced in the 1950s: "
				+ "one even featured in the 1957 romantic comedy, Desk Set. In the 1960s, the first large information retrieval research group "
				+ "was formed by Gerard Salton at Cornell. By the 1970s several different retrieval techniques had been shown to perform "
				+ "well on small text corpora such as the Cranfield collection (several thousand documents). Large-scale retrieval systems, "
				+ "such as the Lockheed Dialog system, came into use early in the 1970s.";

		//InputStream modelIn = new FileInputStream(NAME_MODEL);
		File modelFile = new File(NAME_MODEL);

		TokenNameFinderModel tokenNameFinderModel = new TokenNameFinderModel(modelFile);
		NameFinderME nameFinderME = new NameFinderME(tokenNameFinderModel);

		Span names[] = new Span[]{};

		File modelFileToken = new File(TOKENIZER_MODEL);
		TokenizerModel modelToken = new TokenizerModel(modelFileToken);
		TokenizerME tokenizer = new TokenizerME(modelToken);
		String[] tokens = tokenizer.tokenize(text);
		names = nameFinderME.find(tokenizer.tokenize(text));
		StringBuilder stringBuilder = new StringBuilder("names: ");
		for (int i = 0; i < names.length; i++){
			for (int j = names[i].getStart(); j < names[i].getEnd(); j++)
			{
				stringBuilder.append(tokens[j]);
				if (j < names[i].getEnd() - 1) stringBuilder.append(" ");
			}
			if (i < names.length - 1) stringBuilder.append(", ");
		}
		System.out.println(stringBuilder);
	}

}
