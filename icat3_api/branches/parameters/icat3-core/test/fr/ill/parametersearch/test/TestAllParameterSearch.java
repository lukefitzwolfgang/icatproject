/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 27 juil. 2010
 */

package fr.ill.parametersearch.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author cruzcruz
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ListParameterTest.class,
    ListComparatorTest.class,
    OperableTest.class
})
public class TestAllParameterSearch {

     public static Test suite() {
        return new JUnit4TestAdapter(TestAllParameterSearch.class);
    }

}
