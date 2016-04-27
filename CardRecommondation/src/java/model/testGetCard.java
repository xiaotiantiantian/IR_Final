/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dataAccesObject.CardDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Zhirun Tian
 */
public class testGetCard {
     public static void main(String[] args) throws FileNotFoundException, IOException {
         CardDao cardDao = new CardDao();
         
         Card card = new Card("Citi Prestige",  "Master",  "Citi",  6,  5,  "/a fake path");
         Card DreamCard = cardDao.GetDreamCard(card);
//         List<Card> cardList = new ArrayList<Card>();
//         cardList = cardDao.GetCardListFromDB();
         
         
         
         System.out.println("card info:" + DreamCard.getIssuer() + DreamCard.getName());
         
     }
    
}
