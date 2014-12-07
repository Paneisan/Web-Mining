
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JOptionPane;

public class Input {
	private String relation;

	private DataInstance [] dataInstancesArray;
	private Attribute [] attributesArray;
	private List<String[]> attributesList;
	private List<int[]> dataInstancesList;
	
	private boolean fileIsOk;
	
	public Input(File file){
		int lol = 1;
		List<String> attributesNameList = new ArrayList<String>();
		
		attributesList = new ArrayList<String[]>();
		List attributeValuesList = new ArrayList();
		
		dataInstancesList = new ArrayList<int[]>();
		
		try{
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			for(String s=in.readLine(); s != null; s=in.readLine()) {
				if(s.startsWith("@")){
					if(s.toUpperCase().contains("RELATION") && lol == 1){
						lol = 0;
						relation = s.substring(9, s.length());
					}else if(s.toUpperCase().contains("ATTRIBUTE")){
						s = s.substring(s.indexOf(" ")+1);
						attributesNameList.add(s.substring(0, s.indexOf("{")).trim().replaceAll("'", ""));
						attributeValuesList.clear();
						
						s = s.substring(s.indexOf("{")+1, s.indexOf("}"));
						String [] v = s.split(",");
						for(int n=0; n < v.length; n++)
							v[n] = v[n].trim().replaceAll("'", "");
						attributesList.add(v);
					}else if(s.toUpperCase().contains("DATA")){
						//convert attributesList to array
						int x=0;
						attributesArray = new Attribute[attributesList.size()];
						for(ListIterator i=attributesList.listIterator(); i.hasNext();){
							Attribute attribute = new Attribute((String[])i.next());
							attribute.setAttributeName((String)attributesNameList.get(x));
							attributesArray[x] = attribute;
							x++;
						}
						readData(in);
					}
				}
			}
			
			dataInstancesArray = new DataInstance[dataInstancesList.size()];
			int n=0;
			for(ListIterator i=dataInstancesList.listIterator(); i.hasNext();){
				DataInstance dataInstance = new DataInstance((int[]) i.next());
				dataInstancesArray[n++] = dataInstance;
			}
			if(attributesArray == null)
				throw new IOException("File error");
			fileIsOk = true;
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, "<html><font face=Dialog size=3>Error reading folder.<br><br>Make sure the folder only contains nominal attributes.</font></html>", "Error", JOptionPane.ERROR_MESSAGE);
		}
        
	}
	
	public boolean fileIsOk(){
		return fileIsOk;
	}
	public int getAttributesNo(){
		return attributesArray.length;
	}
	public int getInstancesNo(){
		return dataInstancesArray.length;
	}
	public Attribute[] getAttributesArray(){
		return attributesArray;
	}
	public DataInstance[] getDataInstancesArray(){
		return dataInstancesArray;
	}
	public String getRelation(){
		return relation;
	}
	
	/**
	 * Reads instances line by line
	 */
	private void readData(BufferedReader in) throws IOException{
		for(String s2=in.readLine(); s2 != null; s2=in.readLine()) {
			if(!s2.startsWith("%")){
				String [] v1 = s2.split(",");
				int dataArray[] = new int[attributesList.size()];
				for(int y=0; y < v1.length; y++){
					v1[y] = v1[y].trim().replaceAll("'", "");
					dataArray[y] = attributesArray[y].indexOf(v1[y]);
				}
				dataInstancesList.add(dataArray);
			}
		}
	}
	
}
