package ru.mpei.demo.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "measurements")
@NoArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private double ia;
    @Column
    private double ib;
    @Column
    private double ic;


    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", ia=" + ia +
                ", ib=" + ib +
                ", ic=" + ic +
                '}';
    }

    public Measurement(double ia, double ib, double ic) {
        this.ia = ia;
        this.ib = ib;
        this.ic = ic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return id == that.id && Double.compare(that.ia, ia) == 0 && Double.compare(that.ib, ib) == 0 && Double.compare(that.ic, ic) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ia, ib, ic);
    }
}
