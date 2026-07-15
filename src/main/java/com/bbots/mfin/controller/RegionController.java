package com.bbots.mfin.controller;

import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.RegionId;
import com.bbots.mfin.repository.RegionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
@CrossOrigin(origins = "*")
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping
    public List<Region> getAllRegions(@RequestParam(value = "orgCode", required = false) Long orgCode) {
        if (orgCode != null) {
            return regionRepository.findByIdOrgCode(orgCode);
        }
        return regionRepository.findAll();
    }

    @GetMapping("/{orgCode}/{regionCode}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long orgCode, @PathVariable String regionCode) {
        RegionId id = new RegionId(orgCode, regionCode);
        Optional<Region> region = regionRepository.findById(id);
        return region.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Region> createRegion(@RequestBody Region region) {
        if (region.getId() == null || region.getId().getOrgCode() == null || region.getId().getRegionCode() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        RegionId id = region.getId();
        if (regionRepository.existsById(id)) {
            return ResponseEntity.status(409).build(); // Conflict
        }

        // Set entry metadata
        region.seteUser("admin");
        region.seteDate(LocalDate.now().toString());

        Region savedRegion = regionRepository.save(region);
        return ResponseEntity.ok(savedRegion);
    }

    @PutMapping("/{orgCode}/{regionCode}")
    public ResponseEntity<Region> updateRegion(
            @PathVariable Long orgCode, 
            @PathVariable String regionCode, 
            @RequestBody Region updatedRegionDetails) {
        
        RegionId id = new RegionId(orgCode, regionCode);
        return regionRepository.findById(id).map(existingRegion -> {
            existingRegion.setRegion_name(updatedRegionDetails.getRegion_name());
            existingRegion.setState(updatedRegionDetails.getState());
            existingRegion.setZone(updatedRegionDetails.getZone());
            
            // Set update metadata
            existingRegion.setcUser("admin");
            existingRegion.setcDate(LocalDate.now().toString());
            
            Region savedRegion = regionRepository.save(existingRegion);
            return ResponseEntity.ok(savedRegion);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orgCode}/{regionCode}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long orgCode, @PathVariable String regionCode) {
        RegionId id = new RegionId(orgCode, regionCode);
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
