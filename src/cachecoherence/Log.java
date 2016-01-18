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
public class Log implements Runnable {
    
    //public static String[][] Files=new String[50][50];
    
    
    int Core;
    String Operation;
    int Address;
    int value;
    
     
     static Map<Integer,TransactionLog> map=new HashMap<>();
     static Map<Integer,AddressLog> map1=new HashMap<>();

    
    
    public Log(int core,int address) 
    {
         this.Core=core;
         this.Address=address;  
           
           if(map.containsKey(this.Core))
           {
              map.get(this.Core).AddAddress(this.Address);
           }
           else
           {
            TransactionLog TL=new TransactionLog(this.Address);
            map.put(this.Core,TL);
           }
           
           if(map1.containsKey(this.Address))
           {
             map1.get(this.Address).AddCore(this.Core);             
           }
           else
           {
            AddressLog T=new AddressLog(this.Core);
            map1.put(this.Address,T);
           }
                         
    }
    
    public void run()
    {
        
    }
   /* public void SaveFiles()
    {
       for(int i=0;i<50;i++)
       {
            if(Files[i][0]==null)
           {
               Files[i][0]=this.filename;
               Files[i][1]=this.tid;
               break;
           }
           else if(Files[i][0].equals(this.filename))
           {
               for(int j=1;j<50;j++)
               {
                   if(Files[i][j]!=null)
                   {
                      if( map.get(Files[i][j]).Timestamp>this.Timestamp)
                      {
                          for(int k=50;k>j;k--)
                          {
                              if(Files[i][k-1]!=null)
                              {
                              Files[i][k]=Files[i][k-1];
                              }
                          }
                          Files[i][j]=this.tid;
                          
                          break;
                      }
                   }
                   else 
                   {
                       Files[i][j]=this.tid;
                       break;
                   }
               }
               break;
           }
                
       }
    }*/  
}
