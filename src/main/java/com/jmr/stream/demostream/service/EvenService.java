package com.jmr.stream.demostream.service;

import com.jmr.stream.demostream.model.entity.EventEntity;
import com.jmr.stream.demostream.model.repository.EventEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Event Service
 */
@Slf4j
@Service
@AllArgsConstructor
public class EvenService {

    /**
     * EventEntity Repository
     */
    private final EventEntityRepository repository;
    /**
     * Entity manager for custom query dsl
     */
    private final EntityManager em;


    public EventEntity recordEvent(EventEntity event) {
        return repository.save(event);
    }


    public List<EventEntity> getEvents() {
        return repository.findAll();
    }


    /**
     * JPA API Criteria
     * Get events by type
     *
     * @param type type to filter
     * @return event list
     */
    public List<EventEntity> getEventsByType(String type) {
        log.debug("getEventsByType type [{}]", type);
        List<EventEntity> lista = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<EventEntity> consulta = cb.createQuery(EventEntity.class);
            Root<EventEntity> eventos = consulta.from(EventEntity.class);

            Predicate byType = null;
            if (type != null) {
                byType = cb.equal(eventos.get("type"), type);
            }

            consulta.select(eventos).where(byType);
            lista = em.createQuery(consulta).getResultList();

        } catch (Exception e) {
            log.error("getEventsByType error [{}]", e.getMessage());
        } finally {
            em.close();
        }
        log.debug("getEventsByType lista [{}]", lista);
        return lista;
    }


}
