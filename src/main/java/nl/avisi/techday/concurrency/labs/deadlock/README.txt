Een groep Developers zit in een cirkel om een tafel tijdens hun Concurrency Techday.
Ze willen de beschikbare Laptops gebruiken om hun hands-ons te gaan doen.
Elke Developer pakt eerst de Laptop links van 'm en dan de Laptop rechts van 'm.
Dit lijkt goed te gaan, maar het duurt niet lang of enkele Developers komen helemaal 
vast te zitten.

1. Run de code en verifieer dat je de deadlock ziet gebeuren. Bewijs door een thread dump
   te doen.
   Afhankelijk van de omstandigheden kan het even duren voor de deadlock optreedt.

2. Nu je deadlock hebt, stel vast dat het inderdaad om de left en right locks gaat.
   (Gebruik de gegevsn die je in de thread dump ziet)

3. Probeer nu een oplossing te vinden. Hints:
    - er zijn uiteraard meerdere oplossingen mogelijk
    - er is een oplossing die werkt ZONDER dat je een fancy java.util.concurrent class hoeft te gebruiken
      Hint: je moet iets veranderen/toevoegen aan Laptop
    - er is natuurlijk ook een oplossing die WEL een java.util.concurrent class gebruikt. 
      Laptop hoeft hiervoor niet te worden aangepast
      Hint: denk eens over het "synchronized" keyword: wat is nou een groot nadeel van dat keyword? 
      Maar: deze oplossing heeft mogelijk wel een ander nadelig effect. Hoe heet dat effect?
    - Vraag om hulp of verdere hints als je vast komt te zitten.
 
4. Verifieer dat de deadlock verdwenen is.
