package com.solver.solver_be.domain.visitform.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.solver.solver_be.domain.visitform.entity.VisitForm;

import javax.persistence.EntityManager;
import java.util.List;

import static com.solver.solver_be.domain.visitform.entity.QVisitForm.visitForm;

public class VisitFormRepositoryCustomImpl implements CustomVisitFormRepository{
    private final JPAQueryFactory queryFactory;

    public VisitFormRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<VisitForm> findByGuestNameAndLocationAndTargetAndStartDateAndEndDateAndPurposeAndStatus(
            String guestName, String location, String target, String startDate, String endDate, String purpose, String status) {
        BooleanBuilder builder = new BooleanBuilder();

        if (guestName != null) {
            builder.and(visitForm.guest.name.eq(guestName));
        }
        if (location != null) {
            builder.and(visitForm.location.eq(location));
        }
        if (target != null) {
            builder.and(visitForm.target.eq(target));
        }
        if (startDate != null) {
            builder.and(visitForm.target.eq(startDate));
        }
        if (endDate != null) {
            builder.and(visitForm.target.eq(endDate));
        }
        if (purpose != null) {
            builder.and(visitForm.purpose.eq(purpose));
        }
        if (status != null) {
            builder.and(visitForm.status.eq(status));
        }

        return queryFactory.selectFrom(visitForm)
                .where(builder)
                .orderBy(
                        visitForm.guest.name.asc(),
                        visitForm.location.asc(),
                        visitForm.target.asc(),
                        visitForm.startDate.asc(),
                        visitForm.endDate.asc(),
                        visitForm.purpose.asc(),
                        visitForm.status.asc()
                )
                .fetch();
    }

}
