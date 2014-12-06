package es.javocsoft.tests.onebox.trainmaphelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Unit test for simple App.
 * 
 * @author Javier Gonzalez Serrano
 * @since Dec, 2014
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest 
    extends TestCase
{
	/** TEST Data: Change to desired */
	private final static String INPUT = "AB5,BC4,CD8,CD8,DC8,DE6,AD5,CE2,EB3,AE7";
	private final String[] testCase = {"A-B-C", "A-D", "A-D-C", "A-E-B-C-D", "A-E-D"};
	private final int[] expected = {9,5,13,22,TrainMap.NO_SUCH_ROUTE};
	
	
	
	private static TrainMap tMap = null;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        tMap = new TrainMap(INPUT);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testOneboxT1(){
    	System.out.println("Showing all station information:");
    	tMap.showStationsInformation();
    }
    
    public void testOneboxT2(){
    	System.out.println("Showing all possible routes:");
    	tMap.showAllPossibleRoutes();
    }
    
    /**
     * OneBox Required test.
     */    
    public void testOneboxT3()
    {   
    	System.out.println("Input: " + tMap.getRawMap());
    	int distance = 0;
    	int i = 0;
    	for(String tc:testCase){
    		distance = tMap.getRouteDistance(tc);
    		if(distance == TrainMap.NO_SUCH_ROUTE){
    			System.out.println("NO SUCH ROUTE FOR " + tc + ".");    			
    		}else{
    			System.out.println("Distance " + tc + " is: " + distance);
    		}
    		assertEquals(expected[i], distance);
    		i++;
    	}
    }
    
    /**
     * This gets the shortest distance between 
     * two points because normally people do not want 
     * to know the details of the route, only how 
     * much time or distance is :)
     */
    public void testOneboxT4Extra() {
    	String from = "A";
    	String to = "D";
    	
    	System.out.println("\n\nThis gets the shortest distance between\n "
    			+ "two points because normally people only want to know\n "
    			+ "not the details of the riute, just how much time "
    			+ "or distance is :)");
    	System.out.println("Input: " + tMap.getRawMap());
    	
    	int shortestDistance = tMap.getShortestDistanceBetweenTwoStations(from, to);
    	System.out.println("Shortest distance between " + from + " to " + to + " is: " + (shortestDistance!=TrainMap.NO_SUCH_ROUTE?shortestDistance:"NO SUCH ROUTE"));
    	
    	from = "A";to = "E";
    	shortestDistance = tMap.getShortestDistanceBetweenTwoStations(from, to);
    	System.out.println("Shortest distance between " + from + " to " + to + " is: " + (shortestDistance!=TrainMap.NO_SUCH_ROUTE?shortestDistance:"NO SUCH ROUTE"));
    	
    }
    
    
}
