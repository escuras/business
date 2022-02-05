package business.resource;

import business.assembler.SampleResponseAssembler;
import business.controller.SampleController;
import business.domain.Sample;
import business.dto.request.PaginationRequest;
import business.dto.request.SampleRequest;
import business.dto.response.SampleResponse;
import business.service.SampleService;
import business.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class SampleResourceTest {

    private SampleService service;
    private SampleResponseAssembler assembler;
    private ConversionService conversionService;
    private PagedResourcesAssembler pagedAssembler;


    private MockMvc mockMvc;

    @BeforeEach
    private void setUp() {
        this.service = mock(SampleService.class);
        this.assembler = mock(SampleResponseAssembler.class);
        this.conversionService = mock(ConversionService.class);
        this.pagedAssembler = mock(PagedResourcesAssembler.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new SampleController(this.service, this.assembler, this.pagedAssembler, this.conversionService))
                .build();
    }

    @Test
    public void getByIdTest() throws Exception {
        when(this.service.findById(Mockito.anyLong())).thenReturn(Optional.of(Sample.builder().id(1L).name("name").build()));
        when(this.assembler.toModel(Mockito.any())).thenReturn(SampleResponse.builder().id(1L).build());
        this.mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_PATH + "/1")
                        .param("page", Constants.DEFAULT_PAGE_INITAL)
                        .param("size", Constants.DEFAULT_PAGE_SIZE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
    }

    @Test
    public void getAllPageableTest() throws Exception {
        final List<Sample> samples = new ArrayList<>();
        samples.add(Sample.builder().id(1L).name("name 1").build());
        samples.add(Sample.builder().id(2L).name("name 2").build());

        final List<SampleResponse> sampleResponses = new ArrayList<>();
        sampleResponses.add(SampleResponse.builder().id(1L).nome("name 1").build());
        sampleResponses.add(SampleResponse.builder().id(2L).nome("name 2").build());

        final Page<SampleResponse> page = new PageImpl<>(sampleResponses);

        final Page<Sample> pagedResponse = new PageImpl<>(samples);

        final PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), 2, page.getTotalElements(), page.getTotalPages());

        when(this.service.findByPageable(PaginationRequest.builder().build())).thenReturn(pagedResponse);
        when(this.pagedAssembler.toModel(any(), eq(this.assembler))).thenReturn(PagedModel.of(sampleResponses, metadata));

        this.mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_PATH)
                        .param("page", Constants.DEFAULT_PAGE_INITAL)
                        .param("size", Constants.DEFAULT_PAGE_SIZE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages", is(1)));
    }

    @Test
    public void saveTest() throws Exception {

        when(this.service.save(Mockito.any())).thenReturn(Sample.builder().id(1L).name("name").build());
        when(this.assembler.toModel(Mockito.any())).thenReturn(SampleResponse.builder().id(1L).build());

        this.mockMvc.perform(MockMvcRequestBuilders.post(Constants.BASE_PATH)
                        .content(new ObjectMapper().writeValueAsBytes(SampleRequest.builder().id(1L).name("name").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
    }

    @Test
    public void deleteTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateTest() throws Exception {

        final SampleRequest in = SampleRequest.builder().name("name-alterado").build();
        doNothing().when(this.service).update(1L, Sample.builder().name("name-alterado").build());

        this.mockMvc.perform(MockMvcRequestBuilders.put(Constants.BASE_PATH + "/1")
                        .content(new ObjectMapper().writeValueAsBytes(in))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

}
