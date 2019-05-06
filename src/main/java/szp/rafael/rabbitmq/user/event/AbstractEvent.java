package szp.rafael.rabbitmq.user.event;

import szp.rafael.rabbitmq.user.User;

import java.io.Serializable;

public class AbstractEvent implements Serializable {

  private User user;
  private long oldCount;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public long getOldCount() {
    return oldCount;
  }

  public void setOldCount(long oldCount) {
    this.oldCount = oldCount;
  }
}
