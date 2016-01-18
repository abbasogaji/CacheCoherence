/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cachecoherence;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Bala
 */
public class Cache {
   
    public static int Blocksize=1;//no of addresses
    public static int Setsize=2;//no of blocks
    public static int Cachesize=4;//no of sets
    public static int initialized=0;
     public static int[][] Addresses=new int[CacheCoherence.noofcores+1][Setsize*Cachesize];
    public static int[][] Values=new int[CacheCoherence.noofcores+1][Setsize*Cachesize];
    public static String[][] Status=new String[CacheCoherence.noofcores+1][Setsize*Cachesize];
    public static int[] HitTime=new int[CacheCoherence.noofcores+1];
    public static int[] MissTime=new int[CacheCoherence.noofcores+1];
    
    public Cache( )
    {
       if(initialized==0)
       {
          
         for(int i=0;i<CacheCoherence.noofcores+1;i++)
         {
             for(int j=0;j<Setsize*Cachesize;j++)
             {
            Addresses[i][j]=0;
            Values[i][j]=0;
            Status[i][j]="z";
             }
            
         }
         initialized=1;
       }
    }

    public int InsertBlock(int Addr,int core,String operation,int val)
    {
      int full=1;
      int result=-1;
     int set=Addr%Cachesize;
     int index=set*Setsize;
      this.MissTime[core]=this.MissTime[core]+100;
     for(int i=index;i<index+Setsize;i++)
     {
        if(Addresses[core][i]==0)
        {
         full=0;
         Addresses[core][i]=Addr;
          
         Status[core][i]=operation;
         if(operation.equals("R"))
         {
             int other=0;
              for(int a=1;a<CacheCoherence.noofcores+1;a++)
                   {
                        for(int b=0;b<Setsize*Cachesize;b++)
                        {
                            if(Addresses[a][b]==Addr && a!=core && Status[a][b].equals("W"))
                            {
                                other=1;
                                CacheCoherence.MainMemory.put(Addr,Values[a][b]);
                                 Values[core][i]=Values[a][b];
                                 result= Values[core][i];
                                 break;
                            }
                        }
                   }
              if(other==0)
              {
                Values[core][i]=CacheCoherence.MainMemory.get(Addr);
               result= Values[core][i];
              }
         }
         else
         {
             
                Values[core][i]=val;
                 for(int a=1;a<CacheCoherence.noofcores+1;a++)
                   {
                        for(int b=0;b<Setsize*Cachesize;b++)
                        {
                            if(Addresses[a][b]==Addr && a!=core)
                            {
                                Status[a][b]="I";
                            }
                        }
                   }
                
         }
         break;
        }
     }
        if(full==1)
        {
            if(Status[core][index].equals("W"))
            {
              CacheCoherence.MainMemory.put(Addresses[core][index],Values[core][index]);
            }
           
            for(int j=index;j<index+Setsize-1;j++)
            {
                 Addresses[core][j]=Addresses[core][j+1];
                 Values[core][j]=Values[core][j+1];
                 Status[core][j]=Status[core][j+1];
               
            }
         Addresses[core][index+Setsize-1]=Addr;
           if(operation.equals("R"))
         {
               // Values[core][index+Setsize-1]=CacheCoherence.MainMemory.get(Addr);
                
                int other=0;
              for(int a=1;a<CacheCoherence.noofcores+1;a++)
                   {
                        for(int b=0;b<Setsize*Cachesize;b++)
                        {
                            if(Addresses[a][b]==Addr && a!=core && Status[a][b].equals("W"))
                            {
                                other=1;
                                CacheCoherence.MainMemory.put(Addr,Values[a][b]);
                                 Values[core][index+Setsize-1]=Values[a][b];
                                 result= Values[core][index+Setsize-1];
                                 break;
                            }
                        }
                   }
              if(other==0)
              {
                Values[core][index+Setsize-1]=CacheCoherence.MainMemory.get(Addr);
               result= Values[core][index+Setsize-1];
              }
         }
         else
           {
                Values[core][index+Setsize-1]=val;
                for(int a=1;a<CacheCoherence.noofcores+1;a++)
                   {
                        for(int b=0;b<Setsize*Cachesize;b++)
                        {
                            if(Addresses[a][b]==Addr && a!=core)
                            {
                                Status[a][b]="I";
                            }
                        }
                   }
         }
         Status[core][index+Setsize-1]=operation;
       }
      
     return result;
   }
     public int FindBlock(int Addr,int core,String operation,int val)
     {
                  
     int set=Addr%Cachesize;
     int index=set*Setsize;
     
     for(int i=index;i<index+Setsize;i++)
     {
        if(Addresses[core][i]==Addr)
        {
            ExecuteOperation.HitCount[core]++;
            this.HitTime[core]= this.HitTime[core]+1;
            if(Status[core][i].equals("W"))
            {
                if(operation.equals("W"))
                {
                     //this.HitTime[core]= this.HitTime[core]+10;
                    for(int a=1;a<CacheCoherence.noofcores+1;a++)
                   {
                        for(int b=0;b<Setsize*Cachesize;b++)
                        {
                            if(Addresses[a][b]==Addr && a!=core)
                            {
                                Status[a][b]="I";
                            }
                        }
                   }
                      
                    Values[core][i]=val;
                   return Values[core][i];
                }
                if(operation.equals("R"))
                   return Values[core][i];
                
            }
            if(Status[core][i].equals("R"))
            {
                if(operation.equals("W"))
                {
                     //this.HitTime[core]= this.HitTime[core]+10;
                   for(int a=1;a<CacheCoherence.noofcores+1;a++)
                   {
                        for(int b=0;b<Setsize*Cachesize;b++)
                        {
                            if(Addresses[a][b]==Addr && a!=core)
                            {
                                Status[a][b]="I";
                            }
                        }
                   }
                    Values[core][i]=val;                   
                   Status[core][i]="W";
                   return Values[core][i];
                }
                if(operation.equals("R"))
                   return Values[core][i];
                
            }
            if(Status[core][i].equals("I"))
            {
                ExecuteOperation.MissCount[core]++;
                 this.MissTime[core]= this.MissTime[core]+10;
                for(int a=1;a<CacheCoherence.noofcores+1;a++)
                   {
                        for(int b=0;b<Setsize*Cachesize;b++)
                        {
                            if(Addresses[a][b]==Addr && Status[a][b].equals("W")&& a!=core)
                            {
                               
                               Status[core][i]=operation;                               
                               CacheCoherence.MainMemory.put(Addr, Values[a][b]);
                               if(operation.equals("W"))
                               {
                                   Status[a][b]="I";
                                   Values[core][i]=val;
                                                                                                         
                               }
                               else
                               {
                                   Values[core][i]=Values[a][b];
                               }
                               return Values[core][i];
                            
                            }
                        }
                   }
                 // InsertBlock(Addr,core,operation);
                 
            }
            
                     
        }
             
     }
     return -1;
    }
        
}
    

