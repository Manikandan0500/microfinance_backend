package com.bbots.mfin.service;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.bbots.mfin.dto.AuthRequest;
import com.bbots.mfin.dto.AuthResponse;
import com.bbots.mfin.dto.BranchDto;
import com.bbots.mfin.dto.ExchangeRequest;
import com.bbots.mfin.dto.ExchangeResponse;
import com.bbots.mfin.dto.LoginHistoryDto;
import com.bbots.mfin.dto.OrganizationDto;
import com.bbots.mfin.dto.OrganizationSyncDto;
import com.bbots.mfin.dto.ProductDto;
import com.bbots.mfin.dto.ProductMappedDto;
import com.bbots.mfin.dto.ProductSyncResponseDto;
import com.bbots.mfin.dto.UpdatePasswordRequest;
import com.bbots.mfin.dto.UserDetailsDto;
import com.bbots.mfin.dto.UserDto;
import com.bbots.mfin.dto.UserMappedProducts;
import com.bbots.mfin.dto.VerifyEmailResponse;
import com.bbots.mfin.exception.ResourceNotFoundException;
import com.bbots.mfin.repository.AmUserRepository;
import com.bbots.mfin.repository.BranchRepository;
import com.bbots.mfin.repository.OrganizationRepository;
import com.bbots.mfin.repository.ProductRepository;
import com.bbots.mfin.repository.UserRepository;
import com.bbots.mfin.util.JwtService;

import org.springframework.transaction.support.TransactionTemplate;
 
 
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
 
@Service
public class AuthClientService {
 
    @Autowired
    private WebClient webClient;
 
    @Autowired
    private AmUserRepository userRepo;
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private ProductRepository productRepository;
 
    @Autowired
    private BranchRepository branchRepo;
 
    @Autowired
    private OrganizationRepository orgRepo;
 
    @Autowired
    private JwtService jwtService;
 
    @Autowired
    private TransactionTemplate transactionTemplate;
 
    private static final Logger logger = LoggerFactory.getLogger(AuthClientService.class);
 
    public AuthResponse login(AuthRequest request) {
        return webClient.post()
                .uri("/auth/login")
                .header("Content-Type", "application/json")
                .header("X-Internal-Call", "true")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .retry(1)
                .doOnSuccess(res -> logger.info("Login SUCCESS for user: {}", request.getEmail()))
                .doOnError(err -> logger.error("Login ERROR: {}", err.getMessage()))
                .block();
    }
 
    public ExchangeResponse exchangeToken(String motherToken, Integer product) {
        ExchangeRequest request = new ExchangeRequest(product);
        ExchangeResponse response = webClient.post()
                .uri("/exchange/exchange-token")
                .header("Content-Type", "application/json")
                .header("X-Internal-Call", "true")
                .header("Authorization", "Bearer " + motherToken)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ExchangeResponse.class)
                .retry(1)
                .block();
 
