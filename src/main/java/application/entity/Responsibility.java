package application.entity;

import application.entity.users.Principal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_responsabilities")
public class Responsibility {

      @Id
      @GeneratedValue
      private Long id;
      private String name;

      @JsonIgnore
      @ManyToMany(mappedBy = "responsibilities")
      private List<Principal> principals = new ArrayList<>();

      public Responsibility() {

      }

      public Responsibility(String name) {
          this.name=name;
      }


      public void setId(Long id) {
            this.id = id;
      }


      public void setName(String name) {
            this.name = name;
      }


}
