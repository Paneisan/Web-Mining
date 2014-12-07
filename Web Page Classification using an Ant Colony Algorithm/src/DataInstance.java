
public class DataInstance implements Cloneable {	
	private int [] valuesArray;
	private int crossValidationGroup;
	
	public DataInstance(int [] valueArray){
		this.valuesArray = valueArray;
		crossValidationGroup = -1;
	}
	
	public int[] getValues(){
		return valuesArray;
	}
	public void setValues(int [] p){
		valuesArray = p;
	}
	
	public void setCrossValidationGroup(int group){
		crossValidationGroup = group;
	}
	public int getCrossValidationGroup(){
		return crossValidationGroup;
	}
	public int getClassValue(){
		return valuesArray[valuesArray.length-1];
	}
	
    public Object clone() throws CloneNotSupportedException{
        DataInstance myClone = (DataInstance) super.clone();  
        return myClone;
    }
}
