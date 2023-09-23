package agents;

import java.util.Hashtable;

import behaviours.OfferRequestServer;
import behaviours.PurchaseOrderServer;
import gui.BookSellerGui;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookSellerAgent extends Agent{

	private Hashtable catalogo;
	private BookSellerGui gui;
	
	protected void setup() {
	  catalogo = new Hashtable();
	  
	  gui = new BookSellerGui(this);
	  gui.showGui();
	  
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
	
	protected void takeDown() {
	  try {
	    DFService.deregister(this);
	  }catch(FIPAException fe) {
	    fe.printStackTrace();
	  }
	  
	  gui.dispose();
	  
	  System.out.println("Agente vendedor " + getAID().getName() + "terminado");
	}
	
	public void updateCatalogue(final String titulo, final int precio) {
	  addBehaviour(new OneShotBehaviour() {
	    public void action() {
	      catalogo.put(titulo, precio);
	      System.out.println(titulo + " insertado en el catálogo con el precio " + precio);
	    }
	  });
	}
	
	public Hashtable getCatalogue() {
	  return catalogo;
	}
}
