package io.helidon.techempower.mp.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Fortune model for database and mustache template.
 */
@Entity(name = "Fortune")
@Table(name = "Fortune")
@Access(AccessType.PROPERTY)
@NamedQuery(name = "getAll",
            query = "SELECT f FROM Fortune f")
public class FortuneModel {
  private int id;
  private String message;

  // required by Hibernate
  public FortuneModel() {
  }

  public FortuneModel(int id, String message) {
    this.id = id;
    this.message = message;
  }

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  public int getId() {
    return id;
  }

  @Column(name = "message", nullable = false)
  public String getMessage() {
    return message;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}