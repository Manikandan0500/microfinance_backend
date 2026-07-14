package com.bbots.mfin.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.Auth101;
import com.bbots.mfin.dto.AuthQ001;
import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.RegionId;
import com.bbots.mfin.dto.ResponseDTO;
import com.bbots.mfin.repository.Auth101Repository;
import com.bbots.mfin.repository.AuthQ001Repository;
import com.bbots.mfin.repository.RegionRepository;
import com.bbots.mfin.service.AuthorizationProcedureService;
import com.bbots.mfin.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {

	@Resource
	private RegionRepository regionRepository;

	@Resource
	private AuthorizationProcedureService authProcedureService;
	
    @Resource
    private Auth101Repository auth101Repository;
    
    @Resource
    private AuthQ001Repository authQ001Repository;

	@Override
	public ResponseDTO<Region> createRegion(Region region) {

		ResponseDTO<Region> responseDTO = new ResponseDTO<>();

		try {

			RegionId id = region.getId();
			if (regionRepository.existsById(id)) {

				responseDTO.setSuccess(false);
				responseDTO.setMessage("Region already exists");
				return responseDTO;
			}

			region.seteUser("admin");
			region.seteDate(LocalDate.now());

			authProcedureService.processAuthorization(region.getId().getOrgCode(), "REGMAS", "rgn001", region,
					"INSERT");

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Region created successfully");
//			responseDTO.setData(savedRegion)

		} catch (Exception e) {

			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());

		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<List<Auth101>> getAuthConfigs(Long orgCode) {
		
		List<Auth101> resultData =  new ArrayList<>();
		ResponseDTO<List<Auth101>> responseDTO = new ResponseDTO<>();

		try {
			resultData = auth101Repository.findByIdOrgCode(orgCode);
			
			responseDTO.setSuccess(true);
			responseDTO.setMessage("Record featch successfully");
			responseDTO.setData(resultData);
			
			
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO<List<AuthQ001>> getAuthQueueData(Long orgCode) {
		
		List<AuthQ001> resultData =  new ArrayList<>();
		ResponseDTO<List<AuthQ001>> responseDTO = new ResponseDTO<>();

		try {
			resultData = authQ001Repository.findByIdOrgCode(orgCode);
			
			responseDTO.setSuccess(true);
			responseDTO.setMessage("Record featch successfully");
			responseDTO.setData(resultData);
			
			
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;


	}

}
