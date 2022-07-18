package com.company.controller;

import com.company.dto.SubscriptionDTO;
import com.company.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid SubscriptionDTO dto) {
        subscriptionService.create(dto);
        return ResponseEntity.ok("Subscribed");
    }

    @PutMapping("/update_status")
    public ResponseEntity<String> updateStatus(@RequestBody @Valid SubscriptionDTO dto) {
        subscriptionService.updateStatus(dto);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/update_type")
    public ResponseEntity<String> updateType(@RequestBody @Valid SubscriptionDTO dto) {
        subscriptionService.updateType(dto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/get_list")
    public ResponseEntity<List<SubscriptionDTO>> listByUser() {
        List<SubscriptionDTO> list = subscriptionService.getList();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/adm/get_list_by_userId/{id}")
    public ResponseEntity<List<SubscriptionDTO>> listByUserId(@PathVariable("id") Integer id) {
        List<SubscriptionDTO> list = subscriptionService.getListByUserId(id);
        return ResponseEntity.ok().body(list);
    }
}
