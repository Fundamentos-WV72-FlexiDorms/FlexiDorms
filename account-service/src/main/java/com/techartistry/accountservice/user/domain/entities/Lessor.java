package com.techartistry.accountservice.user.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Lessor extends User {
    /**
     * -Info: UN "arrendador" puede tener MUCHAS "habitaciones"
     * -MappedBy: estará mapeando por el atributo "lessor" de la clase Room
     * -Cascade all & OrphanRemoval: cada vez que se elimine un objeto (un user) automáticamente se eliminan todos los demás datos asociados a este
     */
    @OneToMany(mappedBy = "lessor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    public Lessor() {}
}
