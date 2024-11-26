package sg.com.petpal.petpal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Gender cannot be blank.")
    @Column(name = "gender")
    private Gender gender;

    @NotBlank(message = "Age cannot be blank.")
    @Column(name = "age")
    private Integer age;

    @NotBlank(message = "Is neutered cannot be blank.")
    @Column(name = "is_neutered")
    private boolean isNeutered;

    @ElementCollection
    @Column(name = "pictures")
    private List<String> pictures;

    @NotBlank(message = "Description cannot be blank.")
    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonBackReference
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "pet_data_id", referencedColumnName = "id")
    private PetData petData;
}
