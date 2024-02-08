package com.gsohz.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsohz.certification_nlw.modules.questions.entities.QuestionEntity;
import com.gsohz.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.gsohz.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.gsohz.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.gsohz.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.gsohz.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.gsohz.certification_nlw.modules.students.entities.StudentEntity;
import com.gsohz.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.gsohz.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswerUseCase {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

  public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

    var hasCertification = verifyIfHasCertificationUseCase
        .execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

    if (hasCertification) {
      throw new Exception("Você já tirou sua certificação");
    }

    List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
    List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

    AtomicInteger correctAnswers = new AtomicInteger(0);

    dto.getQuestionsAnswers()
        .stream().forEach(questionAnswer -> {
          var question = questionsEntity.stream()
              .filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();

          var findCorrectAlternative = question.getAlternatives().stream()
              .filter(alternative -> alternative.isCorrect())
              .findFirst().get();

          if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
            correctAnswers.incrementAndGet();
            questionAnswer.setCorrect(true);
          } else {
            questionAnswer.setCorrect(false);
          }

          var answersCertificationsEntities = AnswersCertificationsEntity.builder()
              .answerID(questionAnswer.getAlternativeID())
              .questionID(questionAnswer.getQuestionID())
              .isCorrect(questionAnswer.isCorrect()).build();

          answersCertifications.add(answersCertificationsEntities);

        });

    var student = studentRepository.findByEmail(dto.getEmail());

    UUID studentID;
    if (student.isEmpty()) {
      var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
      studentCreated = studentRepository.save(studentCreated);
      studentID = studentCreated.getId();
    } else {
      studentID = student.get().getId();
    }

    CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
        .studentID(studentID)
        .technology(dto.getTechnology())
        .grade(correctAnswers.get())
        .build();

    var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

    answersCertifications.stream().forEach(answersCertification -> {
      answersCertification.setCertificationID(certificationStudentEntity.getId());
      answersCertification.setCertificationStudentEntity(certificationStudentEntity);
    });

    certificationStudentEntity.setAnswersCertificationsEntities(answersCertifications);

    certificationStudentRepository.save(certificationStudentEntity);

    return certificationStudentCreated;
  }
}
