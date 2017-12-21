package hello;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C:\\Users\\haobo\\SourceCode\\Java\\JavaWeb\\Spring\\11.30\\6220_final_project\\src\\main\\resources\\static\\image\\";

    @GetMapping("/upload")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file, @RequestAttribute String name) {

//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "redirect:uploadStatus";
//        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + name + ".jpg");
            Files.write(path, bytes);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    

    @GetMapping("/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename){
        Resource file = loadAsResource(filename);
        return ResponseEntity
                .ok()
                .body(file);
    }

    private final Path p = Paths.get(UPLOADED_FOLDER);
     public Resource loadAsResource(String filename) {
        try {
            Path file = p.resolve(filename);
            Resource resource =  new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                System.out.println("no file");
            }
        } catch (MalformedURLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(p, 1)
                    .filter(path -> !path.equals(p))
                    .map(path -> p.relativize(path));
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}