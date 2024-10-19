package de.lordlazor.bachelorarbeit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.lordlazor.bachelorarbeit.utils.ControllerUtilities;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {


  private final ControllerUtilities controllerUtilities;

  public ViewController(ControllerUtilities controllerUtilities) {
    this.controllerUtilities = controllerUtilities;
  }

  @PostMapping("/view/viewgraph")
  public String viewGraph(@RequestParam("filename") String filename, RedirectAttributes redirectAttributes)
      throws IOException {
    String jsonFileName = controllerUtilities.extractJsonFileName(filename);

    if (jsonFileName == null) {
      redirectAttributes.addFlashAttribute("message", "Invalid filename");
      return "redirect:/view";
    }
    String rawFilename = filename;
    filename = controllerUtilities.getOutputFolder() + filename;
    String jsonData = JsonUtilities.readJsonFile(filename);

    String fileDataFolder = controllerUtilities.getOutputFolder()  + jsonFileName;
    Map<String, String> programFiles = controllerUtilities.getProgramFiles(fileDataFolder);
    String otherFilesFolder = controllerUtilities.getOutputFolder() + jsonFileName + "/" + controllerUtilities.getOtherFilesFolder();
    Map<String, String> otherFiles = controllerUtilities.getProgramFiles(otherFilesFolder);

    if(otherFiles.isEmpty()){
      otherFiles = null;
    }

    redirectAttributes.addFlashAttribute("selectedOption", rawFilename);
    redirectAttributes.addFlashAttribute("jsonData", jsonData);
    redirectAttributes.addFlashAttribute("filenames", controllerUtilities.allFiles());
    redirectAttributes.addFlashAttribute("programFiles", programFiles);
    redirectAttributes.addFlashAttribute("otherFiles", otherFiles);
    return "redirect:/view";
  }


  @PostMapping("/view/updateGraph")
  @ResponseBody
  public String updateGraph(@RequestBody Map<String, Object> checkboxData) throws IOException {
    String filename = (String) checkboxData.get("filename");
    checkboxData.remove("filename");


    ObjectMapper objectMapper = new ObjectMapper();

    Map<String, Object> jsonMap = controllerUtilities.updateJson(filename, checkboxData);

    return objectMapper.writeValueAsString(jsonMap);
  }

}
