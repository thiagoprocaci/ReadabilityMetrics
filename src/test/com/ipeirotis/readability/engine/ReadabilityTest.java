package com.ipeirotis.readability.engine;


import com.ipeirotis.readability.enums.MetricType;
import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.*;

public class ReadabilityTest {

    @Test
    public void testSomething() {


        String text = "There are a number of different ways to spread a viral infection. Some viruses sit on surfaces (or infected persons) \"waiting\" for someone to touch and take them up, this is called smear infection. Other viruses are airborn inside of small droplets distributed by coughing or sneezing. These are inhaled and can then cause a new infection. Other viruses are transmitted through our body fluids (blood, semen) and either need a wound or can be transmitted sexually (for example Herpes or HIV). Other viruses are transmitted by animals which bite humans (Dengue and rabies would be examples here). Have a look into the Wikipedia for more details and examples.\n" +
                "\n" +
                "Additionally viruses are produced in vast numbers by their hosts with most viral particles never reaching a new host. This strategy helps a lot to transmit further.s";

        Readability readability = new Readability(text);

        Readability readability2 = new Readability(text, new SentenceExtractorStanfordNlp());
        for(MetricType m: MetricType.values()) {
            System.out.println(m + " " + readability.getMetric(m));
            System.out.println(m + " " + readability2.getMetric(m));

            assertEquals(readability.getSMOGIndex(), getSMOGIndex(readability), 0.0001);
            assertEquals(readability2.getSMOGIndex(), getSMOGIndex(readability2), 0.0001);

            assertEquals(readability.getSMOG(), getSMOG(readability), 0.0001);
            assertEquals(readability2.getSMOG(), getSMOG(readability2), 0.0001);

            assertEquals(readability.getFleschReadingEase(), getFleschReadingEase(readability), 0.0001);
            assertEquals(readability2.getFleschReadingEase(), getFleschReadingEase(readability2), 0.0001);

            assertEquals(readability.getFleschKincaidGradeLevel(), getFleschKincaidGradeLevel(readability), 0.0001);
            assertEquals(readability2.getFleschKincaidGradeLevel(), getFleschKincaidGradeLevel(readability2), 0.0001);

            assertEquals(readability.getARI(), getARI(readability), 0.0001);
            assertEquals(readability2.getARI(), getARI(readability2), 0.0001);

            assertEquals(readability.getARI(), getARI(readability), 0.0001);
            assertEquals(readability2.getARI(), getARI(readability2), 0.0001);

            assertEquals(readability.getGunningFog(), getGunningFog(readability), 0.0001);
            assertEquals(readability2.getGunningFog(), getGunningFog(readability2), 0.0001);

            assertEquals(readability.getColemanLiau(), getColemanLiau(readability), 0.0001);
            assertEquals(readability2.getColemanLiau(), getColemanLiau(readability2), 0.0001);
        }
    }


    private double getSMOGIndex(Readability readability ) {
        double score = Math.sqrt(readability.complex * (30.0 / readability.sentences)) + 3;
        return round(score, 3);
    }

    private double getSMOG(Readability readability ) {
        double score = 1.043 * Math.sqrt(readability.complex * (30.0 / readability.sentences)) + 3.1291;
        return round(score, 3);
    }


    private double getFleschReadingEase(Readability readability ) {

        double score = 206.835 - 1.015 * readability.words / readability.sentences - 84.6 * readability.syllables
                / readability.words;

        return round(score, 3);
    }

    private double getFleschKincaidGradeLevel(Readability readability ) {
        double score = 0.39 * readability.words / readability.sentences + 11.8 * readability.syllables / readability.words
                - 15.59;
        return round(score, 3);
    }

    private double getARI(Readability readability ) {
        double score = 4.71 * readability.characters / readability.words + 0.5 * readability.words / readability.sentences
                - 21.43;
        return round(score, 3);
    }

    private double getGunningFog(Readability readability ) {
        double score = 0.4 * (readability.words / readability.sentences + 100 * readability.complex / readability.words);
        return round(score, 3);
    }

    private double getColemanLiau(Readability readability) {
        double score = (5.89 * readability.characters / readability.words) - (30 * readability.sentences / readability.words)
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
