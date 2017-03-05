/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binærtreoblig;


import java.util.Scanner; // Importerer Scannerverktøyet som skal brukes i Main.

class Node <Item> { // Lager navn Generisk. 
    Comparable romNummer;
    Item navn; // Navn blir her Generisk 
    
    
    Node venstreNode;
    Node hoyreNode;
    
    Node(Comparable romNummer, Item navn){
        this.romNummer = romNummer;
        this.navn = navn;
    }
    public String toString(){
        return navn + " bor på rom nr: " + romNummer;  // Bruker toString for å skrive ut.
    }
}


//------------------------------------------------------------------

public class BinærTreOblig <Item> {

   Node root;
    
    public void leggTilNode(Comparable romNummer, Item navn) {  // Lager metoden for å legge til noder
        Node nyNode = new Node(romNummer, navn);                // Deklarerer en ny Node
         if(root == null){                          // Dersom det ikke finnes en rot, settes ny node som rot.
            root = nyNode;              
        }
        else {
        Node fokusNode = root;  // Dersom du allerede har en rot, vil du begynne å lete/legge til fra toppen og nedover. Fokusnoden, (den noden du er på) blir satt til rot
                                // Betyr at om du har en rot, legger du til noder utifra roten
        Node forelderNode; // Lager variablen forelder (parent)
            
        while( true) {                  // Sålenge roten IKKE er lik 0, vil whileløkken gå. 
            forelderNode = fokusNode; // Sålenge forelederNoden er lik fokusnode (i første tilfelle = roten) 
                
                if(romNummer.compareTo(fokusNode.romNummer)< 0){  //Dersom verdien du setter inn er mindre enn forelder, blir den plassert som venstre Node-barn
                fokusNode = fokusNode.venstreNode;
                    
                        if(fokusNode == null){ // Hvis venstre side ikke har barn, settes verdien inn der og returneres
                        forelderNode.venstreNode = nyNode; // Her settes verdien til venstrebarnet dersom den har kommet til en "ledig" plass.
                        return;
                        }
            } 
                else { // Dersom Romnummer(ny) > fokusnoden.Romnummer kjøres disse linjene
                fokusNode = fokusNode.hoyreNode; // 
                if ( fokusNode == null){        // Hvis høyre Node-barn ikke har barn, og den nye verdien er større en fokusnode, settes inn som høyrebarn. 
                        forelderNode.hoyreNode = nyNode; // Her settes høyrebarn til nyNode, altså den nye verdien. 
                        return;
                    }
                }
            }
        }   
    }
    
    
       public void inOrderUtskrift(Node fokusNode){
        if(fokusNode != null){                      // Så lenge fokusNode eksisterer 
            inOrderUtskrift(fokusNode.venstreNode);     // Bruker rekursjon for å skrive ut treet inOrder
                System.out.println(fokusNode);
            inOrderUtskrift(fokusNode.hoyreNode);
        }
    }
      public void preOrderUtskrift(Node fokusNode){
        if(fokusNode != null){   // Så lenge fokusNode eksisterer. 
            System.out.println(fokusNode);
            preOrderUtskrift(fokusNode.venstreNode);
            preOrderUtskrift(fokusNode.hoyreNode);
        }
    }
      
