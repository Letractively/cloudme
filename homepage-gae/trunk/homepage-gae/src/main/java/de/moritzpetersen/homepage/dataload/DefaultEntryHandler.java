package de.moritzpetersen.homepage.dataload;

import de.moritzpetersen.homepage.domain.Entry;

public class DefaultEntryHandler implements EntryHandler {
    @Override
    public void handle(Entry entry) {
        System.out.println(entry.getTitle());
    }
}