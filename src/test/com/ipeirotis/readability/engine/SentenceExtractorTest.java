package com.ipeirotis.readability.engine;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SentenceExtractorTest {

    SentenceExtractor sentenceExtractor;
    SentenceExtractor2 sentenceExtractor2;

    @Before
    public void before() {
        sentenceExtractor = new SentenceExtractor();
        sentenceExtractor2 = new SentenceExtractor2();
    }

    @Test
    public void testGetSimpleSentences() {
        String text = "I was taking out the garbage. " +
                "The cats just walked off. " +
                "Even Tiger territory is being threatened. " +
                "Raccoons won't even blink when you shout at them. " +
                "Just a few trees remain. " +
                "The construction company will just move somewhere else. " +
                "Thankfully, the tiger just injured him. " +
                "Our waste of natural resources is just out of control. " +
                "I'm not a crazy activist; I'm just like you. " +
                "The forest isn't just inhabited by wild animals anymore. " +
                "Only cyclones can do this much damage. ";
        String[] sentences = sentenceExtractor2.getSentences(text);
        assertNotNull(sentences);
        assertEquals(11, sentences.length);
        assertEquals("I was taking out the garbage.", sentences[0].trim());
        assertEquals("The cats just walked off.", sentences[1].trim());
        assertEquals("Even Tiger territory is being threatened.", sentences[2].trim());
        assertEquals("Raccoons won't even blink when you shout at them.", sentences[3].trim());
        assertEquals("Just a few trees remain.", sentences[4].trim());
        assertEquals("The construction company will just move somewhere else.", sentences[5].trim());
        assertEquals("Thankfully, the tiger just injured him.", sentences[6].trim());
        assertEquals("Our waste of natural resources is just out of control.", sentences[7].trim());
        assertEquals("I'm not a crazy activist; I'm just like you.", sentences[8].trim());
        assertEquals("The forest isn't just inhabited by wild animals anymore.", sentences[9].trim());
        assertEquals("Only cyclones can do this much damage.", sentences[10].trim());
    }

    @Test
    public void testGetComplexSentences() {
        String text = "There are a number of different ways to spread a viral infection. Some viruses sit on surfaces (or infected persons) \"waiting\" for someone to touch and take them up, this is called smear infection. Other viruses are airborn inside of small droplets distributed by coughing or sneezing. These are inhaled and can then cause a new infection. Other viruses are transmitted through our body fluids (blood, semen) and either need a wound or can be transmitted sexually (for example Herpes or HIV). Other viruses are transmitted by animals which bite humans (Dengue and rabies would be examples here). Have a look into the Wikipedia for more details and examples.\n" +
                "\n" +
                "Additionally viruses are produced in vast numbers by their hosts with most viral particles never reaching a new host. This strategy helps a lot to transmit further.s";

        String[] sentences = sentenceExtractor2.getSentences(text);
        assertNotNull(sentences);
        assertEquals(9, sentences.length);
    }

}
