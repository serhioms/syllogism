/****************************************************************************
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ***************************************************************************/
package net.ctalkobt;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.mss.syllogism.Copula;
import ca.mss.syllogism.Syllogism;
import ca.mss.syllogism.SyllogisticReasoning;
import ca.mss.syllogism.Term;

/**
 *
 * @author Craig.Taylor
 */
public class SyllogismTest {
    private SyllogisticReasoning context;;
    private Term mMan;
    private Term mMammel;
    private Term mAnimal;
    private Term mInanimate;

    /**
     * Test setup.
     */
    @Before
    public void setUp() {
        context = new SyllogisticReasoning();

        mMan = context.createTerm("Man");
        mMammel = context.createTerm("Mammel");
        mAnimal = context.createTerm("Animal");
        mInanimate = context.createTerm("Inanimate");

        context.addProposition(mMan, Copula.IS, mMammel);
        context.addProposition(mMammel, Copula.IS, mAnimal);
    }
    
    /**
     * Given:  X is Y. 
     * ? X is Y implies true
     */
    @Test
    public void testDirectSyllogism() {
        /**
         * Should return true
         */
        Optional<List<Syllogism>> result = context.validate(mMan, Copula.IS, mMammel);
        Assert.assertTrue(result.isPresent() && !result.get().isEmpty());
    }
    
    /**
     * Given X is Y, Y is Z
     * ? X is Z implies true. 
    */
    @Test
    public void testInDirectSyllogism() {
        /**
         * Should return true
         */
        Optional<List<Syllogism>> result = context.validate(mMan, Copula.IS, mAnimal);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(!result.get().isEmpty());

    }

    /**
     * Given X is Y, Y is Z
     * ? X is A implies (unknown).
     */
    @Test
    public void testUnknownSyllogims() {
        /**
         * Should return false
         */
        Optional<List<Syllogism>> result = context.validate(mMan, Copula.IS, mInanimate);
        Assert.assertTrue(!result.isPresent());
    }

    @Test
    public void testSimpleNotEquivality() {
        Term mA = context.createTerm("A");
        Term mB = context.createTerm("B");
        
        context.addProposition(mA, Copula.ISNOT, mB);
        
        Optional<List<Syllogism>> result = context.validate(mA, Copula.ISNOT, mB);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(!result.get().isEmpty());
    }

    @Test
    public void testIndirectNonEquivality() {
        Term mA = context.createTerm("A");
        Term mB = context.createTerm("B");
        Term mC = context.createTerm("C");
        
        context.addProposition(mA, Copula.ISNOT, mB);
        context.addProposition(mB, Copula.ISNOT, mC);
        
        Optional<List<Syllogism>> result = context.validate(mA, Copula.ISNOT, mC);
        Assert.assertTrue(!result.isPresent());
    }
    
}
