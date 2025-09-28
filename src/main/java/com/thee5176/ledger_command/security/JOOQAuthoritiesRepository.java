package com.thee5176.ledger_command.security;

import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import com.thee5176.ledger_command.record.domain.model.credential.Tables;
import com.thee5176.ledger_command.record.domain.model.credential.tables.pojos.Authorities;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JOOQAuthoritiesRepository {
    private DSLContext dsl;

    public List<Authorities> getAuthoritiesByUsername(String username) {
        return dsl.select(Tables.AUTHORITIES.AUTHORITY)
                .from(Tables.AUTHORITIES)
                .where(Tables.AUTHORITIES.USERNAME.eq(username))
                .fetchInto(Authorities.class);
    }
}