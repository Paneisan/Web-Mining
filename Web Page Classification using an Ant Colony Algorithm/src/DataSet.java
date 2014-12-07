import java.io.*;
import java.util.*;


public class DataSet {
	
	public static int mat[][];
	public static int numOfAttr;
	public static int numOfPages;
	public static Vector <String> Classes;
	public static int limnumOfAttr;
	public static int limnumOfPages;
	public static int Classize[];
	public static int facnumOfAttr,facnumOfPages;
	public File InputConvert(String str) throws IOException
	{
		File folder=new File(str);
		File k=new File(str + "/data.txt");
		Classes=new Vector();
		//System.out.println(str);
		String q=folder.getName();
		if(q.equals("bbc"))
		{
			facnumOfAttr=10;
			facnumOfPages=1;
			Classize=new int[5];
			Classize[0]=350;
			Classize[1]=40;
			Classize[2]=40;
			Classize[3]=40;
			Classize[4]=30;
		}
		else if(q.equals("sport"))
		{
			facnumOfAttr=6;
			facnumOfPages=1;
			Classize=new int[3];
			Classize[0]=50;
			Classize[1]=50;
			Classize[2]=200;
			//Classize[3]=20;
			//Classize[4]=20;
		}
		numOfAttr=0;
		numOfPages=0;
		BufferedWriter bw=new BufferedWriter(new FileWriter(k));
		File[] listOfFiles = folder.listFiles();
		for(File file : listOfFiles)
		{
			if(file.isFile())
			{
				String ext = getExtension(file);
				if((ext.equals("terms")==true))
				{
					BufferedReader br = new BufferedReader(new FileReader(file));
					Vector <String> attr=new Vector();
					String p="";
					while((p=br.readLine()) != null)
					{
						numOfAttr++;
						attr.add(p); 
					}
					br.close();
					limnumOfAttr=numOfAttr/facnumOfAttr;
					limnumOfAttr--;
					int sz=attr.size();
					String w = null;
					if(q.equals("bbc"))
					{
						w="@relation bbc";
					}
					else if(q.equals("sport"))
					{
						w="@relation sport";
					}
					bw.write(w);
					bw.newLine();
					int j=0;
					for(int i=0;i<numOfAttr;i++)
					{
						if(i%facnumOfAttr!=0||j>=limnumOfAttr)
							continue;
						w="@attribute "+attr.elementAt(i)+" {'0','1'}";
						bw.write(w);
						bw.newLine();
						j++;
					}
					//numOfAttr--;
					System.out.println(limnumOfAttr);
					break;
				}
			}
		}
		for(File file: listOfFiles)
		{
			if(file.isFile())
			{
				String ext = getExtension(file);
				if(ext.equals("classes"))
				{
					BufferedReader br=new BufferedReader(new FileReader(file));
					String p="";
					while((p=br.readLine())!=null)
					{
						if(p.charAt(0)=='%')
							continue;
						numOfPages++;
					}
					limnumOfPages=numOfPages/facnumOfPages;
					mat=new int[limnumOfPages+5][limnumOfAttr+5];
					init(limnumOfPages+2,limnumOfAttr+2);
					br=new BufferedReader(new FileReader(file));
					while((p=br.readLine())!=null)
					{
						if(p.charAt(0)=='%')
						{
							String[] spl=p.split("\\s+");
							if(spl[0].equals("%Clusters"))
							{
								String temp="";
								int sz=spl[2].length();
								for(int i=0;i<sz;i++)
								{
									if(spl[2].charAt(i)==',')
									{
									//	System.out.println(temp);
										Classes.add(temp);
										temp="";
										continue;
									}
									temp=temp+spl[2].charAt(i);
								}
								Classes.add(temp);
								continue;
							}
							else
								continue;
						}
						String[] spl=p.split("\\s+");
						int pageno=Integer.parseInt(spl[0]);
						int classno=Integer.parseInt(spl[1]);
						if(pageno%facnumOfPages==0)
						{
							pageno=pageno/facnumOfPages;
					//		System.out.println("lim no of Attr "+limnumOfAttr+" lim no of Pages "+limnumOfPages+"\n"+pageno);
							mat[pageno][limnumOfAttr]=classno;
					//	System.out.println(mat[pageno][numOfAttr+1]+" "+pageno);
						}
					}
					break;
				}
			}
		}
		for(File file : listOfFiles){
			if(file.isFile()){
				String ext = getExtension(file);
				if(ext.equals("mtx")){
					BufferedReader br=new BufferedReader(new FileReader(file));
					String p="";
					p=br.readLine();
					p=br.readLine();
					while((p=br.readLine())!=null){
						String[] spl=p.split("\\s+");
						int Attrno=Integer.parseInt(spl[0]);
						int Pageno=Integer.parseInt(spl[1]);
						Attrno--;
						Pageno--;
						if((Attrno%facnumOfAttr==0)&&(Pageno%facnumOfPages)==0)
						{
							Attrno=Attrno/facnumOfAttr;
							Pageno=Pageno/facnumOfPages;
							if(Attrno!=limnumOfAttr)
								mat[Pageno][Attrno]=1;
						}
					}
					break;
				}
			}
		}
		int sz=Classes.size();
		String we="@attribute Class {";int bc=0;
		for(int i=0;i<sz;i++)
		{
			if(bc==1)
				we=we+",";
			bc=1;
			we=we+"'"+Classes.elementAt(i)+"'";
		}
		we=we+"}";
		bw.write(we);
		bw.newLine();
		bw.write("@data");
		bw.newLine();
		for(int i=0;i<limnumOfPages;i++)
		{
				String w="";
				if(Classize[mat[i][limnumOfAttr]]>0)
				{
					Classize[mat[i][limnumOfAttr]]--;
					for(int j=0;j<limnumOfAttr;j++){
						if(mat[i][j]==0)
							w=w+"'0',";
						else 
							w=w+"'1',";
					}
					System.out.println(i+" "+limnumOfAttr);
					w=w+"'"+Classes.elementAt(mat[i][limnumOfAttr])+"'";
					bw.write(w);
					bw.newLine();
				}
		}
		bw.close();
		return k;
	}
	public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	public static void init(int row,int col)
	{
		for(int i=0;i<=row;i++)
		{
			for(int j=0;j<=col;j++)
			{
				mat[i][j]=0;
			}
		}
	}
}
