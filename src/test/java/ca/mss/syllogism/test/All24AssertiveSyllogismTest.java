package ca.mss.syllogism.test;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.mss.syllogism.Copula;
import ca.mss.syllogism.Proposition;
import ca.mss.syllogism.Syllogism;
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
        context.addProposition(men, Copula.IS, mortal);
        context.addProposition(greeks, Copula.IS, men);
        
        // figure 1
        context.addProposition(reptiles, Copula.ISNOT, fur);	
        context.addProposition(snakes, Copula.IS, reptiles);
        context.addProposition(bears, Copula.IS, fur);	
        
        // figure 1
        context.addProposition(rabbits, Copula.IS, fur);
        context.addProposition(pets, Copula.SomeIS, rabbits);

        // figure 3
        context.addProposition(rabbits, Copula.SomeIS, pets3);
        context.addProposition(rabbits, Copula.IS, fur3);

        // figure 4
        context.addProposition(pets4, Copula.SomeIS, rabbits);
        context.addProposition(rabbits, Copula.IS, fur4);

        // figure 1
        context.addProposition(homework, Copula.ISNOT, fun);	
        context.addProposition(reading, Copula.SomeIS, homework);

        // figure 2
        context.addProposition(fun2, Copula.ISNOT, homework);	
        context.addProposition(reading2, Copula.SomeIS, homework);

        // figure 2
        context.addProposition(homework, Copula.ISNOT, fun3);	
        context.addProposition(homework, Copula.SomeIS, reading3);

        // figure 4
        context.addProposition(fun4, Copula.ISNOT, work);		
        context.addProposition(work, Copula.SomeIS, reading4);

        // figure 2
        context.addProposition(informative, Copula.IS, useful);
        context.addProposition(websites, Copula.SomeISNOT, useful);

        // figure 3
        context.addProposition(cats, Copula.SomeISNOT, tails);
        context.addProposition(cats, Copula.IS, mammals);

        // figure 2
        context.addProposition(horses, Copula.IS, hooves);
        context.addProposition(humans, Copula.ISNOT, hooves);

        // figure 3
        context.addProposition(flowers, Copula.ISNOT, animals);
        context.addProposition(flowers, Copula.IS, plants);

        // figure 4
        context.addProposition(birds, Copula.ISNOT, flowers);	
        context.addProposition(flowers, Copula.IS, plants);

        // figure 3
        context.addProposition(squares, Copula.IS, rectangles);
        context.addProposition(squares, Copula.IS, rhombuses);

    }

    /*
     * A and E = 5 syllogisms are covered by prolog
     */
    
    @Test
    public void cesare_2() {
    	Proposition conclusion = context.createProposition(bears, Copula.ISNOT, reptiles); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eae:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL bears IS fur\n" + 
        		"ALL bears ISNOT reptiles]", result.get().toString());
    }

    @Test
    public void cesaro_2() {
    	Proposition conclusion = context.createProposition(bears, Copula.SomeISNOT, reptiles); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eao:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL bears IS fur\n" + 
        		"SOME bears ISNOT reptiles]", result.get().toString());
   }

    @Test
    public void camestres_2() {
    	Proposition conclusion = context.createProposition(reptiles, Copula.ISNOT, bears); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aee:\n" + 
        		"ALL bears IS fur\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL reptiles ISNOT bears]", result.get().toString());
   }

    @Test
    public void calemes_4() {
    	Proposition conclusion = context.createProposition(fur, Copula.ISNOT, snakes); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aee:\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL fur ISNOT snakes]", result.get().toString());
   }

    @Test
    public void calemos_4() {
    	Proposition conclusion = context.createProposition(fur, Copula.SomeISNOT, snakes); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aeo:\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"SOME fur ISNOT snakes]", result.get().toString());
   }

    @Test
    public void celarent_1() {
    	Proposition conclusion = context.createProposition(snakes, Copula.ISNOT, fur); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eae:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"ALL snakes ISNOT fur]", result.get().toString());
   }

    @Test
    public void barbara_1() {
    	Proposition conclusion = context.createProposition(greeks, Copula.IS, mortal); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aaa:\n" + 
        		"ALL men IS mortal\n" + 
        		"ALL greeks IS men\n" + 
        		"ALL greeks IS mortal]", result.get().toString());
    }
    
    /*
     * A, E, I and O  = 19 syllogisms are not covered by prolog
     */
    @Test
    public void barbari_1() {
    	Proposition conclusion = context.createProposition(greeks, Copula.SomeIS, mortal); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aai:\n" + 
        		"ALL men IS mortal\n" + 
        		"ALL greeks IS men\n" + 
        		"SOME greeks IS mortal]", result.get().toString());
    }

    @Test
    public void celaront_1() {
    	Proposition conclusion = context.createProposition(snakes, Copula.SomeISNOT, fur); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eao:\n" + 
        		"ALL reptiles ISNOT fur\n" + 
        		"ALL snakes IS reptiles\n" + 
        		"SOME snakes ISNOT fur]", result.get().toString());
    }
    
    @Test
    public void baroco_2() {
    	Proposition conclusion = context.createProposition(websites, Copula.SomeISNOT, informative); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aoo:\n" + 
        		"ALL informative IS useful\n" + 
        		"SOME websites ISNOT useful\n" + 
        		"SOME websites ISNOT informative]", result.get().toString());
    }
    
    @Test
    public void bocardo_3() {
    	Proposition conclusion = context.createProposition(mammals, Copula.SomeISNOT, tails); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[oao:\n" + 
        		"SOME cats ISNOT tails\n" + 
        		"ALL cats IS mammals\n" + 
        		"SOME mammals ISNOT tails]", result.get().toString());
    }
    
    @Test
    public void bamalip_4() {
    	Proposition conclusion = context.createProposition(mortal, Copula.SomeIS, greeks); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aai:\n" + 
        		"ALL greeks IS men\n" + 
        		"ALL men IS mortal\n" + 
        		"SOME mortal IS greeks]", result.get().toString());
    }
    
    @Test
    public void camestros_2() {
    	Proposition conclusion = context.createProposition(humans, Copula.SomeISNOT, horses); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aeo:\n" + 
        		"ALL horses IS hooves\n" + 
        		"ALL humans ISNOT hooves\n" + 
        		"SOME humans ISNOT horses]", result.get().toString());
    }
    
    @Test
    public void felapton_3() {
    	Proposition conclusion = context.createProposition(plants, Copula.SomeISNOT, animals); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eao:\n" + 
        		"ALL flowers ISNOT animals\n" + 
        		"ALL flowers IS plants\n" + 
        		"SOME plants ISNOT animals]", result.get().toString());
    }
    
    @Test
    public void darapti_3() {
    	Proposition conclusion = context.createProposition(rhombuses, Copula.SomeIS, rectangles); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aai:\n" + 
        		"ALL squares IS rectangles\n" + 
        		"ALL squares IS rhombuses\n" + 
        		"SOME rhombuses IS rectangles]", result.get().toString());
    }
    
    @Test
    public void ferio_1() {
    	Proposition conclusion = context.createProposition(reading, Copula.SomeISNOT, fun); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eio:\n" + 
        		"ALL homework ISNOT fun\n" + 
        		"SOME reading IS homework\n" + 
        		"SOME reading ISNOT fun]", result.get().toString());
    }
    
    @Test
    public void festino_2() {
    	Proposition conclusion = context.createProposition(reading2, Copula.SomeISNOT, fun2); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eio:\n" + 
        		"ALL fun2 ISNOT homework\n" + 
        		"SOME reading2 IS homework\n" + 
        		"SOME reading2 ISNOT fun2]", result.get().toString());
    }
    
    @Test
    public void ferison_3() {
    	Proposition conclusion = context.createProposition(reading3, Copula.SomeISNOT, fun3); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eio:\n" + 
        		"ALL homework ISNOT fun3\n" + 
        		"SOME homework IS reading3\n" + 
        		"SOME reading3 ISNOT fun3]", result.get().toString());
    }

    @Test
    public void fresison_4() {
    	Proposition conclusion = context.createProposition(reading4, Copula.SomeISNOT, fun4); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eio:\n" + 
        		"ALL fun4 ISNOT work\n" + 
        		"SOME work IS reading4\n" + 
        		"SOME reading4 ISNOT fun4]", result.get().toString());
    }
    
    @Test
    public void darii_1() {
    	Proposition conclusion = context.createProposition(pets, Copula.SomeIS, fur); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aii:\n" + 
        		"ALL rabbits IS fur\n" + 
        		"SOME pets IS rabbits\n" + 
        		"SOME pets IS fur]", result.get().toString());
    }
    
    @Test
    public void datisi_3() {
    	Proposition conclusion = context.createProposition(pets3, Copula.SomeIS, fur3); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[aii:\n" + 
        		"ALL rabbits IS fur3\n" + 
        		"SOME rabbits IS pets3\n" + 
        		"SOME pets3 IS fur3]", result.get().toString());
    }
    
    @Test
    public void disamis_3() {
    	Proposition conclusion = context.createProposition(fur3, Copula.SomeIS, pets3); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[iai:\n" + 
        		"SOME rabbits IS pets3\n" + 
        		"ALL rabbits IS fur3\n" + 
        		"SOME fur3 IS pets3]", result.get().toString());
    }
    
    @Test
    public void dimatis_4() {
    	Proposition conclusion = context.createProposition(fur4, Copula.SomeIS, pets4); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[iai:\n" + 
        		"SOME pets4 IS rabbits\n" + 
        		"ALL rabbits IS fur4\n" + 
        		"SOME fur4 IS pets4]", result.get().toString());
    }
    
    @Test
    public void fesapo_4() {
    	Proposition conclusion = context.createProposition(plants, Copula.SomeISNOT, birds); 
        Optional<List<Syllogism>> result = context.validate(conclusion);
        System.out.println("\n"+Thread.currentThread().getStackTrace()[1].getMethodName()+" = "+result.get().toString());
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
        Assert.assertEquals("[eao:\n" + 
        		"ALL birds ISNOT flowers\n" + 
        		"ALL flowers IS plants\n" + 
        		"SOME plants ISNOT birds]", result.get().toString());
    }
}
