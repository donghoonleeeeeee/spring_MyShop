package portfolio1.Drink.Controller.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio1.Drink.DTO.Community.*;
import portfolio1.Drink.Service.CommunityService;
import portfolio1.Drink.Service.ToolsService;
import portfolio1.Drink.Service.UserManagerService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class CommunityController
{
    private final UserManagerService userManagerService;
    private final CommunityService communityService;
    private final ToolsService toolsService;

    @GetMapping("/Community")
    public String Community(Model model, Principal principal,
                            @RequestParam(name="type", defaultValue="none")String type,
                            @PageableDefault(page=0, size=5, sort = "regdate", direction = Sort.Direction.DESC) Pageable pageable)
    {
        Page<CommunityListDTO> list = communityService.CommunityList(type, principal, pageable);
        int scale = 2;
        int nowPage = list.getPageable().getPageNumber()+1;
        model.addAttribute("type",type);
        model.addAttribute("list",list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - scale, 1));
        model.addAttribute("endPage", Math.min(nowPage + scale, list.getTotalPages()));
        model.addAttribute("maxPage", list.getTotalPages());

        return "User/Community/Community";
    }

    @GetMapping("/CommunityRegister")
    public String Community_Register(Principal principal, Model model)
    {
        model.addAttribute("name",userManagerService.findUsername(principal.getName()));
        return "User/Community/CommunityRegister";
    }

    @PostMapping("/CommunityRegister_proc")
    public String CommunityRegister_proc(Principal principal, CommunityDTO communityDTO) throws Exception
    {
        communityService.CommunityRegister(principal, communityDTO);
        return "redirect:/main/Community";
    }

    @PostMapping("/Preview")
    public void Preview(@RequestParam("files")String files, HttpServletRequest request, HttpServletResponse response)
    {
        toolsService.Preview(files,request,response);
    }

    @GetMapping("/CommunityView")
    public String Community_View(@RequestParam(name = "idx")Long idx, Model model, HttpServletResponse response,
                                 HttpServletRequest request,
                                 @PageableDefault(page=0, size=5, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable)
    {
        model.addAttribute("view",communityService.CommunityView(idx, response, request));

        Page<CommentDTO> list = communityService.CommentView(idx, pageable);
        int scale = 2;
        int nowPage = list.getPageable().getPageNumber()+1;

        System.out.println("nowPage: "+nowPage);
        System.out.println("StartPage: "+Math.max(nowPage - scale, 1));
        System.out.println("endPage: "+Math.min(nowPage + scale, list.getTotalPages()));
        System.out.println("maxPage: "+list.getTotalPages());

        model.addAttribute("comment",list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - scale, 1));
        model.addAttribute("endPage", Math.min(nowPage + scale, list.getTotalPages()));
        model.addAttribute("maxPage", list.getTotalPages());
        return "User/Community/CommunityView";
    }

    @GetMapping("/CommunityDelete")
    public String Community_Delete(@RequestParam(name="check",defaultValue="none")List<Long> check)
    {
        communityService.CommunityDelete(check);
        return "redirect:/main/Community";
    }

    @GetMapping("/CommunityModify")
    public String Community_Modify(@RequestParam("idx")Long idx, Model model)
    {
        model.addAttribute("info",communityService.CommunityModify(idx));
        return "User/Community/CommunityModify";
    }

    @PostMapping("/CommunityModify_proc")
    public String Community_Modify_proc(CommunityModifyDTO communityModifyDTO) throws Exception
    {
        communityService.Community_Modify_proc(communityModifyDTO);
        return "redirect:/main/CommunityView?idx="+communityModifyDTO.getIdx();
    }

    @GetMapping("/Comment_proc")
    public String Comment_proc(CommunityKeyDTO communityKeyDTO, CommentDTO commentDTO, Principal principal)
    {
        communityService.CommentRegister(communityKeyDTO, commentDTO, principal);
        return "redirect:/main/CommunityView?idx="+communityKeyDTO.getCommunity_idx();
    }

    @GetMapping("/CommentDelete")
    public String Comment_Delete(@RequestParam("comment_idx")Long comment_idx, @RequestParam("community_idx")Long community_idx,Principal principal)
    {
        communityService.CommentDelete(comment_idx, principal);
        return "redirect:/main/CommunityView?idx="+community_idx;
    }
}
