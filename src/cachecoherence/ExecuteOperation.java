/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cachecoherence;



/**
 *
 * @author Bala
 */
public class ExecuteOperation implements Runnable {
    int instno;
   int Core;
   String Operation;
   int Address;
   int Value;
      private final Object lock = new Object();
     public static int[] HitCount=new int[CacheCoherence.noofcores+1];
     public static int[] MissCount=new int[CacheCoherence.noofcores+1];
      
    public ExecuteOperation(int i,String operation)
    {
        this.Core=i;
         String delims = "-";
            String[] tokens = operation.split(delims);
            this.instno= Integer.parseInt(tokens[0]);
           this.Operation =tokens[1];         
            this.Address=Integer.parseInt(tokens[2]);  
            if(this.Operation.equals("W"))
            {
            this.Value=Integer.parseInt(tokens[3]); 
            }
    }
    
     public void run() 
    {
      /*  int go=0;
        do{
            if(CacheCoherence.executedinstructions==this.instno-1)
            {
                go=1;
            }
        }while(go==0);*/
         Cache cache=new Cache(); 
     if(this.Operation.equals("R"))
     {
       //  synchronized (lock) 
       //  {
         int found=cache.FindBlock(this.Address,this.Core,"R",this.Value);
         if(found==-1)
         {
             this.MissCount[this.Core]++;
         System.out.println("\n");
          int op=cache.InsertBlock(this.Address,this.Core,this.Operation,this.Value);
         System.out.println("\n Read at "+this.Address+" by "+this.Core+" is "+op+"");
         
         }
         else
          {
               
          System.out.println("\n From Cache : Read at "+this.Address+" by "+this.Core+" is "+found+"");
          }
        // }
     }
     if(this.Operation.equals("W"))
     {
       //  synchronized (lock)
        // {
         int found=cache.FindBlock(this.Address,this.Core,"W",this.Value);
         if(found==-1)
         {
            this.MissCount[this.Core]++;
         System.out.println("\n");
         System.out.println("\n write at "+this.Address+" by "+this.Core+" is succesfull");
         cache.InsertBlock(this.Address,this.Core,this.Operation,this.Value);
         }
         else
          {
              
           System.out.println("\n");
           System.out.println("\n write at "+this.Address+" by "+this.Core+" is succesfull");
          }
        
        
       //  }
        
     }
        CacheCoherence.executedinstructions++;
        
    }
    
}
