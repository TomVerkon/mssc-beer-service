package guru.springframework.msscbeerservice.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jt on 2019-05-17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_generator")
    @SequenceGenerator(name = "beer_generator", sequenceName = "beer_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    private String beerName;
    private String beerStyle;

    @Column(unique = true)
    private String upc;

    private BigDecimal price;

    private Integer minOnHand;
    private Integer quantityToBrew;


}
