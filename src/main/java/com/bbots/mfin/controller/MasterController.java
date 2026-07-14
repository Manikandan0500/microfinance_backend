package com.bbots.mfin.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbots.mfin.dto.Auth101;
import com.bbots.mfin.dto.AuthQ001;
import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.RegionId;
import com.bbots.mfin.dto.ResponseDTO;
import com.bbots.mfin.repository.Auth101Repository;
import com.bbots.mfin.repository.RegionRepository;
import com.bbots.mfin.service.AuthorizationProcedureService;
import com.bbots.mfin.service.MasterService;

@RestController
@RequestMapping("/api/master")
@CrossOrigin(origins = "*")
public class MasterController {

	@Resource
	private RegionRepository regionRepository;

	@Resource
	private MasterService masterService;

	@Resource
	private Auth101Repository auth101Repository;

	@Resource
	private AuthorizationProcedureService authProcedureService;

	@PostMapping("/createRegion")
	public ResponseEntity<ResponseDTO<Region>> createRegion(@RequestBody Region region) {
		if (region.getId() == null || region.getId().getOrgCode() == null || region.getId().getRegionCode() == null) {
			return ResponseEntity.badRequest().build();
		}

		ResponseDTO<Region> response = masterService.createRegion(region);
		if (!response.isSuccess()) {
			return ResponseEntity.badRequest().body(response);
		} else {
			return ResponseEntity.ok(response);
		}
	}
	
	@GetMapping("/getAuthConfigData/{orgCode}")
	public ResponseDTO<List<Auth101>> getAuthConfigs(@PathVariable Long orgCode) {

		ResponseDTO<List<Auth101>> result = masterService.getAuthConfigs(orgCode);

		return result;
	}
	
	@GetMapping("/getAuthQueueData/{orgCode}")
	public ResponseDTO<List<AuthQ001>> getAuthQueueData(@PathVariable Long orgCode) {

		ResponseDTO<List<AuthQ001>> result = masterService.getAuthQueueData(orgCode);

		return result;
	}

	@GetMapping("/getRegionData")
	public List<Region> getAllRegion() {

		return regionRepository.findAll();
	}



	@PostMapping("/authConfig")
	public ResponseEntity<Map<String, String>> createConfig(@RequestBody Auth101 auth101) {
		if (auth101.getOrgCode() == null) {
			return ResponseEntity.badRequest().build();
		}

		auth101.seteUser("admin");
		auth101.seteDate(LocalDate.now());

		auth101Repository.save(auth101);

		authProcedureService.processAuthorization(auth101.getOrgCode(), "AUTHCTL", "auth101", auth101, "INSERT");

		return ResponseEntity
				.ok(Collections.singletonMap("message", "Authorization Configuration stored successfully"));
	}

}
