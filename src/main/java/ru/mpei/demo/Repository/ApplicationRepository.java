package ru.mpei.demo.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ApplicationRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveMeasurement(Measurement m){
        em.persist(m);
    }

    @Transactional
    public List<Measurement> getMeasurementsInInterval(int startIndex, int endIndex){
        List<Measurement> resultList = em.createQuery("select m from Measurement m where m.id between :a and :b",
                        Measurement.class)
                .setParameter("a", startIndex)
                .setParameter("b", endIndex)
                .getResultList();
        return resultList;
    }
}
