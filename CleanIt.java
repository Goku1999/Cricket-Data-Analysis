import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
public class CleanIt
{
    //Order Name,run,ball,4s,6s
    String line1="",line2="";
    int p=-1,b=-1;
    String batting1[][]=new String[11][7];
    String batting2[][]=new String[11][7];
    //order name,overs,runs,wickets
    String balling1[][]=new String[11][4];
    String balling2[][]=new String[11][4];
    String fow1[]=new String[10];
    String fow2[]=new String[10];
    String pover1[]=new String[50];
    String pover2[]=new String[50];
    int runs1=0,wickets1=0;
    int runs2=0,wickets2=0;
    
    public static void main(String args[])
    {
        try
        {
        	File f=new File("C:\\Users\\Sankalp\\Documents\\cricketDataCSV");
        	File f1[]=f.listFiles();
        	for(int z=0;z<f1.length;z++)
        	{
        		if(f1[z].getName().length()==0)
        		{
        			continue;
        		}
        		if(!f1[z].getName().endsWith(".csv"))
        		{
        			continue;
        		}
	            FileReader fr=new FileReader(f1[z]);
	            BufferedReader fbr=new BufferedReader(fr);
	            String s=fbr.readLine();
	            
	            String t1=fbr.readLine();
	            String ar[]=t1.split(",");
	            String team1=ar[2];
	            
	            t1=fbr.readLine();
	            ar=t1.split(",");
	            String team2=ar[2];
	            
	            t1=fbr.readLine();
	            t1=fbr.readLine();
	            ar=t1.split(",");
	            String season=ar[2];
	            
	            t1=fbr.readLine();
	            ar=t1.split(",");
	            String date=ar[2];
	            String clean=team1.replace(" ", "")+"vs"+team2.replace(" ","")+date.replace("/","");
	            System.out.println(clean);
	            
	            String mom="",toss="",winner="";
	            int margin=0;
	            boolean dl=false;
	            boolean run=true;
	            while(!(s=fbr.readLine()).startsWith("ball"))
	            {
	                ar=s.split(",");
	                String d=ar[1];
	                if(d.equals("toss_winner"))
	                {
	                    toss=ar[2];
	                }
	                else if(d.startsWith("player"))
	                {
	                    mom=ar[2];
	                }
	                else if(d.equals("winner"))
	                {
	                    winner=ar[2];
	                }
	                else if(d.equals("winner_wickets"))
	                {
	                    run=false;
	                    margin=Integer.parseInt(ar[2]);
	                }
	                else if(d.contentEquals("winner_runs"))
	                {
	                    margin=Integer.parseInt(ar[2]);
	                }
	                else if(d.equals("method"))
	                {
	                    dl=true;
	                }
	            }
	            int counter=0;
	            CleanIt ob=new CleanIt();
	            boolean innings=true;
	            int pr=0;
	            int wr=0;
	            int o=0;
	            ar=s.split(",");
	            if(!team1.equals(ar[3]))
	            {
	            	String t=team1;
	            	team1=team2;
	            	team2=t;
	            }
	            String previousBall="0.0";
	            do
	            {
	            	ar=s.split(",");
	                if(innings&&ar[1].equals("2"))
	                {
	                    innings=false;
	                    ob.line1="";
	                    ob.line2="";
	                    ob.p=-1;
	                    ob.b=-1;
	                    pr=0;
	                    wr=0;
	                    o=0;
	                    previousBall="0.0";
	                }
	                String stricker=ar[4];
	                String baller=ar[6];
	                int cr=Integer.parseInt(ar[7]);
	                int er=Integer.parseInt(ar[8]);
	                boolean out=true;
	                String currentBall=ar[2];
	                if(er==0)
	                {
	                    ob.addBall(innings,stricker);
	                }
	                int index=ar[2].indexOf('.');
	                int num=Integer.parseInt(ar[2].substring(index+1));
	                if(num<=6)
	                {
	                    if(baller.startsWith("Abdul"))
	                    {
	                        ob.addBall1(innings, baller);
	                    }
	                    else
	                    {
	                        ob.addBall1(innings, baller);
	                    }
	                }
	                try
	                {
	                    String reason=ar[9];
	                    if(reason.equals("\"\""))
	                    {
	                        out=false;
	                    }
	                    String batsmen=ar[10];
	                    if(out)
	                    {
	                        ob.addWicket(innings,stricker,reason,batsmen,baller,cr,er);
	                    }
	                }
	                catch(Exception e)
	                {
	                    out=false;
	                }
	                if(!out)
	                {
	                    if(cr==4)
	                    {
	                        ob.add4(innings, stricker,baller,er);
	                    }
	                    else if(cr==6)
	                    {
	                        ob.add6(innings, stricker,baller,er);  
	                    }
	                    else
	                    {
	                        ob.addRun(innings, stricker,baller, cr, er);
	                    }
	                    if(!currentBall.substring(0,2).equals(previousBall.substring(0,2)))
	                    {
		                    if(innings)
		                    {
		                    	int to=ob.runs1-pr;
		                    	int tw=ob.wickets1-wr;
		                    	ob.pover1[o++]=to+"/"+tw;
		                    	pr=ob.runs1;
		                    	wr=ob.wickets1;
		                    }
		                    else
		                    {
		                    	int to=ob.runs2-pr;
		                    	int tw=ob.wickets2-wr;
		                    	ob.pover2[o++]=to+"/"+tw;
		                    	pr=ob.runs2;
		                    	wr=ob.wickets2;
		                    }
	                    }
	                    previousBall=currentBall;
	                }
	            }while((s=fbr.readLine())!=null);
	            fbr.close();
	            fr.close();
	            File f2=new File("C:\\Users\\Sankalp\\Documents\\cricketDataCSV\\Clean\\"+clean+".csv");
	            f2.createNewFile();
	            ob.write(f2,team1,team2,winner,mom,margin,run,dl);
        	}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void add6(boolean innings, String stricker,String baller,int er) 
    {
    	try
    	{
	    	int c=getIndex(innings,true,stricker);
	        if(innings)
	        {
	            int q=Integer.parseInt(batting1[c][4]);
	            q++;
	            batting1[c][4]=q+"";
	            q=Integer.parseInt(batting1[c][1]);
	            q+=6;
	            batting1[c][1]=q+"";
	            runs1+=6+er;
	            c=getIndex(innings,false,baller);
	            q=Integer.parseInt(balling1[c][2]);
	            q+=6;
	            balling1[c][2]=q+"";
	        }
	        else
	        {
	            int q=Integer.parseInt(batting2[c][4]);
	            q++;
	            batting2[c][4]=q+"";
	            q=Integer.parseInt(batting2[c][1]);
	            q+=6+er;
	            batting2[c][1]=q+"";
	            runs2+=6+er;
	            c=getIndex(innings,false,baller);
	            q=Integer.parseInt(balling2[c][2]);
	            q+=6+er;
	            balling2[c][2]=q+"";
	        }
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    private void add4(boolean innings, String stricker,String baller,int er)
    {
    	try
    	{
	        int c=getIndex(innings,true,stricker);
	        if(innings)
	        {
	            int q=Integer.parseInt(batting1[c][3]);
	            q++;
	            batting1[c][3]=q+"";
	            q=Integer.parseInt(batting1[c][1]);
	            q+=4;
	            batting1[c][1]=q+"";
	            runs1+=4+er;
	            c=getIndex(innings,false,baller);
	            q=Integer.parseInt(balling1[c][2]);
	            q+=4+er;
	            balling1[c][2]=q+"";
	        }
	        else
	        {
	            int q=Integer.parseInt(batting2[c][3]);
	            q++;
	            batting2[c][3]=q+"";
	            q=Integer.parseInt(batting2[c][1]);
	            q+=4;
	            batting2[c][1]=q+"";
	            runs2+=4+er;
	            c=getIndex(innings,false,baller);
	            q=Integer.parseInt(balling2[c][2]);
	            q+=4+er;
	            balling2[c][2]=q+"";
	        }
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    private void addBall(boolean innings,String stricker)
    {
    	try
    	{
	        int c=getIndex(innings,true,stricker);
	        if(innings)
	        {
	            int q=Integer.parseInt(batting1[c][2]);
	            q++;
	            batting1[c][2]=q+"";
	        }
	        else
	        {
	            int q=Integer.parseInt(batting2[c][2]);
	            q++;
	            batting2[c][2]=q+"";
	        }
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    private void addBall1(boolean innings,String baller)
    {
    	try
    	{
	        int c=getIndex(innings,false,baller);
	        if(innings)
	        {
	            int q=Integer.parseInt(balling1[c][1]);
	            q++;
	            balling1[c][1]=q+"";
	        }
	        else
	        {
	            int q=Integer.parseInt(balling2[c][1]);
	            q++;
	            balling2[c][1]=q+"";    
	        }
    	}
    	catch(Exception e)
    	{
    	}
    }
    private void addWicket(boolean innings,String stricker,String reason, String batsmen, String baller, int cr, int er)
    {
    	try
    	{
	        if(innings)
	        {
	            if(stricker.equals(batsmen))
	            {
	                int c=getIndex(innings, true, stricker);
	                if(cr!=0)
	                {
	                    int ir=Integer.parseInt(batting1[c][1]);
	                    ir+=cr;
	                    batting1[c][1]=ir+"";
	                }
	                batting1[c][5]=reason;
	                batting1[c][6]=baller;
	                runs1+=cr+er;
	            }
	            else
	            {
	                int c1=getIndex(innings,true,stricker);
	                int c2=getIndex(innings,true,batsmen);
	                if(cr!=0)
	                {
	                    int ir=Integer.parseInt(batting1[c1][1]);
	                    ir+=cr;
	                    batting1[c1][1]=ir+"";
	                }
	                batting1[c2][5]=reason;
	                batting1[c2][6]=baller;
	                runs1+=cr+er;
	            }
	            if(!(reason.contains("run")||reason.contains("obstruct")))
	            {
	            	int c=getIndex(innings,false,baller);
	            	int q=Integer.parseInt(balling1[c][3]);
	            	q++;
	            	balling1[c][3]=q+"";
	            }
	            fow1[wickets1]=runs1+"/"+(++wickets1);
	        }
	        else
	        {
	            if(stricker.equals(batsmen))
	            {
	                int c=getIndex(innings, true, stricker);
	                if(cr!=0)
	                {
	                    int ir=Integer.parseInt(batting2[c][1]);
	                    ir+=cr;
	                    batting2[c][1]=ir+"";
	                }
	                batting2[c][5]=reason;
	                batting2[c][6]=baller;
	                runs2+=cr+er;
	            }
	            else
	            {
	                int c1=getIndex(innings,true,stricker);
	                int c2=getIndex(innings,true,batsmen);
	                if(cr!=0)
	                {
	                    int ir=Integer.parseInt(batting2[c1][1]);
	                    ir+=cr;
	                    batting2[c1][1]=ir+"";
	                }
	                batting2[c2][5]=reason;
	                batting2[c2][6]=baller;
	                runs2+=cr+er;
	            }
	            if(!(reason.contains("run")||reason.contains("obstruct")))
	            {
	            	int c=getIndex(innings,false,baller);
	            	int q=Integer.parseInt(balling2[c][3]);
	            	q++;
	            	balling2[c][3]=q+"";
	            }
	            fow2[wickets2]=runs2+"/"+(++wickets2);
	        }
    	}
    	catch(Exception e)
    	{
    	}
    }
    private void addRun(boolean innings,String stricker,String baller,int cr,int er)
    {
    	try
    	{
	        int c=getIndex(innings,true,stricker);
	        int b=getIndex(innings,false,baller);
	        if(innings)
	        {
	            int ir=Integer.parseInt(batting1[c][1]);
	            ir+=cr;
	            batting1[c][1]=ir+"";
	            ir=Integer.parseInt(balling1[b][2]);
	            ir+=cr+er;
	            balling1[b][2]=ir+"";
	            runs1+=cr+er;
	        }
	        else
	        {
	            int ir=Integer.parseInt(batting2[c][1]);
	            ir+=cr;
	            batting2[c][1]=ir+"";
	            ir=Integer.parseInt(balling2[b][2]);
	            ir+=cr+er;
	            balling2[b][2]=ir+"";
	            runs2+=cr+er;
	        }
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    private int getIndex(boolean innings,boolean batting,String player)
    {
    	try
    	{
	        if(innings)
	        {
	            if(batting)
	            {
	                int c=-1;
	                if(!line1.contains(player))
	                {
	                    p++;
	                    line1=line1+player+p+",";
	                    batting1[p][0]=player;
	                    batting1[p][1]="0";
	                    batting1[p][2]="0";
	                    batting1[p][3]="0";
	                    batting1[p][4]="0";
	                    batting1[p][5]="-";
	                    batting1[p][6]="-";
	                    c=p;
	                }
	                else
	                {
	                    int i=line1.indexOf(player);
	                    i=i+player.length();
	                    String q="";
	                    while(line1.charAt(i)!=',')
	                    {
	                        q=q+line1.charAt(i);
	                        i++;
	                    }
	                    c=Integer.parseInt(q);
	                }
	                return c;
	            }
	            else
	            {
	                int c=-1;
	                if(!line2.contains(player))
	                {
	                    b++;
	                    line2=line2+player+b+",";
	                    balling1[b][0]=player;
	                    balling1[b][1]="0";
	                    balling1[b][2]="0";
	                    balling1[b][3]="0";
	                    c=b;
	                }
	                else
	                {
	                    int i=line2.indexOf(player);
	                    i=i+player.length();
	                    String q="";
	                    while(line2.charAt(i)!=',')
	                    {
	                        q=q+line2.charAt(i);
	                        i++;
	                    }
	                    c=Integer.parseInt(q);
	                }
	                return c;
	            }
	        }
	        else
	        {
	            if(batting)
	            {
	                int c=-1;
	                if(!line1.contains(player))
	                {
	                    p++;
	                    line1=line1+player+p+",";
	                    batting2[p][0]=player;
	                    batting2[p][1]="0";
	                    batting2[p][2]="0";
	                    batting2[p][3]="0";
	                    batting2[p][4]="0";
	                    batting2[p][5]="-";
	                    batting2[p][6]="-";
	                    c=p;
	                }
	                else
	                {
	                    int i=line1.indexOf(player);
	                    i=i+player.length();
	                    String q="";
	                    while(line1.charAt(i)!=',')
	                    {
	                        q=q+line1.charAt(i);
	                        i++;
	                    }
	                    c=Integer.parseInt(q);
	                }
	                return c;
	            }
	            else
	            {
	                int c=-1;
	                if(!line2.contains(player))
	                {
	                    b++;
	                    line2=line2+player+b+",";
	                    balling2[b][0]=player;
	                    balling2[b][1]="0";
	                    balling2[b][2]="0";
	                    balling2[b][3]="0";
	                    c=b;
	                }
	                else
	                {
	                    int i=line2.indexOf(player);
	                    i=i+player.length();
	                    String q="";
	                    while(line2.charAt(i)!=',')
	                    {
	                        q=q+line2.charAt(i);
	                        i++;
	                    }
	                    c=Integer.parseInt(q);
	                }
	                return c;
	            }
	        }
    	}
    	catch(Exception e)
    	{
    		return -1;
    	}
    }
    private String print(boolean innings,boolean batting)
    {
    	String r="";
        if(innings)
        {
            if(batting)
            {
                int x=0;
                r=batting1[x][0]+","+batting1[x][1]+","+batting1[x][2]+","+batting1[x][3]+","+batting1[x][4]+","+batting1[x][5]+","+batting1[x][6];
                x++;
                while(x<11&&batting1[x][0]!=null)
                {
                    r=r+"\n"+(batting1[x][0]+","+batting1[x][1]+","+batting1[x][2]+","+batting1[x][3]+","+batting1[x][4]+","+batting1[x][5]+","+batting1[x][6]);
                    x++;
                }
            }
            else
            {
                int x=0;
                int balls=Integer.parseInt(balling1[x][1]);
                String over=balls/6+"."+balls%6;
                r=balling1[x][0]+","+over+","+balling1[x][2]+","+balling1[x][3];
                x++;
                while(x<11&&balling1[x][0]!=null)
                {
                    balls=Integer.parseInt(balling1[x][1]);
                    over=balls/6+"."+balls%6;
                    r=r+"\n"+(balling1[x][0]+","+over+","+balling1[x][2]+","+balling1[x][3]);
                    x++;
                }
            }
        }
        else
        {
        	if(batting)
            {
                int x=0;
                r=batting2[x][0]+","+batting2[x][1]+","+batting2[x][2]+","+batting2[x][3]+","+batting2[x][4]+","+batting2[x][5]+","+batting2[x][6];
                x++;
                while(x<11&&batting2[x][0]!=null)
                {
                    r=r+"\n"+(batting2[x][0]+","+batting2[x][1]+","+batting2[x][2]+","+batting2[x][3]+","+batting2[x][4]+","+batting2[x][5]+","+batting2[x][6]);
                    x++;
                }
            }
            else
            {
                int x=0;
                int balls=Integer.parseInt(balling1[x][1]);
                String over=balls/6+"."+balls%6;
                r=balling2[x][0]+","+over+","+balling2[x][2]+","+balling2[x][3];
                x++;
                while(x<11&&balling2[x][0]!=null)
                {
                    balls=Integer.parseInt(balling2[x][1]);
                    over=balls/6+"."+balls%6;
                    r=r+"\n"+(balling1[x][0]+","+over+","+balling2[x][2]+","+balling2[x][3]);
                    x++;
                }
            }
        }
        return r;
    }
    private String printfow(boolean innings)
    {
    	String r="";
    	if(innings)
    	{
    		int x=0;
    		r=fow1[x++];
    		while(x<10&&fow1[x]!=null)
    		{
    			r=r+","+fow1[x];
    			x++;
    		}
    	}
    	else
    	{
    		int x=0;
    		r=fow2[x++];
    		while(x<10&&fow2[x]!=null)
    		{
    			r=r+","+fow2[x];
    			x++;
    		}
    	}
    	return r;
    }
    public String printPer(boolean innings)
    {
    	String r="";
    	if(innings)
    	{
    		int x=0;
    		r=pover1[x++];
    		while(x<50&&pover1[x]!=null)
    		{
    			r=r+","+pover1[x++];
    		}
    	}
    	else
    	{
    		int x=0;
    		r=pover2[x++];
    		while(x<50&&pover2[x]!=null)
    		{
    			r=r+","+pover2[x++];
    		}
    	}
    	return r;
    }
    public void write(File f,String team1,String team2,String winner,String mom,int margin,boolean run,boolean dl)
    {
    	try
    	{
    		FileWriter fw=new FileWriter(f);
    		BufferedWriter fbw=new BufferedWriter(fw);
    		PrintWriter pw=new PrintWriter(fbw);
            
    		pw.println(team1+",Statics");
    		pw.println("Batting");
            pw.println(print(true,true));
            pw.println("Balling");
            pw.println(print(false,false));
            pw.println("fow,"+printfow(true));
            pw.println("per over score,"+printPer(true));
            
            pw.println(team2+",Statics");
    		pw.println("Batting");
            pw.println(print(false,true));
            pw.println("Balling");
            pw.println(print(true,false));
            pw.println("fow,"+printfow(false));
            pw.println("per over score,"+printPer(false));
            String result="Result : ";
            result+=winner+" won by "+margin;
            if(run)
            {
                result+=" runs";
            }
            else
            {
                result+=" wickets";
            }
            if(dl)
            {
                result+=" (D/L Method)";
            }
            pw.println(result+".");
            pw.println("Man of the Match : "+mom);
            pw.close();
            fbw.close();
            fw.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
}