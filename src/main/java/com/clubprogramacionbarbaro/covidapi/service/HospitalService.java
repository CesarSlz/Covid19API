package com.clubprogramacionbarbaro.covidapi.service;

import java.util.List;
import java.util.Optional;

import com.clubprogramacionbarbaro.covidapi.model.Hospital;

public interface HospitalService {

	List<Hospital> findAllHospital();
	Optional<Hospital> findHospitalById(Integer hospitalId);
	Hospital saveHospital(Hospital hospital);
	Hospital updateHospital(Integer hospitalId, Hospital hospital);
	void deleteHospitalById(Integer hospitalId);
}
