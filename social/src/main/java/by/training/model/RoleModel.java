package by.training.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class RoleModel extends Model {

    private static final long serialVersionUID = -2838272686668080339L;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String            name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<UserModel>   users;

    public RoleModel() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(final List<UserModel> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role [id=" + super.getId() + ", name=" + name + "]";
    }

}
