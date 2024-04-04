package portfolio1.Drink.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import portfolio1.Drink.DTO.Items.ItemsDTO;

import java.util.List;

public interface ToolsService
{
    void Upload(List<MultipartFile> files, List<String> filename) throws Exception;
    void file_Upload(MultipartFile file, String filename) throws Exception;
    void thum_file_Upload(MultipartFile file, String filename) throws Exception;
    void file_Delete(String filename);
    void thum_file_Delete(String filename);
    void Thum_Download(String image,HttpServletRequest request, HttpServletResponse response);
    void Download(String image, HttpServletRequest request, HttpServletResponse response);
    void Preview(String image, HttpServletRequest request, HttpServletResponse response);
    void Review_Upload(List<MultipartFile> files,List<String> filename) throws Exception;
    void review_image(String image, HttpServletRequest request, HttpServletResponse response);
    void review_Delete(String filename);
}
