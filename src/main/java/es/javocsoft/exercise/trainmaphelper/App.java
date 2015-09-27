package es.javocsoft.exercise.trainmaphelper;


/**
 * Train Map OneBox test. 
 * 
 * @author Javier Gonzalez Serrano
 * @since Dec, 2014
 * @version 1.0.0
 */
public class App 
{
    public static void main( String[] args )
    {
    	String input = "AB5,BC4,CD8,CD8,DC8,DE6,AD5,CE2,EB3,AE7";
    	if(args==null || (args!=null && args.length==0) || (args!=null && args.length>1)){
    		System.out.println("The raw data about possible station connections is required. Using default OneBox test case.");
    	}else{
    		input = args[0];
    	}
    	
    	//Parameters are OK, we continue.
    	TrainMap tMap = new TrainMap(input);    	
    	tMap.showStationsInformation();    	
    	tMap.showAllPossibleRoutes();
    	
    	String[] testCase = {"A-B-C", "A-D", "A-D-C", "A-E-B-C-D", "A-E-D"};
    	int distance = 0;
    	for(String tc:testCase){
    		distance = tMap.getRouteDistance(tc);
    		if(distance == TrainMap.NO_SUCH_ROUTE){
    			System.out.println("NO SUCH ROUTE FOR " + tc + ".");
    		}else{
    			System.out.println("Distance " + tc + " is: " + distance);
    		}
    	}
    	
    	System.out.println("Just to put some more utility to this tool I add this functionality");
    	String from = "A";
    	String to = "D";
    	distance = tMap.getShortestDistanceBetweenTwoStations("A", "D");
    	System.out.println("Shortest distance between " + from + " to " + to + " is: " + (distance!=TrainMap.NO_SUCH_ROUTE?distance:"NO SUCH ROUTE"));
    }
    
}
