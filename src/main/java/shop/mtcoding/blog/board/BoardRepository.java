package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepository {

    private EntityManager em;

    public BoardRepository(EntityManager em) {
        this.em = em;
    }

    public List<Board> findAll() {
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
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
}
