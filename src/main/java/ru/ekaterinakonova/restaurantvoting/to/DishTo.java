package ru.ekaterinakonova.restaurantvoting.to;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DishTo extends  BaseTo{
    @NotBlank
    @Size(min=2, max=100)
    @SafeHtml
    private String description;

    @Range(min=1)
    private Integer cost;

    public DishTo() {
    }
    public DishTo(Integer id, String description, Integer cost) {
        super(id);
        this.description = description;
        this.cost = cost;
    }
    @Override
    public String toString() {
        return "DishTo{"+
                ", id="+id+
                "description='"+description+'\''+
                ", cost="+cost+'}';
    }
}