       public void postOrderUtskrift(Node fokusNode){
        if(fokusNode != null){
            
            postOrderUtskrift(fokusNode.venstreNode);
            postOrderUtskrift(fokusNode.hoyreNode);
            System.out.println(fokusNode);
        }
    }
    
       
       // Lager metoden for å søke etter Noder: 
       
          
    public Node finnNode(Comparable romNummer){ // Vi ønsker å finne noden med et spesifikt nummer. Dette gjør vi ved å lete etter romnummer. 
        Node fokusNode = root;      // Setter fokusnoden til å være lik roten. Dette gjør vi for å begynne å lete i toppen av treet. 
        boolean erDetEtVenstreBarn = true;
        while(fokusNode.romNummer != romNummer){    // Så lenge romnr. til fokusnoden ikke er romnummeret vi leter etter fortsetter vi letingen nedover treet. 
            if(romNummer.compareTo(fokusNode.romNummer) < 0 ){  // Dersom romnr. er mindre enn fokusnoden, let mot venstre.
                fokusNode = fokusNode.venstreNode;
                erDetEtVenstreBarn = true;// Setter venstre barn som fokusnode.
            }
            else {
                fokusNode = fokusNode.hoyreNode;    // Her har vi gjort en sjekk om først: er romnummer lik fokusnoden, så om den er større eller mindre. avhengig av resultatet 
                 erDetEtVenstreBarn = false;
            }                                       // går den enten mot høyre eller venstre. Når den har nådd noden du leter etter, returnerer vi fokusnoden (den du søker etter).
            
            if(fokusNode == null)       // Dersom noden du søker etter ikke finnes, vil løkka lete igjennom hele treet til det kommer til en null-pointer. og fokusnoden vil være lik (null)
                return null;            // Da returnerer vi null, med meningen om at: "Noden du leter etter, finnes ikke".
        }
        return fokusNode;
    }   
    
    
    // Lager metoden for å slette Noder
    //-------------------------------------//
     public boolean slettNode(Comparable romNummer){
           Node fokusNode = root;       // Starter på toppen av treet, og velger derfor at fokusNode og forelderNode skal være lik root
           Node forelder = root;
           
                                                                 // Lager en Boolean for å sjekke om det er et venstre Node-barn. Det hjelper oss å vite hvor vi skal lete
           boolean erDetEtVenstreBarn = true;
         
           while(fokusNode.romNummer != romNummer){
               forelder = fokusNode;
              
               if (romNummer.compareTo(fokusNode.romNummer) < 0){       // Dette er stort sett det samme som metoden "finnNode" Det gir mening, fordi vi selvsagt må finne noden vi skal 
                   erDetEtVenstreBarn = true;                           // slette før vi sletter den .
                   fokusNode = fokusNode.venstreNode;                   // Om vi leter til Venstre setter vi fokusNoden til venster også
                   
               }
               else{
                   erDetEtVenstreBarn = false;            
                   fokusNode = fokusNode.hoyreNode;
               }
               if(fokusNode == null)
                   return false;   // Dersom den ikke finner noden, har den ikke noe node å slette og returnerer null.
           }
           if(fokusNode.venstreNode == null && fokusNode.hoyreNode == null){ // Betyr i praksis:  dersom fokusnoden ikke har barn, derretter sjekkes det om det er rot, venstre eller høyre barn.
               if(fokusNode==root){ // Dersom fokusnoden = root. 
               root = null;                                                     // Siden den ikke har barn, kan den slette direkte. 
               }
               else if(erDetEtVenstreBarn){
                   forelder.venstreNode = null;         // Sletter venstrebarnet. 
               }
               else {
                   forelder.hoyreNode = null;  // Sletter høyrebarnet
               }
           }
           
           else if(fokusNode.hoyreNode == null){            // Her sjekkes det om det bare er barn på venstresiden, altså: ingen høyrebarn.
               if(fokusNode == root){
                   root = fokusNode.venstreNode;
           }
               else if (erDetEtVenstreBarn){
                   forelder.venstreNode = fokusNode.venstreNode;
               }
               else {  forelder.hoyreNode = fokusNode.venstreNode; }
           }
           
           else if (fokusNode.venstreNode == null){         //her sjekke det etter om den bare har barn på høyresiden, altså: ingen venstrebarn. 
               if(fokusNode == root){                       // Hvis fokusnoden var til venstre for foreldernoden, setter vi høyrenoden til fokusnode. 
                   root = fokusNode.hoyreNode;
               }
               else if (erDetEtVenstreBarn){
                   forelder.venstreNode = fokusNode.hoyreNode;
               }
               else {
                   forelder.hoyreNode = fokusNode.hoyreNode;
               }
           }
           // Dette scenearioet forekommer når Noden du skal slette har to barn. Da må vi finne en erstatter som tar over "rot-plassen"
           else {
               Node erstattning = getErstattningsNode(fokusNode);
               if(fokusNode == root) {  // Dette sjekker om fokusnoden er lik roten, og dersom den er det, sletter vi den og erstatter den med "erstattning"
                   root = erstattning;
               }
               else if (erDetEtVenstreBarn){    // Dersom den vi slettet var et venstrebarn, setter vi erstattning til venstreNode
                   forelder.hoyreNode = erstattning;
               }
               else {
                   forelder.hoyreNode = erstattning;
               }
               erstattning.venstreNode = fokusNode.venstreNode;
           }
           return true;
       }
     
    
       // Dette hjelper på å finne ut hva vi skal erstatte de ulike noden med. 
       public Node getErstattningsNode(Node erstattetNode){
           Node erstattningsParent = erstattetNode;
           Node erstattning = erstattetNode;
           Node fokusNode = erstattetNode.hoyreNode;
           
           while (fokusNode != null){           //Sålenge det ikke er noen flere venstrebarn:
               erstattningsParent = erstattning;
               erstattning = fokusNode;
               fokusNode = fokusNode.venstreNode;
           }
           
           // Her gjennomfører vi en erstattning dersom erstattningsnoden ikke er et høyrebarn.
           if(erstattning != erstattetNode.hoyreNode){
               erstattningsParent.venstreNode = erstattning.hoyreNode;
               erstattning.hoyreNode = erstattetNode.hoyreNode;
           }
           return erstattning;
       }
    
