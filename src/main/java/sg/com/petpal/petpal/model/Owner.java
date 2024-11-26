package sg.com.petpal.petpal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name")
    private String name;

    // Fix: Use @ElementCollection to handle non-entity collection
    // Remember to not allow duplicate ids
    @Valid
    @ElementCollection
    @Column(name = "ownerMatches")
    private List<ValidOwnerMatches> ownerMatches;

    @NotBlank(message = "Area location cannot be blank.")
    @Column(name = "areaLocation")
    private String areaLocation;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private OwnerAuth ownerAuth;

    public void setOwnerAuth(OwnerAuth ownerAuth) {
        this.ownerAuth = ownerAuth;
        if (ownerAuth != null) {
            ownerAuth.setOwner(this);
        }
    }

    @OneToMany (mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pet> pets;

    // public Owner(String name, @Valid List<ValidOwnerMatches> ownerMatches, String areaLocation) {
    //     this();
    //     this.name = name;
    //     this.ownerMatches = ownerMatches;
    //     this.areaLocation = areaLocation;
    // }

    // Dexter - Start
    @ManyToMany(mappedBy = "owners", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private List<ChatMessage> chatMessages;
    // Dexter - End
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable // Added this annotation
class ValidOwnerMatches {

    @NotNull(message = "Value cannot be null")
    @Column(name = "value")
    //@Min(value = 1, message = "Value must be greater than or equal to 1")
    private Long value;
}
