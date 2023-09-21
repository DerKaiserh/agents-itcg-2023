/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comprador;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import behaviours.RequestPerformer;
import gui.BookBuyerGui;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 *
 * @author Maq4Sala1
 */
public class Comprador extends Agent{
    private String titulo;
    private AID[] sellerAgents;
    private int ticker_timer = 10000;
    private Comprador this_agent = this;
    
    private BookBuyerGui bbGui; 
    
    @Override
    protected void setup(){
        System.out.println("Hola soy el agente comprador." + getAID().getName());
        Object[] args = getArguments();
        if(args != null && args.length>0){
            titulo = (String)args[0];
            System.out.println("Vamos a intentar comprar el libro "+ titulo);
            addBehaviour(new TickerBehaviour (this, 10000){
                protected void onTick(){
                    System.out.println("Enviando petición a posibles vendedores");
                    
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("book-selling");
                    template.addServices(sd);
                    
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, template);
                        System.out.println("Found the following seller agents:");
                        sellerAgents = new AID[result.length];
                        for(int i = 0; i < result.length; i++) {
                            sellerAgents[i] = result[i].getName();
                            System.out.println(sellerAgents[i].getName());
                        }

                    }catch(FIPAException fe) {
                        fe.printStackTrace();
                    }

                    myAgent.addBehaviour(new RequestPerformer(this_agent));
                    
                };
            });
        } else {
            System.out.println("No se ha especificado un título de compra, intente de nuevo.");
            doDelete();
        }
    }
    
    @Override
    protected void takeDown(){
        System.out.println("Finalizando el agente comprador: "+ getAID().getName());
    }
    
    public AID[] getSellerAgents() {
        return sellerAgents;
    }
    
    public String getBookTitle() {
        return titulo;
    }
}
