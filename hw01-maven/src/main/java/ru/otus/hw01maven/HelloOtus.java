package ru.otus.hw01maven;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * The class compares the current list of domain names and IP addresses with a blacklist.
 */

public class HelloOtus extends AbstractSubnetsGenerate{

    public static void main(String[] args){

       final Map<String, Subnet> usedSites =  AbstractSubnetsGenerate.getUsedSites();
       final Map<String, Subnet> blackList =  AbstractSubnetsGenerate.getBlackList();
       final MapDifference<String, Subnet> diff = Maps.difference(usedSites, blackList);

       System.out.println("\nList of all used sites:\n");

       for (Map.Entry<String, Subnet> entry: usedSites.entrySet()){
           String key = entry.getKey();
           String value = entry.getValue().getDomain();

           System.out.printf( "IP address: %s, Domain: %s \n", key, value );
       }


       System.out.println("\nList of allowed sites:\n");

       for (Map.Entry<String, Subnet> entry: diff.entriesOnlyOnLeft().entrySet()) {
           String key = entry.getKey();
           String value = entry.getValue().getDomain();

           System.out.printf( "IP address: %s, Domain: %s \n", key, value );
       }

        System.out.println("\nList of new blocked sites: \n");

        for (Map.Entry<String, Subnet> entry: diff.entriesOnlyOnRight().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getDomain();

            System.out.printf( "IP address: %s, Domain: %s \n", key, value );
        }
   }

}
