package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog._core.Constant;

import java.util.List;

@Repository
public class BoardRepository {

    private EntityManager em;

    public BoardRepository(EntityManager em) {
        this.em = em;
    }

    public List<Board> findAll(int page) {
        int value = page * Constant.PAGING_COUNT;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?, ?", Board.class);
        query.setParameter(1, value);
        query.setParameter(2, Constant.PAGING_COUNT);

        return query.getResultList();
    }

    public List<Board> findAll(int page, String keyword) {
        int value = page * Constant.PAGING_COUNT;
        Query query = em.createNativeQuery("select * from board_tb where title like ? order by id desc limit ?, ?", Board.class);
        query.setParameter(1, "%" + keyword + "%");
        query.setParameter(2, value);
        query.setParameter(3, Constant.PAGING_COUNT);

        return query.getResultList();
    }

    @Transactional
    public void save(BoardRequest.SaveDTO requestDTO) {
        Query query = em.createNativeQuery("insert into board_tb (author, title, content, created_at) values (?, ?, ?, now())", Board.class);
        query.setParameter(1, requestDTO.getAuthor());
        query.setParameter(2, requestDTO.getTitle());
        query.setParameter(3, requestDTO.getContent());
        query.executeUpdate();
    }

    @Transactional
    public void delete(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?", Board.class);
        query.setParameter(1, id);
        query.executeUpdate();
    }

    public Board findById(int id) {
        Query query = em.createNativeQuery("select *from board_tb where id = ?", Board.class);
        query.setParameter(1, id);
        return (Board) query.getSingleResult();
    }

    @Transactional
    public void update(BoardRequest.UpdateDTO requestDTO, int id) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content =?, author = ? where id = ?", Board.class);
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, requestDTO.getAuthor());
        query.setParameter(4, id);
        query.executeUpdate();
    }

    public int count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        Number count = (Number) query.getSingleResult();
        return count.intValue();
    }
}
