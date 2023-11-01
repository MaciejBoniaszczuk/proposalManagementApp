package pl.boniaszczuk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.boniaszczuk.model.ActionReason;
import pl.boniaszczuk.model.ProposalModel;
import pl.boniaszczuk.model.ProposalParams;
import pl.boniaszczuk.service.ProposalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proposal")
public class ProposalController {

    private final ProposalService proposalService;

    @GetMapping("/getAll")
    public Page<ProposalModel> getAll(ProposalParams params, Pageable pageable) {
        return (proposalService.getAll(params, pageable));

    }

    @PostMapping("/add")
    public ResponseEntity<Void> createProposal(@Validated @RequestBody ProposalModel proposalModel) {
        proposalService.createProposal(proposalModel);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProposal(@PathVariable Long id, @RequestBody ActionReason reason) {
        proposalService.deleteProposal(reason, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<Void> rejectProposal(@PathVariable Long id, @RequestBody ActionReason reason) {
        proposalService.rejectProposal(reason, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify/{id}")
    public ResponseEntity<Void> verifyProposal(@PathVariable Long id) {
        proposalService.verifyProposal(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<Void> acceptProposal(@PathVariable Long id) {
        proposalService.acceptProposal(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/publish/{id}")
    public ResponseEntity<Void> publishProposal(@PathVariable Long id) {
        proposalService.publishProposal(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateProposal(@RequestBody ProposalModel proposalModel) {
        proposalService.updateProposal(proposalModel);
        return ResponseEntity.ok().build();
    }
}