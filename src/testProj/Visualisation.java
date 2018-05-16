package testProj;

/**
 * This interface contains three basic instances to which any visualisation
 * class implemented into the system will follow. It is implemented with a
 * processor class
 * @author Christopher O'Connor
 * @version 1.0
 *
 */


public interface Visualisation {
	/**
	 * This method sets the duration the visualisation will proceed to execute
	 * @param duration The length of time the visualisation will execute
	 */
	public void setDuration(int duration);
	
	/**
	 * This method sets the file path that the visualisation will be based upon
	 * @param filePath The file path the visualisation will use
	 */
	public void setFilepath(String filePath);
	
	/**
	 * This method calls the run() method to instigate the visualisation process
	 */
	public void Start();
}
