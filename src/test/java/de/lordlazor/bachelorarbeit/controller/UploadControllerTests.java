package de.lordlazor.bachelorarbeit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.lordlazor.bachelorarbeit.utils.ControllerUtilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UploadController.class)
public class UploadControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ControllerUtilities controllerUtilities;

  @Test
  public void testUploadFile() throws Exception{
    MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "".getBytes());

    mockMvc.perform(multipart("/upload/file")
            .file(file)
            .param("textInput", ""))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void testUploadFolder() throws Exception{
    MockMultipartFile file1 = new MockMultipartFile("folder", "test/test1.txt", "text/plain", "".getBytes());

    mockMvc.perform(multipart("/upload/folder")
            .file(file1)

            .param("textInput", ""))
        .andExpect(status().is3xxRedirection());
  }

}
