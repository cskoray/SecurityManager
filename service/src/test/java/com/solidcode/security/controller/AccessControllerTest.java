package com.solidcode.security.controller;

import static com.solidcode.security.util.ObjectFactoryTest.ACCESS_CONTROL;
import static com.solidcode.security.util.ObjectFactoryTest.BuildAccessRequest;
import static com.solidcode.security.util.ObjectFactoryTest.ERROR_CODE;
import static com.solidcode.security.util.ObjectFactoryTest.ERROR_DESCRIPTION;
import static com.solidcode.security.util.ObjectFactoryTest.FIRST_SEEN;
import static com.solidcode.security.util.ObjectFactoryTest.FIRST_SEEN_INVALID;
import static com.solidcode.security.util.ObjectFactoryTest.GET_ACCESS_CONTROL_BY_VALUE;
import static com.solidcode.security.util.ObjectFactoryTest.INVALID_TOTAL_COUNT_MESSAGE;
import static com.solidcode.security.util.ObjectFactoryTest.INVALID_VALUE_MESSAGE;
import static com.solidcode.security.util.ObjectFactoryTest.ObjectToJson;
import static com.solidcode.security.util.ObjectFactoryTest.RECORD;
import static com.solidcode.security.util.ObjectFactoryTest.TOTAL_COUNT;
import static com.solidcode.security.util.ObjectFactoryTest.TOTAL_COUNT_INVALID;
import static com.solidcode.security.util.ObjectFactoryTest.VALUE;
import static com.solidcode.security.util.ObjectFactoryTest.VALUE_INVALID;
import static com.solidcode.security.util.ObjectFactoryTest.buildAccessResponse;
import static com.solidcode.security.exception.ErrorType.DATE_TIME_FORMAT_ERROR;
import static com.solidcode.security.exception.ErrorType.DUPLICATE_ACCESS_VALUE_ERROR;
import static com.solidcode.security.exception.ErrorType.INVALID_PARAMETER_ERROR;
import static com.solidcode.security.exception.ErrorType.VALUE_NOT_FOUND;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.solidcode.security.dto.request.AccessRequestDto;
import com.solidcode.security.exception.AccessControlException;
import com.solidcode.security.exception.GlobalExceptionHandler;
import com.solidcode.security.service.AccessControlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@ComponentScan({"com.solidcode.*"})
class AccessControllerTest {

  private MockMvc mockMvc;

  @Mock
  private AccessControlService accessControlService;

  @InjectMocks
  private AccessController unit;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(unit)
        .setControllerAdvice(new GlobalExceptionHandler()).build();
  }

  @Test
  public void shouldRecordAccess_Record() throws Exception {

    doNothing().when(accessControlService)
        .recordAccess(BuildAccessRequest(VALUE, FIRST_SEEN, TOTAL_COUNT));
    this.mockMvc.perform(
        post(ACCESS_CONTROL + RECORD).content(
                ObjectToJson(BuildAccessRequest(VALUE, FIRST_SEEN, TOTAL_COUNT)))
            .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isOk());
  }

  @Test
  public void should_ThrowException_DuplicateValue_Record() throws Exception {

    doThrow(new AccessControlException(DUPLICATE_ACCESS_VALUE_ERROR)).when(accessControlService)
        .recordAccess(BuildAccessRequest(VALUE, FIRST_SEEN, TOTAL_COUNT));
    this.mockMvc.perform(
            post(ACCESS_CONTROL + RECORD).content(
                    ObjectToJson(BuildAccessRequest(VALUE, FIRST_SEEN, TOTAL_COUNT)))
                .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(DUPLICATE_ACCESS_VALUE_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(DUPLICATE_ACCESS_VALUE_ERROR.getMessage()));
  }

  @Test
  public void should_ThrowException_InvalidValue_Record() throws Exception {

    this.mockMvc.perform(
            post(ACCESS_CONTROL + RECORD).content(
                    ObjectToJson(BuildAccessRequest(VALUE_INVALID, FIRST_SEEN, TOTAL_COUNT)))
                .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(INVALID_PARAMETER_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(INVALID_VALUE_MESSAGE));
  }

  @Test
  public void should_ThrowException_InvalidFirstSeen_Record() throws Exception {

    doThrow(new AccessControlException(DATE_TIME_FORMAT_ERROR)).when(accessControlService)
        .recordAccess(BuildAccessRequest(VALUE, FIRST_SEEN_INVALID, TOTAL_COUNT));
    this.mockMvc.perform(
            post(ACCESS_CONTROL + RECORD).content(
                    ObjectToJson(BuildAccessRequest(VALUE, FIRST_SEEN_INVALID, TOTAL_COUNT)))
                .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(DATE_TIME_FORMAT_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(DATE_TIME_FORMAT_ERROR.getMessage()));
  }

  @Test
  public void should_ThrowException_InvalidTotalCount_Record() throws Exception {

    this.mockMvc.perform(
            post(ACCESS_CONTROL + RECORD).content(
                    ObjectToJson(BuildAccessRequest(VALUE, FIRST_SEEN_INVALID, TOTAL_COUNT_INVALID)))
                .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(INVALID_PARAMETER_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(INVALID_TOTAL_COUNT_MESSAGE));
  }

  @Test
  public void should_GetRecordByValue_GetByValue() throws Exception {

    AccessRequestDto record = BuildAccessRequest(VALUE, FIRST_SEEN, TOTAL_COUNT);
    when(accessControlService.getAccessByValue(VALUE)).thenReturn(buildAccessResponse());
    this.mockMvc.perform(
            get(ACCESS_CONTROL + GET_ACCESS_CONTROL_BY_VALUE, VALUE).accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.type").value(record.getType()))
        .andExpect(jsonPath("$.value").value(record.getValue()))
        .andExpect(jsonPath("$.firstSeen").value(record.getFirstSeen()))
        .andExpect(jsonPath("$.totalCount").value(record.getTotalCount()))
        .andExpect(status().isOk());
  }

  @Test
  public void should_ThrowException_ValueNotFound_Record() throws Exception {

    doThrow(new AccessControlException(VALUE_NOT_FOUND))
        .when(accessControlService)
        .getAccessByValue(VALUE);
    this.mockMvc.perform(
            get(ACCESS_CONTROL + GET_ACCESS_CONTROL_BY_VALUE, VALUE).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath(ERROR_CODE).value(VALUE_NOT_FOUND.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(VALUE_NOT_FOUND.getMessage()));
  }
}