package by.training.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class RoleModel extends Model implements GrantedAuthority {

    private static final long serialVersionUID = -2838272686668080339L;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String            name;

    @JsonIgnore
    @ManyToMany(targetEntity = UserModel.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false))
    private Set<UserModel>    users;

    public RoleModel() {
        super();
    }

    public RoleModel(final String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<UserModel> getUsers() {
        return users;
    }

    public void setUsers(final Set<UserModel> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "Role [id=" + super.getId() + ", name=" + name + "]";
    }

}
