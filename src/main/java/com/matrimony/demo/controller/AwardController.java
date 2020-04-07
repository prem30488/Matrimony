/*
 * package com.example.demo.controller;
 * 
 * import com.example.demo.exception.ResourceNotFoundException; import
 * com.example.demo.model.Award; import com.example.demo.model.User; import
 * com.example.demo.repository.AwardRepository; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.domain.Page; import
 * org.springframework.data.domain.Pageable; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.*; import javax.validation.Valid;
 * 
 * @RestController public class AwardController {
 * 
 * @Autowired private AwardRepository awardRepository;
 * 
 * @GetMapping("/api/user/awards") public Page<Award> getAwards(Pageable
 * pageable) { return awardRepository.findAll(pageable); }
 * 
 * 
 * @PostMapping("/api/user/awards") public Award createAward(@Valid @RequestBody
 * Award award) { return awardRepository.save(award); }
 * 
 * @PutMapping("/api/user/awards/{awardId}") public Award
 * updateAward(@PathVariable Long awardId,
 * 
 * @Valid @RequestBody Award awardRequest) { return
 * awardRepository.findById(awardId) .map(award -> {
 * award.setTitle(awardRequest.getTitle());
 * award.setDescription(awardRequest.getDescription()); return
 * awardRepository.save(award); }).orElseThrow(() -> new
 * ResourceNotFoundException("Award not found with id " + awardId)); }
 * 
 * 
 * @DeleteMapping("/api/user/awards/{awardId}") public ResponseEntity<?>
 * deleteAward(@PathVariable Long awardId) { return
 * awardRepository.findById(awardId) .map(award -> {
 * awardRepository.delete(award); return ResponseEntity.ok().build();
 * }).orElseThrow(() -> new ResourceNotFoundException("Award not found with id "
 * + awardId)); }
 * 
 * @GetMapping("/api/user/awards/{awardId}") public Award
 * fetchUserById(@PathVariable Long awardId) { return
 * awardRepository.findById(awardId) .orElseThrow(() -> new
 * ResourceNotFoundException("Award not found with id " + awardId)); } }
 */