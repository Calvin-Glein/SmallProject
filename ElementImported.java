
public class ElementImported {
	private String type;
	private int xCoordinate;
	private int yCoordinate;
	private String text; 
	
	
	
	
	public ElementImported(String type, String text, int xCoordinate, int yCoordinate) {
		super();
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
