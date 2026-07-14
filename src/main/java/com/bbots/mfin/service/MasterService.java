package com.bbots.mfin.service;

import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.ResponseDTO;

public interface MasterService {
	
	 ResponseDTO<Region> createRegion(Region region);

}
