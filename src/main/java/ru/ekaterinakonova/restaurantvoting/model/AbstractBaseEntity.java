package ru.ekaterinakonova.restaurantvoting.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.ekaterinakonova.restaurantvoting.HasId;



@MappedSuperclass
@Access(AccessType.FIELD)
@Setter
@Getter
public abstract class AbstractBaseEntity implements HasId{
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    public AbstractBaseEntity() {

    }

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
