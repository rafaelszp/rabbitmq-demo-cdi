package szp.rafael.rabbitmq.config;

import com.rabbitmq.client.BuiltinExchangeType;
import net.reini.rabbitmq.cdi.*;
import szp.rafael.rabbitmq.user.event.ReceiveEvent;
import szp.rafael.rabbitmq.user.event.SendEvent;

import javax.enterprise.context.Dependent;

@Dependent
public class RabbitBinder extends EventBinder {

//  public static final String USER_QUEUE = "USER_QUEUE";

  @Override
  protected void bindEvents() {

    //Declarando um decoder para a mensagem
    JsonDecoder<ReceiveEvent> messageDecoder = new JsonDecoder<>(ReceiveEvent.class);
    JsonEncoder<SendEvent> objectJsonEncoder = new JsonEncoder<>();

    ExchangeDeclaration userExchange = declarerFactory()
            .createExchangeDeclaration(exchangeName())
            .withType(BuiltinExchangeType.DIRECT)
            .withAutoDelete(false)
            .withDurable(true); //gravara as mensagens em disco para que possam ser recuperadas se o servidor for reiniciado

    //Declarando a QUEUE que irá receber mensagens
    QueueDeclaration userQueue = declarerFactory()
            .createQueueDeclaration(queueName())
            .withAutoDelete(false)
            .withDurable(true)
            .withExclusiveAccess(false);

    //Fazendo bind de queue decoder e configuracoes da fila
    bind(SendEvent.class)
            .toExchange(exchangeName())
            .withRoutingKey(queueName())
            .withEncoder(objectJsonEncoder);

    bind(ReceiveEvent.class)
            .toQueue(queueName())
            .withDecoder(messageDecoder)
            .withDeclaration(userQueue)
            .withPrefetchCount(1);// ira consumir 1 mensagem por vez (se o consumer estiver instanciando uma thread)
  }

  public String queueName(){
    return System.getProperty("rabbitdemo.USER_QUEUE");
  }

  public String exchangeName(){
    return System.getProperty("rabbitdemo.USER_EXCHANGE");
  }
}