    public static void main(String[] args) {
       Scanner in = new Scanner(System.in); // Bruker Scanner for å ta input av brukeren til "menyen"
       BinærTreOblig binærtreet = new BinærTreOblig();
       
        
        binærtreet.leggTilNode(36, "Stian Fredriksen");
        binærtreet.leggTilNode(4, "Arnulf Paulsen");
        binærtreet.leggTilNode(98, "Silje Nordgård");
        binærtreet.leggTilNode(17, "Therese Johaug");                    // Legger inn noen forskjellige verdier for å sjekke at binærtreet fungerer.
        binærtreet.leggTilNode(3, "Petter Northug");
        binærtreet.leggTilNode(25, "Erna Solberg");
       
           
        
        
      
        
        
         System.out.println("Velkommen til menyen for Hotell Binærtre. Under finner du menyen!");
        for(int i =0; i<20; i++){  // Lager en forløkke for at brukeren skal kunne bruke den mer enn en gang. Talle 20 er tilfeldig, og som en slags limit, før brukeren eventuelt må logge inn på nytt
            System.out.println("Velg:\n 1: for å slette et rom \n 2: for å søke etter et rom. \n 3: for å skrive ut treet inOrder \n 4: for å skrive ut treet preorder \n 5: for å skrive ut treet postOrder"
                    + "\n 6: for å legge til ny person \n 7: for å avslutte");
        int Valg = in.nextInt();  // Brukeren kan selv velge hvilket menyvalg han ønsker. Dette resettes selvsagt hver gang for-løkka går rundt. 
        
        if(Valg==1){ // Bruker if-setninger for å avgjøre dette. (vurderte også en switchsetning. Men var ikke så stor forskjell.
        binærtreet.preOrderUtskrift(binærtreet.root); // Skriver ut treet i preOrder
        System.out.println("Skriv inn hvilket romnummer du vil slette");
        int fjernRomNr;
        fjernRomNr = in.nextInt(); // Setter variablen lik tallet brukeren velger
        binærtreet.slettNode(fjernRomNr); // Sletter noden med verdien brukeren valgte
        System.out.println("Nøkkel " + fjernRomNr + " er fjernet \n");
     
        }
        
        else if(Valg == 2){
        System.out.println("Skriv inn hvilket rom du leter etter");
        int nodeSok = in.nextInt();
        System.out.println(binærtreet.finnNode(nodeSok)); // Kjører metoden finnNode og skriver ut denne. 
        }
        else if(Valg == 3){
            System.out.println("De utleide rommene vises i stigende rekkefølge: \n");
            binærtreet.inOrderUtskrift(binærtreet.root);
            System.out.println();
        }
        else if (Valg == 4){ 
            System.out.println("Treet vises preOrder \n");
            binærtreet.preOrderUtskrift(binærtreet.root);
            System.out.println("");
        }
        else if (Valg ==5)
        {
            System.out.println("Treet vises postOrter \n");
            binærtreet.postOrderUtskrift(binærtreet.root);
            System.out.println("");
        }
        else if (Valg == 6){
            System.out.println("Skriv inn hvilket romnummer personen bor på");
            int romnummer = in.nextInt();
            System.out.println("Skriv inn navn på personen");
            String navn = in.next();
            binærtreet.leggTilNode(romnummer, navn);
            System.out.println("Romnummer " + romnummer + " er lagt til \n");
        }
         else if(Valg == 7){
                System.out.println("Du har valgt å avslutte");
                break; // Bruker break for å komme ut av For-løkka og avbryte sekvensen. På denne måten "avsluttes menyen"
        }
        else {
            System.out.println("Ugyldig valg");
            //System.err.println("Ugyldig valg");
            
        }
        
    }
    }
    
}


