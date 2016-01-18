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
public class TransactionLog {
    
    int[] AddrRequired=new int[50];
    
    public TransactionLog(int add )
    {
        
        for(int i=0;i<50;i++)
        {
            if(AddrRequired[i]==add)
            {
                break;
            }
            if(AddrRequired[i]==0)
            {
                AddrRequired[i]=add;
                break;
            }
        }
    }
    public void AddAddress(int add)
    {
         for(int i=0;i<50;i++)
        {
            if(AddrRequired[i]==add)
            {
                break;
            }
            if(AddrRequired[i]==0)
            {
                AddrRequired[i]=add;
                break;
            }
        }
    }
    
}
