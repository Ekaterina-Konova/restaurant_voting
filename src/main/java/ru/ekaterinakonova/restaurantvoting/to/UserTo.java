package ru.ekaterinakonova.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.SafeHtml;
import ru.ekaterinakonova.restaurantvoting.HasEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTo extends BaseTo implements HasEmail, Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String lastName;

    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml
    private String email;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String firstName, String lastName, String email, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
