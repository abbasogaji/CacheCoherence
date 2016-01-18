/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cachecoherence;

/**
 *
 * @author Bala
 */
public class Core implements Runnable{
   int instno;
    int Core;
   String Operation;
   int Address;
   int Value;
   public static String[] InstructionQueue=new String[CacheCoherence.noofcores+1];
   
       
   public Core(int no,String line) 
 {
             this.instno=no;
            String delims = "-";
            String[] tokens = line.split(delims);
             this.Core=Integer.parseInt(tokens[0]);
           this.Operation =tokens[1];         
            this.Address=Integer.parseInt(tokens[2]);  
            if(this.Operation.equals("W"))
            {
            this.Value=Integer.parseInt(tokens[3]);   
            }
            
             
 }
      
     public void run() 
    {
        if(InstructionQueue[this.Core]=="")
        {
             if(this.Operation.equals("R"))
             {
               InstructionQueue[this.Core]= this.instno+"-"+this.Operation+"-"+this.Address;
             }
            else
            {
             InstructionQueue[this.Core]= this.instno+"-"+this.Operation+"-"+this.Address+"-"+this.Value;
            }
        }
        else
        {
           if(this.Operation.equals("R"))
           {
              InstructionQueue[this.Core]= InstructionQueue[this.Core]+"!"+this.instno+"-"+this.Operation+"-"+this.Address+"";
           }
           else
             {
                InstructionQueue[this.Core]= InstructionQueue[this.Core]+"!"+this.instno+"-"+this.Operation+"-"+this.Address+"-"+this.Value;
             }
        }
      Log request=new Log(this.Core,this.Address);
        Thread thread = new Thread(request);
        thread.start();
        try{
        thread.join();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
}
