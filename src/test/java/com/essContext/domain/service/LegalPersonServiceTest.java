package com.essContext.domain.service;

import com.essContext.BaseTest;
import com.essContext.controller.request.LegalPersonRequest;
import com.essContext.domain.model.LegalPerson;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @ClassName:
 * @Description:
 * @author liuyang
 * @date 2020/7/25
 * @version V1.0
 */
class LegalPersonServiceTest extends BaseTest {

    @Autowired
    private LegalPersonService legalPersonService;

    enum IdType {身份证, 护照}

    enum Type {企业, 农专社, 个体工商户}

    private void test(LegalPerson legalPerson) {
        assertTrue(StringUtils.isNotBlank(legalPerson.getType()));
        assertTrue(StringUtils.isNotBlank(legalPerson.getCompanyName()));
        assertTrue(StringUtils.isNotBlank(legalPerson.getCompanyCode()));
        assertTrue(StringUtils.isNotBlank(legalPerson.getName()));
        assertTrue(StringUtils.isNotBlank(legalPerson.getIdType()));
        assertTrue(StringUtils.isNotBlank(legalPerson.getIdCode()));

        assertTrue(legalPerson.getCompanyName().length() < 51);
        assertTrue(legalPerson.getCompanyCode().length() < 19);
        assertTrue(legalPerson.getName().length() < 21);

        if (IdType.护照.name().equals(legalPerson.getIdType())) {
            assertTrue(legalPerson.getIdCode().length() < 15);
        } else if (IdType.身份证.name().equals(legalPerson.getIdType())) {
            assertTrue(legalPerson.getIdCode().length() < 19);
        } else {
            throw new RuntimeException();
        }

        Type.valueOf(legalPerson.getType());

        if ("00000".equals(legalPerson.getCompanyCode())) {
            throw new RuntimeException("00000已注册");
        }
        if ("11111".equals(legalPerson.getCompanyCode()) && "五一公司".equals(legalPerson.getCompanyName())) {
            throw new RuntimeException("统一社会信用代码与企业名称不匹配");
        }
    }

    @Test
    void should_return_success_given_legalPersonInfo_when_register() throws Exception {
        LegalPersonRequest legalPersonRequest = new LegalPersonRequest();
        legalPersonRequest.setCompanyCode("12345");
        legalPersonRequest.setCompanyName("company");
        legalPersonRequest.setIdCode("67890");
        legalPersonRequest.setIdType("身份证");
        legalPersonRequest.setName("name");
        legalPersonRequest.setType("企业");
        LegalPerson legalPerson = legalPersonService.register(legalPersonRequest);
        test(legalPerson);
    }

    @Test
    void should_return_require_failed_given_legalPersonInfo_when_register() throws Exception {
        LegalPersonRequest legalPersonRequest = new LegalPersonRequest();
        legalPersonRequest.setCompanyCode("");
        legalPersonRequest.setCompanyName("");
        legalPersonRequest.setIdCode("");
        legalPersonRequest.setIdType("");
        legalPersonRequest.setName("");
        legalPersonRequest.setType("");
        LegalPerson legalPerson = legalPersonService.register(legalPersonRequest);
        test(legalPerson);
    }

    @Test
    void should_return_length_failed_given_legalPersonInfo_when_register() throws Exception {
        LegalPersonRequest legalPersonRequest = new LegalPersonRequest();
        legalPersonRequest.setCompanyCode("1234512345678987654321");
        legalPersonRequest.setCompanyName("company123456789876543");
        legalPersonRequest.setIdCode("67890123456789876543");
        legalPersonRequest.setIdType("身份证123456789876543");
        legalPersonRequest.setName("name123456789876543");
        legalPersonRequest.setType("身份证123456789876543");
        LegalPerson legalPerson = legalPersonService.register(legalPersonRequest);
        test(legalPerson);
    }

    @Test
    void should_return_enum_failed_given_legalPersonInfo_when_register() throws Exception {
        LegalPersonRequest legalPersonRequest = new LegalPersonRequest();
        legalPersonRequest.setCompanyCode("1234512345678987654321");
        legalPersonRequest.setCompanyName("company123456789876543");
        legalPersonRequest.setIdCode("67890123456789876543");
        legalPersonRequest.setIdType("身份证123456789876543");
        legalPersonRequest.setName("name123456789876543");
        legalPersonRequest.setType("身份证123456789876543");
        LegalPerson legalPerson = legalPersonService.register(legalPersonRequest);
        test(legalPerson);
    }

    @Test
    void should_return_register_failed_given_legalPersonInfo_when_register() throws Exception {
        LegalPersonRequest legalPersonRequest = new LegalPersonRequest();
        legalPersonRequest.setCompanyCode("00000");
        legalPersonRequest.setCompanyName("五零公司");
        legalPersonRequest.setIdCode("123");
        legalPersonRequest.setIdType("身份证");
        legalPersonRequest.setName("123");
        legalPersonRequest.setType("企业");
        LegalPerson legalPerson = legalPersonService.register(legalPersonRequest);
        test(legalPerson);
    }
}