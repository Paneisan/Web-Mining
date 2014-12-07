

import java.util.List;

public class Ant implements Cloneable{
	private int [] rulesArray;
	private MyList instancesIndexList;
	private int ruleConsequent;
	private double ruleQuality;
	private byte [] memory;
	
	public Ant(int memorySize){
		rulesArray = new int[memorySize];
		for(int x=0; x < memorySize; x++)
			rulesArray[x] = -1;
		instancesIndexList = new MyList();
		memory = new byte[memorySize];
	}
	
	public int[] getRulesArray(){
		return rulesArray;
	}
	
	public List getInstancesIndexList(){
		return instancesIndexList;
	}
	public void setInstancesIndexList(MyList list){
		instancesIndexList = list;
	}
	
	public void setRuleConsequent(int i){
		ruleConsequent = i;
	}
	public int getRuleConsequent(){
		return ruleConsequent;
	}
	
	public void setRulesArray(int [] rulesArray){
		this.rulesArray = rulesArray;
	}
	public void clearRulesArray(){
		for(int x=0; x < rulesArray.length; x++)
			rulesArray[x] = -1;
	}
	
	public byte [] getMemory(){
		return memory;
	}
	public void setMemory(byte [] memory){
		this.memory = memory;
	}
	
	public void setRuleQuality(double d){
		ruleQuality = d;
	}
	public double getRuleQuality(){
		return ruleQuality;
	}
	
	public boolean hasRules(){
		boolean hasRules=false;
		for(int x=0; x < rulesArray.length && !hasRules; x++)
			if(rulesArray[x] != -1)
				hasRules = true;
		return hasRules;
	}
	
	public Object clone() throws CloneNotSupportedException{
		Ant antClone = new Ant(this.memory.length);
		antClone.setRulesArray((int[])this.rulesArray.clone());
		antClone.setInstancesIndexList((MyList)this.instancesIndexList);
		antClone.setRuleConsequent(this.ruleConsequent);
		antClone.setRuleQuality(this.ruleQuality);
		antClone.setMemory(this.memory);
		return antClone;
	}
}

