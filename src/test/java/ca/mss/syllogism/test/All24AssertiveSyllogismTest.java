package ca.mss.syllogism.test;

import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.mss.syllogism.CategoricalSentence;
import ca.mss.syllogism.Copula;
import ca.mss.syllogism.SyllogisticReasoning;
import ca.mss.syllogism.Term;

/**
 *
 * @author Sergey Moskovskiy
 */
public class All24AssertiveSyllogismTest {
    private SyllogisticReasoning context;
    private Term men, mortal, greeks, reptiles, snakes, bears, rabbits;
    private Term fur, pets, fur3, pets3, fur4, pets4;
    private Term homework, work;
    private Term  fun, reading, fun2, reading2, fun3, reading3, fun4, reading4;
    private Term informative, useful, websites;
    private Term cats, tails, mammals, horses, hooves, humans, flowers, birds, animals, plants;
    private Term squares, rectangles, rhombuses;

    /**
     * Test setup.
     */
    @Before
    public void setUp() {
        context = new SyllogisticReasoning();

        men = context.createTerm("men");
        mortal = context.createTerm("mortal");
        greeks = context.createTerm("greeks");
        reptiles = context.createTerm("reptiles");
        fur = context.createTerm("fur");
        pets = context.createTerm("pets");
        fur3 = context.createTerm("fur3");
        pets3 = context.createTerm("pets3");
        fur4 = context.createTerm("fur4");
        pets4 = context.createTerm("pets4");
        snakes = context.createTerm("snakes");
        bears = context.createTerm("bears");
        rabbits = context.createTerm("rabbits");
        homework = context.createTerm("homework");
        work = context.createTerm("work");
        fun = context.createTerm("fun");
        reading = context.createTerm("reading");
        fun2 = context.createTerm("fun2");
        reading2 = context.createTerm("reading2");
        fun3 = context.createTerm("fun3");
        reading3 = context.createTerm("reading3");
        fun4 = context.createTerm("fun4");
        reading4 = context.createTerm("reading4");
        informative = context.createTerm("informative");
        useful = context.createTerm("useful");
        websites = context.createTerm("websites");
        cats = context.createTerm("cats");
        tails = context.createTerm("tails");
        mammals = context.createTerm("mammals");
        horses = context.createTerm("horses");
        hooves = context.createTerm("hooves");
        humans = context.createTerm("humans");
        flowers = context.createTerm("flowers");
        animals = context.createTerm("animals");
        plants = context.createTerm("plants");
        birds = context.createTerm("birds");
        squares = context.createTerm("squares");
        rectangles = context.createTerm("rectangles");
        rhombuses = context.createTerm("rhombuses");

        // figure 1
        context.addSentence(men, Copula.IS, mortal);
        context.addSentence(greeks, Copula.IS, men);
        
        // figure 1
        context.addSentence(reptiles, Copula.ISNOT, fur);	
        context.addSentence(snakes, Copula.IS, reptiles);
        context.addSentence(bears, Copula.IS, fur);	
        
        // figure 1
        context.addSentence(rabbits, Copula.IS, fur);
        context.addSentence(animals, Copula.SomeIS, rabbits);

        // figure 1
        context.addSentence(rabbits, Copula.IS, fur);
        context.addSentence(pets, Copula.SomeIS, rabbits);

        // figure 3
        context.addSentence(rabbits, Copula.SomeIS, pets3);
        context.addSentence(rabbits, Copula.IS, fur3);

        // figure 4
        context.addSentence(pets4, Copula.SomeIS, rabbits);
        context.addSentence(rabbits, Copula.IS, fur4);

        // figure 1
        context.addSentence(homework, Copula.ISNOT, fun);	
        context.addSentence(reading, Copula.SomeIS, homework);

        // figure 2
        context.addSentence(fun2, Copula.ISNOT, homework);	
        context.addSentence(reading2, Copula.SomeIS, homework);

        // figure 2
        context.addSentence(homework, Copula.ISNOT, fun3);	
        context.addSentence(homework, Copula.SomeIS, reading3);

        // figure 4
        context.addSentence(fun4, Copula.ISNOT, work);		
        context.addSentence(work, Copula.SomeIS, reading4);

        // figure 2
        context.addSentence(informative, Copula.IS, useful);
        context.addSentence(websites, Copula.SomeISNOT, useful);

        // figure 3
        context.addSentence(cats, Copula.SomeISNOT, tails);
        context.addSentence(cats, Copula.IS, mammals);

        // figure 2
        context.addSentence(horses, Copula.IS, hooves);
        context.addSentence(humans, Copula.ISNOT, hooves);

        // figure 3
        context.addSentence(flowers, Copula.ISNOT, animals);
        context.addSentence(flowers, Copula.IS, plants);

        // figure 4
        context.addSentence(birds, Copula.ISNOT, flowers);	
        context.addSentence(flowers, Copula.IS, plants);

        // figure 3
        context.addSentence(squares, Copula.IS, rectangles);
        context.addSentence(squares, Copula.IS, rhombuses);

    }

