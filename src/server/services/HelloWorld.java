package server.services;

public class HelloWorld implements Runnable{

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + "Numéro 1");
            //On simule un traitement long en mettant en pause le Thread pendant 4 secondes
            Thread.sleep(1000);
            
            System.out.println(Thread.currentThread().getName() + "Numéro 2");
            //On simule un traitement long en mettant en pause le Thread pendant 4 secondes
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "Numéro 3");
            //On simule un traitement long en mettant en pause le Thread pendant 4 secondes
            Thread.sleep(3000);
            //On affiche le nom du thread où on se trouve
            System.out.println(Thread.currentThread().getName() + "FIN");
        } 

        catch (InterruptedException e) {
            e.printStackTrace();
        }	
	}
}