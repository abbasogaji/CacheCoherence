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
public class AddressLog {
    
     int[] CoresRequired=new int[50];
    
    public AddressLog(int core )
    {
        
        for(int i=0;i<50;i++)
        {
             if(CoresRequired[i]==core)
            {
                break;
            }
            if(CoresRequired[i]==0)
            {
                CoresRequired[i]=core;
                break;
            }
        }
    }
    public void AddCore(int core)
    {
         for(int i=0;i<50;i++)
        {
            if(CoresRequired[i]==core)
            {
                break;
            }
            if(CoresRequired[i]==0)
            {
                CoresRequired[i]=core;
                break;
            }
        }
    }
    
}
