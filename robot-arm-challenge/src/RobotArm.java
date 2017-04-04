
import java.util.*;

/**
 * Your task is to implement the RobotArm API which consists of the methods given below.
 *
 * You are free to choose how you implement it, from the way you store the content to the way it will be retrieved afterwards.
 * Feel free to add any properties or auxiliary methods as you see fit. The only requirement is that you do not
 * modify the given API methods and return the correct items.
 *
 * Handle anything that fails with the same type of exception 'SomethingWentWrongException' and add a reasonable
 * description of what went wrong.
 */

public class RobotArm {

	 Content[][] grid; 
	 int rows;
	 int cols;
	
    /**
     * Constructor for the RobotArm, it takes the size of the location the robot will interact with. Each location can
     * be thought of as a matrix where each cell can contain a content, each location is a rectangle in size and will always contain the same number
     * of columns per row.
     *
     * The top left cell should be at index (0,0)
     *
     * @param numberOfRows number of rows present in the location
     * @param numberOfColumns number of columns of the location
     */
    public RobotArm(Integer numberOfRows, Integer numberOfColumns) {
    	//0,0 will refer to the top left corner of the grid
    	if(numberOfRows < 0 || numberOfColumns < 0)
    		throw new SomethingWentWrongException("Invalid row and column size for grid");
    	rows = numberOfRows;
    	cols = numberOfColumns;
    	grid = new Content[numberOfRows][numberOfColumns];
    
    }


    /**
     * Stores a content at the specified location (row and column)
     * @param content Content which should be stored in the location
     * @param row the row the content should be stored at
     * @param column the column the content should be stored at
     * @throws SomethingWentWrongException
     */
    public void storeItemAtLocation(Content content, Integer row, Integer column) throws SomethingWentWrongException {
    	if(row < 0 || column < 0 || row >= grid.length || column >= grid[0].length ) 
    		throw new SomethingWentWrongException("Cannot store item at requested location; out of bounds");
        grid[row][column] = content;
    }


    /**
     * Retrieves the content previously stored at the given row and column. The content should not be present in the
     * location after it has been retrieved
     *
     * @param row the row the content is currently located
     * @param column the column the content is currently located
     * @return
     * @throws SomethingWentWrongException if anything goes wrong during the retrieval throw this exception with a
     * clear reason why this exception was thrown
     */
    public Content retrieveItemAtLocation(Integer row, Integer column) throws SomethingWentWrongException {
    	if(row < 0 || column < 0 || row >= grid.length || column >= grid[0].length ) 
    		throw new SomethingWentWrongException("Impossible to retrieve content from given coordinates; row and column are out of bounds");
    	if(grid[row][column] == null)
    		throw new SomethingWentWrongException("Nothing to retrieve from this location");
    	Content toRetrieve = grid[row][column];
    	grid[row][column] = null;
    	return toRetrieve;
    		
    }

    /**
     * Should remove any content currently stored in this location
     */
    public void removeAllContentFromLocation(){
    	//set every coordinate in the grid to null
        for(int j = 0; j<grid.length; j++)
        {
        	Arrays.fill(grid[j], null); 
        }
    }

    /**
     * Should retrieve the content with the given barcode. You are allowed to assume that barcodes will be unique. The content should not be present in the
     * location after it has been retrieved
     *
     * @param barcode the barcode for the sample
     * @return
     * @throws SomethingWentWrongException if anything goes wrong during the retrieval throw this exception with a
     * clear reason why this exception was thrown
     */
    public Content retrieveItemWithBarcode(String barcode) throws SomethingWentWrongException {
    	boolean found = false;
    	Content toRetrieve = null;
        for(int i = 0; i < rows; i++)
        {
        	for(int j = 0; j < cols; j++)
        	{
        		if(grid[i][j].getBarcode().equals(barcode)){
        			found = true;
        			toRetrieve = grid[i][j];	
        		}
        	}
        }
        if(!found)
        	throw new SomethingWentWrongException("No content is labeled with the barcode given, sorry!");
        return toRetrieve;
    }

    /**
     * This method should fill up the location with the given contents depending on the 'FillingStrategy'.
     *
     * If the filling strategy is ROW_WISE you should start with the first available space in the top left location
     * and fill up every column until the current row is filled. Then move to the next row.
     *
     * If the filling strategy is COLUMN_WISE you should start by filling it column wise, meaning take the first
     * column and place an item in each row until the column is filled.
     *
     *
     * @param contents list of contents to be inserted into the location
     * @param strategy the strategy of how the location should be filled up
     * @throws SomethingWentWrongException
     */

    public void fillLocationWithItems(List<Content> contents, FillingStrategy strategy) throws SomethingWentWrongException {
    	int numContents = contents.size();
    	if(contents.isEmpty())
    		throw new SomethingWentWrongException("No content to actually store into grid");
    	if(numContents > totalElements(grid))
    		throw new SomethingWentWrongException("Not enough space in location to store all contents");
    	
        switch (strategy) {
    
        case ROW_WISE:
        	for(int r = 0; r < grid.length; r++){
        		for(int c= 0; c<grid[0].length; c++){
        			grid[r][c] = contents.get((r*grid[0].length)+c); // (i*grid[0].length)+j ensures that grid[0][0] = contents.get(0), grid[0][1] = contents.get(1) etc...
        		}		
        	}
            break;
     	
        case COLUMN_WISE: //grid[0][0] = contents.get(0), grid[1][0] = contents. get(1) etc...
        	for(int r = 0; r < grid.length; r++){
        		for(int c= 0; c<grid[0].length; c++){
        			grid[r][c] = contents.get((c*grid.length)+r); // formula flipped to fill up by columns first
        		}		
        	}
            break;
        
        default: throw new SomethingWentWrongException("Invalid strategy"); 	
        }
    }
    
