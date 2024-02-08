package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog._core.PagingUtil;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList);

        int currentPage = page;
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1;

        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);

        boolean first = PagingUtil.isFirst(currentPage);
        boolean last = PagingUtil.isLast(currentPage, boardRepository.count());

        System.out.println(boardRepository.count());

        request.setAttribute("first", first);
        request.setAttribute("last", last);

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO, HttpServletRequest request) {
        System.out.println(requestDTO);

        if (requestDTO.getTitle().length() > 20 || requestDTO.getContent().length() > 20) {
            request.setAttribute("msg", "클라이언트가 잘못된 요청을 하였습니다");
            request.setAttribute("status", "error 400");
            return "error/40x";
        }

        boardRepository.save(requestDTO);
        return "redirect:/";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);

        if (board.getTitle() == null || board.getContent() == null) {
            request.setAttribute("msg", "사용할 수 없습니다");
            request.setAttribute("status", "error 405");
            return "error/40x";
        }

        request.setAttribute("board", board);

        return "board/updateForm";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id, BoardRequest.UpdateDTO requestDTO, HttpServletRequest request) {
        boardRepository.update(requestDTO, id);

        if (requestDTO.getTitle().length() > 20 || requestDTO.getContent().length() > 20) {
            request.setAttribute("msg", "클라이언트가 잘못된 요청을 하였습니다");
            request.setAttribute("status", "error 400");
            return "error/40x";
        }

        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id) {
        boardRepository.delete(id);
        return "redirect:/";
    }
}
