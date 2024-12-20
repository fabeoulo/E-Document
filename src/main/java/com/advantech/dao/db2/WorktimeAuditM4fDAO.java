/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.dao.AuditAction;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.WorktimeM4f;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditDisjunction;
import org.hibernate.envers.query.criteria.AuditProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class WorktimeAuditM4fDAO implements AuditAction<WorktimeM4f, Integer> {

    private final boolean selectedEntitiesOnly = false;
    private final boolean selectDeletedEntities = true;
    private final Set<String> auditColumnNames = new HashSet<>(Arrays.asList(
            new String[]{"REV", "username", "REVTSTMP"}
    ));

    private final Pattern LTRIM = Pattern.compile("^\\s+");
    private final Pattern RTRIM = Pattern.compile("\\s+$");

    Class<WorktimeM4f> clz = WorktimeM4f.class;

    @Autowired
    @Qualifier("sessionFactory2")
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    private AuditReader getReader() {
        return AuditReaderFactory.get(currentSession());
    }

    @Override
    public List findAll() {
        AuditQuery q = getReader().createQuery().forRevisionsOfEntity(clz, selectedEntitiesOnly, selectDeletedEntities);
        return q.getResultList();
    }

    public List findAll(PageInfo info) {
        AuditQuery q = getReader().createQuery().forRevisionsOfEntity(clz, selectedEntitiesOnly, selectDeletedEntities);
        info.setMaxNumOfRows(((Long) q.addProjection(AuditEntity.id().count()).getSingleResult()).intValue());

        q = getReader().createQuery().forRevisionsOfEntityWithChanges(clz, selectDeletedEntities)
                .setMaxResults(info.getRows())
                .setFirstResult((info.getPage() - 1) * info.getRows());

        if (info.getSearchField() != null) {
            addSearchQuery(info, q);
        }

        String sortOrder = info.getSord();
        String sortIndex = info.getSidx();

        AuditProperty property = auditColumnNames.contains(sortIndex) ? AuditEntity.revisionProperty(sortIndex) : AuditEntity.property(sortIndex);
        q.addOrder(sortOrder.equals("asc") || sortOrder.equals("") ? property.asc() : property.desc());

        return q.getResultList();
    }

    public String lrTrim(String s) {
        return LTRIM.matcher(RTRIM.matcher(s).replaceFirst("")).replaceFirst("");
    }

    @Override
    public Object findByPrimaryKeyAndVersion(Integer id, int version) {
        return getReader().find(clz, id, version);
    }

    @Override
    public List findByPrimaryKey(Integer id) {
        AuditQuery q = getReader().createQuery().forRevisionsOfEntityWithChanges(clz, selectDeletedEntities);
        q.add(AuditEntity.id().eq(id));
        return q.getResultList();
    }

    @Override
    public List<Number> findRevisions(Integer id) {
        return getReader().getRevisions(clz, id);
    }

    @Override
    public List forEntityAtRevision(int version) {
        AuditQuery q = getReader().createQuery().forEntitiesAtRevision(clz, version);
        return q.getResultList();
    }

    public List findModifiedAtRevision(int version) {
        AuditQuery q = getReader().createQuery().forRevisionsOfEntity(clz, true, true);
        q.add(AuditEntity.revisionNumber().eq(version));
        return q.getResultList();
    }

    public List findByDate(Integer id, PageInfo info, Date startDate, Date endDate) {
        //Group by first, get total rows number
        AuditQuery q = getReader().createQuery().forRevisionsOfEntity(clz, selectedEntitiesOnly, selectDeletedEntities);
        q.add(AuditEntity.revisionProperty("REVTSTMP").between(startDate.getTime(), endDate.getTime()));
        if (id != null) {
            q.add(AuditEntity.id().eq(id));
        }

        if (info.getSearchField() != null) {
            addSearchQuery(info, q);
        }

        info.setMaxNumOfRows(((Long) q.addProjection(AuditEntity.id().count()).getSingleResult()).intValue());

        //Paginate rows
        q = getReader().createQuery().forRevisionsOfEntityWithChanges(clz, selectDeletedEntities)
                .setMaxResults(info.getRows())
                .setFirstResult((info.getPage() - 1) * info.getRows())
                .add(AuditEntity.revisionProperty("REVTSTMP").between(startDate.getTime(), endDate.getTime()));

        if (id != null) {
            q.add(AuditEntity.id().eq(id));
        }

        if (info.getSearchField() != null) {
            addSearchQuery(info, q);
        }

        String sortOrder = info.getSord();
        String sortIndex = info.getSidx();

        AuditProperty property = auditColumnNames.contains(sortIndex) ? AuditEntity.revisionProperty(sortIndex) : AuditEntity.property(sortIndex);
        q.addOrder(sortOrder.equals("asc") || sortOrder.equals("") ? property.asc() : property.desc());

        return q.getResultList();
    }

    public Number findLastRevisions(Integer id) {
        AuditQuery q = getReader().createQuery()
                .forRevisionsOfEntity(clz, false, true)
                .addProjection(AuditEntity.revisionNumber().max());

        if (id != null) {
            q.add(AuditEntity.id().eq(id));
        }

        Number revision = (Number) q.getSingleResult();
        return revision;
    }

    public boolean isFieldChangedAtLastRevision(Integer id, String[] fieldNames) {
        AuditQuery q = getReader().createQuery()
                .forRevisionsOfEntity(clz, true, false)
                .add(AuditEntity.id().eq(id))
                .add(AuditEntity.revisionNumber().maximize().computeAggregationInInstanceContext());

        for (String field : fieldNames) {
            q.add(AuditEntity.property(field).hasChanged());
        }
        List l = q.getResultList();
        return !l.isEmpty();
    }

    public WorktimeM4f findLastStatusBeforeUpdate(Integer id) {
        AuditQuery q = getReader().createQuery()
                .forRevisionsOfEntity(clz, true, false)
                .add(AuditEntity.id().eq(id))
                .add(AuditEntity.revisionNumber().maximize().computeAggregationInInstanceContext());
        return (WorktimeM4f) q.getSingleResult();
    }

    public List findByFieldChangedInDate(Integer id, List<String> fieldNames, Date startDate, Date endDate) {
        AuditQuery q = getReader().createQuery()
                .forRevisionsOfEntity(clz, false, false)
                .add(AuditEntity.revisionProperty("REVTSTMP").between(startDate.getTime(), endDate.getTime()));

        if (id != null) {
            q.add(AuditEntity.id().eq(id));
        }

        AuditDisjunction disjunction = AuditEntity.disjunction();
        fieldNames.forEach((fieldName) -> {
            disjunction.add(AuditEntity.property(fieldName).hasChanged());
        });

        q.add(disjunction);

        List l = q.getResultList();

        return l;
    }

    private AuditQuery addSearchQuery(PageInfo info, AuditQuery q) {
        if (info.getSearchField() != null && info.get_Search() == true) {
            String searchOper = info.getSearchOper();
            String searchField = info.getSearchField();
            String searchString = info.getSearchString();
            switch (searchOper) {
                case "in":
                    String[] param = searchString.split(",");
                    q.add(AuditEntity.property(searchField).in(param));
                    break;
                case "eq":
                    q.add(AuditEntity.property(searchField).eq(searchString));
                    break;
                case "ne":
                    q.add(AuditEntity.property(searchField).ne(searchString));
                    break;
                case "bw":
                    q.add(AuditEntity.property(searchField).like(searchString, MatchMode.START));
                    break;
                case "ew":
                    q.add(AuditEntity.property(searchField).like(searchString, MatchMode.END));
                    break;
                case "cn":
                    q.add(AuditEntity.property(searchField).like(searchString, MatchMode.ANYWHERE));
                    break;
                case "lt":
                    q.add(AuditEntity.property(searchField).lt(searchString));
                    break;
                case "gt":
                    q.add(AuditEntity.property(searchField).gt(searchString));
                    break;
                default:
                    break;
            }
        }
        return q;
    }

}
