package com.gsohz.certification_nlw.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.gsohz.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;

@Service
public class VerifyIfHasCertificationUseCase {
  public boolean execute(VerifyHasCertificationDTO dto) {
    if (dto.getEmail().equals("fa") && dto.getTechnology().equals("dto")) {
      return true;
    }
    return false;
  }
}
