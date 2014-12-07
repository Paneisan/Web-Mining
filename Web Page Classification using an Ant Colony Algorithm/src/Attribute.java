
public class Attribute {
	private String attributeName;
	private String [] typesArray;
	private int [] intTypesArray;
	
	public Attribute(String [] typesArray){
		this.typesArray = typesArray;
		initializeIntTypesArray();
	}
	
	private void initializeIntTypesArray(){
		intTypesArray = new int[typesArray.length];
		for(int x=0; x < typesArray.length; x++){
			intTypesArray[x] = x;
		}
	}
	
	public void setAttributeName(String name){
		attributeName = name;
	}
	public String getAttributeName(){
		return attributeName;
	}
	public String[] getTypes(){
		return typesArray;
	}
	public int[] getIntTypesArray(){
		return intTypesArray;
	}
	
	public int indexOf(String value){
		for(int x=0; x < typesArray.length; x++){
			if(value.compareTo(typesArray[x]) == 0){
				return x;
			}
		}
		return -1;
	}
}
