package com.bbots.mfin.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbots.mfin.dto.UserDetailsDto;
import com.bbots.mfin.repository.AmUserRepository;
import com.bbots.mfin.service.AuthClientService;
import com.bbots.mfin.dto.*;

@RestController
@RequestMapping("/exchange")
public class AmServiceController {

    @Resource
    private AuthClientService authClientService;
    
    @Resource
    private AmUserRepository amUserRepository;

    @PostMapping("/exchange-token")
    public ResponseEntity<ExchangeResponse> exchangeToken(@RequestHeader("Authorization") String authHeader,
                                                          @RequestBody ExchangeRequest request) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String motherToken = authHeader.substring(7);
        ExchangeResponse response = authClientService.exchangeToken(motherToken, request.getProductCode());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user-sync")
	public ResponseEntity<ApiSyncResponse> syncUser(@RequestBody Map<String, Object> request, HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new RuntimeException("Authorization token missing or invalid");
		}

		Object userScdValue = request.get("userscd");
		Object orgCodeValue = request.get("orgCode");

		if (userScdValue == null || userScdValue.toString().trim().isEmpty()) {
			throw new RuntimeException("Missing userscd");
		}
		if (orgCodeValue == null || orgCodeValue.toString().trim().isEmpty()) {
			throw new RuntimeException("Missing orgCode");
		}

		String userscd = userScdValue.toString();
		Long orgCode = Long.valueOf(orgCodeValue.toString());
		String token = authHeader.substring(7);

		authClientService.syncUser(userscd, orgCode, token);

		return ResponseEntity.ok(new ApiSyncResponse(true, "User sync completed successfully", HttpStatus.OK.value()));
	}
	
	@GetMapping("/get-user")
	public ResponseEntity<UserDetailsDto> getUser(@RequestParam String userCode, @RequestParam Long orgCode,
			HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(401).build();
		}

		String token = authHeader.substring(7);
		UserDetailsDto response = authClientService.getUserDetails(userCode, orgCode, token);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/reset-password/{userScd}/{orgCode}")
	public ResponseEntity<?> resetPassword(@PathVariable String userScd, @PathVariable long orgCode,
			@RequestBody UpdatePasswordRequest request, HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(401).build();
		}

		String token = authHeader.substring(7);
		String resetPassword = authClientService.updatePassword(userScd, orgCode, request);

		if (resetPassword != null && !resetPassword.isEmpty()) {
			return ResponseEntity.ok("Password updated successfully");
		} else {
			return ResponseEntity.badRequest().body("Failed to update password");
		}
	}

	@PostMapping("/organization-sync")
	public ResponseEntity<ApiSyncResponse> getOrganizationByCode(@RequestBody Map<String, Long> request,
			HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");
		Long orgCode = request.get("orgCode");

		if (orgCode == null) {
			throw new RuntimeException("Missing orgCode");
		}
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new RuntimeException("Authorization token missing or invalid");
		}

		String token = authHeader.substring(7);
		authClientService.syncOrganization(orgCode, token);
		return ResponseEntity.ok(new ApiSyncResponse(true, "Organization sync completed successfully", HttpStatus.OK.value()));
	}

	@PostMapping("/products-sync")
	public ResponseEntity<ApiSyncResponse> getProducts(@RequestBody Map<String, Long> request, HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");
		Long orgCode = request.get("orgCode");
		if (orgCode == null) {
			throw new RuntimeException("Missing orgCode");
		}
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new RuntimeException("Authorization token missing or invalid");
		}
		String token = authHeader.substring(7);
		authClientService.syncProducts(orgCode, token);
		return ResponseEntity.ok(new ApiSyncResponse(true, "Products sync completed successfully", HttpStatus.OK.value()));
	}

	@PostMapping("/branches-sync")
	public ResponseEntity<ApiSyncResponse> getBranches(@RequestBody Map<String, Long> request, HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");
		Long orgCode = request.get("orgCode");
		if (orgCode == null) {
			throw new RuntimeException("Missing orgCode");
		}
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new RuntimeException("Authorization token missing or invalid");
		}
		String token = authHeader.substring(7);
		authClientService.syncBranches(orgCode, token);
		return ResponseEntity.ok(new ApiSyncResponse(true, "Branches sync completed successfully", HttpStatus.OK.value()));
	}
	
	@PostMapping("/branch-sync")
	public ResponseEntity<ApiSyncResponse> getBranch(@RequestBody BranchValidationRequestDto request, HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");
	
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new RuntimeException("Authorization token missing or invalid");
		}
		String token = authHeader.substring(7);
		authClientService.syncBranch(request.getOrgCode(), request.getBrncd(), request.getBranchModDate(), token);
		return ResponseEntity.ok(new ApiSyncResponse(true, "Branches sync completed successfully", HttpStatus.OK.value()));
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<UserDetailsDto>> searchUsers(
	        @RequestParam("search") String search,
	        @RequestParam("orgCode") Long orgCode,
	        HttpServletRequest http) {

	    String authHeader = http.getHeader("Authorization");
	    String token = authHeader.substring(7);
	    List<UserDetailsDto> users = authClientService.searchUsers(search, orgCode, token);
	    return ResponseEntity.ok(users);
	}
	
	@PostMapping("/check-mod-dates")
	public ResponseEntity<?> checkModDates(@RequestBody Map<String, String> request) {
		String userScd = request.get("userScd");
		String orgCodeStr = request.get("orgCode");
		LocalDateTime amUserModTime = null;
		LocalDateTime amOrgModTime = null;
		try {
		    if (request.get("incomingUserMod") != null && !request.get("incomingUserMod").isEmpty())
		        amUserModTime = LocalDateTime.parse(request.get("incomingUserMod"));
		    if (request.get("incomingOrgMod") != null && !request.get("incomingOrgMod").isEmpty())
		        amOrgModTime = LocalDateTime.parse(request.get("incomingOrgMod"));
		} catch (Exception e) {}

		if (orgCodeStr == null) {
			return ResponseEntity.badRequest().body("orgCode is required");
		}
		
		if (userScd == null) {
			return ResponseEntity.badRequest().body("userCode is required");
		}

		try {
			Long orgCode = Long.parseLong(orgCodeStr);
			Map<String, LocalDateTime> modDates = amUserRepository.getModDates(userScd, orgCode);
			Map<String, Boolean> response = new HashMap<>();

			if (modDates.containsKey("orgModDate") && amOrgModTime != null) {
				if (amOrgModTime.isAfter(modDates.get("orgModDate"))) {
					response.put("orgMod", true);
				} else {
					response.put("orgMod", false);
				}
			} else {
				response.put("orgMod", true);
			}

			if (modDates.containsKey("userModDate") && amUserModTime != null) {
				if (amUserModTime.isAfter(modDates.get("userModDate"))) {
					response.put("userMod", true);
				} else {
					response.put("userMod", false);
				}
			} else {
				response.put("userMod", true);
			}
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body("Invalid orgCode format");
		}
	}
	
	@PostMapping("/validate-branch")
	public ResponseEntity<?> validateBranchForSync(@RequestBody BranchValidationRequestDto request, HttpServletRequest http) {
		String authHeader = http.getHeader("Authorization");
		String token = authHeader.substring(7);
	    boolean validateBranchForSync = authClientService.validateBranchForSync(
	            request.getOrgCode(),
	            request.getBrncd(),
	            request.getBranchModDate(), token);
	    
	    Map<String, Boolean> res = new HashMap<>();
	    res.put("result", validateBranchForSync);
	    return ResponseEntity.ok(res);
	}
}
