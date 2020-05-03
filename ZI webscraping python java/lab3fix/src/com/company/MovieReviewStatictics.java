package com.company;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import sun.lwawt.macosx.CPrinterDevice;
import sun.util.resources.cldr.lo.CurrencyNames_lo;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MovieReviewStatictics
{

    public static String LANG_DETECT_MODEL = "models/langdetect-183.bin";
    public static String TOKENIZER_MODEL = "models/en-token.bin";
    public static String TOKENIZER_MODEL_GERMAN = "models/de-token.bin";
    public static String SENTENCE_MODEL = "models/en-sent.bin";
    public static String POS_MODEL = "models/en-pos-maxent.bin";
    public static String CHUNKER_MODEL = "models/en-chunker.bin";
    public static String LEMMATIZER_DICT = "models/en-lemmatizer.dict";
    public static String NAME_MODEL = "models/en-ner-person.bin";
    public static String ORG_MODEL = "models/en-ner-organization.bin";
    public static String LOC_MODEL = "models/en-ner-location.bin";

    private static final String DOCUMENTS_PATH = "movies/";
    private int _verbCount = 0;
    private int _nounCount = 0;
    private int _adjectiveCount = 0;
    private int _adverbCount = 0;
    private int _totalTokensCount = 0;

    private PrintStream _statisticsWriter;

    private SentenceModel _sentenceModel;
    private TokenizerModel _tokenizerModel;
    private DictionaryLemmatizer _lemmatizer;
    private PorterStemmer _stemmer;
    private POSModel _posModel;
    private TokenNameFinderModel _peopleModel;
    private TokenNameFinderModel _placesModel;
    private TokenNameFinderModel _organizationsModel;

    public static void main(String[] args)
    {
        MovieReviewStatictics statictics = new MovieReviewStatictics();
        statictics.run();
    }

    private void run()
    {
        try
        {
            initModelsStemmerLemmatizer();

            File dir = new File(DOCUMENTS_PATH);
            File[] reviews = dir.listFiles((d, name) -> name.endsWith(".txt"));

            _statisticsWriter = new PrintStream("statistics.txt", "UTF-8");

            Arrays.sort(reviews, Comparator.comparing(File::getName));
            for (File file : reviews)
            {
                System.out.println("Movie: " + file.getName().replace(".txt", ""));
                _statisticsWriter.println("Movie: " + file.getName().replace(".txt", ""));

                String text = new String(Files.readAllBytes(file.toPath()));
                processFile(text);

                _statisticsWriter.println();
            }

            overallStatistics();
            _statisticsWriter.close();

        } catch (IOException ex)
        {
            Logger.getLogger(MovieReviewStatictics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initModelsStemmerLemmatizer()
    {
        try
        {
            File lemmatizerFile = new File(LEMMATIZER_DICT);
            File sentence = new File(SENTENCE_MODEL);
            File tokenizer = new File(TOKENIZER_MODEL);
            File posmodel = new File(POS_MODEL);
            _sentenceModel = new SentenceModel(sentence);
            _tokenizerModel = new TokenizerModel(tokenizer);
            _lemmatizer = new DictionaryLemmatizer(lemmatizerFile);
            _stemmer = new PorterStemmer();
            _posModel = new POSModel(posmodel);
            File name = new File(NAME_MODEL);
            File loc = new File(LOC_MODEL);
            File org = new File(ORG_MODEL);
            _peopleModel = new TokenNameFinderModel(name);
            _placesModel = new TokenNameFinderModel(loc);
            _organizationsModel = new TokenNameFinderModel(org);
        } catch (IOException ex)
        {
            Logger.getLogger(MovieReviewStatictics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processFile(String text)
    {
        // TODO: process the text to find the following statistics:
        // For each movie derive:
        //    - number of sentences


        //    - number of tokens


        //    - number of (unique) stemmed forms


        //    - number of (unique) words from a dictionary (lemmatization)


        //    -  people



        //    - locations

        //    - organisations


        // TODO + compute the following overall (for all movies) POS tagging statistics:
        //    - percentage number of adverbs (class variable, private int _verbCount = 0)
        //    - percentage number of adjectives (class variable, private int _nounCount = 0)
        //    - percentage number of verbs (class variable, private int _adjectiveCount = 0)
        //    - percentage number of nouns (class variable, private int _adverbCount = 0)
        //    + update _totalTokensCount

        // ------------------------------------------------------------------

        // TODO derive sentences (update noSentences variable)
        SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(_sentenceModel);
        int noSentences = sentenceDetectorME.sentDetect(text).length;

        // TODO derive tokens and POS tags from text
        TokenizerME tokenizer = new TokenizerME(_tokenizerModel);
        String[] tokens = tokenizer.tokenize(text);
        int noTokens = tokens.length;
        _totalTokensCount += tokens.length;

        // TODO perform stemming (use derived tokens)

        Set<String> uniqStem = new HashSet<String>();
        for(String s: tokens) {
            uniqStem.add(_stemmer.stem(s.toLowerCase().replaceAll("[^a-z0-9]", "")));
        }
        int noStemmed = uniqStem.size();

        // (update noStemmed)
        //Set <String> stems = new HashSet <>();

        //for (String token : tokens)
        //{
            // use .toLowerCase().replaceAll("[^a-z0-9]", ""); thereafter, ignore "" tokens
        //}


        // TODO perform lemmatization (use derived tokens)
        // (remove "O" from results - non-dictionary forms, update noWords)

        POSTaggerME tagger = new POSTaggerME(_posModel);
        Set<String> uniqLem = new HashSet<String>();
        for(String s: _lemmatizer.lemmatize(tokens, tagger.tag(tokens))){
            if(s == "O") continue;
            uniqLem.add(s);
        }

        int noWords = uniqLem.size();


        // TODO derive people, locations, organisations (use tokens),
        // (update people, locations, organisations lists).
        Span people[] = new Span[] { };
        NameFinderME nameFinder = new NameFinderME(_peopleModel);
        people = nameFinder.find(tokens);

        Span locations[] = new Span[] { };
        NameFinderME locFinder = new NameFinderME(_placesModel);
        locations = locFinder.find(tokens);

        Span organisations[] = new Span[] { };
        NameFinderME orgFinder = new NameFinderME(_organizationsModel);
        organisations = orgFinder.find(tokens);
        // TODO update overall statistics - use tags and check first letters
        // (see https://www.clips.uantwerpen.be/pages/mbsp-tags; first letter = "V" = verb?)


        for (String tag:
        tagger.tag(tokens)) {
            if(tag.charAt(0) == 'V')
            {
                _verbCount += 1;
            }
            if(tag.charAt(0) == 'N')
            {
                _nounCount += 1;
            }
            if(tag.charAt(0) == 'J')
            {
                _adjectiveCount += 1;
            }
            if(tag.charAt(0) == 'R')
            {
                _adverbCount += 1;
            }
        }


        // ------------------------------------------------------------------

        saveResults("Sentences", noSentences);
        saveResults("Tokens", noTokens);
        saveResults("Stemmed forms (unique)", noStemmed);
        saveResults("Words from a dictionary (unique)", noWords);

        saveNamedEntities("People", people, tokens);
        saveNamedEntities("Locations", locations, tokens);
        saveNamedEntities("Organizations", organisations, tokens);
    }


    private void saveResults(String feature, int count)
    {
        String s = feature + ": " + count;
        System.out.println("   " + s);
        _statisticsWriter.println(s);
    }

    private void saveNamedEntities(String entityType, Span spans[], String tokens[])
    {
        StringBuilder s = new StringBuilder(entityType + ": ");
        for (int sp = 0; sp < spans.length; sp++)
        {
            for (int i = spans[sp].getStart(); i < spans[sp].getEnd(); i++)
            {
                s.append(tokens[i]);
                if (i < spans[sp].getEnd() - 1) s.append(" ");
            }
            if (sp < spans.length - 1) s.append(", ");
        }

        System.out.println("   " + s);
        _statisticsWriter.println(s);
    }

    private void overallStatistics()
    {
        _statisticsWriter.println("---------OVERALL STATISTICS----------");
        DecimalFormat f = new DecimalFormat("#0.00");

        if (_totalTokensCount == 0) _totalTokensCount = 1;
        String verbs = f.format(((double) _verbCount * 100) / _totalTokensCount);
        String nouns = f.format(((double) _nounCount * 100) / _totalTokensCount);
        String adjectives = f.format(((double) _adjectiveCount * 100) / _totalTokensCount);
        String adverbs = f.format(((double) _adverbCount * 100) / _totalTokensCount);

        _statisticsWriter.println("Verbs: " + verbs + "%");
        _statisticsWriter.println("Nouns: " + nouns + "%");
        _statisticsWriter.println("Adjectives: " + adjectives + "%");
        _statisticsWriter.println("Adverbs: " + adverbs + "%");

        System.out.println("---------OVERALL STATISTICS----------");
        System.out.println("Adverbs: " + adverbs + "%");
        System.out.println("Adjectives: " + adjectives + "%");
        System.out.println("Verbs: " + verbs + "%");
        System.out.println("Nouns: " + nouns + "%");
    }

}
