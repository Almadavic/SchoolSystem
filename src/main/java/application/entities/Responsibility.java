package application.entities;

import application.entities.users.Principal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_responsabilities")
public class Responsibility {

      @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Setter(AccessLevel.NONE)
      private Long id;
      private String name;

      @ManyToMany(mappedBy = "responsibilities")
      @Setter(AccessLevel.NONE)
      private List<Principal> principals = new ArrayList<>();

      public Responsibility(String name) {
          this.name=name;
      }

}
