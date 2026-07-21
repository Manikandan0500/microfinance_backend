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
import com.bbots.mfin.dto.BranchRegionMap;
import com.bbots.mfin.dto.DelinquencyBucketDTO;
import com.bbots.mfin.dto.GLMappingDTO;
import com.bbots.mfin.dto.HolidayCalendarDTO;
import com.bbots.mfin.dto.LoanProductDTO;
import com.bbots.mfin.dto.PenaltyRateHistoryDTO;
import com.bbots.mfin.dto.PrepaymentForeclosureConfigDTO;
import com.bbots.mfin.dto.RateRevisionHistoryDTO;
import com.bbots.mfin.dto.ResponseDTO;
import com.bbots.mfin.repository.Auth101Repository;
import com.bbots.mfin.repository.RegionRepository;
import com.bbots.mfin.service.AuthService;
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

	@Resource
	private AuthService authService;

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

	@GetMapping("/getRegionData/{orgCode}")
	public ResponseDTO<List<Region>> getRegionData(@PathVariable Long orgCode) {

		ResponseDTO<List<Region>> result = masterService.getRegionData(orgCode);

		return result;
	}

	@PostMapping("/authConfig")
	public ResponseEntity<Map<String, String>> createConfig(@RequestBody Auth101 auth101) {
		// Derive orgCode from the id block if top-level is missing
		if (auth101.getOrgCode() == null && auth101.getId() != null) {
			auth101.setOrgCode(auth101.getId().getOrgCode());
		}
		if (auth101.getOrgCode() == null) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("message", "orgCode is required"));
		}
		// Ensure id is valid before calling existsById
		if (auth101.getId() == null || auth101.getId().getOrgCode() == null || auth101.getId().getProgramId() == null) {
			return ResponseEntity.badRequest()
					.body(Collections.singletonMap("message", "id (orgCode + programId) is required"));
		}

		auth101.seteUser("admin");
		auth101.seteDate(LocalDate.now());

		boolean exists = auth101Repository.existsById(auth101.getId());
		String action = exists ? "UPDATE" : "INSERT";
		authProcedureService.processAuthorization(auth101.getOrgCode(), "AUTHCTL", "auth101", auth101, action);

		return ResponseEntity
				.ok(Collections.singletonMap("message", "Authorization Configuration stored successfully"));
	}

	@PostMapping("/authSubmit/{authSl}")
	public void approve(@PathVariable Long authSl, @RequestParam int level, @RequestParam String userId) {
		authService.approve(authSl, level, userId);
	}

	@PostMapping("/authReject/{authSl}")
	public void reject(@PathVariable Long authSl, @RequestParam int level, @RequestParam String userId) {
		authService.reject(authSl, level, userId);
	}

	@GetMapping("/getBranchRegionMapData/{orgCode}")
	public ResponseEntity<List<BranchRegionMap>> getBranchRegionMapData(@PathVariable Long orgCode) {
		ResponseDTO<List<BranchRegionMap>> result = masterService.getBranchRegionMapData(orgCode);
		return ResponseEntity.ok(result.getData());
	}

	@PostMapping("/createBranchRegionMap")
	public ResponseEntity<?> createBranchRegionMap(@RequestBody BranchRegionMap map) {
		ResponseDTO<BranchRegionMap> result = masterService.createBranchRegionMap(map);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		return ResponseEntity.badRequest().body(Collections.singletonMap("message", result.getMessage()));
	}

	@PutMapping("/updateBranchRegionMap")
	public ResponseEntity<?> updateBranchRegionMap(@RequestBody BranchRegionMap map) {
		ResponseDTO<BranchRegionMap> result = masterService.updateBranchRegionMap(map);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		return ResponseEntity.badRequest().body(Collections.singletonMap("message", result.getMessage()));
	}

	@PostMapping("/createLoanProduct")
	public ResponseEntity<?> createLoanProduct(@RequestBody LoanProductDTO loan) {
		ResponseDTO<LoanProductDTO> result = masterService.createLoanProduct(loan);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		return ResponseEntity.badRequest().body(Collections.singletonMap("message", result.getMessage()));
	}

	@PutMapping("/updateLoanProduct")
	public ResponseEntity<?> updateLoanProduct(@RequestBody LoanProductDTO loan) {
		ResponseDTO<LoanProductDTO> result = masterService.updateLoanProduct(loan);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		return ResponseEntity.badRequest().body(Collections.singletonMap("message", result.getMessage()));
	}

	@GetMapping("/getLoanProductData/{orgCode}")
	public ResponseEntity<List<LoanProductDTO>> getLoanProductData(@PathVariable Long orgCode) {
		ResponseDTO<List<LoanProductDTO>> result = masterService.getLoanProductData(orgCode);
		return ResponseEntity.ok(result.getData());
	}

	@PostMapping("/createDelinquencyBucket")
	public ResponseEntity<?> createDelinquencyBucket(@RequestBody DelinquencyBucketDTO bucket) {

		ResponseDTO<DelinquencyBucketDTO> result = masterService.createDelinquencyBucket(bucket);

		if (result.isSuccess()) {
			return ResponseEntity.ok(result.getData());
		}

		return ResponseEntity.badRequest().body(Collections.singletonMap("message", result.getMessage()));
	}

	@PutMapping("/updateDelinquencyBucket")
	public ResponseEntity<?> updateDelinquencyBucket(@RequestBody DelinquencyBucketDTO bucket) {

		ResponseDTO<DelinquencyBucketDTO> result = masterService.updateDelinquencyBucket(bucket);

		if (result.isSuccess()) {
			return ResponseEntity.ok(result.getData());
		}

		return ResponseEntity.badRequest().body(Collections.singletonMap("message", result.getMessage()));
	}

	@GetMapping("/getDelinquencyBucketData/{orgCode}")
	public ResponseEntity<List<DelinquencyBucketDTO>> getDelinquencyBucketData(@PathVariable Long orgCode) {

		ResponseDTO<List<DelinquencyBucketDTO>> result = masterService.getDelinquencyBucketData(orgCode);

		return ResponseEntity.ok(result.getData());
	}
	
	@PostMapping("/createPenaltyRateHistory")
	public ResponseEntity<?> createPenaltyRateHistory(@RequestBody PenaltyRateHistoryDTO penalty) {

	    ResponseDTO<PenaltyRateHistoryDTO> result = masterService.createPenaltyRateHistory(penalty);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@PutMapping("/updatePenaltyRateHistory")
	public ResponseEntity<?> updatePenaltyRateHistory(@RequestBody PenaltyRateHistoryDTO penalty) {

	    ResponseDTO<PenaltyRateHistoryDTO> result = masterService.updatePenaltyRateHistory(penalty);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@GetMapping("/getPenaltyRateHistoryData/{orgCode}")
	public ResponseEntity<List<PenaltyRateHistoryDTO>> getPenaltyRateHistoryData(@PathVariable Long orgCode) {

	    ResponseDTO<List<PenaltyRateHistoryDTO>> result =
	            masterService.getPenaltyRateHistoryData(orgCode);

	    return ResponseEntity.ok(result.getData());
	}
	
	
	@PostMapping("/createGLMapping")
	public ResponseEntity<?> createGLMapping(@RequestBody GLMappingDTO map) {

	    ResponseDTO<GLMappingDTO> result = masterService.createGLMapping(map);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@PutMapping("/updateGLMapping")
	public ResponseEntity<?> updateGLMapping(@RequestBody GLMappingDTO map) {

	    ResponseDTO<GLMappingDTO> result = masterService.updateGLMapping(map);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@GetMapping("/getGLMappingData/{orgCode}")
	public ResponseEntity<List<GLMappingDTO>> getGLMappingData(@PathVariable Long orgCode) {

	    ResponseDTO<List<GLMappingDTO>> result =
	            masterService.getGLMappingData(orgCode);

	    return ResponseEntity.ok(result.getData());
	}

	@PostMapping("/createRateRevisionHistory")
	public ResponseEntity<?> createRateRevisionHistory(@RequestBody RateRevisionHistoryDTO rate) {

	    ResponseDTO<RateRevisionHistoryDTO> result =
	            masterService.createRateRevisionHistory(rate);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@PutMapping("/updateRateRevisionHistory")
	public ResponseEntity<?> updateRateRevisionHistory(@RequestBody RateRevisionHistoryDTO rate) {

	    ResponseDTO<RateRevisionHistoryDTO> result =
	            masterService.updateRateRevisionHistory(rate);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@GetMapping("/getRateRevisionHistoryData/{orgCode}")
	public ResponseEntity<List<RateRevisionHistoryDTO>> getRateRevisionHistoryData(
	        @PathVariable Long orgCode) {

	    ResponseDTO<List<RateRevisionHistoryDTO>> result =
	            masterService.getRateRevisionHistoryData(orgCode);

	    return ResponseEntity.ok(result.getData());
	}
	
	@PostMapping("/createHolidayCalendar")
	public ResponseEntity<?> createHolidayCalendar(@RequestBody HolidayCalendarDTO holiday) {

	    ResponseDTO<HolidayCalendarDTO> result =
	            masterService.createHolidayCalendar(holiday);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@PutMapping("/updateHolidayCalendar")
	public ResponseEntity<?> updateHolidayCalendar(@RequestBody HolidayCalendarDTO holiday) {

	    ResponseDTO<HolidayCalendarDTO> result =
	            masterService.updateHolidayCalendar(holiday);

	    if (result.isSuccess()) {
	        return ResponseEntity.ok(result.getData());
	    }

	    return ResponseEntity.badRequest()
	            .body(Collections.singletonMap("message", result.getMessage()));
	}

	@GetMapping("/getHolidayCalendarData/{orgCode}")
	public ResponseEntity<List<HolidayCalendarDTO>> getHolidayCalendarData(
	        @PathVariable Long orgCode) {

	    ResponseDTO<List<HolidayCalendarDTO>> result =
	            masterService.getHolidayCalendarData(orgCode);

	    return ResponseEntity.ok(result.getData());
	}
	
	@PostMapping("/createPrepaymentForeclosureConfig")

	public ResponseEntity<?> createPrepaymentForeclosureConfig(

	        @RequestBody PrepaymentForeclosureConfigDTO config) {
	 
	    ResponseDTO<PrepaymentForeclosureConfigDTO> result =

	            masterService.createPrepaymentForeclosureConfig(config);
	 
	    if (result.isSuccess()) {

	        return ResponseEntity.ok(result.getData());

	    }
	 
	    return ResponseEntity.badRequest()

	            .body(Collections.singletonMap("message", result.getMessage()));

	}
	 
	@PutMapping("/updatePrepaymentForeclosureConfig")

	public ResponseEntity<?> updatePrepaymentForeclosureConfig(

	        @RequestBody PrepaymentForeclosureConfigDTO config) {
	 
	    ResponseDTO<PrepaymentForeclosureConfigDTO> result =

	            masterService.updatePrepaymentForeclosureConfig(config);
	 
	    if (result.isSuccess()) {

	        return ResponseEntity.ok(result.getData());

	    }
	 
	    return ResponseEntity.badRequest()

	            .body(Collections.singletonMap("message", result.getMessage()));

	}
	 
	@GetMapping("/getPrepaymentForeclosureConfigData/{orgCode}")

	public ResponseEntity<List<PrepaymentForeclosureConfigDTO>> getPrepaymentForeclosureConfigData(

	        @PathVariable Long orgCode) {
	 
	    ResponseDTO<List<PrepaymentForeclosureConfigDTO>> result =

	            masterService.getPrepaymentForeclosureConfigData(orgCode);
	 
	    return ResponseEntity.ok(result.getData());

	}
	 
}
