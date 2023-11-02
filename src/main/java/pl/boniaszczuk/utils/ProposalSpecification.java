package pl.boniaszczuk.utils;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import pl.boniaszczuk.entity.Proposal;
import pl.boniaszczuk.model.ProposalParams;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ProposalSpecification {
    public static Specification<Proposal> withDynamicQuery(ProposalParams params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!CollectionUtils.isEmpty(params.getNames())) {
                List<Predicate> namePredicates = new ArrayList<>();
                for (String name : params.getNames()) {
                    namePredicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
                }
                predicates.add(cb.or(namePredicates.toArray(new Predicate[0])));
            }
            if (!CollectionUtils.isEmpty(params.getProposalStatuses())) {
                predicates.add(root.get("proposalStatus").get("identifier").in(params.getProposalStatuses()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
