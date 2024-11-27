package de.lordlazor.bachelorarbeit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.lordlazor.bachelorarbeit.utils.ControllerUtilities;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ViewController.class)
public class ViewControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ControllerUtilities controllerUtilities;

  @Test
  public void testViewGraph() throws Exception{
    mockMvc.perform(post("/view/viewgraph")
            .param("filename", "test.txt"))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void testUpdateGraph() throws Exception {
    Map<String, Object> checkboxData = new HashMap<>();
    checkboxData.put("filename", "test.txt");
    checkboxData.put("someKey", true);

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(checkboxData);

    mockMvc.perform(post("/view/updateGraph")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());
  }

}
