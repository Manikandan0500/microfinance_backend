package com.bbots.mfin.service;

import java.util.List;

import com.bbots.mfin.dto.Auth101;
import com.bbots.mfin.dto.AuthQ001;
import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.ResponseDTO;

public interface MasterService {
	
	 ResponseDTO<Region> createRegion(Region region);
	 
	 ResponseDTO<List<Auth101>> getAuthConfigs(Long orgCode);
	 
	 ResponseDTO<List<AuthQ001>> getAuthQueueData(Long orgCode);

}
