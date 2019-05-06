package szp.rafael.rabbitmq.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import szp.rafael.rabbitmq.user.event.ReceiveEvent;
import szp.rafael.rabbitmq.user.event.SendEvent;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicLong;

@Stateless
public class UserService {

  Logger logger =  LoggerFactory.getLogger(getClass());
  static AtomicLong COUNT = new AtomicLong(0L);

  @Inject
  Event<SendEvent> userEvent;

  public void sendEvent(User user) {
    SendEvent event = new SendEvent();
    event.setUser(user);
    logger.info("Sending user "+event.getUser());
    userEvent.fire(event);
  }


  public void insertUser(@Observes ReceiveEvent recv){
    long andIncrement = COUNT.incrementAndGet();

    //SIMULANDO UM ERRO E DEVOLVENDO A MENSAGEM PARA A FILA PARA QUE SEJA REPROCESSADA
    try {
      Thread.sleep(250L); //0.25 segundos de processamento (simulacao)
      if(COUNT.get() % 10 == 0){
        throw new Exception("FAZ DE CONTA QUE DEU ERRO. DEVO DEVOLVER PARA A FILA");
      }
    } catch (Exception e) {
      SendEvent send = new SendEvent();
      send.setUser(recv.getUser());
      send.setOldCount(COUNT.get());
      logger.info("Devolvendo mensagem para a fila: "+send.getUser()+" Current count:"+send.getOldCount());
      userEvent.fire(send);
    }
    String infoOldCount=recv.getOldCount()>0?"\tAntigo count: "+recv.getOldCount():"";
    logger.info("Count: "+ andIncrement +" Received user "+recv.getUser()+infoOldCount);

  }

}
