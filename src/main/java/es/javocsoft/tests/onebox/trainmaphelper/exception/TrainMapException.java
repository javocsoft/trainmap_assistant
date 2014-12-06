package es.javocsoft.tests.onebox.trainmaphelper.exception;

/**
 * Train Map Assistant exception.
 * 
 * @author Javier Gonzalez Serrano
 * @since Dec, 2014
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class TrainMapException extends Exception {

	public TrainMapException() {
		super();
	}

	public TrainMapException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

	public TrainMapException(String message, Throwable cause) {
		super(message, cause);		
	}

	public TrainMapException(String message) {
		super(message);		
	}

	public TrainMapException(Throwable cause) {
		super(cause);		
	}
	
}
