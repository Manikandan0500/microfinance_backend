package com.bbots.mfin.serviceImpl;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.RegionId;
import com.bbots.mfin.dto.ResponseDTO;
import com.bbots.mfin.repository.RegionRepository;
import com.bbots.mfin.service.AuthorizationProcedureService;
import com.bbots.mfin.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {

	@Resource
	private RegionRepository regionRepository;
	
    @Resource
    private AuthorizationProcedureService authProcedureService;

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
			
			authProcedureService.processAuthorization(region.getId().getOrgCode(), "REGMAS", "rgn001", region, "INSERT");



			responseDTO.setSuccess(true);
			responseDTO.setMessage("Region created successfully");
//			responseDTO.setData(savedRegion)

		} catch (Exception e) {

			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());

		}

		return responseDTO;
	}

}
