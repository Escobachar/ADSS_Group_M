import java.util.*;
public class Role {
    private String roleName;
    private List<String> access;

    public boolean equals (Object o){
        if(o instanceof Role)
        {
            Role r=(Role)o;
            return r.roleName.equals(this.roleName);
        }
        return false;
    }
}
