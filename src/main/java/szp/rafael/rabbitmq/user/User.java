package szp.rafael.rabbitmq.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class User {

  @NotNull @NotBlank
  private String name;
  @NotNull @NotBlank
  private String login;
  @NotNull @NotBlank
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
            "name='" + name + '\'' +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
