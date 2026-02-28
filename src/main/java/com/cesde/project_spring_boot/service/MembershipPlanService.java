package com.cesde.project_spring_boot.service;


import com.cesde.project_spring_boot.dto.MembershipPlanRequest;
import com.cesde.project_spring_boot.dto.MembershipPlanResponse;
import com.cesde.project_spring_boot.model.MembershipPlan;
import com.cesde.project_spring_boot.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class MembershipPlanService {

    private final MembershipPlanRepository repository;

    public MembershipPlanService(MembershipPlanRepository repository) {
        this.repository = repository;
    }

    public MembershipPlanResponse create(MembershipPlanRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Plan name already exists");
        }

        MembershipPlan plan = new MembershipPlan();
        mapRequestToEntity(request, plan);
        plan.setActive(true);

        return mapToResponse(repository.save(plan));
    }

    public List<MembershipPlanResponse> findActivePlans() {
        return repository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MembershipPlanResponse update(Long id, MembershipPlanRequest request) {
        MembershipPlan plan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        mapRequestToEntity(request, plan);
        return mapToResponse(repository.save(plan));
    }

    public void deactivate(Long id) {
        MembershipPlan plan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setActive(false);
        repository.save(plan);
    }

    private void mapRequestToEntity(MembershipPlanRequest r, MembershipPlan p) {
        p.setName(r.getName());
        p.setDescription(r.getDescription());
        p.setDurationDays(r.getDurationDays());
        p.setPrice(r.getPrice());
        p.setMaxGroupClasses(r.getMaxGroupClasses());
        p.setIncludesLocker(r.getIncludesLocker());
    }

    private MembershipPlanResponse mapToResponse(MembershipPlan p) {
        MembershipPlanResponse r = new MembershipPlanResponse();
        r.setId(p.getId());
        r.setName(p.getName());
        r.setDescription(p.getDescription());
        r.setDurationDays(p.getDurationDays());
        r.setPrice(p.getPrice());
        r.setMaxGroupClasses(p.getMaxGroupClasses());
        r.setIncludesLocker(p.getIncludesLocker());
        r.setActive(p.getActive());
        return r;
    }
}
