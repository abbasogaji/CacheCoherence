/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cachecoherence;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * @author Bala
 */
public class CacheCoherence {

  public static Map<Integer,Integer> MainMemory=new ConcurrentHashMap<>();
  public static int noofcores=3;
   public static int noofintrs=0;
   public static int executedinstructions=0;
    public static void main(String[] args) throws Exception
    {
        
       for(int i=100;i<=200;i++)
       {
           MainMemory.put(i,0);
       }
         Scanner in = new Scanner(System.in);
         String path=in.next();
         
         String Inst="";
       
        FileReader file = new FileReader(path);
        BufferedReader br = new BufferedReader(file);
        List<Thread> threadList = new ArrayList<Thread>();
        List<Thread> threadList1 = new ArrayList<Thread>();
        for(int i=1;i<=noofcores;i++)
        {
             Core.InstructionQueue[i]="";
        }
        
        while((Inst = br.readLine()) != null)
                {                  
                  
                      noofintrs++;
                      Core request=new Core(noofintrs,Inst);
                      Thread thread = new Thread(request); 
                      threadList.add(thread);
                      thread.start(); 
                     //Thread.sleep(1000);                                
                   
                }
        
          br.close();
          
        for(Thread t : threadList)
         {
          // waits for this thread to die
          t.join();
         }
                     
      /*   Set<Integer> keySet9= Log.map.keySet();
         for(Integer i:keySet9)
          {
              System.out.println(i);
            
                 for(int j=0;j<5;j++)
              {
                  System.out.print("\t");
                  System.out.print(Log.map.get(i).AddrRequired[j]);
              }
                 System.out.print("\n");
          }
          
           Set<Integer> keySet10= Log.map1.keySet();
          for(Integer i:keySet10)
          {
              System.out.println(i);
             
                 for(int j=0;j<5;j++)
              {
                  System.out.print("\t");
                  System.out.print(Log.map1.get(i).CoresRequired[j]);
              }
                 System.out.print("\n");
          }
           System.out.print("\n");
           
           for(int i=1;i<=noofcores;i++)
           {
                System.out.print(Core.InstructionQueue[i]);
                
                System.out.print("\n");
           }
      */
           for(int i=1;i<noofcores+1;i++)
           {
                ExecuteCore request=new ExecuteCore(i,Core.InstructionQueue[i]);
                      Thread thread = new Thread(request); 
                      threadList1.add(thread);
                      thread.start(); 
                    
           }
           
          for(Thread t : threadList1)
         {
          // waits for this thread to die
          t.join();
         }
        // Thread.sleep(3000);
         for(int i=1;i<=CacheCoherence.noofcores;i++)
         {
             int avghittime=Cache.HitTime[i]/ExecuteOperation.HitCount[i];
             float avgMisstime=(float)Cache.MissTime[i]/ExecuteOperation.MissCount[i];
            // int Missrate=Cache.MissTime[i]/ExecuteOperation.MissCount[i];
             System.out.print("Core"+i+":"+"\n");
             System.out.print("Average Hit Time:"+avghittime+"\n");
             System.out.print("Average Miss Time:"+avgMisstime+"\n");
             //System.out.print("Average Memory Access Time:"+avgMisstime+"\n");
                
         }
         
        /* for(int i=100;i<104;i++)
         {
            System.out.print(MainMemory.get(i)); 
             System.out.print("\n");
         }*/
        
    }
}
