package de.lordlazor.bachelorarbeit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.lordlazor.bachelorarbeit.utils.ControllerUtilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class GetControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ControllerUtilities controllerUtilities;

  @Test
  public void testHomePage() throws Exception{
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
  }

  @Test
  public void testUploadFilePage() throws Exception{
    mockMvc.perform(get("/upload/file"))
        .andExpect(status().isOk())
        .andExpect(view().name("upload"))
        .andExpect(model().attribute("type", "file"));
  }

  @Test
  public void testUploadFolderPage() throws Exception{
    mockMvc.perform(get("/upload/folder"))
        .andExpect(status().isOk())
        .andExpect(view().name("upload"))
        .andExpect(model().attribute("type", "folder"));
  }

  @Test
  public void testViewPage() throws Exception{
    mockMvc.perform(get("/view"))
        .andExpect(status().isOk())
        .andExpect(view().name("view"))
        .andExpect(model().attributeExists("filenames"))
        .andExpect(model().attribute("filenames", controllerUtilities.allFiles()));
  }

}
