package by.kovaliov.cd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DynamicUpdate
public class City extends BaseEntity {

  @Column(length = 50, unique = true)
  private String name;
  @Column(length = 500)
  private String description;

  public City(Long id, String name, String description) {
    super(id);
    this.name = name;
    this.description = description;
  }
}