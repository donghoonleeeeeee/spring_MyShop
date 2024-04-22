package portfolio1.Drink.Service.Impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import portfolio1.Drink.DTO.Community.*;
import portfolio1.Drink.Entity.Community.CommentEntity;
import portfolio1.Drink.Entity.Community.CommunityEntity;
import portfolio1.Drink.Entity.Community.CommunityFileEntity;
import portfolio1.Drink.Entity.UserEntity;
import portfolio1.Drink.Repository.Community.CommentRepository;
import portfolio1.Drink.Repository.Community.CommunityFileRepository;
import portfolio1.Drink.Repository.Community.CommunityRepository;
import portfolio1.Drink.Repository.UserRepository;
import portfolio1.Drink.Service.CommunityService;
import portfolio1.Drink.Service.ToolsService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService
{
    private final UserRepository userRepository;
    private final CommunityFileRepository communityFileRepository;
    private final CommunityRepository communityRepository;
    private final ToolsService toolsService;
    private final CommentRepository commentRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CommunityService.class);
    
    /**
     * @param type : 게시판에서 공지사항 or 리뷰 구분을 위한 변수
     * @param principal : 현재 접속한 계정의 아이디를 추출하기 위한 Security의 기능
     * @param pageable : 게시물 목록에 페이징 처리를 위한 변수
     * @return : 페이징 처리된 DTO를 List에 담아 반환
     */
    @Override
    public Page<CommunityListDTO> CommunityList(String type, Principal principal, Pageable pageable)
    {
        Page<CommunityEntity> Entity_list = null;
        if(type.equals("전체") || type.equals("none"))
        {
            Entity_list = communityRepository.findAll(pageable);
            LOGGER.info("[커뮤니티] 전체 게시물을 출력합니다. / 게시물 수: "+Entity_list.getContent().size());
        }
        else
        {
            Entity_list = communityRepository.findAllByType(type, pageable);
            LOGGER.info("[커뮤니티] 검색 기준을 설정합니다. / TYPE: "+type+", 게시물 수: "+Entity_list.getContent().size());
        }
        List<CommunityListDTO> DTO_list = new ArrayList<>();
        for(int a=0; a<Entity_list.getContent().size(); a++)
        {
            CommunityListDTO communityListDTO = Entity_list.getContent().get(a).toListDTO(principal);
            communityListDTO.setIdx(Entity_list.getContent().get(a).getIdx());
            communityListDTO.setName(Entity_list.getContent().get(a).getUserEntity().getName());
            communityListDTO.setUserid(Entity_list.getContent().get(a).getUserEntity().getUserid());
            communityListDTO.setUidx(Entity_list.getContent().get(a).getUserEntity().getIdx());
            communityListDTO.setSize(Entity_list.getContent().get(a).getCommentEntities().size());
            DTO_list.add(communityListDTO);
        }

        return new PageImpl<>(DTO_list, pageable, Entity_list.getTotalElements());
    }

    /**
     * @param principal : 게시판에서 게시물 작성 시 작성자 구분을 Security의 Priclpal 기능을 사용
     * @param communityDTO : html에서 작성된 내용을 Controller에서 받아서 Entity로 변환 후 DB에 저장하기 위한 용도
     * @throws Exception : DTO에서 첨부된 파일을 업로드 하기 위한 용도
     */
    @Override
    public void CommunityRegister(Principal principal, CommunityDTO communityDTO) throws Exception
    {
        List<String> files = new ArrayList<>();
        List<String> pathfiles = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        LOGGER.info("[커뮤니티] 새로운 게시물을 등록합니다. / 작성자: "+principal.getName());
        if(principal.getName().equals("admin"))
        {
            communityDTO.setType("공지");
        }
        else
        {
            communityDTO.setType("리뷰");
        }
        CommunityEntity communityEntity = communityDTO.toEntity();
        communityEntity.setUserEntity(userRepository.findByUserid(principal.getName()));

        communityRepository.save(communityEntity);
        LOGGER.info("[커뮤니티] 게시물 내용 저장 완료");

        for(int a=0; a<communityDTO.getFiles().size(); a++)
        {
            if(communityDTO.getFiles().get(a).isEmpty())
            {
                LOGGER.info("[커뮤니티] "+(a+1)+"번째 파일 / 등록되지 않음!");
                files.add("none");
                pathfiles.add("none");
            }
            else
            {
                LOGGER.info("[커뮤니티] "+(a+1)+"번째 파일 / 파일명: "+communityDTO.getFiles().get(a).getOriginalFilename());
                files.add(communityDTO.getFiles().get(a).getOriginalFilename());
                pathfiles.add(uuid +"_"+ communityDTO.getFiles().get(a).getOriginalFilename());
            }

            communityFileRepository.save(communityDTO.toFileEntity(files.get(a), pathfiles.get(a), communityEntity));
            LOGGER.info("[커뮤니티] 게시물 파일 저장 완료");
        }
        toolsService.Review_Upload(communityDTO.getFiles(),pathfiles);
        LOGGER.info("[커뮤니티] 첨부파일 업로드 완료");
    }


    /**
     * @param idx : 게시판에서 게시물 하나의 기본키
     * @param response : 게시판에서 하나의 게시물로 이동 했을 때 조회수 증가를 위한 Http Servlet
     * @param request : 게시판에서 하나의 게시물로 이동 했을 때 조회수 증가를 위한 Http Servlet
     * @return : findBy를 통해 하나의 게시물에 대한 Entity를 DTO로 변환하여 리턴
     */
    @Override
    public CommunityViewDTO CommunityView(Long idx, HttpServletResponse response, HttpServletRequest request)
    {
        List<String> origin = new ArrayList<>();
        List<String> path = new ArrayList<>();
        
        CommunityEntity communityEntity = communityRepository.findById(idx).orElse(null);
        LOGGER.info("[커뮤니티] 게시물로 이동합니다. / 제목: "+communityEntity.getTitle());
        
        int count = communityEntity.getHit();
        count = count + 1;
        Cookie[] cookies = request.getCookies();
        List<String> cookie_list = new ArrayList<>();
        
        for(int a=0; a<cookies.length; a++)
        {
            cookie_list.add(cookies[a].getValue());
        }

        if(cookie_list.contains("login")) // 로그인을 했지만 해당 페이지 쿠키가 없으면
        {
            LOGGER.info("[커뮤니티] 로그인 쿠키 확인 / Cookie 존재 여부: "+cookie_list.contains("login"));
            if(!cookie_list.contains("community"+idx))
            {
                LOGGER.info("[커뮤니티] 게시물 조회수 쿠키 확인 / Cookie 존재 여부: "+cookie_list.contains(String.valueOf(idx)));
                Cookie cookie = new Cookie("community"+idx,"community"+idx);
                cookie.setDomain("localhost");
                cookie.setPath("/main/CommunityView");
                cookie.setMaxAge(60*60);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                
                LOGGER.info("[커뮤니티] 새로운 쿠키를 생성합니다.");
                communityEntity.setHit(count);
                communityRepository.save(communityEntity);
            }
        }
        for(int a=0; a<communityEntity.getCommunityFileEntities().size(); a++)
        {
            origin.add(communityEntity.getCommunityFileEntities().get(a).getOrigin());
            path.add(communityEntity.getCommunityFileEntities().get(a).getPath());
        }

        CommunityViewDTO communityViewDTO = communityEntity.toViewDTO();
        communityViewDTO.setOrigin(origin);
        communityViewDTO.setPath(path);

        return communityViewDTO;
    }

    /**
     * @param idx : 관리자 로그인 시 게시판에서 게시물 여러개를 한번에 제거할 수 있는 기능을 구현하기 위해 List 형태의 기본키
     */
    @Override
    public void CommunityDelete(List<Long> idx) // 글 삭제
    {
        CommunityEntity communityEntity = null;

        for(int a=0; a<idx.size(); a++)
        {
            communityEntity = communityRepository.findById(idx.get(a)).orElse(null);
            if(communityEntity != null)
            {
                LOGGER.info("[커뮤니티]"+(a+1)+"번째 게시물을 제거합니다. / 기본키: "+idx.get(a));
                communityRepository.delete(communityEntity);
            }
            LOGGER.info("[도구]"+(a+1)+"번째 게시물에 업로드된 첨부파일을 제거합니다.");
            toolsService.review_Delete(communityEntity.getCommunityFileEntities().get(a).getPath());
        }
    }

    /**
     * @param idx : 게시물 수정을 위해 게시물의 기본키를 받음
     * @return : DB에서 기본키와 일치하는 정보를 찾아 DTO로 반환해서 HTML에서 해당 정보를 보여줌
     */
    @Override
    public CommunityViewDTO CommunityModify(Long idx)
    {
        List<String> origin = new ArrayList<>();
        List<String> path = new ArrayList<>();

        CommunityEntity communityEntity = communityRepository.findById(idx).orElse(null);

        for(int a=0; a<communityEntity.getCommunityFileEntities().size(); a++)
        {
            origin.add(communityEntity.getCommunityFileEntities().get(a).getOrigin());
            path.add(communityEntity.getCommunityFileEntities().get(a).getPath());
        }

        CommunityViewDTO communityViewDTO = communityEntity.toViewDTO();
        communityViewDTO.setOrigin(origin);
        communityViewDTO.setPath(path);

        return communityViewDTO;
    }

    @Override
    public void Community_Modify_proc(CommunityModifyDTO communityModifyDTO) throws Exception
    {
        LOGGER.info("[커뮤니티] 게시물 수정");
        CommunityEntity communityEntity = communityRepository.findById(communityModifyDTO.getIdx()).orElse(null);
        List<CommunityFileEntity> communityFileEntity = null;
        List<String> filename = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        if(communityEntity != null)
        {
            LOGGER.info("[커뮤니티] 작성자 탐색 / 기본키: "+communityModifyDTO.getIdx());
            communityFileEntity = communityEntity.getCommunityFileEntities();
            for(int a=0; a<communityFileEntity.size(); a++)
            {
                LOGGER.info("[커뮤니티] "+a+"번째 사진 업로드 확인 / 업로드: " + !communityModifyDTO.getFiles().get(a).isEmpty());
                if(!communityModifyDTO.getFiles().get(a).isEmpty()) // 파일을 업로드 했다면!
                {
                    String new_filename = uuid+"_"+communityModifyDTO.getFiles().get(a).getOriginalFilename(); // DB수정
                    filename.add(new_filename);

                    LOGGER.info("[커뮤니티] "+a+"번째 사진 수정을 시작합니다.");
                    LOGGER.info("[커뮤니티] 원본 파일 삭제");
                    toolsService.review_Delete(communityFileEntity.get(a).getPath()); // 파일 삭제 진행

                    LOGGER.info("[커뮤니티] DB 수정을 시작합니다.");
                    communityFileEntity.get(a).setOrigin(communityModifyDTO.getFiles().get(a).getOriginalFilename());
                    communityFileEntity.get(a).setPath(new_filename);
                    communityFileRepository.save(communityFileEntity.get(a));
                }
            }
            LOGGER.info("[커뮤니티] 새로운 파일을 업로드 합니다.");
            toolsService.Review_Upload(communityModifyDTO.getFiles(),filename);

            communityEntity.setTitle(communityModifyDTO.getTitle()); // 제목 저장
            communityEntity.setContent(communityModifyDTO.getContent()); // 내용 저장
            communityRepository.save(communityEntity);
        }
    }

    @Override
    public void CommentRegister(CommunityKeyDTO communityKeyDTO, CommentDTO commentDTO, Principal principal)
    {
        LOGGER.info("[댓글] Community DTO 정보: "+communityKeyDTO);
        LOGGER.info("[댓글] Comment DTO 정보: "+commentDTO);
        if (communityKeyDTO.getComment_idx() == 0)
        {
            UserEntity userEntity = userRepository.findByUserid(principal.getName());
            CommunityEntity communityEntity = communityRepository.findById(communityKeyDTO.getCommunity_idx()).orElse(null);

            LOGGER.info("[댓글] 새로운 댓글을 등록합니다. / 작성자: "+userEntity.getName());

            CommentEntity commentEntity = commentDTO.toEntity(communityEntity, userEntity);
            commentRepository.save(commentEntity);
        }
        else // 수정
        {
            LOGGER.info("[댓글] 입력한 댓글을 수정합니다.");
            CommentEntity commentEntity = commentRepository.findById(communityKeyDTO.getComment_idx()).orElse(null);
            if(principal.getName().equals(commentEntity.getUserid()))
            {
                commentEntity.setContent(commentDTO.getContent());
                commentRepository.save(commentEntity);
            }
            else
            {
                LOGGER.info("[경고] 댓글 작성자와 현재 로그인 계정이 일치하지 않습니다.");
                commentRepository.save(commentEntity);
            }
        }
    }

    @Override
    public Page<CommentDTO> CommentView(Long idx, Pageable pageable)
    {
        //CommunityEntity communityEntity = communityRepository.findById(idx).orElse(null); // 문제 없음
        //List<CommentEntity> commentEntity = communityEntity.getCommentEntities();
        Page<CommentEntity> commentEntity = commentRepository.findAllByCommunityEntity_Idx(idx,pageable);

        LOGGER.info("[댓글] 게시물의 댓글을 조회합니다. / 조회된 댓글 수: " + commentEntity.getContent().size());

        List<CommentDTO> commentDTO = new ArrayList<>();
        for(int a=0; a<commentEntity.getContent().size(); a++)
        {
            commentDTO.add(commentEntity.getContent().get(a).toDTO());
        }
        return new PageImpl<>(commentDTO, pageable, commentEntity.getTotalElements());

        //return new PageImpl<>(DTO_list, pageable, Entity_list.getTotalElements());
        //int totalElements = commentDTO.size(); // 전체 요소의 개수
        //int pageStart = (int) pageable.getOffset();
        //int pageEnd = Math.min((pageStart + pageable.getPageSize()), totalElements);

        //return new PageImpl<>(commentDTO.subList(pageStart, pageEnd), pageable, totalElements);

    }

    @Override
    public void CommentDelete(Long idx, Principal principal)
    {
        CommentEntity commentEntity = commentRepository.findById(idx).orElse(null);
        if(principal.getName().equals(commentEntity.getUserid()))
        {
            commentRepository.delete(commentEntity);
        }
        else
        {
            LOGGER.info("[Comment] 일치하지 않는 계정입니다!");
        }
    }
}
