import java.util.Comparator;

public class Content {

    private String barcode;
    private String name;
    private char symbol;
    private Integer volume;
    private Double price;
    private ContentType type;
    public Integer x,y;


    /**
     * Constructor for a content which gets stored in the freezer
     * @param barcode barcode used for the content, usually printed on the tube and can be used with barcode scanners
     * @param name name given to the content, human readable
     * @param symbol abbreviation of a single character to represent the content (useful for console printing as example)
     * @param volume volume of liquid contained in the content
     * @param price price for the content, this is the total price for the current volume being stored, not the price per volume
     * @param type the type this content can be classified as
     */
    public Content(String barcode, String name, char symbol, Integer volume, Double price, ContentType type) {
        this.barcode = barcode;
        this.name = name;
        this.symbol = symbol;
        this.volume = volume;
        this.price = price;
        this.type = type;
    }

    public void setCoordinates(int r, int c)
    {
    	this.x = r;
    	this.y = c;
    }
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public static Comparator<Content> volumeSort = new Comparator<Content>(){

	@Override
	public int compare(Content o1, Content o2) { //used for reorder method in RobotArm
		if(o1.getVolume() == o2.getVolume())
			return o1.getBarcode().compareTo(o2.getBarcode());
		return o2.getVolume().compareTo(o1.getVolume()); // doing o2.compareTo rather than o1.compareTo since highest volume contents should be first when sorted 
	}};
	
    public static Comparator<Content> priceSort = new Comparator<Content>(){

	@Override
	public int compare(Content o1, Content o2) { //used for fulfilOrderWithMinimalCostForVolumeAndType in RobotArm
		Double pricePerVol1 = o1.getPrice()/o1.getVolume();
		Double pricePerVol2 = o2.getPrice()/o1.getVolume();
		return pricePerVol1.compareTo(pricePerVol2);
	}};
}
