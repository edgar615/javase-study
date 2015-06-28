package com.edgar.designpattern.more;
public class DDDemo
{
   public static void main(String[] args)
   {
      Asteroid theAsteroid = new Asteroid();
      SpaceShip theSpaceShip = new SpaceShip();
      ApolloSpacecraft theApolloSpacecraft = new ApolloSpacecraft();
      theAsteroid.collideWith(theSpaceShip);
      theAsteroid.collideWith(theApolloSpacecraft);
      System.out.println();

      ExplodingAsteroid theExplodingAsteroid = new ExplodingAsteroid();
      theExplodingAsteroid.collideWith(theSpaceShip);
      theExplodingAsteroid.collideWith(theApolloSpacecraft);
      System.out.println();

      Asteroid theAsteroidReference = theExplodingAsteroid;
      theAsteroidReference.collideWith(theSpaceShip);
      theAsteroidReference.collideWith(theApolloSpacecraft);
      System.out.println();

      SpaceShip theSpaceShipReference = theApolloSpacecraft;
      theAsteroid.collideWith(theSpaceShipReference);
      theAsteroidReference.collideWith(theSpaceShipReference);
      System.out.println();

      theSpaceShipReference = theApolloSpacecraft;
      theAsteroidReference = theExplodingAsteroid;
      theSpaceShipReference.collideWith(theAsteroid);
      theSpaceShipReference.collideWith(theAsteroidReference);
   }
}

class SpaceShip
{
   void collideWith(Asteroid inAsteroid)
   {
      inAsteroid.collideWith(this);
   }
}

class ApolloSpacecraft extends SpaceShip
{
   void collideWith(Asteroid inAsteroid)
   {
      inAsteroid.collideWith(this);
   }
}

class Asteroid
{
   void collideWith(SpaceShip s)
   {
      System.out.println("Asteroid hit a SpaceShip");
   }

   void collideWith(ApolloSpacecraft as)
   {
      System.out.println("Asteroid hit an ApolloSpacecraft");
   }
}

class ExplodingAsteroid extends Asteroid
{
   void collideWith(SpaceShip s)
   {
      System.out.println("ExplodingAsteroid hit a SpaceShip");
   }

   void collideWith(ApolloSpacecraft as)
   {
      System.out.println("ExplodingAsteroid hit an ApolloSpacecraft");
   }
}