package szp.rafael.rabbitmq.config;

import com.rabbitmq.client.Address;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;

@Singleton
@Startup
public class BindingInitializer {

  @Inject
  private RabbitBinder binder;


  @PostConstruct
  public void initialize() {
    try {
      binder.configuration()
              .addHost(Address.parseAddress(getServerHost()))
              .setUsername(getUserName())
              .setPassword(getPassword())
              .setConnectTimeout(10000)
              .setConnectRetryWaitTime(10000)
              .setRequestedConnectionHeartbeatTimeout(3)
              .setVirtualHost(getVirtualHost());
      binder.initialize();
    } catch (IOException e) {
      LoggerFactory.getLogger(getClass()).error("Unable to initialize", e);
    }

  }

  private String getVirtualHost() {
    return System.getProperty("rabbitdemo.rabbitmq.server.vhost");
  }

  private String getPassword() {
    return System.getProperty("rabbitdemo.rabbitmq.server.password");
  }

  private String getUserName() {
    return System.getProperty("rabbitdemo.rabbitmq.server.username");
  }

  private String getServerHost() {
    return System.getProperty("rabbitdemo.rabbitmq.server.host")+":"+System.getProperty("rabbitdemo.rabbitmq.server.port");
  }
}