/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cachecoherence;

/**
 *
 * @author Bala
 */
public class ExecuteCore implements Runnable {
    int Core;
    
    String CoreOperations;
    
    public ExecuteCore(int i,String operations)
    {
        this.Core=i;
        this.CoreOperations=operations;
    }
    
     public void run() 
    {
        String operation;
        String delims = "!";
        String[] tokens = this.CoreOperations.split(delims);
        for(int j=0;j<tokens.length;j++)
        {
            operation=tokens[j];
            
            ExecuteOperation request=new ExecuteOperation(this.Core,operation);
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
}
