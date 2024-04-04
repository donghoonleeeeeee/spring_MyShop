package portfolio1.Drink.Service.Impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portfolio1.Drink.Entity.Items.ItemsFileEntity;
import portfolio1.Drink.Repository.Items.ItemsFileRepository;
import portfolio1.Drink.Service.ItemsService;
import portfolio1.Drink.Service.ToolsService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.System.out;

@Service
@RequiredArgsConstructor
public class ToolsServiceImpl implements ToolsService
{
    private final ItemsFileRepository itemsFileRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ItemsService.class);
    @Value("${save.path}")
    private String upload_path;
    @Value("${save.thumbnail}")
    private String upload_thumbnail_path;
    @Value("${save.review}")
    private String review_path;

    @Override
    public void file_Delete(String filename)
    {
        LOGGER.info("[도구] 아이템 정보에 등록된 사진을 삭제합니다. / 경로: "+upload_path+", 파일명: "+filename);
        File file = new File(upload_path,filename);
        if(file.exists())
        {
            file.delete();
            LOGGER.info("[도구] 삭제 성공!");
        }
    }

    @Override
    public void thum_file_Delete(String filename)
    {
        LOGGER.info("[도구] 아이템 정보에 등록된 썸네일 사진을 삭제합니다. / 경로: "+upload_path+", 파일명: thum_"+filename);
        File file = new File(upload_thumbnail_path, "thum_"+filename);
        if(file.exists())
        {
            file.delete();
            LOGGER.info("[도구] 삭제 성공!");
        }
    }

    public void review_Delete(String filename)
    {
        LOGGER.info("[도구] 리뷰에 등록된 사진을 삭제합니다. / 경로: "+review_path+", 파일명: "+filename);
        File file = new File(review_path,filename);
        if(file.exists())
        {
            file.delete();
            LOGGER.info("[도구] 삭제 성공!");
        }
    }

    @Override
    public void Upload(List<MultipartFile> files,List<String> filename) throws Exception
    {
        File savefile = null;
        LOGGER.info("[도구] 입력된 파일 업로드를 수행합니다!");
        for(int a=0; a<files.size(); a++)
        {
            savefile = new File(upload_path, filename.get(a));
            files.get(a).transferTo(savefile);
            LOGGER.info("[도구] "+a+"번째 파일 업로드 완료! / 파일명: "+filename.get(a));
        }
        savefile = new File(upload_path,filename.get(0));

        File thumbnail = new File(upload_thumbnail_path,"thum_"+filename.get(0));
        LOGGER.info("[도구] 첫번째 파일은 썸네일 등록을 수행합니다! / 파일명: "+filename.get(0));
        BufferedImage bo_image = ImageIO.read(savefile);

        BufferedImage bt_image = new BufferedImage(300,300, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = bt_image.createGraphics();
        graphics.drawImage(bo_image, 0, 0, 300, 300, null);
        ImageIO.write(bo_image,"jpg",thumbnail);
        LOGGER.info("[도구] 썸네일 업로드 완료!");
    }

    @Override
    public void file_Upload(MultipartFile file, String filename) throws Exception
    {
        LOGGER.info("[도구] 단일 파일 업로드를 수행합니다. / 파일명: "+filename);
        File Upfile = new File(upload_path,filename);
        file.transferTo(Upfile);
        LOGGER.info("[도구] 수행 완료!");
    }

    @Override
    public void thum_file_Upload(MultipartFile file, String filename) throws Exception
    {
        LOGGER.info("[도구] 썸네일 등록을 수행합니다. / 파일명: thum_"+filename);
        File originfile = new File(upload_path, filename);
        File Upfile = new File(upload_thumbnail_path,"thum_"+filename);

        BufferedImage bo_image = ImageIO.read(originfile);
        BufferedImage bt_image = new BufferedImage(300,300,BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics2D = bt_image.createGraphics();
        graphics2D.drawImage(bo_image,0,0,300,300,null);
        ImageIO.write(bo_image,"jpg",Upfile);
        LOGGER.info("[도구] 수행 완료!");
    }

    public void Review_Upload(List<MultipartFile> files,List<String> filename) throws Exception
    {
        File savefile = null;
        out.println(filename);
        for(int a=0; a<files.size(); a++)
        {
            savefile = new File(review_path, filename.get(a));
            files.get(a).transferTo(savefile);
        }
    }

    @Override
    public void Thum_Download(String image, HttpServletRequest request, HttpServletResponse response)
    {
        String ext = image.substring(image.lastIndexOf(".") + 1).toLowerCase();
        try
        {
            File file = new File(upload_thumbnail_path,image);
            InputStream in = new FileInputStream(file);
            String client = request.getHeader("User-Agent");

            if (ext.equals("gif") || ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg"))
            {
                response.setContentType("image/jpeg");
            }
            else
            {
                if (client.contains("MSIE"))
                {
                    response.setHeader("Content-Disposition", "attachment; filename=" + new String(image.getBytes("KSC5601"), "ISO8859_1"));
                }
                else
                {
                    image = new String(image.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + image + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                    response.setHeader("Content-Length", "" + file.length());
                }
            }
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[(int) file.length()];
            int leng = 0;
            while ((leng = in.read(b)) > 0)
            {
                os.write(b, 0, leng);
            }
            os.flush();
            in.close();
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void Download(String image, HttpServletRequest request, HttpServletResponse response)
    {
        String ext = image.substring(image.lastIndexOf(".") + 1).toLowerCase();
        try
        {
            File file = new File(upload_path,image);
            InputStream in = new FileInputStream(file);
            OutputStream os = response.getOutputStream();

            String client = request.getHeader("User-Agent");
            if (ext.equals("gif") || ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg"))
            {
                response.setContentType("image/jpeg");
            }
            else
            {
                if (client.contains("MSIE"))
                {
                    response.setHeader("Content-Disposition", "attachment; filename=" + new String(image.getBytes("KSC5601"), "ISO8859_1"));
                }
                else
                {
                    image = new String(image.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + image + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                    response.setHeader("Content-Length", "" + file.length());
                }
            }
                byte[] b = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0)
                {
                    os.write(b, 0, leng);
                }
                os.flush();
                in.close();
                os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void review_image(String image, HttpServletRequest request, HttpServletResponse response)
    {
        String ext = image.substring(image.lastIndexOf(".") + 1).toLowerCase();
        try
        {
            File file = new File(review_path,image);
            InputStream in = new FileInputStream(file);
            OutputStream os = response.getOutputStream();

            String client = request.getHeader("User-Agent");
            if (ext.equals("gif") || ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg"))
            {
                response.setContentType("image/jpeg");
            }
            else
            {
                if (client.contains("MSIE"))
                {
                    response.setHeader("Content-Disposition", "attachment; filename=" + new String(image.getBytes("KSC5601"), "ISO8859_1"));
                }
                else
                {
                    image = new String(image.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + image + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                    response.setHeader("Content-Length", "" + file.length());
                }
            }


            byte[] b = new byte[(int) file.length()];
            int leng = 0;
            while ((leng = in.read(b)) > 0)
            {
                os.write(b, 0, leng);
            }
            os.flush();
            in.close();
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void Preview(String image, HttpServletRequest request, HttpServletResponse response)
    {
        String ext = image.substring(image.lastIndexOf(".") + 1).toLowerCase();

        InputStream in = null;
        OutputStream os = null;
        File file = null;
        boolean skip = false;
        String client = null;

        try {
            try {
                file = new File(image);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }

            client = request.getHeader("User-Agent");
            response.reset();

            if (ext.equals("gif") || ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg")) {
                response.setContentType("image/jpeg");
            } else {

                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=" + new String(image.getBytes("KSC5601"), "ISO8859_1"));

                } else {
                    image = new String(image.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

                    response.setHeader("Content-Disposition", "attachment; filename=\"" + image + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                    response.setHeader("Content-Length", "" + file.length());
                }
            }

            if (!skip) {
                os = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;

                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                out.println("File not find.");
            }

            in.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
