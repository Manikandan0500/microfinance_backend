package com.bbots.mfin.service;

import java.util.List;

import com.bbots.mfin.dto.Auth101;
import com.bbots.mfin.dto.AuthQ001;
import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.BranchRegionMap;
import com.bbots.mfin.dto.DelinquencyBucketDTO;
import com.bbots.mfin.dto.DisbursementDTO;
import com.bbots.mfin.dto.DisbursementQueueDTO;
import com.bbots.mfin.dto.GLMappingDTO;
import com.bbots.mfin.dto.HolidayCalendarDTO;
import com.bbots.mfin.dto.LoanProductDTO;
import com.bbots.mfin.dto.PenaltyRateHistoryDTO;
import com.bbots.mfin.dto.PrepaymentForeclosureConfigDTO;
import com.bbots.mfin.dto.RateRevisionHistoryDTO;
import com.bbots.mfin.dto.ResponseDTO;

public interface MasterService {
	
	 ResponseDTO<Region> createRegion(Region region);
	 
	 ResponseDTO<List<Auth101>> getAuthConfigs(Long orgCode);
	 
	 ResponseDTO<List<AuthQ001>> getAuthQueueData(Long orgCode);
	 
	 ResponseDTO<List<Region>> getRegionData(Long orgCode);
	 
	 ResponseDTO<List<BranchRegionMap>> getBranchRegionMapData(Long orgCode);
	 
	 ResponseDTO<BranchRegionMap> createBranchRegionMap(BranchRegionMap map);
	 
	 ResponseDTO<BranchRegionMap> updateBranchRegionMap(BranchRegionMap map);

	 ResponseDTO<List<LoanProductDTO>> getLoanProductData(Long orgCode);

	 ResponseDTO<LoanProductDTO> createLoanProduct(LoanProductDTO loan);

	 ResponseDTO<LoanProductDTO> updateLoanProduct(LoanProductDTO loan);

	 ResponseDTO<List<DelinquencyBucketDTO>> getDelinquencyBucketData(Long orgCode);

	 ResponseDTO<DelinquencyBucketDTO> createDelinquencyBucket(DelinquencyBucketDTO bucket);

	 ResponseDTO<DelinquencyBucketDTO> updateDelinquencyBucket(DelinquencyBucketDTO bucket);
	 
	 ResponseDTO<List<PenaltyRateHistoryDTO>> getPenaltyRateHistoryData(Long orgCode);

	 ResponseDTO<PenaltyRateHistoryDTO> createPenaltyRateHistory(PenaltyRateHistoryDTO penalty);

	 ResponseDTO<PenaltyRateHistoryDTO> updatePenaltyRateHistory(PenaltyRateHistoryDTO penalty);
	 
	 ResponseDTO<List<GLMappingDTO>> getGLMappingData(Long orgCode);

	 ResponseDTO<GLMappingDTO> createGLMapping(GLMappingDTO map);

	 ResponseDTO<GLMappingDTO> updateGLMapping(GLMappingDTO map);
	 
	 ResponseDTO<List<RateRevisionHistoryDTO>> getRateRevisionHistoryData(Long orgCode);

	 ResponseDTO<RateRevisionHistoryDTO> createRateRevisionHistory(RateRevisionHistoryDTO rate);

	 ResponseDTO<RateRevisionHistoryDTO> updateRateRevisionHistory(RateRevisionHistoryDTO rate);
	 
	 ResponseDTO<List<HolidayCalendarDTO>> getHolidayCalendarData(Long orgCode);

	 ResponseDTO<HolidayCalendarDTO> createHolidayCalendar(HolidayCalendarDTO holiday);

	 ResponseDTO<HolidayCalendarDTO> updateHolidayCalendar(HolidayCalendarDTO holiday);
	 
	 ResponseDTO<List<PrepaymentForeclosureConfigDTO>> getPrepaymentForeclosureConfigData(Long orgCode);
	 
	 ResponseDTO<PrepaymentForeclosureConfigDTO> createPrepaymentForeclosureConfig(PrepaymentForeclosureConfigDTO config);
	  
	 ResponseDTO<PrepaymentForeclosureConfigDTO> updatePrepaymentForeclosureConfig(PrepaymentForeclosureConfigDTO config);
	 
	 ResponseDTO<List<DisbursementQueueDTO>> getPendingDisbursementQueue(Long orgCode);
	 
	 ResponseDTO<String> completeDisbursement(DisbursementDTO dto);
}