    public int totalElements(Content[][] g)//to calculate how many spots are in the grid
    {
    	int totalElements = 0;
    	for(int i = 0; i<g.length;i++)
    	{
    		for(int j = 0; j<g[0].length;j++)
    			totalElements ++;
    	}
    	return totalElements;
    }

    /**
     * This method should reorder all the contents currently stored in the location. The filling is either column wise or
     * row wise, depending what type of 'FillingStrategy' was passed as a parameter.
     *
     * The order should be the volume of the content in a decreasing order (highest volume first), if two contents have
     * the same volume order them alphabetically after their barcode.
     *
     * @param strategy The strategy how the items should be recorded (row or column wise)
     */
    public void reorder(FillingStrategy strategy) {
        switch(strategy){ //store 2d grid into 1d array, sort 1d array then put back contents of 1d array back into grid either by row or column
       
        case ROW_WISE:
        	Content[] temp = new Content[rows*cols];
        	int counter = 0;
        	for(int r = 0; r < rows; r++){
        		for(int c= 0; c< cols; c++){
        			temp[counter] = grid[r][c];
        			counter ++;
        		}		
        	}
        	Arrays.sort(temp, Content.volumeSort);
        	counter = 0;
        	for(int r = 0; r < rows; r++){
        		for(int c= 0; c< cols; c++){
        			grid[r][c] = temp[counter];
        			counter ++;
        		}		
        	}
        	break;
        	
        	
        case COLUMN_WISE:
        	Content[] tempC = new Content[rows*cols];
        	int counterC = 0;
        	for(int r = 0; r < rows; r++){
        		for(int c= 0; c< cols; c++){
        			tempC[counterC] = grid[r][c];
        			counterC ++;
        		}		
        	}
        	Arrays.sort(tempC, Content.volumeSort);
        	counterC = 0;
        	for(int c = 0; c < cols; c++){
        		for(int r= 0; r< rows; r++){
        			grid[r][c] = tempC[counterC];
        			counterC ++;
        		}		
        	}
        	break;
        
        default: break;
        }
    }


    /**
     * This method will be used when the lab receives an order. A customer might request to the lab to deliver a
     * certain volume of a specified 'ContentType'. The robot should be able to retrieve the required volume from
     * the location and this method should find the minimal cost to the lab which will fulfil the order.
     *
     * Even 'Content' of the same 'ContentType' might have different prices inside the location. To collect the
     * required volume, deduct it from each 'Content' you used to fulfil the order (again try to minimize the total
     * cost).
     *
     * If the given volume can't be retrieved due to the lack of content of the given type,
     * nothing should be deducted from any item.
     *
     * Therefore this method should only modify any content in the location if enough volume is present of the specified
     * type.
     *
     * Partial removal of volume from a content is allowed to fulfil the order.
     *
     * The order returned should account for any content used (through its barcode) even if the content was only
     * partially used in that order.
     *
     * Even if all the volume of a 'Content' is used for an order you do not need to remove it from the location.
     *
     * @param volume
     * @param type
     * @return
     * @throws SomethingWentWrongException
     */
    public Order fulfilOrderWithMinimalCostForVolumeAndType(Integer volume, ContentType type) throws SomethingWentWrongException {

      
        List<Content> vials = new ArrayList<>();
        boolean isThereType = false;
    	for(int r = 0; r < rows; r++){
    		for(int c= 0; c< cols; c++){
    			if(grid[r][c].getType().equals(type))
    			{
    				isThereType = true;
    				grid[r][c].setCoordinates(r,c);
    				vials.add(grid[r][c]);
    			}
    		}		
    	}
    	if(!isThereType)
    		throw new SomethingWentWrongException("Cannot fufill order because of no content of given type");
    	
    	Collections.sort(vials, Content.priceSort);
    	int volumeLeft = volume;
    	double price = 0;
    	List<String> barcodesUsed = new ArrayList<>();
    	int counter = 0;
    	while(volumeLeft != 0)
    	{
    		if(counter >= vials.size())
    			throw new SomethingWentWrongException("Cannot fufill order because not enough in stock");
    		Content c = vials.get(counter);
    		int vol = c.getVolume();
    		barcodesUsed.add(c.getBarcode());
    		if(vol <= volumeLeft)
    		{
    			volumeLeft = volumeLeft-vol;
    			price = price + c.getPrice();
    			c.setVolume(0);
    			grid[c.x][c.y] = c;
    		}
    		else{
    			int diff = c.getVolume()-volumeLeft;
    			double partialprice = ((double)volumeLeft/vol)*(c.getPrice());
    			price = price + partialprice;
    			volumeLeft = 0;
    			c.setVolume(diff);
    			grid[c.x][c.y] = c;
    		}
    		counter ++;
    	}
  
    	
    	return new Order(barcodesUsed,price,volume,type);
    	
    	
      
    }


    /**
     * This method is a little bonus, you should print out (in the console) the current content stored in the location.
     * How you decide to print it out is up to you.
     *
     * @return
     */
    public String showLocationContent(){
        throw new UnsupportedOperationException();
    }

}
