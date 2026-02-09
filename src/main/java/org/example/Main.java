package org.example;

import org.example.service.DictionaryManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        DictionaryManager dictionaryManager = context.getBean("dictionaryManager", DictionaryManager.class);

        dictionaryManager.start();

        ((ClassPathXmlApplicationContext) context).close();
    }
}