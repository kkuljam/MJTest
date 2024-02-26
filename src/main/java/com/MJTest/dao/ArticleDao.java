package com.MJTest.dao;

import com.MJTest.dto.ArticleForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ArticleDao() { // 생성자 : 객체 생성시 초기화 담당
        // - 객체 생성시 db연동
        try {
            // 1. MYSQL 회사의 JDBC 관련된 (Driver)객체를 JVM 에 로딩한다/불러오기
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 연동된 결과의 객체를 Connection 인터페이스에 대입한다
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test9",
                    "root",
                    "1234"
            );
            System.out.println("DB 연동 성공");
        } catch (Exception e) {
            System.out.println("DB 연동 실패" + e);
        }
    }
    public ArticleForm createArticle(ArticleForm form){
        System.out.println("form = " + form);
        ArticleForm saved=new ArticleForm();
        try {
            String sql="insert into board(title,content) values(?,?)";
            ps= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, form.getTitle());
            ps.setString(2, form.getContent());
            ps.executeUpdate();
            rs=ps.getGeneratedKeys();
            if(rs.next()){
                Long bno=rs.getLong(1);
                System.out.println(bno);
                saved.setBno(bno);
                saved.setTitle(form.getTitle());
                saved.setContent(form.getContent());
                return saved;
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return saved;
    }

    public ArticleForm show(long bno){
        try {
            String sql="select*from board where bno=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,bno);
            rs=ps.executeQuery();

            if (rs.next()){
                ArticleForm articleForm=new ArticleForm(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                return articleForm;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<ArticleForm> index(){
        List<ArticleForm>forms=new ArrayList<>();
        try {
            String sql="select*from board";
            ps=conn.prepareStatement(sql);
            rs= ps.executeQuery();

            while (rs.next()){
                ArticleForm articleForm=new ArticleForm(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                forms.add(articleForm);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return forms;
    }

    public ArticleForm findById(Long bno){
        try {
            String sql="select*from board where bno=?";
            ps= conn.prepareCall(sql);
            ps.setLong(1,bno);
            rs=ps.executeQuery();

            if(rs.next()){
                return new ArticleForm(
                        rs.getLong("bno"),
                        rs.getString(2),
                        rs.getString(3)
                );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public ArticleForm update(ArticleForm form){
     try {
         String sql="update board set title = ? , content = ? where bno = ?";
         ps= conn.prepareCall(sql);
         ps.setString(1, form.getTitle());
         ps.setString(2, form.getContent());
         ps.setLong(3,form.getBno());

         int count=ps.executeUpdate();
         if(count==1){
             return  form;
         }
     }catch (Exception e){
         System.out.println(e);
     }
        return null;
    }

    public boolean delete(Long bno){
        try {
            String sql="delete from board where bno=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,bno);
            int count=ps.executeUpdate();
            if(count==1){
                return true;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }
}
