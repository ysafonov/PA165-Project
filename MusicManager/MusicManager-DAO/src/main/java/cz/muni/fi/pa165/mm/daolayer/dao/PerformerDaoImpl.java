package cz.muni.fi.pa165.mm.daolayer.dao;

import cz.muni.fi.pa165.mm.daolayer.entity.Performer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marek Barinka; 456295
 */
@Repository
public class PerformerDaoImpl implements PerformerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Performer p) {
        em.persist(p);
    }

    @Override
    public List<Performer> findAll() {
        return em.createQuery("SELECT p FROM Performer p", Performer.class).getResultList();
    }

    @Override
    public Performer findById(Long id) {
        return em.find(Performer.class, id);
    }

    @Override
    public List<Performer> findByName(String name) {
        return em.createQuery("select p from Performer p where p .name = :name", Performer.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public void update(Performer p) {
        em.persist(em.merge(p));
    }

    @Override
    public void remove(Performer p) {
        em.remove(em.merge(p));
    }

}
