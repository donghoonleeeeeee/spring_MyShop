package portfolio1.Drink.Controller.Admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import portfolio1.Drink.Service.ToolsService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tools")
public class ToolsController
{
    private final ToolsService toolsService;

    @GetMapping("/download")
    public void Download(@RequestParam("image")String image, HttpServletRequest request, HttpServletResponse response)
    {
        toolsService.Download(image, request, response);
    }

    @GetMapping("/thum_download")
    public void Thum_download(@RequestParam("image")String image, HttpServletRequest request, HttpServletResponse response)
    {
        toolsService.Thum_Download(image,request, response);
    }

    @GetMapping("/review")
    public void review(@RequestParam("image")String image, HttpServletRequest request, HttpServletResponse response)
    {
        toolsService.review_image(image, request, response);
    }

}