    /*
     * A and E = 5 syllogisms are covered by prolog
     */
    
    @Test
    public void cesare_2() {
    	CategoricalSentence conclusion = new CategoricalSentence(bears, Copula.ISNOT, reptiles); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eae:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL bears IS fur\n" + 
        		"ALL bears ISNOT reptiles]", conclusion.positiveSyllogism.toString());
    }

    @Test
    public void cesaro_2() {
    	CategoricalSentence conclusion = new CategoricalSentence(bears, Copula.SomeISNOT, reptiles); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eao:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL bears IS fur\n" + 
        		"SOME bears ISNOT reptiles]", conclusion.positiveSyllogism.toString());
   }

    @Test
    public void camestres_2() {
    	CategoricalSentence conclusion = new CategoricalSentence(reptiles, Copula.ISNOT, bears); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aee:\n" + 
        		"ALL bears IS fur\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL reptiles ISNOT bears]", conclusion.positiveSyllogism.toString());
   }

    @Test
    public void calemes_4() {
    	CategoricalSentence conclusion = new CategoricalSentence(fur, Copula.ISNOT, snakes); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aee:\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL fur ISNOT snakes]", conclusion.positiveSyllogism.toString());
   }

    @Test
    public void calemos_4() {
    	CategoricalSentence conclusion = new CategoricalSentence(fur, Copula.SomeISNOT, snakes); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aeo:\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"SOME fur ISNOT snakes]", conclusion.positiveSyllogism.toString());
   }

    @Test
    public void celarent_1() {
    	CategoricalSentence conclusion = new CategoricalSentence(snakes, Copula.ISNOT, fur); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eae:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"ALL snakes ISNOT fur]", conclusion.positiveSyllogism.toString());
   }

    @Test
    public void barbara_1() {
    	CategoricalSentence conclusion = new CategoricalSentence(greeks, Copula.IS, mortal); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aaa:\n" + 
        		"ALL men IS mortal\n" + 
        		"ALL greeks IS men\n" + 
        		"ALL greeks IS mortal]", conclusion.positiveSyllogism.toString());
    }
    
    /*
     * A, E, I and O  = 19 syllogisms are not covered by prolog
     */
    @Test
    public void barbari_1() {
    	CategoricalSentence conclusion = new CategoricalSentence(greeks, Copula.SomeIS, mortal); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aai:\n" + 
        		"ALL men IS mortal\n" + 
        		"ALL greeks IS men\n" + 
        		"SOME greeks IS mortal]", conclusion.positiveSyllogism.toString());
    }

    @Test
    public void celaront_1() {
    	CategoricalSentence conclusion = new CategoricalSentence(snakes, Copula.SomeISNOT, fur); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eao:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"SOME snakes ISNOT fur]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void baroco_2() {
    	CategoricalSentence conclusion = new CategoricalSentence(websites, Copula.SomeISNOT, informative); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aoo:\n" + 
        		"ALL informative IS useful\n" + 
        		"SOME websites ISNOT useful\n" + 
        		"SOME websites ISNOT informative]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void bocardo_3() {
    	CategoricalSentence conclusion = new CategoricalSentence(mammals, Copula.SomeISNOT, tails); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[oao:\n" + 
        		"SOME cats ISNOT tails\n" + 
        		"ALL cats IS mammals\n" + 
        		"SOME mammals ISNOT tails]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void bamalip_4() {
    	CategoricalSentence conclusion = new CategoricalSentence(mortal, Copula.SomeIS, greeks); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aai:\n" + 
        		"ALL greeks IS men\n" + 
        		"ALL men IS mortal\n" + 
        		"SOME mortal IS greeks]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void camestros_2() {
    	CategoricalSentence conclusion = new CategoricalSentence(humans, Copula.SomeISNOT, horses); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aeo:\n" + 
        		"ALL horses IS hooves\n" + 
        		"ALL humans ISNOT hooves\n" + 
        		"SOME humans ISNOT horses]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void felapton_3() {
    	CategoricalSentence conclusion = new CategoricalSentence(plants, Copula.SomeISNOT, animals); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eao:\n" + 
        		"ALL flowers ISNOT animals\n" + 
        		"ALL flowers IS plants\n" + 
        		"SOME plants ISNOT animals]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void darapti_3() {
    	CategoricalSentence conclusion = new CategoricalSentence(rhombuses, Copula.SomeIS, rectangles); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aai:\n" + 
        		"ALL squares IS rectangles\n" + 
        		"ALL squares IS rhombuses\n" + 
        		"SOME rhombuses IS rectangles]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void ferio_1() {
    	CategoricalSentence conclusion = new CategoricalSentence(reading, Copula.SomeISNOT, fun); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eio:\n" + 
        		"ALL homework ISNOT fun\n" + 
        		"SOME reading IS homework\n" + 
        		"SOME reading ISNOT fun]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void festino_2() {
    	CategoricalSentence conclusion = new CategoricalSentence(reading2, Copula.SomeISNOT, fun2); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eio:\n" + 
        		"ALL fun2 ISNOT homework\n" + 
        		"SOME reading2 IS homework\n" + 
        		"SOME reading2 ISNOT fun2]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void ferison_3() {
    	CategoricalSentence conclusion = new CategoricalSentence(reading3, Copula.SomeISNOT, fun3); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eio:\n" + 
        		"ALL homework ISNOT fun3\n" + 
        		"SOME homework IS reading3\n" + 
        		"SOME reading3 ISNOT fun3]", conclusion.positiveSyllogism.toString());
    }

    @Test
    public void fresison_4() {
    	CategoricalSentence conclusion = new CategoricalSentence(reading4, Copula.SomeISNOT, fun4); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eio:\n" + 
        		"ALL fun4 ISNOT work\n" + 
        		"SOME work IS reading4\n" + 
        		"SOME reading4 ISNOT fun4]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void darii_1() {
    	CategoricalSentence conclusion = new CategoricalSentence(pets, Copula.SomeIS, fur); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aii:\n" + 
        		"ALL rabbits IS fur\n" + 
        		"SOME pets IS rabbits\n" + 
        		"SOME pets IS fur]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void datisi_3() {
    	CategoricalSentence conclusion = new CategoricalSentence(pets3, Copula.SomeIS, fur3); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[aii:\n" + 
        		"ALL rabbits IS fur3\n" + 
        		"SOME rabbits IS pets3\n" + 
        		"SOME pets3 IS fur3]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void disamis_3() {
    	CategoricalSentence conclusion = new CategoricalSentence(fur3, Copula.SomeIS, pets3); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[iai:\n" + 
        		"SOME rabbits IS pets3\n" + 
        		"ALL rabbits IS fur3\n" + 
        		"SOME fur3 IS pets3]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void dimatis_4() {
    	CategoricalSentence conclusion = new CategoricalSentence(fur4, Copula.SomeIS, pets4); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[iai:\n" + 
        		"SOME pets4 IS rabbits\n" + 
        		"ALL rabbits IS fur4\n" + 
        		"SOME fur4 IS pets4]", conclusion.positiveSyllogism.toString());
    }
    
    @Test
    public void fesapo_4() {
    	CategoricalSentence conclusion = new CategoricalSentence(plants, Copula.SomeISNOT, birds); 
        Optional<Boolean> result = context.interrogate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+conclusion.positiveSyllogism.stream().collect(Collectors.joining( "\n" )));
        Assert.assertTrue(result.isPresent() && result.get().equals(true));
        Assert.assertEquals("[eao:\n" + 
        		"ALL birds ISNOT flowers\n" + 
        		"ALL flowers IS plants\n" + 
        		"SOME plants ISNOT birds]", conclusion.positiveSyllogism.toString());
    }
}
