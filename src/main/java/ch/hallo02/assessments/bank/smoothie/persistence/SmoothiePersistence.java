package ch.hallo02.assessments.bank.smoothie.persistence;

import ch.hallo02.assessments.bank.smoothie.model.Smoothie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class SmoothiePersistence {

    private final JdbcTemplate jdbcTemplate;

    public SmoothiePersistence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Smoothie> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM SMOOTHIES", getRowMapper());
    }

    public Optional<Smoothie> getById(String id) {
        return Optional.ofNullable(
                this.jdbcTemplate.queryForObject("SELECT * FROM SMOOTHIES s WHERE s.id = ?",
                        new String[]{id},
                        getRowMapper())
        );
    }

    public void update(Smoothie smoothie) {
        this.jdbcTemplate.update("""
                        UPDATE SMOOTHIES 
                        SET name = ?, 
                        img = ?, 
                        nutrition = ? 
                        WHERE id = ?
                        """,
                new String[]{smoothie.name(), smoothie.img(), smoothie.nutrition(), smoothie.id()}
        );
    }

    public Smoothie add(Smoothie smoothie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            """
                                    INSERT INTO SMOOTHIES (name, img, nutrition)
                                    VALUES(?,?,?)
                                        """,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, smoothie.name());
                    ps.setString(2, smoothie.img());
                    ps.setString(3, smoothie.nutrition());
                    return ps;
                },
                keyHolder
        );

        return this.getById(keyHolder.getKey().toString()).orElseThrow();
    }

    public void delete(Smoothie smoothie) {

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM SMOOTHIES WHERE id =?");
            ps.setString(1, smoothie.id());
            return ps;
        });
    }

    private RowMapper<Smoothie> getRowMapper() {
        return (ResultSet rs, int rowNum) -> new Smoothie(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("img"),
                rs.getString("nutrition")
        );
    }

}
