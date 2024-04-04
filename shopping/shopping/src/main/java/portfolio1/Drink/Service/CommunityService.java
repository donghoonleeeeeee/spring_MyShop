package portfolio1.Drink.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import portfolio1.Drink.DTO.Community.*;

import java.security.Principal;
import java.util.List;

public interface CommunityService
{
    Page<CommunityListDTO> CommunityList(String type, Principal principal, Pageable pageable);
    void CommunityRegister(Principal principal, CommunityDTO communityDTO) throws Exception;
    void CommunityDelete(List<Long> idx);
    CommunityViewDTO CommunityView(Long idx, HttpServletResponse response, HttpServletRequest request);
    CommunityViewDTO CommunityModify(Long idx);
    void Community_Modify_proc(CommunityModifyDTO communityModifyDTO) throws  Exception;
    void CommentRegister(CommunityKeyDTO communityKeyDTO, CommentDTO commentDTO, Principal principal);
    Page<CommentDTO> CommentView(Long idx, Pageable pageable);
    void CommentDelete(Long idx, Principal principal);
}
