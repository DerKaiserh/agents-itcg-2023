package agents;

import jade.core.Agent;
import behaviours.RequestPerformer;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import gui.BookBuyerGui;

public class BookBuyerAgent extends Agent {
  private String tituloLibro;
  private AID[] agentesVendedores;
  private int ticker_timer = 10000;
  private BookBuyerAgent this_agent = this;
  private BookBuyerGui gui;
  
  protected void setup() {
    System.out.println("El agente comprador " + getAID().getName() + " esta listo");
    
    gui = new BookBuyerGui(this);
    gui.showGui();
    
    
    
    
  }
  
  public void pedidoCompra(String titulo){
    
    if(titulo != null && titulo.length() > 0) {
      tituloLibro = titulo;
      System.out.println("Libro: " + tituloLibro);
      
      addBehaviour(new TickerBehaviour(this, ticker_timer) {
        protected void onTick() {
          System.out.println("Tratando de comprar " + tituloLibro);
          
          DFAgentDescription template = new DFAgentDescription();
          ServiceDescription sd = new ServiceDescription();
          sd.setType("book-selling");
          template.addServices(sd);
          
          try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            System.out.println("Se han encontrado los siguientes vendedores: ");
            agentesVendedores = new AID[result.length];
            for(int i = 0; i < result.length; i++) {
              agentesVendedores[i] = result[i].getName();
              System.out.println(agentesVendedores[i].getName());
            }
            
          }catch(FIPAException fe) {
            fe.printStackTrace();
          }
          
          myAgent.addBehaviour(new RequestPerformer(this_agent));
        }
      });
    } else {
      System.out.println("No se ha especificado el titulo del libro");
      doDelete();
    }
  }
  
  
  protected void takeDown() {
    System.out.println("Agente comprador " + getAID().getName() + " terminando");
    doDelete();
    gui.dispose();
  }
  
  public AID[] getSellerAgents() {
    return agentesVendedores;
  }
  
  public String getBookTitle() {
    return tituloLibro;
  }
}