        if (response != null && response.getSession_data() != null) {
            syncOrganization(response.getSession_data().getOrgCode(), response.getChild_token());
            syncUser(response.getSession_data().getUserScd(), response.getSession_data().getOrgCode(),
                    response.getChild_token());
        }
        return response;
    }
 
    public UserDetailsDto getUserDetails(Long userCode, Long orgCode, String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/get-user")
                        .queryParam("userCode", userCode)
                        .queryParam("orgCode", orgCode)
                        .build())
                .header("Content-Type", "application/json")
                .header("X-Internal-Call", "true")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(UserDetailsDto.class)
                .retry(1)
                .doOnSuccess(res -> System.out.println("GET USER SUCCESS"))
                .doOnError(err -> System.out.println("GET USER ERROR: " + err.getMessage()))
                .block();
    }
 
    public Object getOrganizations(String token) {
        return webClient.get()
                .uri("/organization/org")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Object.class)
                .retry(1)
                .block();
    }
 
    public Object getLoginHistory(Long orgCode, Long prodcode, String usercd, String token) {
        return webClient.get()
                .uri(uriBuilder -> {
                    org.springframework.web.util.UriBuilder b = uriBuilder.path("/login-history");
                    if (orgCode != null)
                        b = b.queryParam("orgCode", orgCode);
                    if (prodcode != null)
                        b = b.queryParam("prodcode", prodcode);
                    if (usercd != null && !usercd.trim().isEmpty())
                        b = b.queryParam("usercd", usercd);
                    return b.build();
                })
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Object.class)
                .retry(1)
                .block();
    }
 
    public UserDetailsDto syncUser(String userCode, Long orgCode, String token) {
        try {
            if (!userRepository.findByOrgCode(orgCode).isPresent()) {
                syncOrganization(orgCode, token);
            }
 
            UserDetailsDto request = new UserDetailsDto();
            request.setUserScd(userCode);
            request.setOrgCode(orgCode);
            Optional<UserDto> optionalUser = userRepo.findByUserScd(userCode, orgCode);
 
            LocalDateTime incomingUserModDate = null;
            try {
                String uMod = jwtService.extractUserModTime(token);
                if (uMod != null && !uMod.isEmpty()) {
                    incomingUserModDate = LocalDateTime.parse(uMod);
                }
            } catch (Exception e) {
            }
 
            LocalDateTime existingUserModDate = null;
 
            if (optionalUser.isPresent()) {
                existingUserModDate = optionalUser.get().getUsermodDate();
            }
 
            if (existingUserModDate != null && incomingUserModDate != null
&& !incomingUserModDate.isAfter(existingUserModDate)) {
                return new UserDetailsDto();
            }
 
            UserDetailsDto userDto = webClient.post().uri("/user/get-user").header("Content-Type", "application/json")
                    .header("X-Internal-Call", "true").header("Authorization", "Bearer " + token).bodyValue(request)
                    .retrieve().bodyToMono(UserDetailsDto.class).block();
 
            UserDto incomingUser = mapToUserDto(userDto);
            // Branch Sync
            if (userDto.getOrgCode() != null && userDto.getBrncd() != null) {
                Timestamp existingBranchModDate = branchRepo.getExistingDate(userDto.getOrgCode(),
                        Long.valueOf(userDto.getBrncd()));
                if (existingBranchModDate == null) {
                    syncBranch(userDto.getOrgCode(), Long.valueOf(userDto.getBrncd()), userDto.getBrnModDate(), token);
                } else if (userDto.getBrnModDate() != null &&
                        userDto.getBrnModDate().isAfter(existingBranchModDate.toLocalDateTime())) {
                    syncBranch(userDto.getOrgCode(), Long.valueOf(userDto.getBrncd()), userDto.getBrnModDate(), token);
                }
            }
            // Insert or Update User (User Sync)
            transactionTemplate.execute(status -> {
                if (!userRepo.existsByUserScd(userCode, orgCode)) {
                    userRepo.insertUser001(incomingUser);
                } else {
                    userRepo.updateUser001(incomingUser);
                }
                if (!userRepo.existsInUser002(userCode, orgCode)) {
                    userRepo.insertUser002(incomingUser);
                } else {
                    userRepo.updateUser002(incomingUser);
                }
 
                // User Product Mapping Sync
                if (userDto.getProducts() != null && !userDto.getProducts().isEmpty()) {
                    for (UserMappedProducts product : userDto.getProducts()) {
                        product.setOrgcode(userDto.getOrgCode());
                        userRepo.upsertUserProduct(orgCode, userCode, product);
                    }
                }
                return null;
            });
 
            return userDto;
 
        } catch (Exception ex) {
            throw new RuntimeException("User sync failed : " + ex.getMessage());
        }
    }
 
    public void syncBranch(Long orgCode, Long brncd, LocalDateTime modDate, String token) {
        try {
            BranchDto request = new BranchDto();
            request.setOrgCode(orgCode);
            request.setBrncd(brncd);
 
            BranchDto branch = webClient.post()
                    .uri("/branch/get-branch")
                    .header("Content-Type", "application/json")
                    .header("X-Internal-Call", "true")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BranchDto>() {
                    })
                    .block();
 
            if (branch == null || branch.getOrgCode() == null || branch.getBrncd() == null) {
                throw new RuntimeException("Invalid branch data received");
            }
            if (modDate == null) {
                throw new RuntimeException("Branch modification date is missing");
            }
            Timestamp existingBranchModDate = branchRepo.getExistingDate(branch.getOrgCode(), branch.getBrncd());
            if (existingBranchModDate == null) {
                branchRepo.insertBranch(branch);
                return;
            }
            if (modDate.isAfter(existingBranchModDate.toLocalDateTime())) {
                branchRepo.updateBranch(branch);
                return;
            }
            throw new RuntimeException("Branch already up-to-date");
        } catch (Exception ex) {
            throw new RuntimeException("Branch sync failed: " + ex.getMessage());
        }
    }
 
    public boolean validateBranchForSync(Long orgCode, Long brncd, LocalDateTime branchModDate, String token) {
        if (orgCode == null || brncd == null) {
            throw new RuntimeException("Invalid branch data received");
        }
        Timestamp existingBranchModDate = branchRepo.getExistingDate(orgCode, brncd);
        if (existingBranchModDate == null) {
            return true;
        }
        if (branchModDate == null) {
            throw new RuntimeException("Branch approval date is missing");
        }
        if (!branchModDate.isAfter(existingBranchModDate.toLocalDateTime())) {
            return false;
        }
        return true;
    }
 
    public UserDetailsDto getUserDetails(String userCode, Long orgCode, String token) {
        UserDetailsDto userDto = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/get-user").queryParam("userCode", userCode)
                        .queryParam("orgCode", orgCode).build())
                .header("Content-Type", "application/json").header("X-Internal-Call", "true")
                .header("Authorization", "Bearer " + token).retrieve().bodyToMono(UserDetailsDto.class).block();
 
        if (userDto == null) {
            throw new RuntimeException("User service returned null");
        }
        return userDto;
    }
 
    public VerifyEmailResponse verifyEmail(String email) {
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
 
        return webClient.post().uri("/auth/verify-email")
                .header("Content-Type", "application/json").bodyValue(request).retrieve()
                .bodyToMono(VerifyEmailResponse.class).block();
    }
 
    public String updatePassword(String userScd, Long orgCode, UpdatePasswordRequest request) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/reset-password/{userScd}/{orgCode}").build(userScd, orgCode))
                .header("Content-Type", "application/json").bodyValue(request).retrieve()
                .bodyToMono(String.class).block();
    }
 
    public void syncOrganization(Long orgCode, String token) {
        try {
            OrganizationDto request = new OrganizationDto();
            request.setOrgCode(orgCode);
            // Map<String, Long> request = new HashMap<>();
            // request.put("orgcode", orgCode);
            Optional<OrganizationDto> optionalOrg = userRepository.findByOrgCode(orgCode);
            LocalDateTime existingOrgModTime = null;
            if (optionalOrg.isPresent()) {
                existingOrgModTime = optionalOrg.get().getEdate();
            }
            LocalDateTime incomingOrgModTime = LocalDateTime.parse(jwtService.extractOrgModTime(token));
 
            // Already latest
            if (existingOrgModTime != null && incomingOrgModTime != null && !incomingOrgModTime.isAfter(existingOrgModTime)) {
                return;
            }
 
            OrganizationSyncDto orgDto = webClient.post().uri("/organization/get-organization")
                    .header("Content-Type", "application/json").header("X-Internal-Call", "true")
                    .header("Authorization", "Bearer " + token).bodyValue(request).retrieve()
                    .bodyToMono(OrganizationSyncDto.class).block();
 
            if (orgDto == null) {
 
                throw new ResourceNotFoundException("Organization details not found");
            }
 
            transactionTemplate.execute(status -> {
                if (optionalOrg.isPresent()) {
                    userRepository.updateOrg(orgDto.getOrganization());
                } else {
                    userRepository.insertOrg(orgDto.getOrganization());
                }
 
                if (orgDto.getMappedProducts() != null && !orgDto.getMappedProducts().isEmpty()) {
                    for (ProductMappedDto mappedProduct : orgDto.getMappedProducts()) {
                        mappedProduct.setOrgcode(orgCode);
                        productRepository.upsertMappedProduct(mappedProduct);
                    }
                }
                return null;
            });
 
        } catch (ResourceNotFoundException ex) {
 
            throw ex;
 
        } catch (Exception ex) {
 
            throw new RuntimeException("Organization sync failed : " + ex.getMessage());
        }
    }
 
    private UserDto mapToUserDto(UserDetailsDto dto) {
        UserDto user = new UserDto();
 
        user.setOrgCode(dto.getOrgCode());
        user.setUserScd(dto.getUserScd());
        user.setfName(dto.getfName());
        user.setmName(dto.getmName());
        user.setlName(dto.getlName());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setCallcode(dto.getCallcode());
 
        try {
            if (dto.getBrncd() != null)
                user.setBrncd(Long.valueOf(dto.getBrncd()));
        } catch (Exception e) {
        }
 
        user.setCountry(dto.getCountry());
        user.setGender(dto.getGender());
        user.setTitle(dto.getTitle());
        user.setMenuType(dto.getMenuType());
        user.setUserName(dto.getAuser());
        user.setEuser(dto.getEuser());
        user.setAuser(dto.getAuser());
        user.setStatus(dto.getStatus());
 
        return user;
    }
 
    public void syncProducts(Long orgCode, String token) {
        try {
            if (!userRepository.findByOrgCode(orgCode).isPresent()) {
                syncOrganization(orgCode, token);
            }
 
            ProductDto request = new ProductDto();
            request.setOrgcode(orgCode);
            ProductSyncResponseDto response = webClient.post().uri("/product/get-products")
                    .header("Content-Type", "application/json").header("X-Internal-Call", "true")
                    .header("Authorization", "Bearer " + token).bodyValue(request).retrieve()
                    .bodyToMono(ProductSyncResponseDto.class).block();
 
            if (response == null) {
                throw new RuntimeException("No response received from product service");
            }
            if (response.getProducts() != null && !response.getProducts().isEmpty()) {
                for (ProductDto product : response.getProducts()) {
                    product.setOrgcode(orgCode);
                    productRepository.upsertProduct(product);
                }
            }
            if (response.getMappedProducts() != null && !response.getMappedProducts().isEmpty()) {
                for (ProductMappedDto mappedProduct : response.getMappedProducts()) {
                    mappedProduct.setOrgcode(orgCode);
                    productRepository.upsertMappedProduct(mappedProduct);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Products sync failed : " + ex.getMessage());
        }
    }
 
    public void recordLogin(LoginHistoryDto dto) {
        webClient.post().uri("/login-history/record").header("Content-Type", "application/json")
                .header("X-Internal-Call", "true").bodyValue(dto).retrieve().bodyToMono(Void.class)
                .doOnError(e -> logger.error("recordLogin error: {}", e.getMessage())).subscribe();
    }
 
    public void recordLogout(String usercd, String orgcode) {
        Map<String, String> body = new HashMap<>();
        body.put("usercd", usercd);
        body.put("orgcode", orgcode);
 
        webClient.post().uri("/auth/logout-internal").header("Content-Type", "application/json")
                .header("X-Internal-Call", "true").bodyValue(body).retrieve().bodyToMono(Void.class)
                .doOnError(e -> logger.error("recordLogout error: {}", e.getMessage())).subscribe();
    }
 
    private boolean isUserChanged(UserDto db, UserDto incoming) {
        return !Objects.equals(db.getfName(), incoming.getfName())
                || !Objects.equals(db.getlName(), incoming.getlName())
                || !Objects.equals(db.getMobile(), incoming.getMobile())
                || !Objects.equals(db.getCallcode(), incoming.getCallcode())
                || !Objects.equals(db.getCountry(), incoming.getCountry())
                || !Objects.equals(db.getGender(), incoming.getGender())
                || !Objects.equals(db.getTitle(), incoming.getTitle());
    }
 
    public void syncBranches(Long orgCode, String token) {
        try {
            if (!userRepository.findByOrgCode(orgCode).isPresent()) {
                syncOrganization(orgCode, token);
            }
 
            BranchDto request = new BranchDto();
            request.setOrgCode(orgCode);
 
            List<BranchDto> branches = webClient.post()
                    .uri("/branch/get-branches")
                    .header("Content-Type", "application/json")
                    .header("X-Internal-Call", "true")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<BranchDto>>() {
                    })
                    .block();
 
            if (branches == null || branches.isEmpty()) {
                throw new RuntimeException("No branches received from service");
            }
 
            transactionTemplate.execute(status -> {
                for (BranchDto branch : branches) {
                    branch.setOrgCode(orgCode);
                    Timestamp localApprovalDate = branchRepo.getExistingDate(branch.getOrgCode(), branch.getBrncd());
                    LocalDateTime remoteApprovalDate = branch.getAdate();
                    if (localApprovalDate == null) {
                        branchRepo.insertBranch(branch);
                        continue;
                    }
                    if (remoteApprovalDate != null && remoteApprovalDate.isAfter(localApprovalDate.toLocalDateTime())) {
                        branchRepo.updateBranch(branch);
                    }
                }
                return null;
            });
        } catch (Exception ex) {
            throw new RuntimeException("Branches sync failed : " + ex.getMessage());
        }
    }
 
    public List<UserDetailsDto> searchUsers(String search, Long orgCode, String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/search")
                        .queryParam("search", search)
                        .queryParam("orgCode", orgCode)
                        .build())
                .header("Content-Type", "application/json")
                .header("X-Internal-Call", "true")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserDetailsDto>>() {
                })
                .block();
    }
}