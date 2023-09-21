/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendedor;
import behaviours.OfferRequestServer;
import behaviours.PurchaseOrderServer;
import jade.core.Agent;
import java.util.Hashtable;
import jade.core.behaviours.OneShotBehaviour;
import gui.BookSellerGui;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 *
 * @author Sala 1 Pc 9
 */
public class Vendedor extends Agent{
    private Hashtable catalogo;
    private BookSellerGui miGUI;
    
    protected void setup(){
        System.out.println("Hola soy el agente vendedor " + getAID().getName());
        catalogo = new Hashtable();
        miGUI = new BookSellerGui(this);
        miGUI.showGui();
        
        DFAgentDescription dfd = new DFAgentDescription();
	  dfd.setName(getAID());
	  
	  ServiceDescription sd = new ServiceDescription();
	  sd.setType("book-selling");
	  sd.setName("book-trading");
	  dfd.addServices(sd);
	  
	  try {
	    DFService.register(this, dfd);
	  }catch(FIPAException fe) {
	    fe.printStackTrace();
	  }
	  
	  addBehaviour(new OfferRequestServer(this));
	  
	  addBehaviour(new PurchaseOrderServer(this));
        
    }
    
    protected void takeDown(){
        
         try {
	    DFService.deregister(this);
	  }catch(FIPAException fe) {
	    fe.printStackTrace();
	  }
        
        System.out.println("Finalizando el agente vendedor: " + getAID().getName());
        miGUI.dispose();
    }
    
    public void updateCatalogue(final String titulo, final int precio){
        addBehaviour(new OneShotBehaviour(){
            public void action(){
                catalogo.put(titulo, precio);
                System.out.println(titulo + " ha sido insertado en el catálogo con el precio " + precio);
            }
        });
    }
    public Hashtable getCatalogue() {
        return catalogo;
    }
}
