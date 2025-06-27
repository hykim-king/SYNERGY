package com.pcwk.ehr.boardservice;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.board.BoardDTO;

public interface BoardService {

    /**
     * 게시글 등록
     * 
     * @param board 등록할 게시글
     * @return 성공(1) / 실패(0)
     */
    int doSave(BoardDTO board) throws SQLException;

    /**
     * 게시글 수정
     * 
     * @param board 수정할 게시글
     * @return 성공(1) / 실패(0)
     */
    int doUpdate(BoardDTO board) throws SQLException;

    /**
     * 게시글 삭제
     * 
     * @param board 삭제할 게시글
     * @return 성공(1) / 실패(0)
     */
    int doDelete(BoardDTO board) throws SQLException;

    /**
     * 게시글 단건 조회
     * 
     * @param board 조회 조건 포함 객체 (예: boardCode)
     * @return 단건 게시글
     */
    BoardDTO doSelectOne(BoardDTO board) throws SQLException;

    /**
     * 게시글 목록 조회
     * 
     * @param search 검색 조건
     * @return 게시글 목록
     */
    List<BoardDTO> doRetrieve(BoardDTO board);

    /**
     * 게시글 전체 건수 반환
     * 
     * @return 게시글 수
     */
    int getCount() throws SQLException;

    /**
     * 게시글 전체 삭제
     */
    void deleteAll() throws SQLException;
}