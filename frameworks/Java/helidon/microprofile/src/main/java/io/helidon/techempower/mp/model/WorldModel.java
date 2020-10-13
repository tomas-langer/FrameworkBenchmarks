package io.helidon.techempower.mp.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * World model used in database and for data transfer.
 */
@Entity(name = "World")
@Table(name = "World")
@Access(AccessType.PROPERTY)
@NamedQuery(name = "getById",
            query = "SELECT w FROM World w WHERE w.id = :id")
public class WorldModel {
    private int id;
    private int randomNumber;

    // used by Hibernate
    public WorldModel() {
    }

    public WorldModel(int id, int randomNumber) {
        this.id = id;
        this.randomNumber = randomNumber;
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "randomNumber", nullable = false)
    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int newRandom) {
        this.randomNumber = newRandom;
    }

}
