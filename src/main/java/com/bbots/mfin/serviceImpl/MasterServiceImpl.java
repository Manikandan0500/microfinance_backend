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
import com.bbots.mfin.dto.BranchRegionMap;
import com.bbots.mfin.dto.BranchRegionMapId;
import com.bbots.mfin.dto.DelinquencyBucketDTO;
import com.bbots.mfin.dto.GLMappingDTO;
import com.bbots.mfin.dto.HolidayCalendarDTO;
import com.bbots.mfin.dto.LoanProductDTO;
import com.bbots.mfin.dto.PenaltyRateHistoryDTO;
import com.bbots.mfin.dto.PrepaymentForeclosureConfigDTO;
import com.bbots.mfin.dto.RateRevisionHistoryDTO;
import com.bbots.mfin.dto.ResponseDTO;
import com.bbots.mfin.repository.Auth101Repository;
import com.bbots.mfin.repository.AuthQ001Repository;
import com.bbots.mfin.repository.RegionRepository;
import com.bbots.mfin.repository.BranchRegionMapRepository;
import com.bbots.mfin.repository.DelinquencyBucketRepository;
import com.bbots.mfin.repository.GLMappingRepository;
import com.bbots.mfin.repository.HolidayCalendarRepository;
import com.bbots.mfin.repository.LoanProductRepository;
import com.bbots.mfin.repository.PenaltyRateHistoryRepository;
import com.bbots.mfin.repository.PrepaymentForeclosureConfigRepository;
import com.bbots.mfin.repository.RateRevisionHistoryRepository;
import com.bbots.mfin.service.AuthorizationProcedureService;
import com.bbots.mfin.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {

	@Resource
	private RegionRepository regionRepository;

	@Resource
	private BranchRegionMapRepository branchRegionMapRepository;

	@Resource
	private AuthorizationProcedureService authProcedureService;

	@Resource
	private Auth101Repository auth101Repository;

	@Resource
	private AuthQ001Repository authQ001Repository;

	@Resource
	private LoanProductRepository loanProductRepository;

	@Resource
	private DelinquencyBucketRepository delinquencyBucketRepository;
	
	@Resource
	private PenaltyRateHistoryRepository penaltyRateHistoryRepository;
	
	@Resource
	private GLMappingRepository glMappingRepository;
	
	@Resource
	private RateRevisionHistoryRepository rateRevisionHistoryRepository;
	
	@Resource
	private HolidayCalendarRepository holidayCalendarRepository;
	
	@Resource

	private PrepaymentForeclosureConfigRepository prepaymentForeclosureConfigRepository;
	 

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

			region.setOrgcode(region.getId().getOrgCode());
			region.setRegion_code(region.getId().getRegionCode());
			region.seteUser("admin");
			region.seteDate(LocalDate.now().toString());

			region.setId(new RegionId());

			authProcedureService.processAuthorization(region.getOrgcode(), "REGMAS", "rgn001", region, "INSERT");

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

		List<Auth101> resultData = new ArrayList<>();
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

		List<AuthQ001> resultData = new ArrayList<>();
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

	@Override
	public ResponseDTO<List<Region>> getRegionData(Long orgCode) {
		List<Region> resultData = new ArrayList<>();
		ResponseDTO<List<Region>> responseDTO = new ResponseDTO<>();

		try {
			resultData = regionRepository.findByIdOrgCode(orgCode);

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
	public ResponseDTO<List<BranchRegionMap>> getBranchRegionMapData(Long orgCode) {
		List<BranchRegionMap> resultData = new ArrayList<>();
		ResponseDTO<List<BranchRegionMap>> responseDTO = new ResponseDTO<>();

		try {
			resultData = branchRegionMapRepository.findAllByOrgCode(orgCode);

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
	public ResponseDTO<BranchRegionMap> createBranchRegionMap(BranchRegionMap map) {
		ResponseDTO<BranchRegionMap> responseDTO = new ResponseDTO<>();

		try {
			BranchRegionMapId id = map.getId();
			if (id != null && branchRegionMapRepository.existsById(id)) {
				responseDTO.setSuccess(false);
				responseDTO.setMessage("Branch Region Map already exists");
				return responseDTO;
			}

			map.setOrgcode(map.getId().getOrgcode());
			map.setBranch_code(map.getId().getBranch_code());
			map.seteUser("admin");
			map.seteDate(LocalDate.now().toString());

			map.setId(new BranchRegionMapId());

			authProcedureService.processAuthorization(map.getOrgcode(), "BRANCHMST", "rgn002", map, "INSERT");

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Sent for authorization");
			responseDTO.setData(map);

		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<BranchRegionMap> updateBranchRegionMap(BranchRegionMap map) {
		ResponseDTO<BranchRegionMap> responseDTO = new ResponseDTO<>();

		try {
			BranchRegionMapId id = map.getId();
			if (id == null || !branchRegionMapRepository.existsById(id)) {
				responseDTO.setSuccess(false);
				responseDTO.setMessage("Branch Region Map does not exist");
				return responseDTO;
			}

			map.setOrgcode(map.getId().getOrgcode());
			map.setBranch_code(map.getId().getBranch_code());
			map.setId(new BranchRegionMapId());

			map.seteUser("admin");
			map.seteDate(LocalDate.now().toString());

			authProcedureService.processAuthorization(map.getOrgcode(), "BRANCHMST", "rgn002", map, "UPDATE");

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Sent for authorization");
			responseDTO.setData(map);

		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<List<LoanProductDTO>> getLoanProductData(Long orgCode) {

		List<LoanProductDTO> resultData = new ArrayList<>();
		ResponseDTO<List<LoanProductDTO>> responseDTO = new ResponseDTO<>();

		try {

			resultData = loanProductRepository.findByIdOrgCode(orgCode);

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Record fetched successfully");
			responseDTO.setData(resultData);

		} catch (Exception e) {

			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<LoanProductDTO> createLoanProduct(LoanProductDTO loan) {

		ResponseDTO<LoanProductDTO> responseDTO = new ResponseDTO<>();

		try {

			if (loanProductRepository.existsById(loan.getOrgcode(), loan.getProduct_code())) {

				responseDTO.setSuccess(false);
				responseDTO.setMessage("Loan Product already exists");
				return responseDTO;
			}

			loan.setEuser("admin");
			loan.setEdate(LocalDate.now().toString());

			authProcedureService.processAuthorization(loan.getOrgcode(), "LOANMST", "LOAN101", loan, "INSERT");

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Sent for authorization");
			responseDTO.setData(loan);

		} catch (Exception e) {

			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<LoanProductDTO> updateLoanProduct(LoanProductDTO loan) {

		ResponseDTO<LoanProductDTO> responseDTO = new ResponseDTO<>();

		try {

			if (!loanProductRepository.existsById(loan.getOrgcode(), loan.getProduct_code())) {
				responseDTO.setSuccess(false);
				responseDTO.setMessage("Loan Product does not exist");
				return responseDTO;
			}

			loan.setEuser("admin");
			loan.setEdate(LocalDate.now().toString());

			authProcedureService.processAuthorization(loan.getOrgcode(), "LOANMST", "LOAN101", loan, "UPDATE");

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Sent for authorization");
			responseDTO.setData(loan);

		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<List<DelinquencyBucketDTO>> getDelinquencyBucketData(Long orgCode) {

		List<DelinquencyBucketDTO> resultData = new ArrayList<>();
		ResponseDTO<List<DelinquencyBucketDTO>> responseDTO = new ResponseDTO<>();

		try {

			resultData = delinquencyBucketRepository.findByIdOrgCode(orgCode);

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Record fetched successfully");
			responseDTO.setData(resultData);

		} catch (Exception e) {

			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<DelinquencyBucketDTO> createDelinquencyBucket(DelinquencyBucketDTO bucket) {

		ResponseDTO<DelinquencyBucketDTO> responseDTO = new ResponseDTO<>();

		try {

			if (delinquencyBucketRepository.existsById(bucket.getOrgcode(), bucket.getProduct_code(),
					bucket.getDelinquency_code())) {

				responseDTO.setSuccess(false);
				responseDTO.setMessage("Delinquency Bucket already exists");
				return responseDTO;
			}

			bucket.setEuser("admin");
			bucket.setEdate(LocalDate.now().toString());

			authProcedureService.processAuthorization(bucket.getOrgcode(), "LOANDBM", "LOAN102", bucket, "INSERT");

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Sent for authorization");
			responseDTO.setData(bucket);

		} catch (Exception e) {

			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO<DelinquencyBucketDTO> updateDelinquencyBucket(DelinquencyBucketDTO bucket) {

		ResponseDTO<DelinquencyBucketDTO> responseDTO = new ResponseDTO<>();

		try {

			if (!delinquencyBucketRepository.existsById(bucket.getOrgcode(), bucket.getProduct_code(),
					bucket.getDelinquency_code())) {

				responseDTO.setSuccess(false);
				responseDTO.setMessage("Delinquency Bucket does not exist");
				return responseDTO;
			}

			bucket.setEuser("admin");
			bucket.setEdate(LocalDate.now().toString());

			authProcedureService.processAuthorization(bucket.getOrgcode(), "LOANDBM", "LOAN102", bucket, "UPDATE");

			responseDTO.setSuccess(true);
			responseDTO.setMessage("Sent for authorization");
			responseDTO.setData(bucket);

		} catch (Exception e) {

			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}
	
	@Override
	public ResponseDTO<List<PenaltyRateHistoryDTO>> getPenaltyRateHistoryData(Long orgCode) {

	    List<PenaltyRateHistoryDTO> resultData = new ArrayList<>();
	    ResponseDTO<List<PenaltyRateHistoryDTO>> responseDTO = new ResponseDTO<>();

	    try {

	        resultData = penaltyRateHistoryRepository.findByIdOrgCode(orgCode);

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Record fetched successfully");
	        responseDTO.setData(resultData);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	@Override
	public ResponseDTO<PenaltyRateHistoryDTO> createPenaltyRateHistory(PenaltyRateHistoryDTO penalty) {

	    ResponseDTO<PenaltyRateHistoryDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (penaltyRateHistoryRepository.existsById(
	                penalty.getOrgcode(),
	                penalty.getProduct_code(),
	                penalty.getDelinquency_code(),
	                penalty.getEff_date())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("Penalty Rate History already exists");
	            return responseDTO;
	        }

	        penalty.setEuser("admin");
	        penalty.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                penalty.getOrgcode(),
	                "LOANPRH",
	                "LOAN103",
	                penalty,
	                "INSERT");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(penalty);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	@Override
	public ResponseDTO<PenaltyRateHistoryDTO> updatePenaltyRateHistory(PenaltyRateHistoryDTO penalty) {

	    ResponseDTO<PenaltyRateHistoryDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (!penaltyRateHistoryRepository.existsById(
	                penalty.getOrgcode(),
	                penalty.getProduct_code(),
	                penalty.getDelinquency_code(),
	                penalty.getEff_date())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("Penalty Rate History does not exist");
	            return responseDTO;
	        }

	        penalty.setEuser("admin");
	        penalty.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                penalty.getOrgcode(),
	                "LOANPRH",
	                "LOAN103",
	                penalty,
	                "UPDATE");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(penalty);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	@Override
	public ResponseDTO<List<GLMappingDTO>> getGLMappingData(Long orgCode) {

	    List<GLMappingDTO> resultData = new ArrayList<>();
	    ResponseDTO<List<GLMappingDTO>> responseDTO = new ResponseDTO<>();

	    try {

	        resultData = glMappingRepository.findByIdOrgCode(orgCode);

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Record fetched successfully");
	        responseDTO.setData(resultData);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	@Override
	public ResponseDTO<GLMappingDTO> createGLMapping(GLMappingDTO map) {

	    ResponseDTO<GLMappingDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (glMappingRepository.existsById(
	                map.getOrgcode(),
	                map.getProduct_code(),
	                map.getDelinquency_code())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("GL Mapping already exists");
	            return responseDTO;
	        }

	        map.setEuser("admin");
	        map.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                map.getOrgcode(),
	                "LOANGLM",
	                "LOAN104",
	                map,
	                "INSERT");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(map);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	@Override
	public ResponseDTO<GLMappingDTO> updateGLMapping(GLMappingDTO map) {

	    ResponseDTO<GLMappingDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (!glMappingRepository.existsById(
	                map.getOrgcode(),
	                map.getProduct_code(),
	                map.getDelinquency_code())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("GL Mapping does not exist");
	            return responseDTO;
	        }

	        map.setEuser("admin");
	        map.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                map.getOrgcode(),
	                "LOANGLM",
	                "LOAN104",
	                map,
	                "UPDATE");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(map);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	@Override
	public ResponseDTO<List<RateRevisionHistoryDTO>> getRateRevisionHistoryData(Long orgCode) {

	    List<RateRevisionHistoryDTO> resultData = new ArrayList<>();
	    ResponseDTO<List<RateRevisionHistoryDTO>> responseDTO = new ResponseDTO<>();

	    try {

	        resultData = rateRevisionHistoryRepository.findByIdOrgCode(orgCode);

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Record fetched successfully");
	        responseDTO.setData(resultData);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	@Override
	public ResponseDTO<RateRevisionHistoryDTO> createRateRevisionHistory(RateRevisionHistoryDTO rate) {

	    ResponseDTO<RateRevisionHistoryDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (rateRevisionHistoryRepository.existsById(
	                rate.getOrgcode(),
	                rate.getProduct_code(),
	                rate.getEff_date())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("Rate Revision History already exists");
	            return responseDTO;
	        }

	        rate.setEuser("admin");
	        rate.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                rate.getOrgcode(),
	                "LOANRRH",
	                "LOAN108",
	                rate,
	                "INSERT");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(rate);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	
	@Override
	public ResponseDTO<RateRevisionHistoryDTO> updateRateRevisionHistory(RateRevisionHistoryDTO rate) {

	    ResponseDTO<RateRevisionHistoryDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (!rateRevisionHistoryRepository.existsById(
	                rate.getOrgcode(),
	                rate.getProduct_code(),
	                rate.getEff_date())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("Rate Revision History does not exist");
	            return responseDTO;
	        }

	        rate.setEuser("admin");
	        rate.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                rate.getOrgcode(),
	                "LOANRRH",
	                "LOAN108",
	                rate,
	                "UPDATE");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(rate);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	
	@Override
	public ResponseDTO<List<HolidayCalendarDTO>> getHolidayCalendarData(Long orgCode) {

	    List<HolidayCalendarDTO> resultData = new ArrayList<>();
	    ResponseDTO<List<HolidayCalendarDTO>> responseDTO = new ResponseDTO<>();

	    try {

	        resultData = holidayCalendarRepository.findByIdOrgCode(orgCode);

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Record fetched successfully");
	        responseDTO.setData(resultData);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	
	@Override
	public ResponseDTO<HolidayCalendarDTO> createHolidayCalendar(HolidayCalendarDTO holiday) {

	    ResponseDTO<HolidayCalendarDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (holidayCalendarRepository.existsById(
	                holiday.getOrgcode(),
	                holiday.getBranch_code(),
	                holiday.getHoliday_date())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("Holiday Calendar already exists");
	            return responseDTO;
	        }

	        holiday.setEuser("admin");
	        holiday.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                holiday.getOrgcode(),
	                "HOLICAL",
	                "CAL001",
	                holiday,
	                "INSERT");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(holiday);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	
	@Override
	public ResponseDTO<HolidayCalendarDTO> updateHolidayCalendar(HolidayCalendarDTO holiday) {

	    ResponseDTO<HolidayCalendarDTO> responseDTO = new ResponseDTO<>();

	    try {

	        if (!holidayCalendarRepository.existsById(
	                holiday.getOrgcode(),
	                holiday.getBranch_code(),
	                holiday.getHoliday_date())) {

	            responseDTO.setSuccess(false);
	            responseDTO.setMessage("Holiday Calendar does not exist");
	            return responseDTO;
	        }

	        holiday.setEuser("admin");
	        holiday.setEdate(LocalDate.now().toString());

	        authProcedureService.processAuthorization(
	                holiday.getOrgcode(),
	                "HOLICAL",
	                "CAL001",
	                holiday,
	                "UPDATE");

	        responseDTO.setSuccess(true);
	        responseDTO.setMessage("Sent for authorization");
	        responseDTO.setData(holiday);

	    } catch (Exception e) {

	        responseDTO.setSuccess(false);
	        responseDTO.setMessage(e.getMessage());
	    }

	    return responseDTO;
	}
	
	
	@Override

	public ResponseDTO<List<PrepaymentForeclosureConfigDTO>> getPrepaymentForeclosureConfigData(Long orgCode) {
	 
	    List<PrepaymentForeclosureConfigDTO> resultData = new ArrayList<>();

	    ResponseDTO<List<PrepaymentForeclosureConfigDTO>> responseDTO = new ResponseDTO<>();
	 
	    try {
	 
	        resultData = prepaymentForeclosureConfigRepository.findByIdOrgCode(orgCode);
	 
	        responseDTO.setSuccess(true);

	        responseDTO.setMessage("Record fetched successfully");

	        responseDTO.setData(resultData);
	 
	    } catch (Exception e) {
	 
	        responseDTO.setSuccess(false);

	        responseDTO.setMessage(e.getMessage());

	    }
	 
	    return responseDTO;

	}
	 
	@Override

	public ResponseDTO<PrepaymentForeclosureConfigDTO> createPrepaymentForeclosureConfig(PrepaymentForeclosureConfigDTO config) {
	 
	    ResponseDTO<PrepaymentForeclosureConfigDTO> responseDTO = new ResponseDTO<>();
	 
	    try {
	 
	        if (prepaymentForeclosureConfigRepository.existsById(

	                config.getOrgcode(),

	                config.getProduct_code())) {
	 
	            responseDTO.setSuccess(false);

	            responseDTO.setMessage("Prepayment / Foreclosure Configuration already exists");

	            return responseDTO;

	        }
	 
	        config.setEuser("admin");

	        config.setEdate(LocalDate.now().toString());
	 
	        authProcedureService.processAuthorization(

	                config.getOrgcode(),

	                "LOANPFC",

	                "LOAN107",

	                config,

	                "INSERT");
	 
	        responseDTO.setSuccess(true);

	        responseDTO.setMessage("Sent for authorization");

	        responseDTO.setData(config);
	 
	    } catch (Exception e) {
	 
	        responseDTO.setSuccess(false);

	        responseDTO.setMessage(e.getMessage());

	    }
	 
	    return responseDTO;

	}
	 
	@Override

	public ResponseDTO<PrepaymentForeclosureConfigDTO> updatePrepaymentForeclosureConfig(PrepaymentForeclosureConfigDTO config) {
	 
	    ResponseDTO<PrepaymentForeclosureConfigDTO> responseDTO = new ResponseDTO<>();
	 
	    try {
	 
	        if (!prepaymentForeclosureConfigRepository.existsById(

	                config.getOrgcode(),

	                config.getProduct_code())) {
	 
	            responseDTO.setSuccess(false);

	            responseDTO.setMessage("Prepayment / Foreclosure Configuration does not exist");

	            return responseDTO;

	        }
	 
	        config.setEuser("admin");

	        config.setEdate(LocalDate.now().toString());
	 
	        authProcedureService.processAuthorization(

	                config.getOrgcode(),

	                "LOANPFC",

	                "LOAN107",

	                config,

	                "UPDATE");
	 
	        responseDTO.setSuccess(true);

	        responseDTO.setMessage("Sent for authorization");

	        responseDTO.setData(config);
	 
	    } catch (Exception e) {
	 
	        responseDTO.setSuccess(false);

	        responseDTO.setMessage(e.getMessage());

	    }
	 
	    return responseDTO;

	}
	 
	

}
